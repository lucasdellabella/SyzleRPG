package rpg.syzle.Screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Gdx.app;

import rpg.syzle.Components.CameraComponent;
import rpg.syzle.Components.PlayerComponent;
import rpg.syzle.Components.TextureComponent;
import rpg.syzle.EntityCreator;
import rpg.syzle.Input.AndroidGameInputProcessor;
import rpg.syzle.Input.DesktopInputProcessor;
import rpg.syzle.Systems.*;
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
    private Entity cameraEntity;
    private Enemy enemy;

    private OrthographicCamera camera;
    private Viewport viewport;
    private PooledEngine engine;
    private EntityCreator entityCreator;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<PlayerComponent> playerMapper;

    public GameScreen(final SyzleRPG game) {
        this.game = game;

        player = new Player();
        enemy = new Enemy(player);

        engine = new PooledEngine();

        // Create necessary entities
        entityCreator = new EntityCreator(engine);
        for (int i = 0; i < 20; i++) {
            entityCreator.createRoom(50, 50);
        }
        playerEntity = entityCreator.createPlayer();
        cameraEntity = entityCreator.createCamera(playerEntity);
        Entity enemyEntity = entityCreator.createEnemy();

        // Instantiate systems
        CameraSystem cameraSystem = new CameraSystem();
        RenderingSystem renderingSystem = new RenderingSystem(game.batch, cameraEntity);
        DebugCollisionSystem debugCollisionSystem = new DebugCollisionSystem(cameraEntity);
        MovementSystem movementSystem = new MovementSystem();
        AttackSystem attackSystem = new AttackSystem(engine);
        CollisionSystem collisionSystem = new CollisionSystem();
        engine.addSystem(cameraSystem);
        engine.addSystem(renderingSystem);
        engine.addSystem(debugCollisionSystem);
        engine.addSystem(movementSystem);
        engine.addSystem(attackSystem);
        engine.addSystem(collisionSystem);

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);

        pickInputProcessor();

        // load sound effects and start music
        ambientMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        ambientMusic.setLooping(true);

        camera = cameraEntity.getComponent(CameraComponent.class).camera;
        viewport = cameraEntity.getComponent(CameraComponent.class).viewport;
    }

    @Override
    public void show() {
        //ambientMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        // NOTE: Changing some attributes before rendering and others after can cause weird jitter effects
        //   and inconsistencies. Render first, update state afterwards.

        // ecs render sprites
        engine.update(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            resetLevel();
        }
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

    private void pickInputProcessor() {
        // Do application based setup
        Application.ApplicationType appType = app.getType();
        switch (appType) {
            case Android:
                Gdx.input.setInputProcessor(new AndroidGameInputProcessor(player));
                break;
            case Desktop:
                Gdx.input.setInputProcessor(new DesktopInputProcessor(playerEntity));
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
