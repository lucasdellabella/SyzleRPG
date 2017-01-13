package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/7/17.
 */
public class RoomComponent implements Component, Poolable {

    final int MIN_SIZE = 4;
    final int MAX_SIZE = 25;

    @Override
    public void reset() {

    }
}
