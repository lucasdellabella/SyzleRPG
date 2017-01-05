package rpg.syzle.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import rpg.syzle.Components.AttackComponent;
import rpg.syzle.Components.AttackPatternComponent;
import rpg.syzle.Components.PlayerComponent;
import rpg.syzle.Components.TransformComponent;
import rpg.syzle.EntityCreator;

import static rpg.syzle.DungeonConstants.SCREEN_HEIGHT;
import static rpg.syzle.DungeonConstants.SCREEN_WIDTH;

/**
 * Created by lucasdellabella on 1/4/17.
 */

public class AttackSystem extends IteratingSystem {

    private ComponentMapper<AttackComponent> attackM;
    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<AttackPatternComponent> attackPatternM;
    private ComponentMapper<PlayerComponent> playerM;

    private Engine engine;
    private EntityCreator entityCreator;

    public AttackSystem(PooledEngine engine) {
        super(Family.all(AttackComponent.class, TransformComponent.class)
                .one(AttackPatternComponent.class, PlayerComponent.class)
                .get());
        this.engine = engine;
        this.entityCreator = new EntityCreator(engine);
        attackM = ComponentMapper.getFor(AttackComponent.class);
        attackPatternM = ComponentMapper.getFor(AttackPatternComponent.class);
        playerM = ComponentMapper.getFor(PlayerComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // access the attack component, based on inAttack state, continue or don't
        // use playerComponent or attackPattern component to determine what happens when inAttack
        // state

        long curTime = TimeUtils.nanoTime();
        AttackComponent attackComp = attackM.get(entity);
        if (attackComp.attacking && canAttack(attackComp, curTime)) {
            // what happens for an entity depends on whether it is an enemy or the player
            if (attackPatternM.has(entity)) {
                AttackPatternComponent attackPatternComp = attackPatternM.get(entity);

                attackPatternComp.attackPatternTick += 1;
                attackComp.lastAttackTime = curTime;

            } else if (playerM.has(entity)) {
                PlayerComponent playerComp = playerM.get(entity);
                TransformComponent transformComp = transformM.get(entity);
                entityCreator.createBullet(
//                        new TextureRegion(new Texture(Gdx.files.internal("bullet.png"))),
                        entity,
                        transformComp.pos,
                        (new Vector2(playerComp.fireCoords.x - SCREEN_WIDTH / 2,
                                SCREEN_HEIGHT / 2 - playerComp.fireCoords.y)).nor(),
                        150);
                attackComp.lastAttackTime = curTime;
            }
        }
    }

    private boolean canAttack(AttackComponent attackComp, long curTime) {
        return attackComp.lastAttackTime + attackComp.attackSpeed * 5 * Math.pow(10, 8) <= curTime;

    }
}
