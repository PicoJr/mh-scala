package game.unit

/**
  * Created by nol on 06/12/17.
  */
trait MonsterFactory {
  def generateMonster(level: Int, name: String): Monster
}
