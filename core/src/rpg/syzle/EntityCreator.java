package rpg.syzle;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.math.Vector2;
import rpg.syzle.Components.*;

/**
 * Created by joe on 1/2/17.
 */

public class EntityCreator {

    PooledEngine engine;
    ComponentMapper<TransformComponent> transformM;

    public EntityCreator(PooledEngine engine) {
        this.engine = engine;
    }

    public Entity createPlayer() {
        Entity player = engine.createEntity();

        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

        boundsComponent.rectangle.setSize(playerComponent.WIDTH, playerComponent.HEIGHT);
        textureComponent.region.setRegion(new Texture(Gdx.files.internal("harold.jpg")));
        movementComponent.moveSpeed = playerComponent.MOVE_SPEED;
        healthComponent.hp = 5;

        player.add(attackComponent);
        player.add(boundsComponent);
        player.add(healthComponent);
        player.add(movementComponent);
        player.add(playerComponent);
        player.add(textureComponent);
        player.add(transformComponent);

        engine.addEntity(player);

        return player;
    }


    public Entity createEnemy() {
        Entity enemy = engine.createEntity();

        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        AttackPatternComponent attackPatternComponent = engine.createComponent(AttackPatternComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        MovementPatternComponent movementPatternComponent = engine.createComponent(MovementPatternComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

        boundsComponent.rectangle.setSize(64, 64);
        textureComponent.region.setRegion(new Texture(Gdx.files.internal("harambe.jpg")));
        movementComponent.moveSpeed = 80;
        healthComponent.hp = 10;

        enemy.add(attackComponent);
        enemy.add(attackPatternComponent);
        enemy.add(boundsComponent);
        enemy.add(healthComponent);
        enemy.add(movementComponent);
        enemy.add(movementPatternComponent);
        enemy.add(textureComponent);
        enemy.add(transformComponent);

        engine.addEntity(enemy);

        return enemy;
    }

    public Entity createBullet(Entity owner, Vector2 startPos, Vector2 direction, float speed) {

        Entity bullet = engine.createEntity();

        // Grab components
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        BulletComponent bulletComponent = engine.createComponent(BulletComponent.class);
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

        // TODO: We shouldn't have duplicate data, because it becomes hard to manage. Ask joe!

        // Set component values
        bulletComponent.owner = owner;
        movementComponent.velocity.x = direction.x * speed;
        movementComponent.velocity.y = direction.y * speed;
        textureComponent.region.setRegion(new Texture(Gdx.files.internal("bullet.png")));
        transformComponent.pos.set(startPos.x, startPos.y);
        transformComponent.scale.set(0.1f, 0.1f);
        transformComponent.rotation = direction.angle() - 90;

        // set bounds relative to transform and texture component.
        boundsComponent.rectangle.set(startPos.x,
                startPos.y,
                textureComponent.region.getRegionWidth() * transformComponent.scale.x,
                textureComponent.region.getRegionHeight() * transformComponent.scale.y);

        // Add components to entity
        bullet.add(boundsComponent);
        bullet.add(bulletComponent);
        bullet.add(movementComponent);
        bullet.add(textureComponent);
        bullet.add(transformComponent);

        engine.addEntity(bullet);

        return bullet;
    }
}
