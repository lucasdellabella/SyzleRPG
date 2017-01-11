package rpg.syzle;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import rpg.syzle.Components.*;

import static rpg.syzle.DungeonConstants.SCREEN_HEIGHT;
import static rpg.syzle.DungeonConstants.SCREEN_WIDTH;
import static rpg.syzle.WallType.*;

/**
 * Created by joe on 1/2/17.
 */

public class EntityCreator {

    PooledEngine engine;
    TextureHolder textureHolder;
    ComponentMapper<TransformComponent> transformM;

    public EntityCreator(PooledEngine engine) {
        this.engine = engine;
        this.textureHolder = new TextureHolder();
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

        boundsComponent.addHitbox(0, 0, playerComponent.WIDTH, playerComponent.HEIGHT);
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

        boundsComponent.addHitbox(0, 0, 64, 64);
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
        transformComponent.translate.set(startPos.x, startPos.y);
        transformComponent.scale.set(0.1f, 0.1f);
        transformComponent.rotation = direction.angle() - 90;
        // set hitboxes relative to transform and texture component.
        boundsComponent.addHitbox(0,
                0,
                textureComponent.region.getRegionWidth(),
                textureComponent.region.getRegionHeight());

        // Add components to entity
        bullet.add(boundsComponent);
        bullet.add(bulletComponent);
        bullet.add(movementComponent);
        bullet.add(textureComponent);
        bullet.add(transformComponent);

        engine.addEntity(bullet);

        return bullet;
    }

    /**
     * Create a room entity
     * TODO: Make all arguments in number of tiles?
     * @param xPos the xPos in pixels
     * @param yPos the yPos in pixels
     * @param width the width in number of tiles
     * @param height the height in number of tiles
     * @return
     */
    public Entity createRoom(int xPos, int yPos, int width, int height) {

        Entity room = engine.createEntity();

        final int tileWidth = 32;
        final int tileHeight = 32;

        // Create Components
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        TileComponent tileComponent = engine.createComponent(TileComponent.class);
        RoomComponent roomComponent = engine.createComponent(RoomComponent.class);

        // Set component values
        transformComponent.translate.set(xPos, yPos);
        tileComponent.tileMatrix[0][0] = textureHolder.getWall(NORTH_WEST);
        tileComponent.tileMatrix[0][1] = textureHolder.getWall(NORTH);
        tileComponent.tileMatrix[0][2] = textureHolder.getWall(NORTH_EAST);
        tileComponent.tileMatrix[1][0] = textureHolder.getWall(WEST);
        tileComponent.tileMatrix[1][1] = textureHolder.getWall(FLOOR);
        tileComponent.tileMatrix[1][2] = textureHolder.getWall(EAST);
        tileComponent.tileMatrix[2][0] = textureHolder.getWall(SOUTH_WEST);
        tileComponent.tileMatrix[2][1] = textureHolder.getWall(SOUTH);
        tileComponent.tileMatrix[2][2] = textureHolder.getWall(SOUTH_EAST);
        tileComponent.width = width;
        tileComponent.height = height;

        // Set hitboxes for all walls:
        // - 1 to set the hitboxes to the nth index represented by height or width
        // North wall hitbox
        boundsComponent.addHitbox(0, (height - 1) * tileHeight, width * tileWidth, 1 * tileHeight);
        // South wall hitbox
        boundsComponent.addHitbox(0, 0, width * tileWidth, 1 * tileHeight);
        // East wall hitbox
        boundsComponent.addHitbox((width - 1) * tileWidth, 0, 1 * tileWidth, height * tileHeight);
        // West wall hitbox
        boundsComponent.addHitbox(0, 0, 1 * tileWidth, height * tileHeight);

        // Add Components to entity
        room.add(boundsComponent);
        room.add(transformComponent);
        room.add(roomComponent);
        room.add(tileComponent);

        engine.addEntity(room);

        return room;
    }

    public Entity createCamera(Entity target) {

        Entity camera = engine.createEntity();

        CameraComponent cameraComponent = engine.createComponent(CameraComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

        // Set up camera state
        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        Viewport vp = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, cam);
        vp.apply();
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);

        // Set camera component values
        cameraComponent.camera = cam;
        cameraComponent.viewport = vp;
        cameraComponent.target = target;

        camera.add(cameraComponent);
        camera.add(transformComponent);

        engine.addEntity(camera);

        return camera;

    }
}
