package rpg.syzle.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import rpg.syzle.Components.CameraComponent;
import rpg.syzle.Components.TransformComponent;

/**
 * Created by lucasdellabella on 1/7/17.
 */
public class CameraSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<CameraComponent> cameraM;

    public CameraSystem() {
        super(Family.all(CameraComponent.class).get());

        transformM = ComponentMapper.getFor(TransformComponent.class);
        cameraM = ComponentMapper.getFor(CameraComponent.class);
    }

    /**
     * Manages the all (if there is more than one) camera entity
     * @param entity a camera entity
     * @param deltaTime time since last engine.update() call in seconds
     */
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        CameraComponent cameraComponent = cameraM.get(entity);
        TransformComponent targetTransformComponent = transformM.get(cameraComponent.target);
        Vector2 cameraTranslate = transformM.get(entity).translate;

        cameraTranslate.set(targetTransformComponent.translate);
        cameraComponent.camera.position.x = cameraTranslate.x;
        cameraComponent.camera.position.y = cameraTranslate.y;
        cameraComponent.camera.update();
    }
}
