package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

import rpg.syzle.Constants.ATTACK_PATTERN;

/**
 * Created by joe on 1/2/17.
 */

public class AttackPatternComponent implements Component {
    public Array<ATTACK_PATTERN> attackPatterns = new Array<ATTACK_PATTERN>();
}
