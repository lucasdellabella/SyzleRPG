package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class BoundsComponent implements Component, Poolable {
    public Polygon bounds = new Polygon();

    @Override
    public void reset() {
        bounds.setPosition(0, 0);
        bounds.setVertices(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
    }
}
