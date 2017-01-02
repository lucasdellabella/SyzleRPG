package rpg.syzle.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import static rpg.syzle.DungeonConstants.SCREEN_HEIGHT;
import static rpg.syzle.DungeonConstants.SCREEN_WIDTH;

/**
 * Created by lucasdellabella on 12/29/16.
 */
public class Player extends Body {

    private float moveSpeed;
    private boolean firing = false;
    private Vector2 moveCenter;
    private Vector2 moveDirection;
    private int fireCoordX;
    private int fireCoordY;

    public Player() {
        super(new Texture(Gdx.files.internal("harold.jpg")));
        moveSpeed = 1;
        hp = 5;
        moveCenter = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        moveDirection = new Vector2(0, 0);
        setBounds(0, 0, 32, 32);
    }

    public void move() {
        super.move();
        setX(getX() + getMoveDirectionX() * moveSpeed * 150 * Gdx.graphics.getDeltaTime());
        setY(getY() + getMoveDirectionY() * moveSpeed * 150 * Gdx.graphics.getDeltaTime());
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) { setX(getX() + moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) { setX(getX() - moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) { setY(getY() + moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) { setY(getY() - moveSpeed * 150 * Gdx.graphics.getDeltaTime()); }
    }

    // Maybe this direction vector should be done in game coordinates instead,
    // after converting Gdx.input to game coordinates
    @Override
    public void attack() {
        // only make attack during a 'touchDown' and if there has been sufficient time since the last attack
        long curTime = TimeUtils.nanoTime();
        if (firing && lastAttackTime + atkSpeed * 5 * Math.pow(10, 8) <= curTime) {
            System.out.print("blabla");
            Vector2 direction = new Vector2(fireCoordX - SCREEN_WIDTH / 2,
                    SCREEN_HEIGHT / 2 - fireCoordY);
            Bullet bullet = new Bullet(new Texture(Gdx.files.internal("bullet.png")),
                    this,
                    new Vector2(getX() + getWidth() / 2, getY() - getHeight() / 2),
                    direction,
                    5f);
            bullets.add(bullet);
            lastAttackTime = curTime;
        }
    }

    public void startFiring() {
        firing = true;
    }

    public void stopFiring() {
        firing = false;
    }

    public void setFireCoords(int x, int y) {
        fireCoordX = x;
        fireCoordY = y;
    }

    public Vector2 getMoveCenter() {
        return moveCenter;
    }

    public void setMoveCenter(Vector2 moveCenter) {
        this.moveCenter = moveCenter;
    }

    public Vector2 getMoveDirection() {
        return moveDirection;
    }

    // TODO: A little misleading because you are expected to set with whole numbers, but then the
    // gets return floats that is just the component
    public void setMoveDirection(float x, float y) {
        moveDirection.x = x;
        moveDirection.y = y;
        moveDirection.nor();
    }

    public void setMoveDirectionX(int x) {
        moveDirection.x = x;
        moveDirection.y = Math.signum(moveDirection.y);
        moveDirection.nor();
    }

    public void setMoveDirectionY(int y) {
        moveDirection.x = Math.signum(moveDirection.x);
        moveDirection.y = y;
        moveDirection.nor();
    }

    public float getMoveDirectionX() {
        return moveDirection.x;
    }

    public float getMoveDirectionY() {
        return moveDirection.y;
    }

    /**
     *
     * @param keycode
     * @param activate
     * @return true if keycode corresponds to a readable input, false otherwise
     */
    public boolean setMovement(int keycode, boolean activate) {
        switch (keycode) {
            case Input.Keys.W:
                if (activate) {
                    setMoveDirectionY(1);
                } else if (Math.signum(getMoveDirectionY()) == 1) {
                    setMoveDirectionY(0);
                }
                break;
            case Input.Keys.A:
                if (activate) {
                    setMoveDirectionX(-1);
                } else if (Math.signum(getMoveDirectionX()) == -1) {
                    setMoveDirectionX(0);
                }
                break;
            case Input.Keys.S:
                if (activate) {
                    setMoveDirectionY(-1);
                } else if (Math.signum(getMoveDirectionY()) == -1) {
                    setMoveDirectionY(0);
                }
                break;
            case Input.Keys.D:
                if (activate) {
                    setMoveDirectionX(1);
                } else if (Math.signum(getMoveDirectionX()) == 1) {
                    setMoveDirectionX(0);
                }
                break;
            default:
                return false;

        }
        return true;

    }
}
