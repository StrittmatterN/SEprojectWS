
import model.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class PlayerSpec extends AnyWordSpec with Matchers {
 "A player" when { "new" should {
   val player = Player("Your Name")
   "have a name" in {
     player.name should be("Your Name")
   }
   "have a nice String representation" in {
     player.toString should be("Your Name")
   }
 }

 }
}
