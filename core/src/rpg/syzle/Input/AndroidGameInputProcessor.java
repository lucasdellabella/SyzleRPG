package rpg.syzle.Input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import rpg.syzle.Model.Player;

/**
 * Created by lucasdellabella on 1/1/17.
 */
public class AndroidGameInputProcessor implements InputProcessor {

    Player player;

    public AndroidGameInputProcessor(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer == 0) {
            player.setMoveCenter(new Vector2(screenX, screenY));
        } else if (pointer == 1) {
            player.startFiring();
            player.setFireCoords(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == 0) {
            player.setMoveDirection(0, 0);
        } else if (pointer == 1) {
            player.stopFiring();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer == 0) {
            player.setMoveDirection(screenX - player.getMoveCenter().x,
                     - screenY + player.getMoveCenter().y);
        } else if (pointer == 1) {
            player.setFireCoords(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
