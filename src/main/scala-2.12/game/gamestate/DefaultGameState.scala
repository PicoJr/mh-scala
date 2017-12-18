package game.gamestate

import game.gameEventsHandler.GameEventsHandler
import game.item.craft.Crafts
import game.quest._
import game.unit.Hunter

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class DefaultGameState(hunter: Hunter, quests: Seq[Quest], crafts: Crafts, questLogic: QuestLogic, gameEventsHandler: GameEventsHandler) extends GameState {

  private var questsCompletedIds: Set[Long] = Set.empty

  gameEventsHandler.questSucceeded += { (questId: Long) => if (!isCompletedQuest(questId)) gameEventsHandler.questCompleted(questId) }
  gameEventsHandler.questCompleted += { (questId: Long) => questsCompletedIds += questId }

  override def getHunter: Hunter = hunter

  override def getQuests: Seq[Quest] = quests

  override def getCrafts: Crafts = crafts

  override def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)

  override def getQuestLogic: QuestLogic = questLogic
}
