package rpg.syzle;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Vector2;

import rpg.syzle.Components.*;

/**
 * Created by joe on 1/2/17.
 */

public class EntityCreator {

    PooledEngine engine;

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

        setBounds(boundsComponent, playerComponent.WIDTH, playerComponent.HEIGHT);
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
        transformComponent.translate.set(startPos.x, startPos.y);
        transformComponent.scale.set(0.1f, 0.1f);
        transformComponent.rotation = direction.angle() - 90;

        // set bounds relative to transform and texture component.
        setBounds(boundsComponent, textureComponent.region.getRegionWidth(),
                textureComponent.region.getRegionHeight(), startPos);
        // Add components to entity
        bullet.add(boundsComponent);
        bullet.add(bulletComponent);
        bullet.add(movementComponent);
        bullet.add(textureComponent);
        bullet.add(transformComponent);

        engine.addEntity(bullet);

        return bullet;
    }

    private void setBounds(BoundsComponent boundsComponent, float width, float height) {
        boundsComponent.bounds.setVertices(
                new float[]{0, 0, 0, width, height, 0, width, height});
    }

    private void setBounds(BoundsComponent boundsComponent,
                           float width, float height, Vector2 startPos) {
        setBounds(boundsComponent, width, height);
        boundsComponent.bounds.setPosition(startPos.x, startPos.y);
    }
}
