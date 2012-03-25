import java.util.Timer;
import java.util.TimerTask;



public class RestartTime extends Listener {
	Timer timer;
	
	
	public RestartTime(int seconds) {
		timer = new Timer();
		timer.schedule(new RestartTask(), seconds*1000);
	}
	class RestartTask extends TimerTask {
		public void run() {
			etc.getServer().messageAll(defaultrestartpremessage);
			try{
		     	  Thread.sleep(10000); //wait 10 seconds
			}
		       	catch(InterruptedException e){
		       	     e.printStackTrace();
		       	}
			etc.getServer().messageAll(defaultrestartpostmessage);
			etc.getServer().useConsoleCommand("save-all");
     		etc.getServer().useConsoleCommand("stop");
			timer.cancel();
			
		}
		
}
}