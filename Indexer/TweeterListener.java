import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import twitter4j.Status;
import twitter4j.User;

class TweeterListener extends StatusListenerTrash{

	private IndexWriter indexWriter;
	private int tweets_counter;
	private PrintWriter writer;
	
	TweeterListener() throws IOException {
		tweets_counter = 0;
		indexWriter = new IndexWriter(FSDirectory.open(new File("tweets_index")), new StandardAnalyzer(Version.LUCENE_30),
									  true, MaxFieldLength.UNLIMITED);
		writer = new PrintWriter(new BufferedWriter(new FileWriter("tweets.txt")));
	}
	
	@Override
	public void onStatus(Status status) {			
		String text = status.getText();
		if(!text.contains("http") && !text.contains("RT")) {
			tweets_counter++;
			if (tweets_counter > 10) {
				try {
					System.out.println(indexWriter.numDocs());
					indexWriter.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		
			User user = status.getUser();
			String location = user.getLocation();
			if(location == null)
				location = "uknown";
			String name = user.getScreenName();
			Date date = status.getCreatedAt();
			
			System.out.println(tweets_counter + "|" + location + "|" + name + "|" + date + "|" + text);
			writer.println(tweets_counter + "|" + location + "|" + name + "|" + date + "|" + text);
			
			Document document = new Document();
			document.add(new Field("location", location, Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("user", name, Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
			document.add(new Field("date", date.toString(), Field.Store.YES, Field.Index.NO));
			
			try {
				indexWriter.addDocument(document);
				System.out.println("Tweet added");
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}
