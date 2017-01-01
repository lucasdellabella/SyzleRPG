package rpg.syzle.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static rpg.syzle.Constants.SCREEN_HEIGHT;
import static rpg.syzle.Constants.SCREEN_WIDTH;

/**
 * Created by lucasdellabella on 12/29/16.
 */
public class Enemy extends Body {

    private float moveSpeed;
    private Player player;
    private int currentAttackPattern;
    private float[] attackPatternBulletFrequencies;
    private int attackPatternTicks;

    public Enemy(Player player) {
        super(new Texture(Gdx.files.internal("harambe.jpg")));
        this.player = player;
        moveSpeed = 1;
        hp = 10;
        currentAttackPattern = -1;
        attackPatternTicks = 1;
        attackPatternBulletFrequencies = new float[]{0.2f, 0.3f};
        setBounds(30, 30, 32, 32);
    }

    // Neat way to get elastic movement is to have the distance between two things be used as the speed.
    // Then as the gap closes, the speed decreases
    @Override
    public void move() {
        super.move();
        Vector2 directionToPlayer = new Vector2(getX() - player.getX(), getY() - player.getY());
        directionToPlayer.nor();
        setX(getX() - directionToPlayer.x * 0.65f);
        setY(getY() - directionToPlayer.y * 0.65f);
    }

    // TODO: clean up this if block, it is prone to bugs
    @Override
    public void attack() {
        // only make attack during a 'touchDown' and if there has been sufficient time since the last attack
        long curTime = TimeUtils.nanoTime();
        if (lastAttackTime + atkSpeed * 1 * Math.pow(10, 8) <= curTime) {
            if (currentAttackPattern == -1) {
                currentAttackPattern = MathUtils.random(0, 1);

                // 8 x4 attack pattern
            } else if (currentAttackPattern == 0) {
                singleAttackPatternTick(4);
                for (int i = 0; i < 8; i++) {
                    Vector2 direction = new Vector2(1, 0);
                    direction.rotateRad(i * MathUtils.PI/4);
                    Bullet bullet = new Bullet(new Texture(Gdx.files.internal("bullet.png")),
                            this,
                            new Vector2(getX() + getWidth() / 2, getY() - getHeight() / 2),
                            direction,
                            5f);
                    bullets.add(bullet);
                }

                // 16 x2 attack pattern
            } else if (currentAttackPattern == 1) {
                singleAttackPatternTick(2);
                for (int i = 0; i < 16; i++) {
                    Vector2 direction = new Vector2(1, 0);
                    direction.rotateRad(i * MathUtils.PI/8);
                    Bullet bullet = new Bullet(new Texture(Gdx.files.internal("bullet.png")),
                            this,
                            new Vector2(getX() + getWidth() / 2, getY() - getHeight() / 2),
                            direction,
                            5f);
                    bullets.add(bullet);
                }
            }

            lastAttackTime = curTime;
        }
    }

    private void singleAttackPatternTick(int tickMax) {
        if (attackPatternTicks < tickMax) {
            attackPatternTicks += 1;
        } else {
            attackPatternTicks = 1;
            currentAttackPattern = -1;
        }
    }
}
