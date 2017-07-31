package me.telegram.getplaybot.lib

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.JPEGTranscoder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

suspend fun svgToJpg(svg: String): InputStream {
    val image = JPEGTranscoder()

    image.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, 900f)
    image.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, 300f)
    image.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f)

    val resultByteStream = ByteArrayOutputStream()
    val input = TranscoderInput(ByteArrayInputStream(svg.toByteArray()))
    val output = TranscoderOutput(resultByteStream)

    image.transcode(input, output)

    val photo = ByteArrayInputStream(resultByteStream.toByteArray())

    resultByteStream.flush()
    resultByteStream.close()

    return photo
}
