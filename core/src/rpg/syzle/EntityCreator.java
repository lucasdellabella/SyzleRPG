package rpg.syzle;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

/**
 * Created by joe on 1/2/17.
 */

public class EntityCreator {

    PooledEngine engine;

    public EntityCreator(PooledEngine engine) {
        this.engine = engine;
    }

    // TODO: add components to player
    public Entity createPlayer() {
        Entity player = engine.createEntity();
        return player;
    }

    public Entity createEnemy() {
        Entity enemy = engine.createEntity();
        return enemy;
    }

    public Entity createBullet() {
        Entity bullet = engine.createEntity();
        return bullet;
    }
}
