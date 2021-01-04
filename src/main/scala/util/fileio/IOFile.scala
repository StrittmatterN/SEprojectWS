package util.fileio
import java.io.{File, PrintWriter}
import scala.xml._
import model._

class IOFile extends IOInterface {
  override def load(gameTable: ModelInterface): ModelInterface = {
    val xmlFile = XML.loadFile("CardDeck.xml")
    val slice = xmlFile \\ "text"
    val cardDeck = CardDeck(List[String](), List[String]())
    for (x <- slice) {
      if (x.contains("_")) cardDeck.blacks :+ BlackCard(x.text)
      else cardDeck.whites :+ WhiteCard(x.text)
    }
    gameTable.setCardDeck(cardDeck)
  }

  override def save(modelInterface: ModelInterface): Unit = {
    val printWriter = new PrintWriter(new File("CardDeck.xml"))
    val blackCardsXML = <CardDeck>{modelInterface.getWhitesOrBlacks("blacks").map(x => x.toXML())}</CardDeck>
    val whiteCardsXML = <CardDeck>{modelInterface.getWhitesOrBlacks("whites").map(x => x.toXML())}</CardDeck>
    printWriter.write(blackCardsXML.toString())
    printWriter.write(whiteCardsXML.toString())
  }
}
