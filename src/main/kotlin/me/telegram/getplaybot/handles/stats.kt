package me.telegram.getplaybot.handles

import me.telegram.getplaybot.lib.svgToJpg
import org.telegram.telegrambots.api.methods.send.SendPhoto

val svg = """<?xml version='1.0' standalone='no'?>
<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'>
<svg width='100%' height='100%' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'>
   <title>...</title>
   <desc>...</desc>
   <text x='30' y='30' font-size='16px'>
      <tspan x='30'>There are no 'table'-type elements in SVG, but you can</tspan>
   </text>
</svg>
"""

suspend fun handleScores(message: SendPhoto) {
    message.setNewPhoto("scores.png", svgToJpg(svg))
}

//suspend fun handleScores(message: SendPhoto) {
//    JFXPanel()
//    message.setNewPhoto("aaa.png", htmlToImage(html))
//}

//val league = get("champions-2017") ?:
//        return scores.getOrDefault("no-league-found", "no-league-found")
//
//val body = league.teams.map {
//    "<tr><td>${it.id}</td></tr>"
//}
//
//return "<table>$body</table>"