package uk.ac.tees.e4109732.mam_bucketgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.graphics.use

class Main : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

class FirstScreen : KtxScreen {
    private val backgroundTexture = loadTexture("background.png")
    private val backgroundMusic = Gdx.audio.newMusic("music.mp3".toInternalFile())

    private val bucketTexture = loadTexture("bucket.png")
    private val bucketSprite = Sprite(bucketTexture).apply { setSize(1f, 1f) }
    private val bucketRectangle = Rectangle()

    private var dropTimer = 0f
    private val dropTexture = loadTexture("drop.png")
    private val dropSprites = com.badlogic.gdx.utils.Array<Sprite>()
    private val dropRectangle = Rectangle()
    private val dropSound = Gdx.audio.newSound("drop.mp3".toInternalFile())

    val touchPosition = Vector2()
    private val viewPort = FitViewport(8f, 5f)
    private val batch = SpriteBatch()

    override fun show() {
        backgroundMusic.isLooping = true
        backgroundMusic.volume = 0.5f
        backgroundMusic.play()
    }

    override fun render(delta: Float) {
        handleInput()
        update(delta)
        draw()
    }

    fun draw() {
        viewPort.apply()
        clearScreen(0f, 0f, 0f)

        batch.use {
            it.projectionMatrix = viewPort.camera.combined

            it.draw(backgroundTexture, 0f, 0f, viewPort.worldWidth, viewPort.worldHeight)

            bucketSprite.draw(it)

            for (dropSprite in dropSprites) {
                dropSprite.draw(it)
            }
        }
    }

    private fun handleInput() {
        if(Gdx.input.isTouched) {
            with(Gdx.input) {
                touchPosition.set(x.toFloat(), y.toFloat())
                viewPort.unproject(touchPosition)
                bucketSprite.setCenterX(touchPosition.x)
            }
        }
    }

    fun update(delta: Float) {
        bucketSprite.x = MathUtils.clamp(bucketSprite.x, 0f, viewPort.worldWidth - bucketSprite.width)

        updateDroplets(delta)

        dropTimer += delta
        if (dropTimer > 1f) {
            dropTimer = 0f
            createDroplet()
        }
    }

    private fun createDroplet() {
        dropSprites.add(
            Sprite(dropTexture).apply {
                setSize(DROP_SIZE, DROP_SIZE)
                x = MathUtils.random(0f, viewPort.worldWidth - DROP_SIZE)
                y = viewPort.worldHeight
            }
        )
    }

    private fun updateDroplets(delta: Float) {
        bucketRectangle.set(bucketSprite.x, bucketSprite.y, bucketSprite.width, bucketSprite.height)

        for (i in dropSprites.size - 1 downTo 0) {
            val dropSprite = dropSprites.get(i)

            dropSprite.translateY(-2f * delta)
            dropRectangle.set(dropSprite.x, dropSprite.y, dropSprite.width, dropSprite.height)

            if (dropSprite.y < -dropSprite.height) { dropSprites.removeIndex(i) }
            else if (bucketRectangle.overlaps(dropRectangle)) {
                dropSprites.removeIndex(i)
                dropSound.play()
            }
        }
    }

    override fun resize(width: Int, height: Int) {
        viewPort.update(width, height, true)
    }

    override fun dispose() {
        backgroundTexture.disposeSafely()
        bucketTexture.disposeSafely()
        dropTexture.disposeSafely()
        dropSound.disposeSafely()
        backgroundMusic.disposeSafely()
        batch.disposeSafely()
    }

    companion object {
        private const val DROP_SIZE = 1f

        private fun loadTexture(fileName: String) = Texture(fileName.toInternalFile(), true).apply { setFilter(Linear, Linear) }
    }
}
