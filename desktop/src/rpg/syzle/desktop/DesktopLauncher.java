package rpg.syzle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import rpg.syzle.SyzleRPG;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Syzle";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new SyzleRPG(), config);
	}
}
