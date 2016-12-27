## December 22nd, 2016
#### Lucas

For dungeon generation, we will obviously have enemies, randomly occuring objects that are not classified as enemies,
and in some sense of the word, rooms. Thus we most likely take a three layered approach towards generating the dungeon:
first we pick a room / wall structure (maybe after picking if we're going to have 'special' rooms? Then we generate any
objects of interest within the room structure such as chests, pits?, portals, traps, etc. Finally we place enemies as
appropriate around these objects - more enemies might guard more powerful chests, there might be a few less enemies where
there are traps etc.

--> Is this bad design because we are placing the enemies reactively based on how the rest of the map has been generated?

New idea: any given level receives a value based on the expected difficulty. Based on certain values attached to single
or groups of game objects, we can distribute the roomContents (groupings of dungeon objects) so that the total value
across the roomContents is equivalent to the value that the level itself receives. However, we need to account for the
increased difficulty when there is a lot of content in a single room. We also have to account for the fact that in
general, the difficulty of a level is it's hardest room on the way to the exit.

This means the difficulty of the level is the max between hardest room difficulty and total room difficulty.
(Though more likely it is 75% the max, 25% the other - it's harder when both are high, than when one is high and
the other very low)

So now I want to do this.

1. Generate some room structure between 3 - 8 rooms where the expected difficulty of a room is between
0.25 * levelDifficulty/numOfRooms and 3 * levelDifficulty/numOfRooms.
2. Select enough roomContents to fill up a room.

I think I'm overengineering this... How about I just create 'DungeonObjectGroup's and place them in rooms and see where
that takes me. I haven't even put the walls in place yet!

My next step: generate rooms with walls. Probably use libgdx's TileMap.

## December 26th, 2016
#### Lucas

Want to figure out how to best to draw Rooms and the Dungeon as a whole. Maybe Dungeon class with list of Rooms, all of
which can be drawn
