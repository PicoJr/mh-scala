package game.quest

import game.Config
import game.id.Identifiable
import game.item.ItemType
import game.unit.{Monster, RandomMonsterFactory}

/** Rewards hunter with loot when monster is slain
  * Created by nol on 05/11/17.
  */
class Quest(monster: Monster, loot: Seq[ItemType]) extends Identifiable {
  private final val uniqueID: Long = Quest.getNewUniqueQuestID

  def getUniqueId: Long = uniqueID

  def getMaxDuration: Int = Config.QUEST_DURATION_MAX

  def getMonster: Monster = monster

  def getLoot: Seq[ItemType] = loot
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
