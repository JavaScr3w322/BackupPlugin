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
			etc.getServer().messageAll(defaultbackupmessage);
			try{
		     	  Thread.sleep(10000); //wait 10 seconds
			}
		       	catch(InterruptedException e){
		       	     e.printStackTrace();
		       	}
			Backup(null);
			etc.getServer().messageAll(defaultbackuppostmessage);
		new BackupTime(backuptime);
			timer.cancel();
			
		}
		
}
}