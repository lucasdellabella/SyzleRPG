package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class BoundsComponent implements Component, Poolable {
    public Rectangle rectangle = new Rectangle();

    @Override
    public void reset() {
        rectangle.setPosition(0, 0);
        rectangle.setSize(0, 0);
    }
}
