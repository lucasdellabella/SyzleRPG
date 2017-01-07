package rpg.syzle.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

import rpg.syzle.Components.MovementComponent;
import rpg.syzle.Components.MovementPatternComponent;
import rpg.syzle.Components.PlayerComponent;
import rpg.syzle.Components.TransformComponent;
import rpg.syzle.Constants.MOVEMENT_PATTERN;

import static rpg.syzle.Constants.MOVEMENT_PATTERN.*;

/**
 * Created by joe on 1/7/17.
 */

public class MovementPatternSystem extends EntitySystem {
    // Does not currently handle multiple players
    private Engine engine;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> ais;

    private ComponentMapper<MovementComponent> movementM;
    private ComponentMapper<MovementPatternComponent> patternM;
    private ComponentMapper<TransformComponent> transformM;

    public MovementPatternSystem() {
        movementM = ComponentMapper.getFor(MovementComponent.class);
        patternM = ComponentMapper.getFor(MovementPatternComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        ais = engine.getEntitiesFor(Family.all(MovementPatternComponent.class, MovementComponent.class,
                TransformComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if (players.size() > 0) {
            for (Entity entity : ais) {
                Entity player = players.first();
                MovementPatternComponent patternComponent = patternM.get(entity);
                MovementComponent movementComponent = movementM.get(entity);
                MOVEMENT_PATTERN movePattern = patternComponent.movementPattern;
                if (movePattern == STAND_STILL) {
                    if (!movementComponent.velocity.isZero()) {
                        movementComponent.velocity.setZero();
                    }
                    if (!movementComponent.acceleration.isZero()) {
                        movementComponent.acceleration.setZero();
                    }
                } else if (movePattern == TOWARDS_PLAYER || movePattern == AWAY_PLAYER){
                    TransformComponent aiTransformComponent = transformM.get(entity);
                    TransformComponent playerTransformComponent = transformM.get(player);
                    Vector2 aiPos = aiTransformComponent.translate;
                    Vector2 playerPos = playerTransformComponent.translate;
                    Vector2 directionToPlayer;
                    if (movePattern == TOWARDS_PLAYER) {
                        directionToPlayer = new Vector2(playerPos.x - aiPos.x,
                                playerPos.y - aiPos.y);
                    } else {
                        directionToPlayer = new Vector2(aiPos.x - playerPos.x,
                                aiPos.y - playerPos.y);
                    }
                    directionToPlayer.nor();
                    movementComponent.velocity = directionToPlayer;
                }
            }
        }
    }
}
