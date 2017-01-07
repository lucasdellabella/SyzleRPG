package rpg.syzle.Components.Enemies;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

import rpg.syzle.Constants.MOVEMENT_PATTERN;

import static rpg.syzle.Constants.MOVEMENT_PATTERN.TOWARDS_PLAYER;

/**
 * Created by joe on 1/7/17.
 */

public class SmallBearComponent implements Component, Poolable {
    public final int WIDTH = 75;
    public final int HEIGHT = 78;
    public final int HP = 10;
    public final int MOVE_SPEED = 80;
    public final String TEXTURE_PATH = "harambe.jpg";
    public final MOVEMENT_PATTERN MOVE_PATTERN = TOWARDS_PLAYER;

    @Override
    public void reset() {
    }
}
