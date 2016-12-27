package rpg.syzle.Model;

import com.badlogic.gdx.graphics.g2d.Batch;
import rpg.syzle.SyzleRPG;

/**
 * Created by lucasdellabella on 12/26/16.
 * A Corridor will connect two rooms. Currently Corridors will have a 1 player unit width.
 * -- Should probably share some code with room
 */
public class Corridor {

    public Corridor(SyzleRPG game, Room room1, Room room2) {
        this();
    }

    private Corridor(SyzleRPG game, int x, int y, int width, int height) {
        super(game, x, y, width, height);
    }

    @Override
    public void draw(Batch batch) {

    }
}
