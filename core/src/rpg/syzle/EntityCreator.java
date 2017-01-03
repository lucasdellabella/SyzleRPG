package rpg.syzle;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import rpg.syzle.Components.AttackComponent;
import rpg.syzle.Components.AttackPatternComponent;
import rpg.syzle.Components.BoundsComponent;
import rpg.syzle.Components.HealthComponent;
import rpg.syzle.Components.MovementComponent;
import rpg.syzle.Components.MovementPatternComponent;
import rpg.syzle.Components.PlayerComponent;
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

        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        boundsComponent.rectangle = new Rectangle(0, 0,
                playerComponent.WIDTH, playerComponent.HEIGHT);
        textureComponent.region = new TextureRegion(new Texture(Gdx.files.internal("harold.jpg")));

        player.add(healthComponent);
        player.add(boundsComponent);
        player.add(movementComponent);
        player.add(attackComponent);
        player.add(textureComponent);
        player.add(playerComponent);

        return player;
    }


    public Entity createEnemy() {
        Entity enemy = engine.createEntity();

        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        MovementPatternComponent movementPatternComponent = engine.createComponent(MovementPatternComponent.class);
        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        AttackPatternComponent attackPatternComponent = engine.createComponent(AttackPatternComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

        enemy.add(healthComponent);
        enemy.add(movementComponent);
        enemy.add(movementPatternComponent);
        enemy.add(attackComponent);
        enemy.add(attackPatternComponent);
        enemy.add(boundsComponent);
        enemy.add(textureComponent);

        return enemy;
    }

    public void createBullet() {
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
