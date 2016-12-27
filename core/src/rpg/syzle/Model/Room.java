package rpg.syzle.Model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import rpg.syzle.SyzleRPG;
import static rpg.syzle.Constants.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucasdellabella on 12/23/16.
 */
public class Room implements MyDrawable {

    private Map<String, TextureRegion> roomTextures;

    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public int width;
    public int height;

    public Vector2 center;

    public Room(SyzleRPG game, int x, int y, int width, int height) {
        super();
        x1 = x;
        y1 = y;
        x2 = x + width;
        y2 = y + height;
        this.width = width;
        this.height = height;

        //setSize(width * TILE_WIDTH, height * TILE_HEIGHT);
        center = new Vector2((float) Math.floor((x1 + x2)/2.0), (float) Math.floor((y1 + y2)/2.0));
        roomTextures = new HashMap<>();

        // build mapping for parts of a wall
        int rowCenter = 3;
        int colCenter = 25;
        roomTextures.put("nwall", game.spriteMatrixBaseAtlas[rowCenter + 1][colCenter]);
        roomTextures.put("swall", game.spriteMatrixBaseAtlas[rowCenter - 1][colCenter]);
        roomTextures.put("ewall", game.spriteMatrixBaseAtlas[rowCenter][colCenter + 1]);
        roomTextures.put("wwall", game.spriteMatrixBaseAtlas[rowCenter][colCenter - 1]);
        roomTextures.put("nwwall", game.spriteMatrixBaseAtlas[8][1]);
        roomTextures.put("newall", game.spriteMatrixBaseAtlas[8][1]);
        roomTextures.put("swwall", game.spriteMatrixBaseAtlas[8][1]);
        roomTextures.put("sewall", game.spriteMatrixBaseAtlas[8][1]);
        roomTextures.put("floor", game.spriteMatrixBaseAtlas[3][19]);
    }

    public boolean intersects(Room room) {
        return (x1 <= room.x2 && x2 >= room.x1 &&
                y1 <= room.y2 && room.y2 >= room.y1);
    }

    @Override
    public void draw(Batch batch) {
        // drawing occurs by what is invoked first. Draw what will be behind then paint on top.
        drawFilling(batch);
        drawWalls(batch);
        drawCorners(batch);
    }

    // TODO: make sure that x1 y1 is always bottom right corner, and x2 y2 is always
    // +- 1 is to account for manual drawing of corners
    private void drawWalls(Batch batch) {
        // draw north wall
        for (int curX = x1 + 1; curX < x2; curX++) {
            batch.draw(roomTextures.get("nwall"), TILE_WIDTH * curX, TILE_HEIGHT * y2);
        }

        // draw south wall
        for (int curX = x1 + 1; curX < x2; curX++) {
            batch.draw(roomTextures.get("swall"), TILE_WIDTH * curX, TILE_HEIGHT * y1);
        }

        // draw east wall
        for (int curY = y1 + 1; curY < y2; curY++) {
            batch.draw(roomTextures.get("ewall"), TILE_WIDTH * x1, TILE_HEIGHT * curY);
        }

        // draw west wall
        for (int curY = y1 + 1; curY < y2; curY++) {
            batch.draw(roomTextures.get("wwall"), TILE_WIDTH * x2, TILE_HEIGHT * curY);
        }
    }

    private void drawCorners(Batch batch) {
        batch.draw(roomTextures.get("nwwall"), TILE_WIDTH * x1, TILE_HEIGHT * y2);
        batch.draw(roomTextures.get("newall"), TILE_WIDTH * x2, TILE_HEIGHT * y2);
        batch.draw(roomTextures.get("swwall"), TILE_WIDTH * x1, TILE_HEIGHT * y1);
        batch.draw(roomTextures.get("sewall"), TILE_WIDTH * x2, TILE_HEIGHT * y1);
    }

    private void drawFilling(Batch batch) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                batch.draw(roomTextures.get("floor"), TILE_WIDTH * i, TILE_HEIGHT * j);
            }
        }
    }
}
