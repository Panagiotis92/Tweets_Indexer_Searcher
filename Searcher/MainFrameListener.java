import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

class MainFrameListener {

	private MainFrame mainFrame;
	private IndexSearcher indexSearcher;
	private Analyzer textAnalyzer, userAnalyzer;
	private QueryParser textParser, userParser;
	
	MainFrameListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		try {
			indexSearcher = new IndexSearcher(FSDirectory.open(new File("tweets_index")));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		textAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
		textParser = new QueryParser(Version.LUCENE_30, "text", textAnalyzer);
		textParser.setDefaultOperator(Operator.AND);
		textParser.setAllowLeadingWildcard(true);
		userAnalyzer = new WhitespaceAnalyzer(); 
		userParser = new QueryParser(Version.LUCENE_30, "user", userAnalyzer);
	}
	
	void search(String text, String user, String location, int maxDocsReturned, boolean orderByDate) 
	throws ParseException, IOException, InvalidTokenOffsetsException {
		BooleanQuery query = new BooleanQuery();
		Highlighter highlighter = null;
		TopDocs collection = null;
		
		if(!text.equals("")) {
			Query textQuery = textParser.parse(text);
			query.add(textQuery, Occur.MUST);
			highlighter = new Highlighter(new QueryScorer(textQuery));
		}
		if(!user.equals(""))
			query.add(userParser.parse(user), Occur.MUST);
		if(!location.equals(""))
			query.add(new TermQuery(new Term("location", location)), Occur.MUST);
		
		if(orderByDate)
			collection = indexSearcher.search(query, null, maxDocsReturned, new Sort(new SortField(null, SortField.DOC, true)));
		else
			collection = indexSearcher.search(query, maxDocsReturned);
		
		mainFrame.display(collection.totalHits);
		for(ScoreDoc scoreDoc : collection.scoreDocs) {
			Document document = indexSearcher.doc(scoreDoc.doc);
			if(!text.equals("")) {
				String highlightedText = highlighter.getBestFragments(textAnalyzer, "text", document.get("text"), 1)[0];
				mainFrame.display(document.get("user"), highlightedText, document.get("location"), document.get("date"));
			}
			else
				mainFrame.display(document.get("user"), document.get("text"), document.get("location"), document.get("date"));
		}
	}
}
