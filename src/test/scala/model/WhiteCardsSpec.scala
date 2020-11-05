package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class WhiteCardsSpec extends AnyWordSpec with Matchers{
  "A WhiteCard" should { "new" should {
    val whitecard = WhiteCards("DefaultText")
    "have a Text" in {
      whitecard.text should be("DefaultText")
    }
    "have a nice string representation" in {
      whitecard.toString should be("DefaultText")
    }
    }
  }
}

