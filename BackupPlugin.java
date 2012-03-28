import java.util.logging.Logger;


	public class BackupPlugin extends Plugin {
		
//		private final Listener listener = new Listener();
		static final Listener l = new Listener();
		private Logger log = Logger.getLogger("Minecraft");
		private String name = "BackupPlugin";
		private String version = "v1.5.2";
		//private String author = "JavaScr3w322";
		
		public BackupPlugin() {
			log.info("BackupPlugin() new");
		}
		
		public void enable()
		{
			this.log.info("Loading " + this.name + " " + this.version + "...");
			l.Properties();
			l.ifautobackup();
			l.ifautosave();
			l.ifautorestart();
		}
		
		public void disable() 
		{
			this.log.info(this.name + this.version + " was disabled");
		}
		
		public void initialize() 
		{
			log.info("[BackupPlugin] Succesfully Loaded.");
			etc.getLoader().addListener(PluginLoader.Hook.COMMAND, l, this, 
					PluginListener.Priority.MEDIUM);
		}
		
		
	}