import Config.Config
import item.ElementType.ElementType
import item.Item
import unit.{GameUnit, Monster}

/**
  * Created by nol on 05/11/17.
  */
class Quest(monster: Monster, loot: Seq[Item]) {
  def getMonster: Monster = monster

  def getLoot: Seq[Item] = loot
}

object Quest {
  val MAX_DURATION: Int = Config.getQuestMaxDuration

  def createLoot(level: Int, elementType: ElementType): Seq[Item] = {
    Seq.empty // TODO make it random
  }

  def createQuest(level: Int): Quest = {
    val monster = GameUnit.generateMonster(level)
    val loot = Quest.createLoot(level, monster.getAttackElementType)
    new Quest(monster, loot)
  }
}
