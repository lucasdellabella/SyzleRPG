package rpg.syzle.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by lucasdellabella on 12/29/16.
 */
public abstract class Body extends CollidableSprite implements Disposable, MyDrawable {

    float atkSpeed;
    int hp;
    long lastAttackTime;
    // UNORDERED array for better performance
    public Array<Bullet> bullets = new Array<>(false, 32);

    public Body(Texture texture) {
        super(texture);
        atkSpeed = 1;
        lastAttackTime = TimeUtils.nanoTime();
    }

    public void move() {
        for (Bullet bullet: bullets) {
            bullet.move();
        }
    }

    // TODO: collide should somehow belong in the CollidableSprite abstract class.
    public void collide(Bullet bullet) {
        Rectangle intersection = overlaps(bullet);
        if (intersection.getHeight() != 0 || intersection.getWidth() != 0) {
            takeDamage(bullet.getDamage());
            bullet.remove();
        }
    }

    public abstract void attack();

    public int getHp() {
        return hp;
    }

    public void takeDamage(int damage) {
        this.hp = hp - damage;
    }
    @Override
    public void draw(Batch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
        for (Bullet bullet: bullets) {
            bullet.draw(batch);
        }
    }

    @Override
    public void dispose() {
        getTexture().dispose();
    }
}
