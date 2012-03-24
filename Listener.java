
import java.io.IOException;

import java.util.logging.Logger;
import java.util.Timer;
import java.util.TimerTask;


 public class Listener extends PluginListener  {
 Timer timer;
 
 
	 
 
	 
	 
	 Server srv = etc.getServer();
	 private Logger log = Logger.getLogger("Minecraft");
	 
	 protected PropertiesFile config;
	 
	 static String defaultsavepremessage="千[Server] Saving Map...";
	 private static String defaultbackupmessage="千[Server] Backing up Map...";	 
	 private static String defaultrestartpremessage="千[Server] Restarting Server in 10 Seconds...";
	 private static String defaultrestartpostmessage="千[Server] Restarting Server...";
	 private static String defaultautosavesetting="true";
	 private static String defaultautobackupsetting="true";
	 static String defaultsavepostmessage="千[Server] Save Complete.";
	 private static String defaultbackuppostmessage="千[Server] Backup Complete.";
	 private void Properties() {
		 
		 config = new PropertiesFile("backupplugin.properties");

		 //contents of properies file will be
		 //default-save-message="the special save message"
		 //
		
		 defaultsavepremessage = config.getString("pre-save-message", defaultsavepremessage);
		 defaultsavepostmessage= config.getString("post-save-message", defaultsavepostmessage);
		 defaultbackupmessage = config.getString("backup-pre-message", defaultbackupmessage);
		 defaultbackuppostmessage = config.getString("backup-post-message", defaultbackuppostmessage);
		 defaultrestartpremessage = config.getString("pre-restart-message", defaultrestartpremessage);
		 defaultrestartpostmessage = config.getString("post-restart-message", defaultrestartpostmessage);   		   
	 	 defaultautosavesetting = config.getString("Auto-Save", defaultautosavesetting);
	 	 defaultautobackupsetting = config.getString("Auto-Backup", defaultautobackupsetting);
	 }
	
		
	
	 
	 
	 
		 
	
	public void ifautobackup() {
		//if defaultautobackupsetting is true, new BackupTime(1800);
		//if false, do nothing.
	}
	 public void ifautosave() {
		 //if defaultautosavesetting is true, new ActionTime(1800);
		 //if false, do nothing.
	 }
	 
	public Listener() {
		 Properties();
	 }
	 
	 public boolean Backup(Player p) {
	   //launch shell script
	   try {
		   Runtime r = Runtime.getRuntime();
		   r.exec("./Backup.sh");
	   } catch (IOException e) {
			p.sendMessage("千Execution of backup script failed.");
			return false;
	   }
	   
	   return true;
   }
	
	 
	 public void autosave() {
		new ActionTime(0);
		 }
		
	 public boolean onCommand(Player p, String[] cmd)
	 {
		 
		 if ((cmd[0].equals("/backup")) && (p.canUseCommand("/backup")))
     		{	
	    	 	new BackupTime(0);
	    	 	return true;
     		}	
	     
	     	if ((cmd[0].equals("/save")) && (p.canUseCommand("/backup")))
	     	{
	     		new SaveTime(0);
	     		return true;
	     	}

	     	if ((cmd[0].equals("/autosave")) && (p.canUseCommand("/backup")))
	     	{
	     		autosave();
	     		p.sendMessage("勺Auto-Saving has been enabled manually in-game.");
	     		p.sendMessage("勺(To set autosaving permanently, edit backupplugin.properties)");
	     		return true;
	     	}

	     	if ((cmd[0].equals("/restart")) && (p.canUseCommand("/backup")))
	     	{
	     		etc.getServer().messageAll(defaultrestartpremessage);
	     		etc.getServer().useConsoleCommand("save-all");
	     		etc.getServer().useConsoleCommand("stop");
	     		try{
	     			Thread.sleep(10000); 
	     		}
	     		catch(InterruptedException e){
	     			e.printStackTrace();
	     		}
       
	     		etc.getServer().messageAll("千" +defaultrestartpostmessage);
	     		return true;
	     	}

	     	if ((cmd[0].equals("/autobackup")) && (p.canUseCommand("/backup")))
	     	{
	     		p.sendMessage("勺Auto-Backups has been enabled manually in game.");
	     		p.sendMessage("勺(To set Auto-Backups permanently, edit backupplugin.properties)");
	     		new BackupTime(0);
	     		return true; 
	     		
	     	}
	 return false;
	 }
 }
 

 
 
 
 
 
 
 