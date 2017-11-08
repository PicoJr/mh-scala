package unit

/**
  * Created by nol on 05/11/17.
  */
class Monster(name: String, life: Int) extends GameUnitDefault(name: String, life: Int) {
}

object Monster {

  def generateName(): String = {
    "not so random" // TODO let the player rename monsters
  }

  def generateLife(level: Int): Int = {
    42 // TODO make it random but level dependant
  }

  def generateMonster(level: Int): Monster = {
    new Monster(generateName(), generateLife(level))
  }
}
