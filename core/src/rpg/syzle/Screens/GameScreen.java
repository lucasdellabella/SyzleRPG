package rpg.syzle.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Gdx.app;
import static rpg.syzle.Constants.SCREEN_WIDTH;
import static rpg.syzle.Constants.SCREEN_HEIGHT;

import rpg.syzle.Input.AndroidGameInputProcessor;
import rpg.syzle.Input.DesktopGameInputProcessor;
import rpg.syzle.Model.Bullet;
import rpg.syzle.Model.Dungeon;
import rpg.syzle.Model.Enemy;
import rpg.syzle.Model.Player;
import rpg.syzle.SyzleRPG;

/**
 * Created by lucasdellabella on 12/20/16.
 */

public class GameScreen implements Screen {

    final SyzleRPG game;

    private Music ambientMusic;
    private Player player;
    private Enemy enemy;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Dungeon dungeon;

    public GameScreen(final SyzleRPG game) {
        this.game = game;
        dungeon = new Dungeon(game, 8);

        player = new Player();
        enemy = new Enemy(player);

        // Do application based setup
        Application.ApplicationType appType = app.getType();
        switch (appType) {
            case Android:
                Gdx.input.setInputProcessor(new AndroidGameInputProcessor(player));
                break;
            case Desktop:
                Gdx.input.setInputProcessor(new DesktopGameInputProcessor(player));
                break;
            case HeadlessDesktop:
                Gdx.input.setInputProcessor(new InputAdapter());
                break;
            case Applet:
                Gdx.input.setInputProcessor(new InputAdapter());
                break;
            case WebGL:
                Gdx.input.setInputProcessor(new InputAdapter());
                break;
            case iOS:
                Gdx.input.setInputProcessor(new InputAdapter());
                break;
        }


        // load sound effects
        ambientMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // start music
        ambientMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
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
        enemy.draw(game.batch);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            dispose();
            game.setScreen(new GameScreen(game));
        }

        // NOTE: Changing some attributes before rendering and others after can cause weird jitter effects
        //   and inconsistencies. Render first, update state afterwards.

        player.move();
        player.attack();
        for (Bullet bullet: enemy.bullets) {
            player.collide(bullet);
        }
        if (player.isDead()) {
            resetLevel();
        }


        enemy.move();
        enemy.attack();
        for (Bullet bullet: player.bullets) {
            enemy.collide(bullet);
        }
        if (enemy.isDead()) {
            resetLevel();
        }

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
        enemy.dispose();
        ambientMusic.dispose();
    }

    public void resetLevel() {
        dispose();
        game.setScreen(new GameScreen(game));
    }
}
