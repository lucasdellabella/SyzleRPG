package rpg.syzle.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by lucasdellabella on 12/29/16.
 */
public class Player extends Sprite implements Disposable, MyDrawable {

    private float moveSpeed;
    private float atkSpeed;
    private long lastShotTime;
    // UNORDERED array for better performance
    //Array<Bullet> bullets = new Array<>(false, 32);

    public Player() {
        super(new Texture(Gdx.files.internal("harold.jpg")));
        setBounds(800 / 2 - 32 / 2, 480 / 2 - 32 / 2, 32, 32);
        moveSpeed = 1;
        atkSpeed = 1;
        lastShotTime = -1;
    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { setX(getX() + moveSpeed * 200 * Gdx.graphics.getDeltaTime()); }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { setX(getX() - moveSpeed * 200 * Gdx.graphics.getDeltaTime()); }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { setY(getY() + moveSpeed * 200 * Gdx.graphics.getDeltaTime()); }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { setY(getY() - moveSpeed * 200 * Gdx.graphics.getDeltaTime()); }
    }

    public void attack() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {}
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        getTexture().dispose();
    }
}
