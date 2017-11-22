package game.quest

import game.item.{Item, ItemTrait, ItemTypeTrait}
import game.unit.{Monster, RandomMonsterFactory}

/** Rewards hunter with loot when monster is slain
  * Created by nol on 05/11/17.
  */
class Quest(monster: Monster, loot: Seq[ItemTypeTrait]) extends QuestTrait {
  private final val uniqueID: Long = Quest.getNewUniqueQuestID

  def getUniqueId: Long = uniqueID

  def getMonster: Monster = monster

  def createLoot: Seq[ItemTrait] = loot.map(i => Item.createItem(i))
}

object Quest {

  private var questID: Long = 0

  def getNewUniqueQuestID: Long = {
    questID += 1
    questID
  }

  def createQuest(level: Int, loot: Seq[ItemTypeTrait]): QuestTrait = {
    val monster = RandomMonsterFactory.generateMonster(level)
    new Quest(monster, loot)
  }
}
