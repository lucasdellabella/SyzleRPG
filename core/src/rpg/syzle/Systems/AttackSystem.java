package rpg.syzle.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import rpg.syzle.Components.*;
import rpg.syzle.EntityCreator;

/**
 * Created by lucasdellabella on 1/4/17.
 */

public class AttackSystem extends IteratingSystem {

    private ComponentMapper<TextureComponent> textureM;
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
        textureM = ComponentMapper.getFor(TextureComponent.class);
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
                // These components are for the current entity, not the bullet we are creating
                PlayerComponent playerComp = playerM.get(entity);
                TransformComponent transformComp = transformM.get(entity);
                TextureComponent textureComp = textureM.get(entity);
                Vector2 direction = new Vector2(playerComp.fireCoords.x - transformComp.translate.x,
                                playerComp.fireCoords.y - transformComp.translate.y).nor();
                entityCreator.createBullet(
//                        new TextureRegion(new Texture(Gdx.files.internal("bullet.png"))),
                        entity,
                        transformComp.translate.cpy().add(
                                textureComp.region.getRegionWidth() * transformComp.scale.x
                                        * direction.x,
                                textureComp.region.getRegionHeight() * transformComp.scale.y
                                        * direction.y),
                        direction,
                        150);
                attackComp.lastAttackTime = curTime;
            }
        }
    }

    private boolean canAttack(AttackComponent attackComp, long curTime) {
        return attackComp.lastAttackTime + attackComp.attackSpeed * 5 * Math.pow(10, 8) <= curTime;

    }
}
