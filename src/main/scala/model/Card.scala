package model

import scala.xml.Node

// Factory Method

trait Card {
  def printCard()
  def createNewCard (text: String): Card
  def toXML: Node
}

object Card {
  def apply(text: String): Card = {
    if (text.contains("_")) BlackCard(text)
    else WhiteCard(text)
  }
}

// Composite

case class Cards(cardList: List[Card]) extends Card {

  def printCard(): Unit = cardList.foreach((c: Card) => c.printCard())

  def addNew(card: Card): Cards = {
    copy(cardList = cardList :+ card)
  }

  def remove(card: Card): Cards = copy(cardList.filterNot(_ == card))

  def dropFirst: Option[Card] = Some(cardList.head)

  override def createNewCard(text: String): Card = Card.apply(text)

  override def toXML: Node = <card>{cardList.head.printCard()}</card>
}
