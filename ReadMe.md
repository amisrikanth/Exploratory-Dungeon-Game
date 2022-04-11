# Dungeons
## About/Overview:

A dungeon is a network of tunnels and caves that are interconnected so that player can explore the entire world by traveling from cave to cave through the tunnels that connect them. these dungeons are generated at random following some set of constraints resulting in a different network each time the game is begins. The dungeon contains treasures, arrows and Otyughs (extremely smelly creatures). The player can collect treasure and arrows and can slay the Otyughs using a pair arrows. The player can also be eaten by the Otyugh. The game ends when the player is killed or the end location is reached.

## List of features:
- The player can set the configuration of the game.
- Dungeons can be created with different degrees of interconnectivity and can be both wrapping and non-wrapping.
- provides support for three types of treasure: diamonds, rubies, and sapphires and crooked arrows as the weapon.
- Treasure can be added to a specified percentage of caves and same percentage of arrows are to be assigned to the locations of the dungeon. The user has to specify this percentage.
- A cave can have more than one treasure or arrows.
- The description of the player and the locations are available to the user.
- player can move from their current location to any location if there is a path in that direction.
- Player can pick up treasure and arrows that is located in the location as the player.
- The player can shoot arrows at the Otyughs in the caves around the player's current location.
- The arrows can bend through the tunnels but not through the caves.
- The player gets eaten by an Otyugh when the player enter the cave that has an Otyugh with full strength. The player has 50% chances of evading the Otyugh if it is injured by an arrow.
- The player can choose one of four avatars.
- The player can choose to play again with the same dungeon, different dungeon with the same configuration or a dungeon with a different configuration altogether.

## How To Run:
Use the java command to run the jar file:
To play the text based game:
java -jar Dungeons_Model.jar <PlayerName> <rows> <columns> <interconnectivity> <isWrapped> <percentageOfCavesWithTreasureOrLocationWithArrows> <NumberOfOtyughs>
To play the GUI Based game:
java -jar Dungeons_Model.jar

## How to Use the Program:
Various scenarios have been carried out in the driver class to demonstrate the working of the Dungeon. The user can define the size of the dungeon by passing in values for the row and column values for the dungeon creation along with the interconnectivity and wrapping constraints. The user can specify the percentage of caves that have treasure/ the number of locations that have arrows and also the number of Otyughs in the Dungeon. The driver performs various actions to demonstrate the features mentioned above.
The Text based game is very descriptive and asks you to enter input by showing what options you can choose and thus choosing each option will further prompt you how the move mutated the state of the game.
In the GUI based game the game starts with a start screen and you reach the home screen or the dungeon configuration screen by clicking on the middle tile. The configuration screen takes in values for the Player's name and configurations of the dungeon and lets the user choose an avatar from the available 4 avatars, then you enter the game where you can initially see the the start location and you can see the entire dungeon by exploring the adjacent locations.
Controls to play the game:
- Move:
You can click on adjacent locations to move if there's a path.
You can use the arrow keys to move in the corresponding direction if there's a path.
- Pick:
"a" key press picks an arrow.
"s" key press picks a sapphire.
"d" key press picks a diamond.
"r" key press picks a ruby.
- Shoot:
"f" key press to activate or deactivate the shoot mode (or fire arrows).
After activating the shoot mode if you need to give the direction to fire the arrows by using the arrow keys and then a popup asks for the number of caves you want to shoot to, entering the value here will complete the shoot operation and the shoot mode automatically deactivates.
There's a Menu with options such as :
- Home which takes you to the Dungeon configuration screen where you can reconfigure your dungeon and change your avatar if you need to.
- Restart which in turn has two sub options to restart with either the same dungeon or a new dungeon with the same configuration.
- Player description that gives the description of the player and the items collected by the player at any point of time
- Location Description that gives the description of the location and the items in the location.
- Quit closes the game.

## Description of Examples:
The screenshots attached shows all possible actions that a player can take and also the screenshots show what to expect if each action is made. The images show all the screens and the controls on each screen and what click on each of them will do.
Some of the scenarios shown in the images:
- Configuration of the dungeon.
- Player choosing an avatar.
- Player picking arrow, sapphire, ruby and a diamond.
- player shooting arrows.
- Player reaching the end location.
- player getting eaten by an Otyugh.
- The player's location description at a point of time.
- The player's description at a point of time. 
- The Menu options.

## Design/Model Changes:
- Created a View interface, GUI controller inteface and their implementing classes.
- created a few package private classes that extend the Java swing components that are used by the Dungeon view.
- Added an functionality to reset the dungeon to the initial state.
- Added the functionality to regenerate the dungeon.

## Assumptions:
- There will only be 3 types of treasures.
- The dungeon has to be created with at least 6 rows and 6 columns.
- The player can choose not to pick the treasure.
- The player can pick only one of the collectibles at a time.
- The player will always start with 3 arrows.
- There will always be an Otyugh at the end location and there wont be any Otyugh at the start location.
- The treasures can only be found in caves but the arrows can be found in both caves and tunnels.
- The Otyughs can only be found in caves.
- If the number of monsters to be placed in the dungeon is greater than the number of caves in the dungeon, all the caves except the start cave will be assigned a monster.

## Limitations:
- The player has to pick multiple times to collect all of the treasure or arrows.
- Player starts with 3 arrows at the beginning of the game.
- The player cannot shoot arrows at the monster at the player's current location.
- If the number of monsters to be placed in the dungeon is greater than the number of caves in the dungeon, all the caves except the start cave will be assigned a monster.

## Citations:
- https://www.programiz.com/java-programming/library/hashmap/merge 
- https://www.baeldung.com/java-breadth-first-search
- https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
- https://www.geeksforgeeks.org/java-swing-simple-user-registration-form/
