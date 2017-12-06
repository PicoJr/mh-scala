package game.unit

/**
  * Created by nol on 05/11/17.
  */
abstract class DefaultGameUnit(name: String) extends GameUnit {

  private var _name: String = name

  override def getName: String = _name

  override def setName(newName: String): Unit = {
    _name = newName
  }

}






