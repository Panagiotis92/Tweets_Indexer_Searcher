import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

class StatusListenerTrash implements StatusListener {

	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO Auto-generated method stub
		
	}

}
