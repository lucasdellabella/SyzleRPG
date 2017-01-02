package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class MovementComponent implements Component {
    public int moveSpeed = 1;
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();
}
