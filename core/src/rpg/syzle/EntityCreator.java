package rpg.syzle;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.omg.CORBA.Bounds;
import rpg.syzle.Components.*;
import rpg.syzle.Model.Room;

import static rpg.syzle.DungeonConstants.*;
import static rpg.syzle.WallType.*;

/**
 * Created by joe on 1/2/17.
 */

public class EntityCreator {

    private PooledEngine engine;
    private TextureHolder textureHolder;
    private ComponentMapper<BoundsComponent> boundsM;
    private ComponentMapper<TransformComponent> transformM;
    private Array<Entity> rooms;

    public EntityCreator(PooledEngine engine) {
        this.engine = engine;
        this.textureHolder = new TextureHolder();
        this.boundsM = ComponentMapper.getFor(BoundsComponent.class);
        this.transformM = ComponentMapper.getFor(TransformComponent.class);
        this.rooms = new Array<>();
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

        textureComponent.region.setRegion(new Texture(Gdx.files.internal("harambe.jpg")));
        boundsComponent.addHitbox(0,
                0,
                textureComponent.region.getRegionWidth(),
                textureComponent.region.getRegionHeight());
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
     * Attempts to create a room within the given map
     * @param mapWidth the coordinate width in which the room can be generated
     * @param mapHeight the coordinate height in which the room can be generated
     * @return the created room entity. NULL if all retryAttempts failed.
     */
    public Entity createRoom(int mapWidth, int mapHeight) {
        int retryAttempts = 10;
        Entity room = null;
        for (int i = 0; room == null && i < retryAttempts; i++) {
            int w = MathUtils.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
            int h = MathUtils.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
            int x = MathUtils.random(mapWidth - w - 1) * 32;
            int y = MathUtils.random(mapHeight - h - 1) * 32;

            if (overlapsNoOtherRoom(x, y, w, h)) {
                room = createRoom(x, y, w, h);
                rooms.add(room);
            }
        }
        return room;
    }

    /**
     * Checks whether the given coordinates for a room will not overlap any other room
     * @param x the x coordinate of the room
     * @param y the y coordinate of the room
     * @param width the width of the room
     * @param height the height of the room
     * @return true if the coordinates overlap no other room, false otherwise
     */
    private boolean overlapsNoOtherRoom(int x, int y, int width, int height) {
        // finds whether our room intersects with any other room
        boolean intersection = false;
        Rectangle roomBounds = new Rectangle(x, y, width * 32, height * 32);
        Rectangle otherBounds;
        for (Entity otherRoom: rooms) {
            otherBounds = boundsM.get(otherRoom).getBoundingRectangle();
            otherBounds.setPosition(transformM.get(otherRoom).translate);
            if (roomBounds.overlaps(otherBounds)) {
                intersection = true;
                break;
            }
        }

        return !intersection;
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

        int pixelRoomWidth = width * tileWidth;
        int pixelRoomHeight = height * tileHeight;

        // TODO: write down formula for wall
        // Set hitboxes for all walls, must shift walls forward by origin
        // - 1 to set the hitboxes to the nth index represented by height or width
        // North wall hitbox
        boundsComponent.addHitbox(pixelRoomWidth/4,
                pixelRoomHeight/2 - tileHeight/4,
                pixelRoomWidth,
                tileHeight);
        // South wall hitbox
        boundsComponent.addHitbox(pixelRoomWidth/4,
                tileHeight/4,
                pixelRoomWidth,
                tileHeight);
        // East wall hitbox
        boundsComponent.addHitbox(pixelRoomWidth/2 - tileWidth/4,
                pixelRoomHeight/4,
                tileWidth,
                pixelRoomHeight);
        // West wall hitbox
        boundsComponent.addHitbox(tileWidth/4,
                pixelRoomHeight/4,
                tileWidth,
                pixelRoomHeight);

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
