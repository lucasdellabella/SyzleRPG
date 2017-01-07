package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.utils.Pool.Poolable;
import rpg.syzle.Constants.MOVEMENT_PATTERN;

/**
 * Created by joe on 1/2/17.
 */

public class MovementPatternComponent implements Component, Poolable {
    public MOVEMENT_PATTERN movementPattern = MOVEMENT_PATTERN.STAND_STILL;

    @Override
    public void reset() {
        movementPattern = MOVEMENT_PATTERN.STAND_STILL;
    }
}
