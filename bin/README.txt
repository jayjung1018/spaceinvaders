README:

Classes:
AlienA, AlienB, AlienC - extends the GameObj class, each has its own distinct score and image
BarrierPiece - extends the GameObj class - each has 4 lives and protects user from bullets
Bullet - GameObj for bullet
Direction - enum of directions
EnemyBullet - GameObj for bullets
Game - sets the frames and widgets of the game GUI
GameCourt - holds the primary game logic
GameObj - holds the logic for how objects interact with each other within the game space
PowerUp - GameObj for the mothership - holds its own image
RandomNumber - class with static methods to help generate random numbers for different cases
Tank - the GameObj for the tank

Run Game to run the game. Make sure images folder is in the root.