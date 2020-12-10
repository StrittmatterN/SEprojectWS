package model

// Factory Method

trait Card {
  def printCard()
  def createNewCard (text: String): Card
}

object Card {
  def apply(text: String): Card = {
    if (text.contains("_")) BlackCard(text)
    else WhiteCard(text)
  }
}

case class Cards(cardList: List[Card]) {

  def printCard(): Unit = cardList.foreach((c: Card) => c.printCard())

  def addNew(card: Card): Cards = {
    val tmp = cardList :+ card
    copy(tmp)
  }

  def remove(card: Card): Cards = copy(cardList.filterNot(_ == card))
}
