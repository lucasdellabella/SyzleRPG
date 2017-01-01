package rpg.syzle;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Application.ApplicationType;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import rpg.syzle.Screens.GameScreen;

public class SyzleRPG extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public TextureRegion[][] spriteMatrixBaseAtlas;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		// TODO: Update InputAdapter to be custom InputProcessor

		// Set our sprite matrix
		Texture spriteBaseAtlas = new Texture(Gdx.files.internal("Atlas/base_out_atlas.png"));
		spriteMatrixBaseAtlas = TextureRegion.split(spriteBaseAtlas, 32, 32);
		this.setScreen(new GameScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}

