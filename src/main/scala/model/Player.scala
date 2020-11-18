package model

case class Player(name: String, cards: List[WhiteCard]) {
  override def toString: String = name
}
