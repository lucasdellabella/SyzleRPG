package rpg.syzle.Input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import rpg.syzle.Components.AttackComponent;
import rpg.syzle.Components.MovementComponent;
import rpg.syzle.Components.PlayerComponent;

/**
 * Created by lucasdellabella on 1/1/17.
 */
public class DesktopInputProcessor implements InputProcessor {

    Entity player;
    ComponentMapper<MovementComponent> movementM;
    ComponentMapper<PlayerComponent> playerM;
    ComponentMapper<AttackComponent> attackM;

    public DesktopInputProcessor(Entity player) {
        this.player = player;
        movementM = ComponentMapper.getFor(MovementComponent.class);
        playerM = ComponentMapper.getFor(PlayerComponent.class);
        attackM = ComponentMapper.getFor(AttackComponent.class);
    }

    private float convertToYUp(float yDown) {
        return Gdx.graphics.getHeight() - 1 - yDown;
    }

    @Override
    public boolean keyDown(int keycode) {
        return setMovement(keycode, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        return setMovement(keycode, false);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        attackM.get(player).attacking = true;
        playerM.get(player).fireCoords.set(screenX, convertToYUp(screenY));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        attackM.get(player).attacking = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        playerM.get(player).fireCoords.set(screenX, convertToYUp(screenY));
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Vector2 getMoveDirection() {
        return movementM.get(player).velocity;
    }

    // TODO: A little misleading because you are expected to set with whole numbers, but then the
    // gets return floats that is just the component
    public void setMoveDirection(float x, float y) {
        Vector2 velocity = movementM.get(player).velocity;
        velocity.x = x;
        velocity.y = y;
        velocity.nor();
    }

    public void setMoveDirectionX(int x) {
        Vector2 velocity = movementM.get(player).velocity;
        velocity.x = x;
        velocity.y = Math.signum(velocity.y);
        velocity.nor();
    }

    public void setMoveDirectionY(int y) {
        Vector2 velocity = movementM.get(player).velocity;
        velocity.x = Math.signum(velocity.x);
        velocity.y = y;
        velocity.nor();
    }

    public float getMoveDirectionX() {
        Vector2 velocity = movementM.get(player).velocity;
        return velocity.x;
    }

    public float getMoveDirectionY() {
        Vector2 velocity = movementM.get(player).velocity;
        return velocity.y;
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
