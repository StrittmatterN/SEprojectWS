
import aview.{Gui, Tui}
import com.google.inject.{Guice, Injector}
import control.Controller
import module.Module

import scala.io.StdIn.readLine

object CardsagainstHumanity {
  val injector: Injector = Guice.createInjector(new Module)
  val controller: Controller = injector.getInstance(classOf[Controller])
  val tui = new Tui(controller)
  val gui = new Gui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = "0"
    gui.open()
    if (args.length > 0) input = args(0)
    else do {
      input = readLine()
      tui.processInput(input)
    } while (input != 'q')
  }
}
