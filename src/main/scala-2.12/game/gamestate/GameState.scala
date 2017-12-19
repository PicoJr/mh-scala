package game.gamestate

import game.item.Item
import game.item.craft.Crafts
import game.quest.Quest
import game.unit.{Hunter, Monster}

/** Holds all game instances and states.
  * Created by nol on 22/11/17.
  */
trait GameState {

  /** Get hunter
    *
    * @return hunter
    */
  def getHunter: Hunter

  /** Get quests
    *
    * @return quests regardless of completion
    */
  def getQuests: Seq[Quest]

  /** Get craft recipes
    *
    * @return craft recipes
    */
  def getCrafts: Crafts

  def addCompletedQuest(questId: Long): Unit

  /** Find quest with id questId
    *
    * @param questID to find
    * @return quest with id questId if any else None
    */
  def findQuest(questID: Long): Option[Quest] = {
    getQuests.find(q => q.getUniqueId == questID)
  }

  /** Find monster with id monsterId
    *
    * @param monsterId to find
    * @return monster with id monsterId if any else None
    */
  def findMonster(monsterId: Long): Option[Monster] = {
    getQuests.find(q => q.getMonster.getUniqueId == monsterId) match {
      case Some(q) => Some(q.getMonster)
      case None => None
    }
  }

  /** Find item with id itemId
    *
    * @param itemId to find
    * @return item with id itemId if any else None
    */
  def findItem(itemId: Long): Option[Item] = {
    getHunter.getInventory.findItem(itemId)
  }

  /** Check quest with id questId is completed
    *
    * @param questId of quest
    * @return true if quest is completed, false if not or invalid Id
    */
  def isCompletedQuest(questId: Long): Boolean

  /** Same as getQuests.filter(q => isCompletedQuest(q.getUniqueId))
    *
    * @return quest completed
    */
  def getCompletedQuests: Seq[Quest] = {
    getQuests.filter(q => isCompletedQuest(q.getUniqueId))
  }

  /** Same as getCompletedQuests.size == getQuests.size
    *
    * @return all quests are completed
    */
  def allQuestsCompleted: Boolean = {
    getCompletedQuests.size == getQuests.size
  }

}
