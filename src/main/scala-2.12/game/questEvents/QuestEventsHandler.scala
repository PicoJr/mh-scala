package game.questEvents

import game.config.{DefaultGameConfig, GameConfig}
import game.gameStateEvents.GameStateEvents
import game.gameStatistics.DefaultGameStatistics
import game.gamestate.GameState
import game.id.Identifiable
import game.item.element.EEResolver
import game.item.{AbstractItemFactory, Item, ItemType}
import game.unit.GameUnit

/**
  * Created by nol on 19/12/17.
  */
class QuestEventsHandler[TItem <: Item, TItemType <: ItemType](gameState: GameState[TItem, TItemType], questEvents: QuestEvents, gameStateEvents: GameStateEvents, itemFactory: AbstractItemFactory[TItem, TItemType], eEResolver: EEResolver, gameConfig: GameConfig = DefaultGameConfig.getInstance) {

  type Id = Identifiable.Id

  private def computeDamageDealt(attacker: GameUnit, defender: GameUnit): Double = {
    val multiplier = defender.getElementalResistances.foldLeft(1.0)((m, e) => m * eEResolver.multiplier(attacker.getAttackElementType, e))
    math.max(gameConfig.getDamageMin, attacker.getDamage * multiplier - defender.getArmor)
  }

  def onQuestStarted(questId: Id): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) =>
        DefaultGameStatistics.questStartedCount() = DefaultGameStatistics.questStartedCount.now + 1
        val damageDealtByHunter = computeDamageDealt(gameState.hunter, quest.monster)
        val damageDealtByMonster = computeDamageDealt(quest.monster, gameState.hunter)
        val durationMaxHunter = gameState.hunter.getLife / damageDealtByMonster
        val durationMaxMonster = quest.monster.getLife / damageDealtByHunter
        val timeElapsed = math.min(gameConfig.getQuestDurationMax, math.min(durationMaxHunter, durationMaxMonster))
        val hunterDefeated = durationMaxHunter < durationMaxMonster && durationMaxHunter < gameConfig.getQuestDurationMax
        val monsterSlain = durationMaxMonster < durationMaxHunter && durationMaxMonster < gameConfig.getQuestDurationMax
        if (hunterDefeated || !monsterSlain) {
          questEvents.questFailed(quest.getUniqueId)
        }
        if (!hunterDefeated && monsterSlain) {
          questEvents.questSucceeded(quest.getUniqueId)
          val items = quest.loot.map(i => itemFactory.createItem(i))
          gameState.hunter.inventory.addItems(items: _*)
        }
      case None =>
    }
  }

  def onQuestSucceeded(questId: Id): Unit = {
    DefaultGameStatistics.questSucceededCount() = DefaultGameStatistics.questSucceededCount.now + 1
    DefaultGameStatistics.questSucceeded() = DefaultGameStatistics.questSucceeded.now + questId
    gameStateEvents.questSucceeded(questId)
  }

  def onQuestFailed(questId: Id): Unit = {
    DefaultGameStatistics.questFailedCount() = DefaultGameStatistics.questFailedCount.now + 1
    gameStateEvents.questFailed(questId)
  }

  questEvents.questStarted += {
    (questId: Id) => onQuestStarted(questId)
  }

  questEvents.questSucceeded += {
    (questId: Id) => onQuestSucceeded(questId)
  }

  questEvents.questFailed += {
    (questId: Id) => onQuestFailed(questId)
  }

}
