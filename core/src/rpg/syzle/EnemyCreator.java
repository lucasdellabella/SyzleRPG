package rpg.syzle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import rpg.syzle.Components.AttackComponent;
import rpg.syzle.Components.AttackPatternComponent;
import rpg.syzle.Components.BoundsComponent;
import rpg.syzle.Components.Enemies.*;
import rpg.syzle.Components.HealthComponent;
import rpg.syzle.Components.MovementComponent;
import rpg.syzle.Components.MovementPatternComponent;
import rpg.syzle.Components.TextureComponent;
import rpg.syzle.Components.TransformComponent;
import rpg.syzle.Constants.MOVEMENT_PATTERN;

import static rpg.syzle.Constants.MOVEMENT_PATTERN.*;

/**
 * Created by joe on 1/7/17.
 */

public class EnemyCreator {
    private PooledEngine engine;

    public EnemyCreator(PooledEngine engine) {
        this.engine = engine;
    }

    public Entity createGenericEnemy(int width, int height, String texturePath, int moveSpeed,
                                     int health, MOVEMENT_PATTERN movePattern) {
        Entity enemy = engine.createEntity();

        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        AttackPatternComponent attackPatternComponent = engine.createComponent(AttackPatternComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        MovementPatternComponent movementPatternComponent = engine.createComponent(
                MovementPatternComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

        setBounds(boundsComponent, width, height);
        textureComponent.region.setRegion(new Texture(Gdx.files.internal(texturePath)));
        movementComponent.moveSpeed = moveSpeed;
        healthComponent.hp = health;
        movementPatternComponent.movementPattern = movePattern;

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


    public Entity createSmallBear() {
        SmallBearComponent ec = engine.createComponent(SmallBearComponent.class);
        Entity enemy = createGenericEnemy(ec.WIDTH, ec.HEIGHT, ec.TEXTURE_PATH,
                ec.MOVE_SPEED, ec.HP, ec.MOVE_PATTERN);
        enemy.add(ec);

        return enemy;
    }

    public Entity createSlimeTurret() {
        SlimeTurretComponent ec = engine.createComponent(SlimeTurretComponent.class);
        Entity enemy = createGenericEnemy(ec.WIDTH, ec.HEIGHT, ec.TEXTURE_PATH,
                ec.MOVE_SPEED, ec.HP, ec.MOVE_PATTERN);
        enemy.add(ec);

        return enemy;
    }

    private void setBounds(BoundsComponent boundsComponent, float width, float height) {
        boundsComponent.bounds.setVertices(
                new float[]{0, 0, 0, width, height, 0, width, height});
    }
}
