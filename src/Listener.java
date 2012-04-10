
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	static String defaultbackuptime="3600";
	static String defaultsavetime="1740";
	static String defaultrestarttime="10780";
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

	public void writeBP() {
		String loc = new String("BackupPlugin.sh");              		
		if (new File(loc).exists()) {
			
		}else {
			log.info("[BackupPlugin] Warning: BackupPlugin.sh does not exist.");
			log.info("[BackupPlugin] Writing BackupPlugin.sh...");
			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(loc, false));
				StringBuilder builder = new StringBuilder();

				builder.append("#!/bin/sh" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("while true; do" + System.getProperty("line.separator"));
				builder.append("	java -Xms1024M -Xmx1024M -jar CanaryMod.jar nogui" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("	echo 'Starting Backup Script.'" + System.getProperty("line.separator"));
				builder.append("	echo '(Press control+c if you would like to stop the server.)'" + System.getProperty("line.separator"));
				builder.append("	sleep 5" + System.getProperty("line.separator"));
				builder.append("	echo 'STARTING BACKUP.'" + System.getProperty("line.separator"));
				builder.append("	sleep 5" + System.getProperty("line.separator"));
				builder.append("	echo '*************************************'" + System.getProperty("line.separator"));
				builder.append("	echo '**          BACKING UP MAP         **'" + System.getProperty("line.separator"));
				builder.append("	echo '*************************************'" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("	if [ ! -d backups ]; then" + System.getProperty("line.separator"));
				builder.append("		mkdir backup;" + System.getProperty("line.separator"));
				builder.append("	fi" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("		name=backup/world-`date '+%m-%d-%Y-%H-%M-%S'`" + System.getProperty("line.separator"));
				builder.append("		zip -r $name.zip world" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("	echo '-------------------------------------'" + System.getProperty("line.separator"));
				builder.append("	echo" + System.getProperty("line.separator"));
				builder.append("	echo 'BACKUP COMPLETE. RESTARTING SERVER...'" + System.getProperty("line.separator"));
				builder.append("	echo" + System.getProperty("line.separator"));
				builder.append("	echo '-------------------------------------'" + System.getProperty("line.separator"));
				builder.append("	sleep 2" + System.getProperty("line.separator"));
				builder.append("done" + System.getProperty("line.separator"));

				bw.append(builder.toString());
				bw.newLine();

				bw.close();
				Runtime.getRuntime().exec("chmod u+x BackupPlugin.sh");
				log.info("[BackupPlugin] BackupPlugin.sh Written Successfully.");
			} catch (Exception e) {
				log.info("[BackupPlugin] Exception while writing missing file: BackupPlugin.sh");
			}

		}
	}
	
	public void writeB() {
		String loc = new String("Backup.sh");
		if (new File(loc).exists()) {

		}else {
			log.info("[BackupPlugin] Warning: Backup.sh does not exist.");
			log.info("[BackupPlugin] Writing Backup.sh...");
			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(loc, false));
				StringBuilder builder = new StringBuilder();
				
				builder.append("#!/bin/sh" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("if [ ! -d backups ]; then" + System.getProperty("line.separator"));
				builder.append("mkdir backups;" + System.getProperty("line.separator"));
				builder.append("	fi" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("	name=backups/world-`date '+%m-%d-%Y-%H-%M-%S'`" + System.getProperty("line.separator"));
				builder.append("	zip -r $name.zip world" + System.getProperty("line.separator"));
				
				bw.append(builder.toString());
				bw.newLine();

				bw.close();
				Runtime.getRuntime().exec("chmod u+x Backup.sh");
				log.info("[BackupPlugin] Backup.sh Written Successfully.");

			} catch (Exception e) {
				log.info("[BackupPlugin] Exception while writing missing file: Backup.sh");
			}
		}

	}
	
	public void writeBP_NB() {
		String loc = new String("BackupPlugin_nobackup.sh");
		if (new File(loc).exists()) {
			
		}else {
			log.info("[BackupPlugin] Warning: BackupPlugin_nobackup.sh does not exist.");
			log.info("[BackupPlugin] Writing BackupPlugin_nobackup.sh...");
		
			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(loc, false));
				StringBuilder builder = new StringBuilder();
			
				builder.append("#!/bin/sh" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("while true; do" + System.getProperty("line.separator"));
				builder.append("	java -Xms1024M -Xmx1024M -jar CanaryMod.jar nogui" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("	echo 'Starting Restart Script.'" + System.getProperty("line.separator"));
				builder.append("	echo '(Press control+c if you would like to stop the server.)'" + System.getProperty("line.separator"));
				builder.append("	sleep 5" + System.getProperty("line.separator"));
				builder.append("	echo 'Retarting Server...'" + System.getProperty("line.separator"));
				builder.append("	sleep 2" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				builder.append("done");
				
				bw.append(builder.toString());
				bw.newLine();

				bw.close();
				Runtime.getRuntime().exec("chmod u+x BackupPlugin_nobackup.sh");
				log.info("[BackupPlugin] BackupPlugin_nobackup.sh Written Successfully.");
			
			}catch (Exception e) {
				log.info("[BackupPlugin] Exception while writing missing file: Backup.sh");
			}
		
		}
	}

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

	public boolean onCommand(Player p, String[] split)
	{

		if ((split[0].equalsIgnoreCase("/backup")) && (p.canUseCommand("/backup")))
		{	
			new BackupTime(0);
			return true;
		}	

		if ((split[0].equalsIgnoreCase("/save")) && (p.canUseCommand("/backup")))
		{
			new SaveTime(0);
			return true;
		}

		if ((split[0].equalsIgnoreCase("/autosave")) || ((split[0].equalsIgnoreCase("/as"))) && (p.canUseCommand("/backup")))
		{
			autosave();
			p.sendMessage("勺Auto-Saving has been enabled manually in-game.");
			p.sendMessage("勺(To set autosaving permanently, edit backupplugin.properties)");
			return true;
		}

		if ((split[0].equalsIgnoreCase("/restart")) && (p.canUseCommand("/backup")))
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

		if ((split[0].equalsIgnoreCase("/autobackup")) || ((split[0].equalsIgnoreCase("/ab"))) && (p.canUseCommand("/backup")))
		{
			p.sendMessage("勺Auto-Backups has been enabled manually in game.");
			p.sendMessage("勺(To set Auto-Backups permanently, edit backupplugin.properties)");
			new BackupTime(0);
			return true; 
		}
		if ((split[0].equalsIgnoreCase("/autorestart")) || ((split[0].equalsIgnoreCase("/ar"))) && (p.canUseCommand("/backup")))
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








