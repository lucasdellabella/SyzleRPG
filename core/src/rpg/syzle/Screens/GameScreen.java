package rpg.syzle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import rpg.syzle.Model.Dungeon;
import rpg.syzle.Model.Player;
import rpg.syzle.SyzleRPG;

/**
 * Created by lucasdellabella on 12/20/16.
 */

public class GameScreen implements Screen {

    final SyzleRPG game;

    private Texture enemyImage;
    private Music ambientMusic;
    private Player player;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Dungeon dungeon;
    private Rectangle enemyRect;

    public GameScreen(final SyzleRPG game) {
        this.game = game;
        dungeon = new Dungeon(game, 8);

        player = new Player();

        // load player and enemy images
        enemyImage = new Texture(Gdx.files.internal("harambe.jpg"));

        enemyRect = new Rectangle();
        enemyRect.x = 800 / 2 - 32 / 2;
        enemyRect.y = 480 / 2 - 20;
        enemyRect.width = 32;
        enemyRect.height = 32;

        // load sound effects
        ambientMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // start music
        ambientMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        viewport = new FitViewport(800, 480, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    @Override
    public void show() {
        ambientMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // render images
        game.batch.begin();
        dungeon.draw(game.batch);
        player.draw(game.batch);
        //game.batch.draw(enemyImage, enemyRect.x, enemyRect.y);
        game.batch.end();

        if(Gdx.input.isTouched()) {
            dispose();
            game.setScreen(new GameScreen(game));
        }

        // NOTE: Changing some attributes before rendering and others after can cause weird jitter effects
        //   and inconsistencies. Render first, update state afterwards.

        player.move();

        camera.position.x = player.getX() + player.getWidth() / 2;
        camera.position.y = player.getY() + player.getHeight() / 2;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        player.dispose();
        enemyImage.dispose();
        ambientMusic.dispose();
    }
}
