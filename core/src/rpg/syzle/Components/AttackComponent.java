package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by joe on 1/2/17.
 */

public class AttackComponent implements Component, Poolable {
    public float attackDamage = 1.0f;
    public float attackSpeed = 1.0f;
    public boolean attacking = false;

    @Override
    public void reset() {
        attackDamage = 1.0f;
        attackSpeed = 1.0f;
        attacking = false;
    }
}
