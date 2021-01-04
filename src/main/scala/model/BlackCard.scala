package model
import scala.xml.Node

case class BlackCard(text1: String) extends Card {

  override def toString: String = text1

  override def printCard(): Unit = println(text1)

  override def createNewCard(text1: String): Card = BlackCard(text1)

  def toXML(): Node = <card><text>{text1}</text></card>
}
