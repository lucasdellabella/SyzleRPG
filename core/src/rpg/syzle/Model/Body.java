package rpg.syzle.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by lucasdellabella on 12/29/16.
 */
public abstract class Body extends Sprite implements Disposable, MyDrawable {

    float atkSpeed;
    long lastAttackTime;
    // UNORDERED array for better performance
    Array<Bullet> bullets = new Array<>(false, 32);

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
    public abstract void attack();

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
