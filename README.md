# SyzleRPG

### Important Information
To be able to test the application on your desktop
1. Go to _edit configurations_
2. Click plus button in top left corner
3. Choose _Desktop_ from drop down
4. Fill out the fields with the following info
![Desktop Run Config](/readme-assets/desktop-run-config.png)

### Easy Mistakes
* Is the behavior of some object off or jittery? Make sure the render method is modifying the state of your object AFTER the SpriteBatch finishes drawing.


## TODO

#### Code
###### Short Term
- [ ] Remove bullet from game once it has left the screen
- [ ] Add health + bullet collisions
- [ ] Add wall collisions

###### Long Term
- [ ] Dungeon generation, Layer 1: Walls + Corridors
- [ ] Dungeon generation, Layer 2: Enemies + Objects
- [ ] Make room and corridor generation based on a set of points, so that rooms and corridors can vary in size.
- [ ] Refactor code to be using getters and setters

###### Longer Term
- [ ] Set up some system to allow us to assign a CollidableSprite a hitbox

#### Design
- [ ] Build a flexible FSM / system for enemy behaviors
- [ ] HOW do we want to do `Dungeon generation, Layer 1`
- [ ] Come up with a menu format
- [ ] Have design discussion Skype session
