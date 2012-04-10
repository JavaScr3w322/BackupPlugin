import java.util.logging.Logger;


	public class BackupPlugin extends Plugin {
		
//		private final Listener listener = new Listener();
		static final Listener l = new Listener();
		private Logger log = Logger.getLogger("Minecraft");
		private String name = "BackupPlugin";
		private String version = "v1.6";
		//private String author = "JavaScr3w322";
		
		public BackupPlugin() {
			
		}
		
		public void enable()
		{
			this.log.info("|========Loading " + this.name + " " + this.version + "...========|");
			l.writeBP();
			l.writeBP_NB();
			l.writeB();
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
			log.info("|========BackupPlugin " +this.version + " Loaded.===========|");
			etc.getLoader().addListener(PluginLoader.Hook.COMMAND, l, this, 
					PluginListener.Priority.MEDIUM);
		}
		
		
	}