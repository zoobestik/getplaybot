package me.telegram.getplaybot.lib.convert

import javafx.application.Platform
import javafx.embed.swing.SwingFXUtils
import javafx.scene.web.WebView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun htmlToImage(html: String) = suspendCoroutine<InputStream> { cont ->
    Platform.runLater {
        try {
            val browser = WebView()
            browser.isContextMenuEnabled = false
            browser.setPrefSize(800.0, 80.0)
            browser.engine.loadContent(html, "text/html")
            val snapshot = browser.snapshot(null, null)
            val image = SwingFXUtils.fromFXImage(snapshot, null)
            val imageOutput = ByteArrayOutputStream()
            ImageIO.write(image, "png", imageOutput)
            val inputStream = ByteArrayInputStream(imageOutput.toByteArray())
            cont.resume(inputStream)
        } catch (e: Exception) {
            cont.resumeWithException(e)
        }
    }
}