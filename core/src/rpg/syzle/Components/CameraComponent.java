package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class CameraComponent implements Component, Poolable {
    public Entity target;
    public OrthographicCamera camera;
    public Viewport viewport;

    @Override
    public void reset() {
        target = null;
        camera = null;
        viewport = null;
    }
}
