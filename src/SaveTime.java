import java.util.Timer;
import java.util.TimerTask;



public class SaveTime extends Listener {
	Timer timer;
	
	public SaveTime(int seconds) {
        timer = new Timer();
        timer.schedule(new SaveTimeTask(), seconds*1000);
	}
	class SaveTimeTask extends TimerTask {
        public void run() {
        	etc.getServer().messageAll(defaultsavepremessage);
            etc.getServer().useConsoleCommand("save-all");
            try{
          	  Thread.sleep(10000); 
            }
            	catch(InterruptedException e){
            	     e.printStackTrace();
            	}
            
            etc.getServer().messageAll(defaultsavepostmessage);
            
            timer.cancel(); //Stop the timer thread
        }
	
}

}