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

Pt. 1: Want to figure out how to best to draw Rooms and the Dungeon as a whole. Maybe Dungeon class with list of Rooms, all of
which can be drawn.

Pt. 2: These rain effects is the perfect white noise to code to. This is great.

## December 31st, 2016
#### Lucas

Sometime in the past five days I got a lot of the MVP done. Was pretty easy surprisingly, which was nice. More likely
maybe I was just well rested and focusing well. Current goal today is to get collision detection into the game - that
means collision detection between bodies, bullets, walls, etc. The whole shebang.

I wanted to do this via a Collidable interface that is every body has, but then I would have to write some custom collision code which seems
really obnoxious as libgdx already has this done for me.

---

Ok talked to Jay looks like going with another Abstract class is probably the correct call. Does certainly make things easy.

On another note, going to start a style rules doc so things are consistent once Joe starts working.

Because I have to check for collisions between bullets and walls, or maybe inanimate objects and walls,
it's best if I can just check for collisions against everything.

I need to remove bullets from the game based on certain events. Each game character (player || enemy) has an array of their bullets.
I draw the bullets on the screen using the draw method of the character. Thus to remove the bullet from the screen I need to remove the bullet from that characters array.
I want bullets to act upon whatever they collide with themselves. In that case I need to be able to remove the bullet from the game using the bullet class.
Clearly I need a way to remove the reference the player has to the bullet. Maybe it is simply better to not  GIVE the player a reference to the bullet.
A bullet is a bullet once it's on it's own. We still need to be able to reference all the bullets so maybe instead we have some massive static set of bullets in the bullet class.

## January 1st, 2017
#### Lucas

I really like functions that do some kind of check on state, do something if the conditions are right, and return
a boolean based on whether the important contents within the function occurred. (see collide in Body)
