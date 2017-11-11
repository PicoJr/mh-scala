package quest

import config.Config
import item.ElementType.ElementType
import item.{Item, RandomItemFactory}
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
