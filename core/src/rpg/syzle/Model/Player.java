package rpg.syzle.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.Time;

import static rpg.syzle.Constants.SCREEN_HEIGHT;
import static rpg.syzle.Constants.SCREEN_WIDTH;

/**
 * Created by lucasdellabella on 12/29/16.
 */
public class Player extends Sprite implements Disposable, MyDrawable {

    private float moveSpeed;
    private float atkSpeed;
    private long lastAttackTime;
    // UNORDERED array for better performance
    Array<Bullet> bullets = new Array<>(false, 32);

    public Player() {
        super(new Texture(Gdx.files.internal("harold.jpg")));
        moveSpeed = 1;
        atkSpeed = 1;
        lastAttackTime = TimeUtils.nanoTime();
//        setBounds(800 / 2 - 32 / 2, 480 / 2 - 32 / 2, 32, 32);
        setBounds(0, 0, 32, 32);
    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) { setX(getX() + moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) { setX(getX() - moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) { setY(getY() + moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) { setY(getY() - moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
        for (Bullet bullet: bullets) {
            bullet.move();
        }
    }

    public void attack() {
        // only make attack during a 'touchDown' and if there has been sufficient time since the last attack
        long curTime = TimeUtils.nanoTime();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && lastAttackTime + atkSpeed * 5 * Math.pow(10, 8) <= curTime) {
            Vector2 direction = new Vector2(Gdx.input.getX() - SCREEN_WIDTH / 2 , SCREEN_HEIGHT / 2 - Gdx.input.getY());
            Bullet bullet = new Bullet(new Texture(Gdx.files.internal("bullet.png")),
                    new Vector2(getX() + getWidth() / 2, getY() - getHeight() / 2),
                    direction,
                    5f);
            bullets.add(bullet);
            lastAttackTime = curTime;
        }
        System.out.println(lastAttackTime);
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
