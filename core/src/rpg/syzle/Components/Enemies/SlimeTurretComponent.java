package rpg.syzle.Components.Enemies;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import rpg.syzle.Constants.MOVEMENT_PATTERN;

import static rpg.syzle.Constants.MOVEMENT_PATTERN.STAND_STILL;

/**
 * Created by joe on 1/7/17.
 */

public class SlimeTurretComponent implements Component, Pool.Poolable {
    public final int WIDTH = 64;
    public final int HEIGHT = 64;
    public final int HP = 10;
    public final int MOVE_SPEED = 80;
    public final String TEXTURE_PATH = "harambe.jpg";
    public final MOVEMENT_PATTERN MOVE_PATTERN = STAND_STILL;

    @Override
    public void reset() {
    }
}
