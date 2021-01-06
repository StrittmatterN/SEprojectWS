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
      if (x.text.contains("_")) cardDeck.blacks = cardDeck.blacks :+ x.text
      else cardDeck.whites = cardDeck.whites :+ x.text
    }
    gameTable.setCardDeck(cardDeck)
  }

  override def save(modelInterface: ModelInterface): Unit = {
  }
}
