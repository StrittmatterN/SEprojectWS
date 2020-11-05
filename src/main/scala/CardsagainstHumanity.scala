import model.{BlackCards, Player, WhiteCards}

object CardsagainstHumanity {
  def main(args: Array[String]): Unit = {
    println("Cards against Humanity! by Niklas and Paul")

    val player1 = Player("Niklas")
    val testBC = BlackCards("___ ist sehr cool")
    val testWC = WhiteCards("Barack Obama")

    println(player1.toString)
    println(testBC.toString)
    println(testWC.toString)

  }
}
