package rpg.syzle;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import rpg.syzle.Components.BoundsComponent;
import rpg.syzle.Components.MovementComponent;
import rpg.syzle.Components.TextureComponent;

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

    public Void createBullet() {
        Entity bullet = engine.createEntity();

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

        // SET STATE OF COMPONENTS

        bullet.add(movementComponent);
        bullet.add(boundsComponent);
        bullet.add(textureComponent);

        engine.addEntity(bullet);
    }
}
