package game.questEvents

import game.config.{DefaultGameConfig, GameConfig}
import game.gameStateEvents.GameStateEvents
import game.gamestate.GameState
import game.item.element.{DefaultEEResolver, EEResolver}
import game.quest.Quest
import game.unit.GameUnit

/**
  * Created by nol on 19/12/17.
  */
class QuestEventsHandler(
                          gameState: GameState,
                          eEResolver: EEResolver,
                          gameConfig: GameConfig
                        ) {

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

  QuestEvents.questStarted += {
    (quest: Quest) =>
      val damageDealtByHunter = computeDamageDealt(gameState.getHunter, quest.getMonster)
      val damageDealtByMonster = computeDamageDealt(quest.getMonster, gameState.getHunter)
      val durationMaxHunter = gameState.getHunter.getLife / damageDealtByMonster
      val durationMaxMonster = quest.getMonster.getLife / damageDealtByHunter
      val timeElapsed = math.min(gameConfig.getQuestDurationMax, math.min(durationMaxHunter, durationMaxMonster))
      val hunterDefeated = durationMaxHunter < durationMaxMonster && durationMaxHunter < gameConfig.getQuestDurationMax
      val monsterSlain = durationMaxMonster < durationMaxHunter && durationMaxMonster < gameConfig.getQuestDurationMax
      if (hunterDefeated || !monsterSlain) QuestEvents.questFailed(quest.getUniqueId)
      if (!hunterDefeated && monsterSlain) {
        QuestEvents.questSucceeded(quest.getUniqueId)
        gameState.getHunter.getInventory.addItems(quest.createLoot: _*)
      }
  }

  QuestEvents.questSucceeded += {
    (questId: Id) => GameStateEvents.questSucceeded(questId)
  }


}
