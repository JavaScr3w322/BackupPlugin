
import java.io.IOException;

import java.util.logging.Logger;
import java.util.Timer;

public class Listener extends PluginListener  {

	 Timer timer;
	 
	 Server srv = etc.getServer();
	 private Logger log = Logger.getLogger("Minecraft");
	 
	 protected PropertiesFile config;
	 
	 static String topline="Save-time, Backup-Time, and Restart-Time are all in seconds.";
	 static String topline2="1800(half-hour), 900(15 minutes), 7200(2 hours)";
	 static String defaultrestartsetting="false";
	 static String defaultsavepremessage="千[Server] Saving Map...";
	 static String defaultbackupmessage="千[Server] Backing up Map...";	 
	 static String defaultrestartpremessage="千[Server] Restarting Server in 10 Seconds...";
	 static String defaultrestartpostmessage="千[Server] Restarting Server...";
	 static String defaultautosavesetting="false";
	 static String defaultautobackupsetting="false";
	 static String defaultsavepostmessage="千[Server] Save Complete.";
	 static String defaultbackuppostmessage="千[Server] Backup Complete.";
	 static String defaultbackuptime="1800";
	 static String defaultsavetime="900";
	 static String defaultrestarttime="7200";
	 static int backuptime;
	 static int savetime;
	 static int restarttime;
	 static boolean autobackupsetting;
	 static boolean autosavesetting;
	 static boolean autorestartsetting;
	 void Properties() {
	
		 config = new PropertiesFile("backupplugin.properties");

		 
		
		 //topline = config.getString("#", topline);
		 //topline2 = config.getString("#", topline2);
		 defaultsavepremessage = config.getString("pre-save-message", defaultsavepremessage);
		 defaultsavepostmessage= config.getString("post-save-message", defaultsavepostmessage);
		 defaultbackupmessage = config.getString("backup-pre-message", defaultbackupmessage);
		 defaultbackuppostmessage = config.getString("backup-post-message", defaultbackuppostmessage);
		 defaultrestartpremessage = config.getString("pre-restart-message", defaultrestartpremessage);
		 defaultrestartpostmessage = config.getString("post-restart-message", defaultrestartpostmessage);   		   
	 	 defaultautosavesetting = config.getString("Auto-Save", defaultautosavesetting);
	 	 defaultautobackupsetting = config.getString("Auto-Backup", defaultautobackupsetting);
	 	 defaultbackuptime = config.getString("Backup-Time(Seconds)", defaultbackuptime);
	 	 defaultsavetime = config.getString("Save-Time(Seconds)", defaultsavetime);
	 	 defaultrestarttime = config.getString("Restart-Time(Seconds)", defaultrestarttime);
	 	 defaultrestartsetting = config.getString("Auto-Restart", defaultrestartsetting);
	 	 
	 	 log.info("[BackupPlugin] AutoSaving=[" + defaultautosavesetting + "]");
	 	 log.info("[BackupPlugin] AutoBackups=[" + defaultautobackupsetting + "]");
	 	 log.info("[BackupPlugin] AutoRestarts=[" + defaultrestartsetting + "]");
	 	 if (defaultrestartsetting.equals("true")) {
	 		 autorestartsetting = true;
	 	 } else {
	 		 autosavesetting = false;
	 	 }
	 	 
	 	 if (defaultautosavesetting.equals("true")) {
	 		 autosavesetting = true;
	 	 } else
	 		 autosavesetting = false;

	 	 if (defaultautobackupsetting.equals("true")) 
	 		 autobackupsetting = true;
	 	 else
	 		 autobackupsetting = false;
	 	 
	 	 savetime = Integer.parseInt(defaultsavetime);
	 	 backuptime = Integer.parseInt(defaultbackuptime);
	 	 restarttime = Integer.parseInt(defaultrestarttime);
	 	 if (savetime == backuptime) {
	 		 log.info("[BackupPlugin] Save-Time is the same as Backup-Time, Save-Time cut in half.");
	 		 savetime=savetime/2;
	 	 
	 	 }
	 	 
	 	 if (savetime < 60) {
	 		 log.info("Save-Time is too small, Save-Time set to 60 seconds.");
	 		 savetime=60;
	 	 }
	 if (backuptime < 120) {
		 log.info("[BackupPlugin] BackupTime is to small, set to 120 seconds.");
		 backuptime=120;
	 }
	 }
	
	 public void ifautorestart() {
		 if (autorestartsetting) {
			 log.info("[BackupPlugin] Set Time Between Restarts is=[" + defaultrestarttime + "]");
		 new RestartTime(restarttime);
		 }
	 }
	
	 public void ifautobackup() {
		
		if (autobackupsetting) {
			log.info("[BackupPlugin] Set Time Between Backups is=[" + defaultbackuptime + "]");
			new BackupTime(backuptime);
		}
	}
	 public void ifautosave() {
		 
		 if (autosavesetting) {
			 log.info("[BackupPlugin] Set Time Between Saves is=[" + defaultsavetime + "]");
			 new ActionTime(savetime);
		 
		 }
	 }
	 
//	public Listener() {
//		log.info("backupplugin::listener called");
//		 Properties();
//	}
	 
	 public boolean Backup(Player p) {
	   
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

	     	if ((cmd[0].equals("/autosave")) || ((cmd[0].equals("/as"))) && (p.canUseCommand("/backup")))
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

	     	if ((cmd[0].equals("/autobackup")) || ((cmd[0].equals("/ab"))) && (p.canUseCommand("/backup")))
	     	{
	     		p.sendMessage("勺Auto-Backups has been enabled manually in game.");
	     		p.sendMessage("勺(To set Auto-Backups permanently, edit backupplugin.properties)");
	     		new BackupTime(0);
	     		return true; 
	     	}
	     	if ((cmd[0].equals("/autorestart")) || ((cmd[0].equals("/ar"))) && (p.canUseCommand("/backup")))
	     	{
	     		p.sendMessage("勺Auto-Restarts has been enabled manually in game.");
	     		p.sendMessage("勺(To set Auto-Backups permanently, edit backupplugin.properties.");
	     		p.sendMessage("勺The Next Auto-Restart will occur in 15 minutes, then every 2 hours.");
	     		new RestartTime(900);
	     		return true;
	     	}
	     	
	     	
	     	return false;
	 }
 }
 

 
 
 
 
 
 
 