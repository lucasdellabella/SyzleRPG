package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/7/17.
 */
public class TileComponent implements Component, Poolable {
    public TextureRegion[][] tileMatrix = new TextureRegion[3][3];
    public int width = 1;
    public int height = 1;

    @Override
    public void reset() {
        // Clean out tile matrix
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                tileMatrix[i][j] = null;
            }
        }
        width = 1;
        height = 1;
    }
}

