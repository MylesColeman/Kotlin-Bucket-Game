package uk.ac.tees.e4109732.mam_bucketgame

import com.badlogic.gdx.Gdx
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.graphics.use

class MainMenuScreen(val game: Main) : KtxScreen {
    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f)

        game.viewport.apply()
        game.batch.use { batch ->
            batch.projectionMatrix = game.viewport.camera.combined

            game.font.draw(batch, "Welcome to Drop!", 1f, 1.5f)
            game.font.draw(batch, "Tap anywhere to begin!", 1f, 1f)
        }

        if (Gdx.input.isTouched) {
            game.addScreen(GameScreen(game))
            game.setScreen<GameScreen>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
    }

    override fun resize(width: Int, height: Int) {
        game.viewport.update(width, height, true)
    }
}
