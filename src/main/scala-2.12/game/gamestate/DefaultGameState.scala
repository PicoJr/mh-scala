package game.gamestate

import game.item.craft.Crafts
import game.quest._
import game.unit.Hunter

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class DefaultGameState(hunter: Hunter, quests: Seq[Quest], crafts: Crafts, questLogic: QuestLogic) extends GameState {

  private var questsCompletedIds: Set[Long] = Set.empty
  private val defaultScore: DefaultScore = new DefaultScore

  override def getHunter: Hunter = hunter

  override def getQuests: Seq[Quest] = quests

  override def getCrafts: Crafts = crafts

  override def setCompleted(questId: Long): Unit = {
    questsCompletedIds += questId
  }

  override def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)

  override def getQuestLogic: QuestLogic = questLogic

  override def getScore: Score = defaultScore
}
