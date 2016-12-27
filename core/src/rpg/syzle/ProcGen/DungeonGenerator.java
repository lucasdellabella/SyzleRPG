package rpg.syzle.ProcGen;

import com.badlogic.gdx.math.MathUtils;
import rpg.syzle.Model.Corridor;
import rpg.syzle.Model.Room;
import rpg.syzle.SyzleRPG;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static rpg.syzle.Constants.*;

/**
 * Created by lucasdellabella on 12/23/16.
 */
public class DungeonGenerator {

    List<Room> rooms;

    public List<Room> placeRooms(SyzleRPG game, int numRooms, int mapWidth, int mapHeight) {
        // create array for room storage for easy access
        rooms = new ArrayList<>();

        // TODO: Try again when overlapsNoOtherRoom returns false instead of skipping
        // Solution cannot just not inc counter, or else a too small dungeon size / too large numRooms will cause
        // an infini loop
        for (int i = 0; i < 20; i++) {
            // randomize values for each room
            int w = MathUtils.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
            int h = MathUtils.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
            int x = MathUtils.random(mapWidth - w - 1);
            int y = MathUtils.random(mapHeight - h - 1);

            // create room with randomized values
            Room newRoom = new Room(game, x, y, w, h);

            // if there is no room overlap
            if (overlapsNoOtherRoom(newRoom)) {
                rooms.add(newRoom);
            }
        }

        return rooms;
    }


    /**
     * Gives rooms corridors to their closest 1 to 3 other rooms
     * @param game
     */
    public void createCorridors(SyzleRPG game) {
        for (Room curRoom: rooms) {
            // Every room gets between 1 and 3 corridors to other rooms.
            int numCorridors = MathUtils.random(1, 3);
            for (int i = 0; i < numCorridors; i++) {
                List<Room> closestRooms = findClosestRooms(curRoom, numCorridors);
                for (Room closeRoom: closestRooms) {
                    Corridor newCorridor = new Corridor(game, curRoom, closeRoom);
                    curRoom.corridors.add(newCorridor);
                    closeRoom.corridors.add(newCorridor);
                }
            }
        }
    }

    private boolean overlapsNoOtherRoom(Room newRoom) {
        // finds whether our room intersects with any other room
        boolean intersection = false;
        for (Room otherRoom: rooms) {
            if (newRoom.intersects(otherRoom)) {
                intersection = true;
                break;
            }
        }

        return !intersection;
    }

    // Runs n times (once for each room) in order to
    private List<Room> findClosestRooms(Room room, int numRooms) {
        // Comparator cares about distance from r1/r2 to our 'base' room
        PriorityQueue<Room> closestRoomsPQ = new PriorityQueue<>(
                (Room r1, Room r2) -> (int) (distance(r1, room) - distance(r2, room)));
        closestRoomsPQ.addAll(rooms);

        // return `numRooms` closestRooms
        List<Room> closestRoomsList = new ArrayList<>();
        for (int i = 0; i < numRooms; i++) {
            closestRoomsList.add(closestRoomsPQ.poll());
        }
        return closestRoomsList;
    }

    private double distance(Room r1, Room r2) {
        return Math.sqrt((r1.x1 - r2.x1) * (r1.x1 - r2.x1) + (r1.y1 - r1.y2) * (r1.y1 - r1.y2));
    }
}
