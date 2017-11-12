package quest

import config.Config
import item.ElementType.ElementType
import item.{Item, RandomItemFactory}
import unit.{GameUnit, Monster}

/**
  * Created by nol on 05/11/17.
  */
class Quest(monster: Monster, loot: Seq[Item]) {
  private var completed: Boolean = false
  private val uniqueID = Quest.getNewUniqueQuestID

  def isCompleted: Boolean = completed

  def complete(): Unit = {
    completed = true
  }

  def getUniqueID: Long = uniqueID

  def getMaxDuration: Int = Config.getQuestMaxDuration

  def getMonster: Monster = monster

  def getLoot: Seq[Item] = loot
}

object Quest {

  private var questID: Long = 0

  def getNewUniqueQuestID: Long = {
    questID += 1
    questID
  }

  def createLoot(level: Int, elementType: ElementType): Seq[Item] = {
    val w = RandomItemFactory.getRandomWeapon(level)
    val a = RandomItemFactory.getRandomArmor(level)
    val c = RandomItemFactory.getRandomCharm(level)
    Seq(w, a, c)
  }

  def createQuest(level: Int): Quest = {
    val monster = GameUnit.generateMonster(level)
    val loot = Quest.createLoot(level, monster.getAttackElementType)
    new Quest(monster, loot)
  }
}
