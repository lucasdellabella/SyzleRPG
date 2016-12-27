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
import rpg.syzle.SyzleRPG;

/**
 * Created by lucasdellabella on 12/20/16.
 */

public class GameScreen implements Screen {

    final SyzleRPG game;

    private Texture playerImage;
    private Texture enemyImage;
    private Sound attackSound;
    private Music ambientMusic;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Dungeon dungeon;
    private Rectangle playerRect;
    private Rectangle enemyRect;

    public GameScreen(final SyzleRPG game) {
        this.game = game;
        dungeon = new Dungeon(game, 8);

        // load player and enemy images
        playerImage = new Texture(Gdx.files.internal("harold.jpg"));
        enemyImage = new Texture(Gdx.files.internal("harambe.jpg"));

        // set player and enemy rectangles
        playerRect = new Rectangle();
        playerRect.x = 800 / 2 - 32 / 2;
        playerRect.y = 20;
        playerRect.width = 32;
        playerRect.height = 32;

        enemyRect = new Rectangle();
        enemyRect.x = 800 / 2 - 32 / 2;
        enemyRect.y = 480 / 2 - 20;
        enemyRect.width = 32;
        enemyRect.height = 32;

        // load sound effects
        attackSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
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
        game.batch.draw(playerImage, playerRect.x, playerRect.y);
        game.batch.draw(enemyImage, enemyRect.x, enemyRect.y);
        game.batch.end();

        if(Gdx.input.isTouched()) {
            dispose();
            game.setScreen(new GameScreen(game));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerRect.x += 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerRect.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) playerRect.y += 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerRect.y -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) { attackSound.play(); }

        camera.position.x = playerRect.getX();
        camera.position.y = playerRect.getY();
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
        playerImage.dispose();
        enemyImage.dispose();
        attackSound.dispose();
        ambientMusic.dispose();
    }
}
