package model

case class Player(name: String, var cards: List[WhiteCard], isOnIt: Boolean) {

  override def toString: String = {name + "State: " + isOnIt}

  def addCard(whiteCard: WhiteCard): Player = {
    val newList = cards :+ whiteCard
    copy(name, newList)
  }

}
