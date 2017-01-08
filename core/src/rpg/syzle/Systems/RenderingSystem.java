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

    private SpriteBatch batch;
    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TileComponent> tileM;
    private ComponentMapper<TransformComponent> transformM;
    private Array<Entity> renderQueue;
    private OrthographicCamera camera;

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

    private void drawTileComponent(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;

        if (tileMatrix[1][1] == null) {
            return;
        }

        TransformComponent t = transformM.get(entity);

        float spriteWidth = tileMatrix[1][1].getRegionWidth();
        float spriteHeight = tileMatrix[1][1].getRegionHeight();
        float centerX = spriteWidth * 0.5f;
        float centerY = spriteHeight * 0.5f;

        // full-ish matrix case
        // ? x ?
        // x x x
        // ? x ?
        if (tileMatrix[0][1] != null && tileMatrix[2][1] != null
                && tileMatrix[1][0] != null && tileMatrix[1][2] != null) {
            drawFilling(entity);
            drawNorthSouthEdges(entity);
            drawEastWestEdges(entity);
            drawCorners(entity);
        }

        // vertical line case
        // . x .
        // . x .
        // . x .
        else if (tileMatrix[0][1] != null && tileMatrix[2][1] != null) {
            drawFilling(entity);
            drawNorthSouthEdges(entity);
        }

        // horizontal line case
        // . . .
        // x x x
        // . . .
        else if (tileMatrix[1][0] != null && tileMatrix[1][2] != null) {
            drawFilling(entity);
            drawEastWestEdges(entity);
        }

        // only middle caes
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

        float spriteWidth = tileMatrix[1][1].getRegionWidth();
        float spriteHeight = tileMatrix[1][1].getRegionHeight();

        // draw filling
        for (int i = 0; i < tileComponent.width; i++) {
            for (int j = 0; j < tileComponent.height; j++) {
                batch.draw(tileMatrix[1][1],
                        t.translate.x + spriteWidth * i,
                        t.translate.y + spriteHeight * j,
                        spriteWidth,
                        spriteHeight);
            }
        }

    }

    private void drawNorthSouthEdges(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        float spriteWidth = tileMatrix[1][1].getRegionWidth();
        float spriteHeight = tileMatrix[1][1].getRegionHeight();

        //draw north edge
        for (int i = 0; i < tileComponent.width; i++) {
            batch.draw(tileMatrix[0][1],
                    t.translate.x + i * spriteWidth,
                    t.translate.y + (tileComponent.height - 1) * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }

        // draw south edge
        for (int i = 0; i < tileComponent.width; i++) {
            batch.draw(tileMatrix[2][1],
                    t.translate.x + i * spriteWidth,
                    t.translate.y,
                    spriteWidth,
                    spriteHeight);
        }
    }

    private void drawEastWestEdges(Entity entity) {
        TileComponent tileComponent = tileM.get(entity);
        TextureRegion[][] tileMatrix = tileComponent.tileMatrix;
        TransformComponent t = transformM.get(entity);

        float spriteWidth = tileMatrix[1][1].getRegionWidth();
        float spriteHeight = tileMatrix[1][1].getRegionHeight();

        // draw west edge
        for (int i = 0; i < tileComponent.height; i++) {
            batch.draw(tileMatrix[1][2],
                    t.translate.x,
                    t.translate.y + i * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }

        // draw east edge
        for (int i = 0; i < tileComponent.height; i++) {
            batch.draw(tileMatrix[1][0],
                    t.translate.x + (tileComponent.width - 1)* spriteWidth,
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

        // name values for readability
        final int WEST = 0;
        final int NORTH = 0;
        final int EAST = 2;
        final int SOUTH = 2;

        // draw corners
        batch.draw(tileMatrix[NORTH][WEST],
                t.translate.x,
                t.translate.y + spriteHeight * tileComponent.height);
        batch.draw(tileMatrix[NORTH][EAST],
                t.translate.x + spriteWidth * tileComponent.width,
                t.translate.y + spriteHeight * tileComponent.height);
        batch.draw(tileMatrix[SOUTH][WEST],
                t.translate.x,
                t.translate.y);
        batch.draw(tileMatrix[SOUTH][EAST],
                t.translate.x + spriteWidth * tileComponent.width,
                t.translate.y);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
