package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.Pool.Poolable;
import rpg.syzle.Constants.ATTACK_PATTERN;

/**
 * Created by joe on 1/2/17.
 */

public class AttackPatternComponent implements Component, Poolable {
    public Array<ATTACK_PATTERN> attackPatterns = new Array<ATTACK_PATTERN>();
    public int attackPatternTick = 0;

    @Override
    public void reset() {
        attackPatterns.clear();
        attackPatternTick = 0;
    }
}
