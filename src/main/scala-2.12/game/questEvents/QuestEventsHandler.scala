package game.questEvents

import game.config.{DefaultGameConfig, GameConfig}
import game.gameStateEvents.GameStateEvents
import game.gameStatistics.DefaultGameStatistics
import game.gamestate.GameState
import game.item.element.{DefaultEEResolver, EEResolver}
import game.quest.Quest
import game.unit.GameUnit

/**
  * Created by nol on 19/12/17.
  */
class QuestEventsHandler(gameState: GameState, eEResolver: EEResolver, gameConfig: GameConfig) {

  type Id = Long

  def this(gameState: GameState) = {
    this(
      gameState,
      new DefaultEEResolver(),
      DefaultGameConfig.getGameConfig
    )
  }

  private def computeDamageDealt(attacker: GameUnit, defender: GameUnit): Double = {
    val multiplier = defender.getElementalResistances.foldLeft(1.0)((m, e) => m * eEResolver.multiplier(attacker.getAttackElementType, e))
    math.max(gameConfig.getDamageMin, attacker.getDamage * multiplier - defender.getArmor)
  }

  def onQuestStarted(quest: Quest): Unit = {
    DefaultGameStatistics.questStartedCount() = DefaultGameStatistics.questStartedCount.now + 1
    val damageDealtByHunter = computeDamageDealt(gameState.getHunter, quest.monster)
    val damageDealtByMonster = computeDamageDealt(quest.monster, gameState.getHunter)
    val durationMaxHunter = gameState.getHunter.getLife / damageDealtByMonster
    val durationMaxMonster = quest.monster.getLife / damageDealtByHunter
    val timeElapsed = math.min(gameConfig.getQuestDurationMax, math.min(durationMaxHunter, durationMaxMonster))
    val hunterDefeated = durationMaxHunter < durationMaxMonster && durationMaxHunter < gameConfig.getQuestDurationMax
    val monsterSlain = durationMaxMonster < durationMaxHunter && durationMaxMonster < gameConfig.getQuestDurationMax
    if (hunterDefeated || !monsterSlain) {
      QuestEvents.questFailed(quest.getUniqueId)
    }
    if (!hunterDefeated && monsterSlain) {
      QuestEvents.questSucceeded(quest.getUniqueId)
      gameState.getHunter.inventory.addItems(quest.createLoot: _*)
    }
  }

  def onQuestSucceeded(questId: Id): Unit = {
    DefaultGameStatistics.questSucceededCount() = DefaultGameStatistics.questSucceededCount.now + 1
    GameStateEvents.questSucceeded(questId)
  }

  def onQuestFailed(questId: Id): Unit = {
    DefaultGameStatistics.questFailedCount() = DefaultGameStatistics.questFailedCount.now + 1
  }

  QuestEvents.questStarted += {
    (quest: Quest) => onQuestStarted(quest)
  }

  QuestEvents.questSucceeded += {
    (questId: Id) => onQuestSucceeded(questId)
  }

  QuestEvents.questFailed += {
    (questId: Id) => onQuestFailed(questId)
  }

}
