package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by joe on 1/2/17.
 */

public class TextureComponent implements Component, Poolable {
    public TextureRegion region = new TextureRegion();

    @Override
    public void reset() {
        region = new TextureRegion();
    }
}
