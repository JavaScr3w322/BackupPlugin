import java.util.Timer;
import java.util.TimerTask;



public class BackupTime extends Listener {
	Timer timer;
	
	
	public BackupTime(int seconds) {
		timer = new Timer();
		timer.schedule(new BackupTask(), seconds*1000);
	}
	class BackupTask extends TimerTask {
		public void run() {
			etc.getServer().messageAll("¤d[Server] Backing up Map...");
			try{
		     	  Thread.sleep(10000); //wait 10 seconds
			}
		       	catch(InterruptedException e){
		       	     e.printStackTrace();
		       	}
			Backup(null);
			etc.getServer().messageAll("¤d[Server] Backup Complete.");
		new BackupTime(1800);
			timer.cancel();
			
		}
		
}
}