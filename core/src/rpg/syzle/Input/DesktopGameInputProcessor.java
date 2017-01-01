package rpg.syzle.Input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import rpg.syzle.Model.Player;

/**
 * Created by lucasdellabella on 1/1/17.
 */
public class DesktopGameInputProcessor implements InputProcessor {

    Player player;

    public DesktopGameInputProcessor(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        return player.setMovement(keycode, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        return player.setMovement(keycode, false);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.startFiring();
        player.setFireCoords(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.stopFiring();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        player.setFireCoords(screenX, screenY);
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
