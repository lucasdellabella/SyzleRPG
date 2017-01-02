package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class CameraComponent implements Component {
    public Entity target;
    public OrthographicCamera camera;
}
