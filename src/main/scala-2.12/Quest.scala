import item.Item
import unit.Monster

/**
  * Created by nol on 05/11/17.
  */
class Quest(monster: Monster, loot: Seq[Item]) {
  def getMonster: Monster = monster

  def getLoot: Seq[Item] = loot
}

object Quest {
  val MAX_DURATION: Int = 100 // TODO load it from config

  def createLoot(level: Int): Seq[Item] = {
    Seq.empty // TODO make it random
  }

  def createQuest(level: Int): Quest = {
    new Quest(Monster.generateMonster(level), createLoot(level))
  }
}
