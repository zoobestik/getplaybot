package me.telegram.getplaybot.handles

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.JPEGTranscoder
import org.telegram.telegrambots.api.methods.send.SendPhoto
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

val svg = """<?xml version='1.0' standalone='no'?>
<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'>
<svg width='100%' height='100%' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'>
   <title>...</title>
   <desc>...</desc>
   <g id='columnGroup'>
      <rect x='65' y='10' width='75' height='110' fill='gainsboro'/>
      <rect x='265' y='10' width='75' height='110' fill='gainsboro'/>

      <text x='30' y='30' font-size='18px' font-weight='bold' fill='crimson'>
         <tspan x='30' dy='1.5em'>Q1</tspan>
         <tspan x='30' dy='1em'>Q2</tspan>
         <tspan x='30' dy='1em'>Q3</tspan>
         <tspan x='30' dy='1em'>Q4</tspan>
      </text>

      <text x='100' y='30' font-size='18px' text-anchor='middle'>
         <tspan x='100' font-weight='bold' fill='crimson'>Sales</tspan>
         <tspan x='100' dy='1.5em'>223</tspan>
         <tspan x='100' dy='1em'>183</tspan>
         <tspan x='100' dy='1em'>277</tspan>
         <tspan x='100' dy='1em'>402</tspan>
      </text>

      <text x='200' y='30' font-size='18px' text-anchor='middle'>
         <tspan x='200' font-weight='bold' fill='crimson'>Expenses</tspan>
         <tspan x='200' dy='1.5em'>195</tspan>
         <tspan x='200' dy='1em'>70</tspan>
         <tspan x='200' dy='1em'>88</tspan>
         <tspan x='200' dy='1em'>133</tspan>
      </text>

      <text x='300' y='30' font-size='18px' text-anchor='middle'>
         <tspan x='300' font-weight='bold' fill='crimson'>Net</tspan>
         <tspan x='300' dy='1.5em'>28</tspan>
         <tspan x='300' dy='1em'>113</tspan>
         <tspan x='300' dy='1em'>189</tspan>
         <tspan x='300' dy='1em'>269</tspan>
      </text>
   </g>

   <g id='rowGroup' transform='translate(0, 150)'>
      <rect x='25' y='40' width='310' height='20' fill='gainsboro'/>
      <rect x='25' y='76' width='310' height='20' fill='gainsboro'/>

      <text x='30' y='30' font-size='18px' font-weight='bold' fill='crimson' text-anchor='middle'>
         <tspan x='100'>Sales</tspan>
         <tspan x='200'>Expenses</tspan>
         <tspan x='300'>Net</tspan>
      </text>

      <text x='30' y='30' font-size='18px' text-anchor='middle'>
         <tspan x='30' dy='1.5em' font-weight='bold' fill='crimson' text-anchor='start'>Q1</tspan>
         <tspan x='100'>223</tspan>
         <tspan x='200'>195</tspan>
         <tspan x='300'>28</tspan>
      </text>

      <text x='30' y='30' font-size='18px' text-anchor='middle'>
         <tspan x='30' dy='2.5em' font-weight='bold' fill='crimson' text-anchor='start'>Q2</tspan>
         <tspan x='100'>183</tspan>
         <tspan x='200'>70</tspan>
         <tspan x='300'>113</tspan>
      </text>

      <text x='30' y='30' font-size='18px' text-anchor='middle'>
         <tspan x='30' dy='3.5em' font-weight='bold' fill='crimson' text-anchor='start'>Q3</tspan>
         <tspan x='100'>277</tspan>
         <tspan x='200'>88</tspan>
         <tspan x='300'>189</tspan>
      </text>

      <text x='30' y='30' font-size='18px' text-anchor='middle'>
         <tspan x='30' dy='4.5em' font-weight='bold' fill='crimson' text-anchor='start'>Q4</tspan>
         <tspan x='100'>402</tspan>
         <tspan x='200'>133</tspan>
         <tspan x='300'>269</tspan>
      </text>
   </g>

   <text x='370' y='30' font-size='16px'>
      <tspan x='370'>There are no 'table'-type elements in SVG, but you can</tspan>
      <tspan x='370' dy='1em'>achieve a similar visual and interactive effect using the</tspan>
      <tspan x='370' dy='1em'>'text' and 'tspan' elements.</tspan>
      <tspan x='370' dy='1.5em'>On the left are 2 such tabular representations, the top</tspan>
      <tspan x='370' dy='1em'>one with columnar layout (that is, the user can select all</tspan>
      <tspan x='370' dy='1em'>the text in a column), and the bottom table with row-</tspan>
      <tspan x='370' dy='1em'>based layout.</tspan>
      <tspan x='370' dy='1.5em'>An obvious disadvantage to this approach is that you</tspan>
      <tspan x='370' dy='1em'>cannot create a table with both vertical and horizontal</tspan>
      <tspan x='370' dy='1em'>selectivity.</tspan>
      <tspan x='370' dy='1.5em'>A less obvious flaw is that creating a tabular appearance</tspan>
      <tspan x='370' dy='1em'>does not confer the semantic qualities of a real table,</tspan>
      <tspan x='370' dy='1em'>which is disadvantageous to accessibility and is not</tspan>
      <tspan x='370' dy='1em'>conducive to rich interactivity and navigation.</tspan>
   </text>
</svg>
"""

suspend fun handleScores(message: SendPhoto) {
    val image = JPEGTranscoder()

    image.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, 900f)
    image.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, 300f)
    image.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f)

    val resultByteStream = ByteArrayOutputStream()
    val input = TranscoderInput(ByteArrayInputStream(svg.toByteArray()))
    val output = TranscoderOutput(resultByteStream)

    image.transcode(input, output)

    val photo = ByteArrayInputStream(resultByteStream.toByteArray())
    message.setNewPhoto("scores.png", photo)

    resultByteStream.flush()
    resultByteStream.close()
}

//val league = get("champions-2017") ?:
//        return scores.getOrDefault("no-league-found", "no-league-found")
//
//val body = league.teams.map {
//    "<tr><td>${it.id}</td></tr>"
//}
//
//return "<table>$body</table>"