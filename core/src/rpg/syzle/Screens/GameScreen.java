package rpg.syzle.Screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Gdx.app;
import static rpg.syzle.DungeonConstants.SCREEN_WIDTH;
import static rpg.syzle.DungeonConstants.SCREEN_HEIGHT;

import rpg.syzle.Components.PlayerComponent;
import rpg.syzle.Components.TextureComponent;
import rpg.syzle.EntityCreator;
import rpg.syzle.Input.AndroidGameInputProcessor;
import rpg.syzle.Systems.*;
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
    private Entity playerEntity;
    private Enemy enemy;

    private OrthographicCamera camera;
    private Viewport viewport;
    private PooledEngine engine;
    private EntityCreator entityCreator;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<PlayerComponent> playerMapper;

    private Dungeon dungeon;

    public GameScreen(final SyzleRPG game) {
        this.game = game;
        dungeon = new Dungeon(game, 8);

        player = new Player();
        enemy = new Enemy(player);

        engine = new PooledEngine();

        RenderingSystem renderingSystem = new RenderingSystem(game.batch);
        MovementSystem movementSystem = new MovementSystem();
        AttackSystem attackSystem = new AttackSystem(engine);
        CollisionSystem collisionSystem = new CollisionSystem();
        engine.addSystem(renderingSystem);
        engine.addSystem(movementSystem);
        engine.addSystem(attackSystem);
        engine.addSystem(collisionSystem);

        entityCreator = new EntityCreator(engine);
        playerEntity = entityCreator.createPlayer();
        Entity enemyEntity = entityCreator.createEnemy();

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);

        this.setInputProcessor();

        // load sound effects and start music
        ambientMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        ambientMusic.setLooping(true);

        camera = renderingSystem.getCamera();
        viewport = renderingSystem.getViewport();
    }

    @Override
    public void show() {
        //ambientMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        // ecs render sprites
        // NOTE: to have ECS architecture rendered, comment out the non-esc render sprites section
        engine.update(delta);

        // non-ecs render sprites

        // render images
        /*game.batch.begin();
        dungeon.draw(game.batch);
        player.draw(game.batch);
        enemy.draw(game.batch);
        game.batch.end();*/

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            dispose();
            game.setScreen(new GameScreen(game));
        }

        // NOTE: Changing some attributes before rendering and others after can cause weird jitter effects
        //   and inconsistencies. Render first, update state afterwards.

        /*player.move();
        player.attack();
        for (Bullet bullet: enemy.bullets)
            player.collide(bullet);
        }
        if (player.isDead()) {
            resetLevel();
        }*

        enemy.move();
        enemy.attack();
        for (Bullet bullet: player.bullets) {
            enemy.collide(bullet);
        }
        if (enemy.isDead()) {
            resetLevel();
        }

        camera.position.x = player.getX() + player.getWidth() / 2;
        camera.position.y = player.getY() + player.getHeight() / 2;*/
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

    private void setInputProcessor() {
        // Do application based setup
        Application.ApplicationType appType = app.getType();
        switch (appType) {
            case Android:
                Gdx.input.setInputProcessor(new AndroidGameInputProcessor(player));
                break;
            case Desktop:
                Gdx.input.setInputProcessor(new DesktopInputProcessorSystem(playerEntity));
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
    }
}
