package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class MovementComponent implements Component, Poolable {
    public int moveSpeed = 1;
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();

    @Override
    public void reset() {
        moveSpeed = 1;
        velocity.setZero();
        acceleration.setZero();
    }
}
