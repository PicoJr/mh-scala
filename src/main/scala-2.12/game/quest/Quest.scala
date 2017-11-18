package game.quest

import game.id.Identifiable
import game.item.{Item, ItemType}
import game.unit.{Monster, RandomMonsterFactory}

/** Rewards hunter with loot when monster is slain
  * Created by nol on 05/11/17.
  */
class Quest(monster: Monster, loot: Seq[ItemType]) extends Identifiable {
  private final val uniqueID: Long = Quest.getNewUniqueQuestID

  def getUniqueId: Long = uniqueID

  def getMonster: Monster = monster

  def getLoot: Seq[Item] = loot.map(i => ItemType.createItem(i))
}

object Quest {

  private var questID: Long = 0

  def getNewUniqueQuestID: Long = {
    questID += 1
    questID
  }

  def createQuest(level: Int, loot: Seq[ItemType]): Quest = {
    val monster = RandomMonsterFactory.generateMonster(level)
    new Quest(monster, loot)
  }
}
