package rpg.syzle.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.omg.CORBA.Bounds;
import rpg.syzle.Components.*;

/**
 * Created by lucasdellabella on 1/3/17.
 * --
 * Works on entities with TextureComponent AND TransformComponent
 */

public class DebugCollisionSystem extends IteratingSystem {

    private ShapeRenderer shapeRenderer;
    private ComponentMapper<BoundsComponent> boundsM;
    private ComponentMapper<TransformComponent> transformM;
    private Array<Entity> renderQueue;
    private OrthographicCamera camera;

    private Polygon tempDrawingBounds = new Polygon();

    public DebugCollisionSystem(Entity cameraEntity) {
        super(Family.all(TransformComponent.class, BoundsComponent.class).get());

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.RED);

        boundsM = ComponentMapper.getFor(BoundsComponent.class);
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
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity: renderQueue) {

            BoundsComponent bounds = boundsM.get(entity);
            TransformComponent transform = transformM.get(entity);

            // draw the bounding rectangle
            for (Polygon hitbox: bounds.getHitboxes()) {
                Rectangle boundingRect = hitbox.getBoundingRectangle();
                float width = boundingRect.getWidth();
                float height = boundingRect.getHeight();
                float centerX = width * 0.5f;
                float centerY = height * 0.5f;

                tempDrawingBounds.setVertices(hitbox.getVertices());
                tempDrawingBounds.setOrigin(centerX, centerY);
                tempDrawingBounds.setPosition(
                        transform.translate.x
                                - (width - width * transform.scale.x)/2f
                                - width/2
                                + boundingRect.getX(),
                        transform.translate.y
                                - (height - height * transform.scale.y)/2f
                                - height/2
                                + boundingRect.getY());
//                tempDrawingBounds.setPosition(transform.translate.x - width/2,
//                        transform.translate.y - height/2);
                tempDrawingBounds.setRotation(transform.rotation);
                tempDrawingBounds.setScale(transform.scale.x, transform.scale.y);
                shapeRenderer.polygon(tempDrawingBounds.getTransformedVertices());
            }

        }

        renderQueue.clear();
        shapeRenderer.end();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
