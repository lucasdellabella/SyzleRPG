package rpg.syzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucasdellabella on 1/7/17.
 */
public class TextureHolder {

    TextureRegion[][] spriteMatrixBaseAtlas;

    private Map<WallType, TextureRegion> wallTextures;
    final int rowOffset = 3;
    final int colOffset = 25;

    public TextureHolder() {
        Texture spriteBaseAtlas = new Texture(Gdx.files.internal("Atlas/base_out_atlas.png"));
        spriteMatrixBaseAtlas = TextureRegion.split(spriteBaseAtlas, 32, 32);

        wallTextures = new HashMap<>();
        wallTextures.put(WallType.NORTH, spriteMatrixBaseAtlas[rowOffset + 1][colOffset]);
        wallTextures.put(WallType.SOUTH, spriteMatrixBaseAtlas[rowOffset - 1][colOffset]);
        wallTextures.put(WallType.EAST, spriteMatrixBaseAtlas[rowOffset][colOffset - 1]);
        wallTextures.put(WallType.WEST, spriteMatrixBaseAtlas[rowOffset][colOffset + 1]);
        wallTextures.put(WallType.NORTH_WEST, spriteMatrixBaseAtlas[8][1]);
        wallTextures.put(WallType.NORTH_EAST, spriteMatrixBaseAtlas[8][1]);
        wallTextures.put(WallType.SOUTH_WEST, spriteMatrixBaseAtlas[8][1]);
        wallTextures.put(WallType.SOUTH_EAST, spriteMatrixBaseAtlas[8][1]);
        wallTextures.put(WallType.FLOOR, spriteMatrixBaseAtlas[3][19]);
    }

    public TextureRegion getWall(WallType wallType) {
        return wallTextures.get(wallType);
    }
}
