package rpg.syzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucasdellabella on 1/7/17.
 * TODO: should not have some used for public, some used for private
 */
public enum WallType {
    // FOR PUBLIC USE
    NORTH,
    SOUTH,
    EAST,
    WEST,

    // FOR PRIVATE USE
    NORTH_WEST,
    NORTH_EAST,
    SOUTH_WEST,
    SOUTH_EAST,
    FLOOR;
}
