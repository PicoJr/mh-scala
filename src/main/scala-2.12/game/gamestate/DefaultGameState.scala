package game.gamestate

import game.item.craft.Crafts
import game.item.{Item, ItemType}
import game.quest._
import game.unit.Hunter

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class DefaultGameState[TItem <: Item, TItemType <: ItemType](gameHunter: Hunter[TItem], gameQuests: Seq[Quest[TItemType]], gameCrafts: Crafts[TItemType]) extends GameState[TItem, TItemType] {

  private var questsCompletedIds: Set[Long] = Set.empty

  override val hunter: Hunter[TItem] = gameHunter

  override val quests: Seq[Quest[TItemType]] = gameQuests

  override val crafts: Crafts[TItemType] = gameCrafts

  override def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)

  override def addCompletedQuest(questId: Long): Unit = questsCompletedIds += questId
}
