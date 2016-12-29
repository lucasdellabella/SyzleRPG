package rpg.syzle.Model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import rpg.syzle.SyzleRPG;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucasdellabella on 12/27/16.
 */
public abstract class Walkable {

    private static Map<String, TextureRegion> walkableTextures;
    private final SyzleRPG game;

    public Walkable(SyzleRPG game) {
        this.game = game;
        walkableTextures = new HashMap<>();

        if (walkableTextures.isEmpty()) {
            // build mapping for parts of a wall
            int rowCenter = 3;
            int colCenter = 25;
            walkableTextures.put("nwall", game.spriteMatrixBaseAtlas[rowCenter + 1][colCenter]);
            walkableTextures.put("swall", game.spriteMatrixBaseAtlas[rowCenter - 1][colCenter]);
            walkableTextures.put("ewall", game.spriteMatrixBaseAtlas[rowCenter][colCenter + 1]);
            walkableTextures.put("wwall", game.spriteMatrixBaseAtlas[rowCenter][colCenter - 1]);
            walkableTextures.put("nwwall", game.spriteMatrixBaseAtlas[8][1]);
            walkableTextures.put("newall", game.spriteMatrixBaseAtlas[8][1]);
            walkableTextures.put("swwall", game.spriteMatrixBaseAtlas[8][1]);
            walkableTextures.put("sewall", game.spriteMatrixBaseAtlas[8][1]);
            walkableTextures.put("floor", game.spriteMatrixBaseAtlas[3][19]);
        }
    }

    public TextureRegion getTexture(String key) {
        return walkableTextures.get(key);
    }
}
