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
public class Room extends Walkable implements MyDrawable {

    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public int width;
    public int height;

    public Vector2 center;

    public Room(SyzleRPG game, int x, int y, int width, int height) {
        super(game);
        x1 = x;
        y1 = y;
        x2 = x + width;
        y2 = y + height;
        this.width = width;
        this.height = height;

        center = new Vector2((float) Math.floor((x1 + x2)/2.0), (float) Math.floor((y1 + y2)/2.0));
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
    private void drawWalls(Batch batch) {
        // draw north wall
        for (int curX = x1 + 1; curX < x2; curX++) {
            batch.draw(getTexture("nwall"), TILE_WIDTH * curX, TILE_HEIGHT * y2);
        }

        // draw south wall
        for (int curX = x1 + 1; curX < x2; curX++) {
            batch.draw(getTexture("swall"), TILE_WIDTH * curX, TILE_HEIGHT * y1);
        }

        // draw east wall
        for (int curY = y1 + 1; curY < y2; curY++) {
            batch.draw(getTexture("ewall"), TILE_WIDTH * x1, TILE_HEIGHT * curY);
        }

        // draw west wall
        for (int curY = y1 + 1; curY < y2; curY++) {
            batch.draw(getTexture("wwall"), TILE_WIDTH * x2, TILE_HEIGHT * curY);
        }
    }

    private void drawCorners(Batch batch) {
        batch.draw(getTexture("nwwall"), TILE_WIDTH * x1, TILE_HEIGHT * y2);
        batch.draw(getTexture("newall"), TILE_WIDTH * x2, TILE_HEIGHT * y2);
        batch.draw(getTexture("swwall"), TILE_WIDTH * x1, TILE_HEIGHT * y1);
        batch.draw(getTexture("sewall"), TILE_WIDTH * x2, TILE_HEIGHT * y1);
    }

    private void drawFilling(Batch batch) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                batch.draw(getTexture("floor"), TILE_WIDTH * i, TILE_HEIGHT * j);
            }
        }
    }
}
