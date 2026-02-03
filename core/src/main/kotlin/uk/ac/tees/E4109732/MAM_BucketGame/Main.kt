package uk.ac.tees.E4109732.MAM_BucketGame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.graphics.use
import kotlin.collections.set

class Main : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

class FirstScreen : KtxScreen {
    private val backgroundTexture =
        loadTexture("background.png")
            .apply {
                setFilter(Linear, Linear)
            }

    private val backgroundMusic = Gdx.audio.newSound("music.mp3".toInternalFile())
    private val bucketTexture =
        loadTexture("bucket.png").apply {
            setFilter(Linear, Linear)
        }

    private val bucketSprite =
        Sprite(bucketTexture).apply {
            setSize(1f, 1f)
        }



    private val dropTexture =
        loadTexture("drop.png")
            .apply {
                setFilter(Linear, Linear)
            }

    private val dropSound = Gdx.audio.newSound("drop.mp3".toInternalFile())
    private val viewPort = FitViewport(8f, 5f)

    private val batch = SpriteBatch()

    override fun render(delta: Float) {
        handleInput()
        update(delta)
        draw(delta)
    }

    fun draw(delta: Float) {
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)

        batch.use {
            it.projectionMatrix = viewPort.camera.combined

            it.draw(backgroundTexture, 0f, 0f, viewPort.worldWidth, viewPort.worldHeight)

            bucketSprite.draw(it)
        }
    }

    private fun handleInput() {
        val touchPosition = Vector2()

        if(Gdx.input.isTouched()) {
            with(Gdx.input) {
                touchPosition.set(x.toFloat(), y.toFloat())
                viewPort.unproject(touchPosition)
                bucketSprite.setCenterX(touchPosition.x)
            }
        }
    }

    fun update(delta: Float) {

    }

    override fun resize(width: Int, height: Int) {
        viewPort.update(width, height, true)
    }

    override fun dispose() {
        backgroundTexture.disposeSafely()
        bucketTexture.disposeSafely()
        dropSound.disposeSafely()
        batch.disposeSafely()
    }

    companion object {
        private fun loadTexture(fileName: String) = Texture(fileName.toInternalFile(), true)
    }
}
