import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ActionTime extends Listener  {
	Timer timer;
	
	public ActionTime(int seconds) {
        timer = new Timer();
        timer.schedule(new SaveTask(), seconds*1000);
	}

	class SaveTask extends TimerTask {
        public void run() {
        	etc.getServer().messageAll("¤d[Server] Saving Map...");
            etc.getServer().useConsoleCommand("save-all");
            try{
          	  Thread.sleep(10000); 
            }
            	catch(InterruptedException e){
            	     e.printStackTrace();
            	}
            
            etc.getServer().messageAll("¤d[Server] Save Complete.");
            new ActionTime(1800);
            timer.cancel(); //Stop the timer thread
        }
    }
	
	
	
	
	
}



	

	




