package rpg.syzle.Systems;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.ashley.core.*;
import rpg.syzle.Components.*;

/**
 * Created by lucasdellabella on 1/3/17.
 * --
 * Checks for collisions BoundsComponent AND TransformComponent
 * ~ Looks like the immutable arrays are only being set on addedToEngine
 *     what will happen if we add entities afterwards?
 */
public class CollisionSystem extends EntitySystem {

    private ComponentMapper<BoundsComponent> boundsM;
    private ComponentMapper<MovementComponent> movementM;
    private ComponentMapper<HealthComponent> healthM;
    private ComponentMapper<BulletComponent> bulletM;

    public static interface CollisionListener {
        public void jump ();
        public void highJump ();
        public void hit ();
        public void coin ();
    }

    private Engine engine;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> enemies;
    private ImmutableArray<Entity> bullets;
    private ImmutableArray<Entity> walls;

    public CollisionSystem() {
        boundsM = ComponentMapper.getFor(BoundsComponent.class);
        movementM = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        players = engine.getEntitiesFor(Family.all(PlayerComponent.class, BoundsComponent.class)
                .get());
        enemies = engine.getEntitiesFor(Family.all(BoundsComponent.class)
                .one(AttackPatternComponent.class, MovementPatternComponent.class)
                .get());
        bullets = engine.getEntitiesFor(Family.all(BulletComponent.class, BoundsComponent.class)
                .get());
        walls = engine.getEntitiesFor(Family.all(WallComponent.class, BoundsComponent.class)
                .get());
    }

    @Override
    public void update(float deltaTime) {

        for (Entity bullet: bullets) {

            BoundsComponent bulletBounds = boundsM.get(bullet);
            // Whats a good name for the BulletComponent of a bullet?
            BulletComponent bulletBullet = bulletM.get(bullet);

            // Detects bullet - player collisions, and if it occurs, removes the bullet and
            // damages the player
            for (Entity player: players) {

                BoundsComponent playerBounds = boundsM.get(player);
                HealthComponent playerHp = healthM.get(player);

                if (bulletBounds.rectangle.overlaps(playerBounds.rectangle)
                        && bulletBullet.owner != player) {
                    playerHp.hp -= bulletBullet.damage;
                    engine.removeEntity(bullet);
                    if (playerHp.hp <= 0) {
                        engine.removeEntity(player);
                    }

                }
            }

            // Detects bullet - enemy collisions, and if it occurs, removes the bullet and
            // damages the enemy
            for (Entity enemy: enemies) {

                BoundsComponent enemyBounds = boundsM.get(enemy);
                HealthComponent enemyHp = healthM.get(enemy);

                if (bulletBounds.rectangle.overlaps(enemyBounds.rectangle)
                        && bulletBullet.owner != enemy) {
                    enemyHp.hp -= bulletBullet.damage;
                    engine.removeEntity(bullet);
                    if (enemyHp.hp <= 0) {
                        engine.removeEntity(enemy);
                    }

                }
            }

            // Detects bullet - wall collisions, and if it occurs, removes the bullet
            for (Entity wall: walls) {

                BoundsComponent wallBounds = boundsM.get(wall);

                if (bulletBounds.rectangle.overlaps(wallBounds.rectangle)) {
                    engine.removeEntity(bullet);
                }
            }
        }

//        for (Entity player: players) {
//        }
//
//        for (Entity enemy: enemies) {
//        }
    }
}

