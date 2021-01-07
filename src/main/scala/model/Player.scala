package model

case class Player(name: String, var cards: List[WhiteCard], isOnIt: Boolean) {

  override def toString: String = name

}
