package game.gamestate

import game.item.craft.Crafts
import game.quest._
import game.unit.Hunter

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class DefaultGameState(gameHunter: Hunter, gameQuests: Seq[Quest], gameCrafts: Crafts) extends GameState {

  private var questsCompletedIds: Set[Long] = Set.empty

  override val hunter: Hunter = gameHunter

  override val quests: Seq[Quest] = gameQuests

  override val crafts: Crafts = gameCrafts

  override def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)

  override def addCompletedQuest(questId: Long): Unit = questsCompletedIds += questId
}
