import model.{Player, WhiteCard}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class model_PlayerSpec extends AnyWordSpec with Matchers {
 "A player" when { "new" should {
   val player = Player("Your Name", List[WhiteCard](), isOnIt = true)
   "have a name" in {
     player.name should be("Your Name")
   }
   "have a nice String representation" in {
     player.toString should be("Your Name")
   }
   "have a current state" in {
     player.isOnIt should be (true)
   }
 }

 }
}
