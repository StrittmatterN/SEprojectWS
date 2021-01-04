package module

import com.google.inject.AbstractModule
import model._
import net.codingwell.scalaguice.ScalaModule

class Module extends AbstractModule with ScalaModule {

  override def configure(): Unit = {

    bind[ModelInterface].toInstance(GameTable())
  }

}
