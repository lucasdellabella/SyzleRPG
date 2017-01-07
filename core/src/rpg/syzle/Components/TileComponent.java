package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/7/17.
 */
public class TileComponent implements Component, Poolable {
    public TextureRegion tile = new TextureRegion();

    @Override
    public void reset() {
        tile = new TextureRegion();
    }
}

