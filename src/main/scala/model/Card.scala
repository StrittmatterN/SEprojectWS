package model

import scala.xml.Node



trait Card {
  def printCard()
  def createNewCard (text: String): Card
  def toXML: Node
}

// Factory Method

object Card {
  def apply(text: String): Card = {
    if (text.contains("_")) BlackCard(text)
    else WhiteCard(text)
  }
}

// Composite

case class Cards(cardList: List[Card]) extends Card {

  def getBlacksAndWhites: List[Card] = {
    var composite = List[Card]()
    for (x <- cardList) {
      if (x.toString.contains("_") || x.toString.contains("?")) composite = composite :+ BlackCard(x.toString)
      else composite = composite :+ WhiteCard(x.toString)
    }
    composite
  }

  def printCard(): Unit = cardList.foreach((c: Card) => c.printCard())

  def addNew(card: Card): Cards = {
    copy(cardList = cardList :+ card)
  }

  def remove(card: Card): Cards = copy(cardList.filterNot(_ == card))

  def dropFirst: Option[Card] = Some(cardList.head)

  override def createNewCard(text: String): Card = Card.apply(text)

  override def toXML: Node = <card>{cardList.head.printCard()}</card>
}
