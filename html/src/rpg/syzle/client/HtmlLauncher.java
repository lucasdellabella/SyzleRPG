package rpg.syzle.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import rpg.syzle.SyzleRPG;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
            return new GwtApplicationConfiguration(800, 480);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new SyzleRPG();
        }
}