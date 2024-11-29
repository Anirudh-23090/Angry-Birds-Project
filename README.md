# AngryBirdsProject

## Setting up the Project:
Build a gradle project with LibGDX.
Replace the src files and assets of the Project with the our src and asset files.
Add the gradle dependencies from the repository in your gradle project.

## Run the Project:
Open the project in IntelliJ.
Click on the run button or the run button with the following path:
Gradle -> <Project_Name> -> Tasks -> application -> run

## Testing the Project:
The visual elements and components are quite self-explanatory.
The different types of buttons used to navigate are:
Play, GoToMainMenu, SaveAndExit, Load, Retry, Pause, Music, Back, Different Levels, NextLevel, etc.
Test the project by working with the buttons, the gameplay, and the implementation. We have kept the debugrenderer on so that it is easier to understand what the bodies are doing, we are also printing the velocities to the console. 

## About the Project:
The project is a try at replicating the famous Angry Birds game to learn about LibGDX, Object Oriented Programming, Serialisation, and JUnit testing. 

## Contributors
Anirudh Bharatiya - 2023090 and Arjun Chetan Pandya - 2023120.
Team Name - TLE-Haters

## Credits:
To remove background from images: https://www.remove.bg/.
To crop the image with rounded corners: Google Search.
Images of different elements and components from Original Game, YT and Google Search (No particular website).
Music from Google Search.

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.


## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
