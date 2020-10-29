case class card(x: String)
val card1 = card("SampleText")
card1.x

case class player(zar: Boolean, points: Int, nbr: Int, cards: card)
val player1 = player(true, 2, 1, card1)
player1.points
player1.cards.x