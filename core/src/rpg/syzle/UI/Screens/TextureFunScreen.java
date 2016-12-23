package rpg.syzle.UI.Screens;

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

/**
 * Created by lucasdellabella on 12/23/16.
 */
public class TextureFunScreen implements Screen {

    final SyzleRPG game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture texture;
    private SpriteBatch batch;
    private TextureRegion[][] regions;

    public TextureFunScreen(SyzleRPG game) {
        this.game = game;

        texture = new Texture(Gdx.files.internal("Atlas/build_atlas.png"));
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        viewport = new FitViewport(800, 480, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        regions = TextureRegion.split(texture, 32, 32);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //batch.draw(texture, 0, 0, 64, 64);              // #7
        for (int row = 0; row < regions.length; row++) {
            for (int col = 0; col < regions[0].length; col++) {
                batch.draw(regions[row][col], 32 * (row + 1), 32 * (col + 1));      // #8
            }
        }
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
