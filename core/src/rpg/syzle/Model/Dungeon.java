package rpg.syzle.Model;

import com.badlogic.gdx.graphics.g2d.Batch;
import rpg.syzle.ProcGen.DungeonGenerator;
import rpg.syzle.SyzleRPG;

import java.util.List;

import static rpg.syzle.DungeonConstants.*;

/**
 * Created by lucasdellabella on 12/26/16.
 */
public class Dungeon implements MyDrawable {

    private List<Room> rooms;
    final SyzleRPG game;
    final int dungeonWidth = 50;
    final int dungeonHeight = 50;

    public Dungeon(SyzleRPG game, int numRooms) {
        DungeonGenerator generator = new DungeonGenerator();
        rooms = generator.placeRooms(game, numRooms, dungeonWidth , dungeonHeight);
        this.game = game;
    }

    @Override
    public void draw(Batch batch) {
        for (int i = 0; i < dungeonWidth; i++) {
            for (int j = 0; j < dungeonHeight; j++) {
                batch.draw(game.spriteMatrixBaseAtlas[3][25], TILE_WIDTH * i, TILE_HEIGHT * j);
            }
        }

        for (Room room: rooms) {
            room.draw(batch);
        }
    }
}
