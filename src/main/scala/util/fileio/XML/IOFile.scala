package util.fileio.XML

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
    // This shit doesnt work yet
    /**
    val printWriter = new PrintWriter(new File("CardDeck.xml"))
    val blacks = <CardDeck>{modelInterface.getDeck.blacks.map(x => toXML(x))}</CardDeck>
    val whites = <CardDeck>{modelInterface.getDeck.blacks.map(x => toXML(x))}</CardDeck>
    for (x <- blacks) printWriter.write("<CardDeck>" + x.toString() + "\n")
    for (x <- whites) printWriter.write(x.toString() + "\n" + "<\\CardDeck>")
    printWriter.close()
     */
  }
  def toXML(x: String): Node = <card><text>{x}</text></card>
}
