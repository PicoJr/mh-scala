package game.unit

/**
  * Created by nol on 06/12/17.
  */
trait MonsterFactory {
  def createMonster(level: Int, name: String): Monster
}
