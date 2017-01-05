package rpg.syzle.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import rpg.syzle.Components.TextureComponent;
import rpg.syzle.Components.TransformComponent;

import static rpg.syzle.DungeonConstants.SCREEN_HEIGHT;
import static rpg.syzle.DungeonConstants.SCREEN_WIDTH;

/**
 * Created by lucasdellabella on 1/3/17.
 * --
 * Works on entities with TextureComponent AND TransformComponent
 */

public class RenderingSystem extends IteratingSystem {

    private final int PIXELS_TO_METERS = 1;

    private SpriteBatch batch;
    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;
    private Array<Entity> renderQueue;
    private OrthographicCamera camera;
    private Viewport viewport;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(TextureComponent.class, TransformComponent.class).get());

        this.batch = batch;
        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);
        renderQueue = new Array<>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (Entity entity: renderQueue) {

            TextureComponent textureComponent = textureM.get(entity);

            if (textureComponent.region == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);

            float width = textureComponent.region.getRegionWidth();
            float height = textureComponent.region.getRegionHeight();
            float centerX = width * 0.5f;
            float centerY = height * 0.5f;

            batch.draw(textureComponent.region,
                    t.pos.x - centerX, t.pos.y - centerY,
                    centerX, centerY,
                    width, height,
                    t.scale.x, t.scale.y,
                    t.rotation);
        }

        renderQueue.clear();
        batch.end();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
