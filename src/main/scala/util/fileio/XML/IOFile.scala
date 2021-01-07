package util.fileio.XML

import java.io.{File, PrintWriter}

import model._
import util.fileio.IOInterface

import scala.xml._

class IOFile extends IOInterface {
  override def load(gameTable: ModelInterface): ModelInterface = {
    val xmlFile = XML.loadFile("CardDeck.xml")
    val slice = xmlFile \\ "text"
    val cardDeck = CardDeck(List[String](), List[String]())
    for (x <- slice) {
      if (x.text.contains("_")) cardDeck.blacks = cardDeck.blacks :+ x.text
      else cardDeck.whites = cardDeck.whites :+ x.text
    }
    gameTable.setCardDeck(cardDeck)
  }

  override def save(modelInterface: ModelInterface): Unit = {
    val printWriter = new PrintWriter(new File("CardDeck.xml"))
    val blacks = {modelInterface.getDeck.blacks.map(x => toXML(x))}
    val whites = {modelInterface.getDeck.whites.map(x => toXML(x))}
    printWriter.write("<CardDeck>\n")
    for (x <- blacks) printWriter.write("\t" + x.toString().replace("&quot;", "\"") +" \n")
    for (x <- whites) printWriter.write("\t" + x.toString().replace("&quot;", "\"")+ "\n")
    printWriter.write("\n</CardDeck>")
    printWriter.close()
  }
  def toXML(x: String): Node = <card><text>{x}</text></card>
}
