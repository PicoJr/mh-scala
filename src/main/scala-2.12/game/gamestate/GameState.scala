package game.gamestate

import game.item.Item
import game.item.craft.Crafts
import game.quest.{Quest, QuestLogic}
import game.unit.{Hunter, Monster}

/**
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

  /** Get quest logic
    *
    * @return quest logic
    */
  def getQuestLogic: QuestLogic

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

  /** Set quest with id questId as completed
    *
    * @param questId of quest
    */
  def setCompleted(questId: Long): Unit

  /** Check quest with id questId is completed
    *
    * @param questId of quest
    * @return true if quest is completed, false if not or invalid Id
    */
  def isCompletedQuest(questId: Long): Boolean

}