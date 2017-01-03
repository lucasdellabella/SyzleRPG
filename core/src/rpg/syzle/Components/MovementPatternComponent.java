package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.Pool.Poolable;
import rpg.syzle.Constants.MOVEMENT_PATTERN;

/**
 * Created by joe on 1/2/17.
 */

public class MovementPatternComponent implements Component, Poolable {
    public Array<MOVEMENT_PATTERN> attackPatterns = new Array<MOVEMENT_PATTERN>();

    @Override
    public void reset() {
        attackPatterns.clear();
    }
}
