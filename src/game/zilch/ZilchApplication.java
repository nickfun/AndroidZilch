package game.zilch;

import android.app.Application;
/**
 * Android Application. Deprecated?
 * @author nick
 *
 */
public class ZilchApplication extends Application {
	private static ZilchApplication singleton;
	
	// returns application instance
	public static ZilchApplication getInstance() {
		return singleton;
	}
	
	// Entry point to application?
	@Override
	public final void onCreate() {
		super.onCreate();
		singleton = this;
	}
}
