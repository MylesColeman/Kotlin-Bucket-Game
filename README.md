# MAM_BucketGame (WIP)

This repository serves as a practical introduction to **Kotlin** and **Android Studio**, focusing on the fundamental components of mobile game development as part of my **Mobile and Multiplayer** module at **Teesside University**.

*This repository will eventually be merged into a comprehensive Mobile & Multiplayer game demo collection.*

---

## Technical Focus
* **Mobile Input Systems**: Implementation of touch-based controls and coordinate mapping for mobile screens.
* **Game Loop Management**: Establishing a consistent update/render cycle using the LibGDX framework.
* **Resource Handling**: Managing assets including textures and audio (SFML-style logic translated to Kotlin).

## Project Structure
- `Main.kt`: Entry point for the game logic and screen management.
- `MainMenuScreen.kt`: Handling UI states and transitions.
- `assets/`: Contains core 2D sprites and audio files used for collision feedback.

---

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a Kotlin project template that includes Kotlin application launchers and [KTX](https://libktx.github.io/) utilities.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `android`: Android mobile platform. Needs Android SDK.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `android:lint`: performs Android project validation.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
