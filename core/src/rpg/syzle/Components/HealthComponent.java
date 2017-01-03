package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class HealthComponent implements Component, Poolable {
    public int hp = Integer.MIN_VALUE;

    @Override
    public void reset() {
        hp = Integer.MIN_VALUE;
    }
}
