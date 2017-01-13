package rpg.syzle.ProcGen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import rpg.syzle.Model.Room;
import rpg.syzle.WallType;
import rpg.syzle.SyzleRPG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static rpg.syzle.DungeonConstants.*;
import static rpg.syzle.WallType.*;

/**
 * Created by lucasdellabella on 12/23/16.
 */
public class DungeonGeneration {
//
//    List<Room> rooms;
//
//    public List<Room> placeRooms(SyzleRPG game, int numRooms, int mapWidth, int mapHeight) {
//        // create array for room storage for easy access
//        rooms = new ArrayList<>();
//
//        // TODO: Try again when overlapsNoOtherRoom returns false instead of skipping
//        // Solution cannot just not inc counter, or else a too small dungeon size / too large numRooms will cause
//        // an infini loop
//        for (int i = 0; i < 20; i++) {
//            // randomize values for each room
//            int w = MathUtils.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
//            int h = MathUtils.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
//            int x = MathUtils.random(mapWidth - w - 1);
//            int y = MathUtils.random(mapHeight - h - 1);
//
//            // create room with randomized values
//            Room newRoom = new Room(game, x, y, w, h);
//
//            // if there is no room overlap
//            if (overlapsNoOtherRoom(newRoom)) {
//                rooms.add(newRoom);
//            }
//        }
//
//        return rooms;
//    }
//
//
//
//    private boolean overlapsNoOtherRoom(int x, int y, int width, int height) {
//        // finds whether our room intersects with any other room
//        boolean intersection = false;
//        Rectangle roomBounds = new Rectangle()
//        for (Room otherRoom: rooms) {
//            if (newRoom.intersects(otherRoom)) {
//                intersection = true;
//                break;
//            }
//        }
//
//        return !intersection;
//    }

}
