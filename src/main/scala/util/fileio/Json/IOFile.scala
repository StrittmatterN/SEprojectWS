package util.fileio.Json

import java.io.{File, PrintWriter}

import model.{CardDeck, ModelInterface}
import util.fileio.IOInterface
import play.api.libs.json._

import scala.io.Source

class IOFile extends IOInterface {
  override def load(modelInterface: ModelInterface): ModelInterface = {
    val source = Source.fromFile("CardDeck.json")
    val jsonFile: String = source.getLines().mkString
    val json: JsValue = Json.parse(jsonFile)
    val items = json \\ "card"
    val cardDeck = CardDeck(List[String](), List[String]())
    for (x <- items) {
      if (x.toString().contains("_") || x.toString().contains("?"))
        cardDeck.blacks = cardDeck.blacks :+ x.toString().replace("\"","")
      else cardDeck.whites = cardDeck.whites :+ x.toString().replace("\"", "")
    }
    source.close()
    modelInterface.setCardDeck(cardDeck)
  }

  override def save(modelInterface: ModelInterface): Unit = {
    val printWriter = new PrintWriter(new File("CardDeck.json"))
    printWriter.write(Json.prettyPrint(toJson(modelInterface)))
    printWriter.close()
  }

  def toJson(modelInterface: ModelInterface): JsObject = {
    var combined = modelInterface.getDeck.whites ::: modelInterface.getDeck.blacks
    Json.obj(
      "cards" -> Json.toJson(for{x <- combined} yield {
        Json.obj("card" -> JsString(x))
      })
    )
  }
}
