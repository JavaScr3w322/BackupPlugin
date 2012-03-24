import java.util.logging.Logger;


	public class BackupPlugin extends Plugin {
		
//		private final Listener listener = new Listener();
		static final Listener l = new Listener();
		private Logger log = Logger.getLogger("Minecraft");
		private String name = "BackupPlugin";
		private String version = "v1.5";
		private String author = "JavaScr3w322";
		
		
		public void enable()
		{
			this.log.info("Initialized " + this.name + " " + this.version + " by " + this.author + "!");
		}
		
		public void disable() 
		{
			this.log.info(this.name + " was disabled");
		}
		
		public void initialize() 
		{
			etc.getLoader().addListener(PluginLoader.Hook.COMMAND, l, this, 
					PluginListener.Priority.MEDIUM);
		}
		
		
	}