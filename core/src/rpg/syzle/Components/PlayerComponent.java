package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by joe on 1/3/17.
 */

public class PlayerComponent implements Component, Poolable {
    public final int WIDTH = 64;
    public final int HEIGHT = 64;
    public int MOVE_SPEED = 150;
    public Vector2 fireCoords = new Vector2();

    @Override
    public void reset() {
        fireCoords.set(0, 0);
    }
}
