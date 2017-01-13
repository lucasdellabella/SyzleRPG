package rpg.syzle.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import rpg.syzle.Components.CameraComponent;
import rpg.syzle.Components.TextureComponent;
import rpg.syzle.Components.TileComponent;
import rpg.syzle.Components.TransformComponent;

/**
 * Created by lucasdellabella on 1/3/17.
 * --
 * Works on entities with TextureComponent AND TransformComponent
 */

public class RenderingSystem extends IteratingSystem {

    private final int PIXELS_TO_METERS = 1;
    private final int spriteWidth = 32;
    private final int spriteHeight = 32;

    private SpriteBatch batch;
    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TileComponent> tileM;
    private ComponentMapper<TransformComponent> transformM;
    private Array<Entity> renderQueue;
    private OrthographicCamera camera;

    // name values for readability
    final int WEST = 0;
    final int NORTH = 0;
    final int EAST = 2;
    final int SOUTH = 2;
    final int NEUTRAL = 1;

    public RenderingSystem(SpriteBatch batch, Entity cameraEntity) {
        super(Family.all(TransformComponent.class)
                .one(TextureComponent.class, TileComponent.class)
                .get());

        this.batch = batch;
        textureM = ComponentMapper.getFor(TextureComponent.class);
        tileM = ComponentMapper.getFor(TileComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);
        renderQueue = new Array<>();

        this.camera = cameraEntity.getComponent(CameraComponent.class).camera;
    }

    /**
     * Draws all entities in renderQueue, can draw textureComponent and / or tileComponent
     * @param deltaTime time since last engine.update() call in seconds
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (Entity entity: renderQueue) {
            // render the texture component
            if (textureM.has(entity)) {
                drawTextureComponent(entity);
            }

            // render the tile component
            if (tileM.has(entity)) {
                drawTileComponent(entity);
            }
        }

        renderQueue.clear();
        batch.end();
    }

    /**
     * Draws an entity that has a texture component
     * @param entity the entity to be drawn
     */
    private void drawTextureComponent(Entity entity) {
        TextureComponent textureComponent = textureM.get(entity);

        if (textureComponent.region == null) {
            return;
        }

        TransformComponent t = transformM.get(entity);

        float width = textureComponent.region.getRegionWidth();
        float height = textureComponent.region.getRegionHeight();
        float centerX = width * 0.5f;
        float centerY = height * 0.5f;

        batch.draw(textureComponent.region,
                t.translate.x - centerX, t.translate.y - centerY,
                centerX, centerY,
                width, height,
                t.scale.x, t.scale.y,
                t.rotation);

    }

    /**
     * Draws an entity that has a tile component.
     *
     * Depending on the non-null values in the tileMatrix, just the center tile or the center tile
     * and appropriate cornering and edging tiles will be drawn.
     *
     * See comments below for which tileMatrix set-up are availible.
     * x indicates mandatory value
     * ? indicates optional value
     * . indicates unused value
     *
     * @param entity the entity to be drawn
     */
    private void drawTileComponent(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;

        if (tileMatrix[1][1] == null) {
            return;
        }

        TransformComponent t = transformM.get(entity);

        // full-ish matrix case
        // ? x ?
        // x x x
        // ? x ?
        if (tileMatrix[NORTH][NEUTRAL] != null
                && tileMatrix[SOUTH][NEUTRAL] != null
                && tileMatrix[NEUTRAL][WEST] != null
                && tileMatrix[NEUTRAL][EAST] != null) {
            drawFilling(entity);
            drawNorthEdge(entity);
            drawSouthEdge(entity);
            drawEastEdge(entity);
            drawWestEdge(entity);
            drawCorners(entity);
        }

        // north no-wall case
        // . . .
        // x x x
        // ? x ?
        else if (tileMatrix[SOUTH][NEUTRAL] != null
                && tileMatrix[NEUTRAL][WEST] != null
                && tileMatrix[NEUTRAL][EAST] != null) {
            drawFilling(entity);
            drawSouthEdge(entity);
            drawEastEdge(entity);
            drawWestEdge(entity);
            drawCorners(entity);
        }

        // south no-wall case
        // ? x ?
        // x x x
        // . . .
        else if (tileMatrix[NORTH][NEUTRAL] != null
                && tileMatrix[NEUTRAL][WEST] != null
                && tileMatrix[NEUTRAL][EAST] != null) {
            drawFilling(entity);
            drawNorthEdge(entity);
            drawEastEdge(entity);
            drawWestEdge(entity);
            drawCorners(entity);
        }

        // east no-wall case
        // ? x .
        // x x .
        // ? x .
        else if (tileMatrix[NORTH][NEUTRAL] != null
                && tileMatrix[SOUTH][NEUTRAL] != null
                && tileMatrix[NEUTRAL][WEST] != null) {
            drawFilling(entity);
            drawNorthEdge(entity);
            drawSouthEdge(entity);
            drawWestEdge(entity);
            drawCorners(entity);
        }

        // west no-wall case
        // . x ?
        // . x x
        // . x ?
        else if (tileMatrix[NORTH][NEUTRAL] != null
                && tileMatrix[SOUTH][NEUTRAL] != null
                && tileMatrix[NEUTRAL][EAST] != null) {
            drawFilling(entity);
            drawNorthEdge(entity);
            drawSouthEdge(entity);
            drawEastEdge(entity);
            drawCorners(entity);
        }

        // vertical line case
        // . x .
        // . x .
        // . x .
        else if (tileMatrix[NORTH][NEUTRAL] != null && tileMatrix[SOUTH][NEUTRAL] != null) {
            drawFilling(entity);
            drawNorthEdge(entity);
            drawSouthEdge(entity);
        }

        // horizontal line case
        // . . .
        // x x x
        // . . .
        else if (tileMatrix[NEUTRAL][WEST] != null && tileMatrix[NEUTRAL][EAST] != null) {
            drawFilling(entity);
            drawEastEdge(entity);
            drawWestEdge(entity);
        }

        // only middle case
        // . . .
        // . x .
        // . . .
        else {
            drawFilling(entity);
        }

    }

    private void drawFilling(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        float spriteWidth = tileMatrix[NEUTRAL][NEUTRAL].getRegionWidth();
        float spriteHeight = tileMatrix[NEUTRAL][NEUTRAL].getRegionHeight();

        // draw filling
        for (int i = 0; i < tileComponent.width; i++) {
            for (int j = 0; j < tileComponent.height; j++) {
                batch.draw(tileMatrix[NEUTRAL][NEUTRAL],
                        t.translate.x + spriteWidth * i,
                        t.translate.y + spriteHeight * j,
                        spriteWidth,
                        spriteHeight);
            }
        }

    }
    private void drawNorthEdge(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        //draw north edge
        for (int i = 0; i < tileComponent.width; i++) {
            batch.draw(tileMatrix[NORTH][NEUTRAL],
                    t.translate.x + i * spriteWidth,
                    t.translate.y + (tileComponent.height - 1) * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }

    }

    private void drawSouthEdge(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        // draw south edge
        for (int i = 0; i < tileComponent.width; i++) {
            batch.draw(tileMatrix[SOUTH][NEUTRAL],
                    t.translate.x + i * spriteWidth,
                    t.translate.y,
                    spriteWidth,
                    spriteHeight);
        }
    }

    private void drawEastEdge(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        // draw east edge
        for (int i = 0; i < tileComponent.height; i++) {
            batch.draw(tileMatrix[NEUTRAL][EAST],
                    t.translate.x + (tileComponent.width - 1) * spriteWidth,
                    t.translate.y + i * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }

    }

    private void drawWestEdge(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        // draw west edge
        for (int i = 0; i < tileComponent.height; i++) {
            batch.draw(tileMatrix[NEUTRAL][WEST],
                    t.translate.x,
                    t.translate.y + i * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }
    }

    private void drawCorners(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        float spriteWidth = tileMatrix[1][1].getRegionWidth();
        float spriteHeight = tileMatrix[1][1].getRegionHeight();

        // draw corners
        if (tileMatrix[NORTH][WEST] != null) {
            batch.draw(tileMatrix[NORTH][WEST],
                    t.translate.x,
                    t.translate.y + spriteHeight * (tileComponent.height - 1));
        }

        if (tileMatrix[NORTH][EAST] != null) {
            batch.draw(tileMatrix[NORTH][EAST],
                    t.translate.x + spriteWidth * (tileComponent.width - 1),
                    t.translate.y + spriteHeight * (tileComponent.height - 1));
        }

        if (tileMatrix[SOUTH][WEST] != null) {
            batch.draw(tileMatrix[SOUTH][WEST],
                    t.translate.x,
                    t.translate.y);
        }

        if (tileMatrix[SOUTH][EAST] != null) {
            batch.draw(tileMatrix[SOUTH][EAST],
                    t.translate.x + spriteWidth * (tileComponent.width - 1),
                    t.translate.y);
        }
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
