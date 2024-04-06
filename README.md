# My Personal Project

## Task 2, Description
For my personal project, I plan on creating a basic aim training application. If a user were to run this application,
the first thing that will show up is a menu screen with various game modes to choose from (I am not entirely sure what
these game modes will be yet, but possibly one continuous mode, and one timed mode, or others). Once a game mode is
chosen, the user will spawn into a 2D interface that, once a key is pressed or a mouse click occurs, will begin
generating targets, but there will be a grace period to get ready. The targets will only show up for a few seconds before the user misses their opportunity to hit
them. There will be a timer at the top, an accuracy indicator, and possibly a lives tracker (for a more challenging
game mode). As well, there may be a lifetime statistics tracker. The application is targeted towards users who play
video games with a mouse and keyboard input (although I may try adding controller support on my own after the term).
I am interested in this project because I play video games on PC, and the video games that I play are mostly FPS games.
Typically, I use a controller as my input device, but I want to switch to mouse and keyboard full-time. So, this
application would help me, as well as others who either want to switch to mouse and keyboard or want to practice their
aim, in transitioning.

## Task 3, User Stories:
- As a user, I want to be able to add a target that has been to a list of hit targets
- As a user, I want to be able to add a target that has not been hit a list of non-hit targets
- As a user, I want to be able to add a list of the rounds I have played to a class that analyzes the data and produces statistics
- As a user, I want to be able to select a previously saved aim training session and view all of its corresponding
  statistics
- As a user, I want to be able to select moving targets to shoot or stationary targets to shoot
- As a user, I want to be able to select a previous aim training session that has been automatically saved (the user
  can manually save the aim training sessions that they want to keep for however long, but the automatically saved aim
  training sessions disappear after more aim training sessions have been logged) and view all of its corresponding
  statistics
- As a user, I want to be able to select multiple previously automatically / manually saved aim training sessions
  and compare all of their statistics with each other
- As a user, I want to be able to change the color, size, and speed (if the moving targets option is chosen) of the targets
- As a user, I want to be able to view a graph of my individual session statistics and a graph of my lifetime statistics
- As a user, I want to be able to stop the aim training session and have the option to save the state of session
- As a user I want to be able to have the option to reload a previously state-saved aim training session and resume the session

## Instructions For Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by playing any game mode, and 
  clicking a target, and this will add a Target (X) to a HitTargets (Y). 
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by pressing
  the "Display All Hit Targets" button after finishing a game, and specifying the maximum target size to filter by, and
  this will display all the targets (X) that have been hit and added to a HitTargets (Y). To view all the X's in the Y,
  specify a value of 0 to filter by.
- You can locate my visual component by playing any game mode, or pressing the Statistics button, then the View Progress button,
  to view a bar graph of the progress made of the saved sessions. Note that the bars are sorted from newest saved session to
  oldest saved session.
- You can save the state of my application by pressing the Quit button during a game, or losing a game,
  then pressing the Save Session button in the game over screen.
- You can reload the state of my application by pressing the Statistics button, then pressing the
  View Previous Session button. This will load the state and show the statistics and info associated with the most recent saved session.
  
## Phase 4: Task 2
Target at position (650, 650) is hit \
Added hit target at position (650, 650) to hit targets array \
Target at position (451, 451) is hit \
Added hit target at position (451, 451) to hit targets array \
Target at position (527, 527) is hit \
Added hit target at position (527, 527) to hit targets array \
Target at position (642, 642) is hit \
Added hit target at position (642, 642) to hit targets array \
Target at position (138, 138) is hit \
Added hit target at position (138, 138) to hit targets array \
Target at position (136, 136) is hit \
Added hit target at position (136, 136) to hit targets array \
Target at position (459, 459) is hit \
Added hit target at position (459, 459) to hit targets array \
Target at position (210, 210) is hit \
Added hit target at position (210, 210) to hit targets array \
Filtered all hit targets without a radius greater than 40000
