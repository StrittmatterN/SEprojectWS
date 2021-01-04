package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BlackCardsSpec extends AnyWordSpec with Matchers{
  "A BlackCard" should { "new" should {
    val blackcard = BlackCard("DefaultText")
    "have a Text" in {
      blackcard.text1 should be("DefaultText")
    }
    "have a nice string representation" in {
      blackcard.toString should be("DefaultText")
    }
    }
  }
}
