import javax.swing.SwingUtilities;

class StartingPoint {

	public static void main(String[] args) {
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame mainFrame = new MainFrame();
				MainFrameListener mainFrameListener = new MainFrameListener(mainFrame);
				mainFrame.setListener(mainFrameListener);
				mainFrame.setVisible(true);
			}
		});
	}

}
