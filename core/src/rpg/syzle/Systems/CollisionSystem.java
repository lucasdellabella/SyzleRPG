package rpg.syzle.Systems;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import rpg.syzle.Components.*;
import rpg.syzle.Model.Bullet;

/**
 * Created by lucasdellabella on 1/3/17.
 * --
 * Checks for collisions BoundsComponent AND TransformComponent
 * ~ Looks like the immutable arrays are only being set on addedToEngine
 *     what will happen if we add entities afterwards?
 */
public class CollisionSystem extends EntitySystem {

    private ComponentMapper<BoundsComponent> boundsM;
    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<MovementComponent> movementM;
    private ComponentMapper<HealthComponent> healthM;
    private ComponentMapper<BulletComponent> bulletM;

    private Polygon tempBounds1;
    private Polygon tempBounds2;

    private Engine engine;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> enemies;
    private ImmutableArray<Entity> bullets;
    private ImmutableArray<Entity> rooms;
    private Intersector intersector;

    public CollisionSystem() {
        boundsM = ComponentMapper.getFor(BoundsComponent.class);
        movementM = ComponentMapper.getFor(MovementComponent.class);
        bulletM = ComponentMapper.getFor(BulletComponent.class);
        healthM = ComponentMapper.getFor(HealthComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        tempBounds1 = new Polygon();
        tempBounds2 = new Polygon();
        intersector = new Intersector();
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
        rooms = engine.getEntitiesFor(Family.all(RoomComponent.class, BoundsComponent.class)
                .get());
    }

    @Override
    public void update(float deltaTime) {

        for (Entity bullet: bullets) {

            BoundsComponent bulletBoundsComp = boundsM.get(bullet);
            BulletComponent bulletBulletComp = bulletM.get(bullet);

            // Detects bullet - player collisions, and if it occurs, removes the bullet and
            // damages the player
            for (Entity player: players) {
                if (collides(bullet, player) && bulletBulletComp.owner != player) {
                    healthEntityHit(player, bullet);
                }
            }

            // Detects bullet - enemy collisions, and if it occurs, removes the bullet and
            // damages the enemy
            for (Entity enemy: enemies) {
                if (collides(bullet, enemy) && bulletBulletComp.owner != enemy) {
                    healthEntityHit(enemy, bullet);
                }
            }

            // Detects bullet - wall collisions, and if it occurs, removes the bullet
            for (Entity room: rooms) {
                BoundsComponent roomBounds = boundsM.get(room);

                if (collides(bullet, room)) {
                    engine.removeEntity(bullet);
                }
            }
        }

    }

    /**
     * Checks for whether two entities collide
     * @param a Collidable entity a
     * @param b Collidable entity b
     * @return true if entities collide, false otherwise
     */
    private boolean collides(Entity a, Entity b) {
        if (!boundsM.has(a)) {
           Gdx.app.debug("CollisionSystem", "Collision entity does not have hitboxes component.");
        }
        if (!transformM.has(a)) {
            Gdx.app.debug("CollisionSystem", "Collision entity does not have transform component.");
        }

        BoundsComponent aBoundsComp = boundsM.get(a);
        BoundsComponent bBoundsComp = boundsM.get(b);
        TransformComponent aTransform = transformM.get(a);
        TransformComponent bTransform = transformM.get(b);
        tempBounds1.setPosition(aTransform.translate.x, aTransform.translate.y);
        tempBounds2.setPosition(bTransform.translate.x, bTransform.translate.y);
        tempBounds1.setScale(aTransform.scale.x, aTransform.scale.y);
        tempBounds2.setScale(bTransform.scale.x, bTransform.scale.y);

        for (Polygon aHitbox: aBoundsComp.getHitboxes()) {
            for (Polygon bHitbox: bBoundsComp.getHitboxes()) {
                tempBounds1.setVertices(aHitbox.getVertices());
                tempBounds2.setVertices(bHitbox.getVertices());
                if (intersector.overlapConvexPolygons(tempBounds1, tempBounds2)) { return true; }
            }
        }

        return false;
    }

    /**
     * Does collision logic for health component
     * @param hitEntity the entity that has been hit
     * @param bullet the entity that has collided with hitEntity
     */
    private void healthEntityHit(Entity hitEntity, Entity bullet) {
        HealthComponent hpComp = healthM.get(hitEntity);
        BulletComponent bulletComp = bulletM.get(bullet);
        hpComp.hp -= bulletComp.damage;
        engine.removeEntity(bullet);
        if (hpComp.hp <= 0) {
            engine.removeEntity(hitEntity);
        }
    }
}

