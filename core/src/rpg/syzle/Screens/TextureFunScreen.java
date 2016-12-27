package rpg.syzle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import rpg.syzle.SyzleRPG;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucasdellabella on 12/23/16.
 */
public class TextureFunScreen implements Screen {

    final SyzleRPG game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture texture;
    private SpriteBatch batch;
    private TextureRegion[][] spriteMatrix;
    private Map<String, TextureRegion> regions = new HashMap<String, TextureRegion>();

    public TextureFunScreen(SyzleRPG game) {
        this.game = game;

        texture = new Texture(Gdx.files.internal("Atlas/base_out_atlas.png"));
        batch = new SpriteBatch();

        spriteMatrix = TextureRegion.split(texture, 32, 32);

        // build mapping for parts of a wall
        int rowCenter = 3;
        int colCenter = 25;
        regions.put("nwall", spriteMatrix[rowCenter + 1][colCenter]);
        regions.put("swall", spriteMatrix[rowCenter - 1][colCenter]);
        regions.put("wwall", spriteMatrix[rowCenter][colCenter + 1]);
        regions.put("ewall", spriteMatrix[rowCenter][colCenter - 1]);
        regions.put("nwwall", spriteMatrix[rowCenter + 1][colCenter - 1]);
        regions.put("newall", spriteMatrix[rowCenter + 1][colCenter + 1]);
        regions.put("swwall", spriteMatrix[rowCenter - 1][colCenter - 1]);
        regions.put("sewall", spriteMatrix[rowCenter - 1][colCenter + 1]);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        viewport = new FitViewport(800, 480, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(spriteMatrix[2][25], 0, 64, 64, 32);
        batch.draw(spriteMatrix[4][25], 64, 64, 64, 32);
        batch.end();
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
