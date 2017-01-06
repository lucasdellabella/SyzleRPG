package rpg.syzle.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import rpg.syzle.Components.MovementComponent;
import rpg.syzle.Components.TransformComponent;

/**
 * Created by joe on 1/3/17.
 */

public class MovementSystem extends IteratingSystem {
    private Vector2 tmp = new Vector2();

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<MovementComponent> movementMapper;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        MovementComponent movementComponent = movementMapper.get(entity);;

        tmp.set(movementComponent.acceleration).scl(deltaTime);
        movementComponent.velocity.add(tmp);

        tmp.set(movementComponent.velocity).scl(deltaTime);
        transformComponent.translate.add(
                tmp.x * movementComponent.moveSpeed, tmp.y * movementComponent.moveSpeed);
    }
}
