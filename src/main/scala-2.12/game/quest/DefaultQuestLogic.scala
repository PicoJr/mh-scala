package game.quest

import game.config.{DefaultGameConfig, GameConfig}
import game.gameEventsHandler.{DefaultGameEventsHandler, GameEventsHandler}
import game.gamestate.GameState
import game.item.element.{DefaultEEResolver, EEResolver}
import game.unit.GameUnit

/**
  * Created by nol on 22/11/17.
  */
class DefaultQuestLogic(eEResolver: EEResolver, gameConfig: GameConfig, gameEventsHandler: GameEventsHandler) extends QuestLogic {

  def this() = {
    this(new DefaultEEResolver(), DefaultGameConfig.getGameConfig, DefaultGameEventsHandler.getGameEventsHandler)
  }

  private def computeDamageDealt(attacker: GameUnit, defender: GameUnit): Double = {
    val multiplier = defender.getElementalResistances.foldLeft(1.0)((m, e) => m * eEResolver.multiplier(attacker.getAttackElementType, e))
    math.max(gameConfig.getDamageMin, attacker.getDamage * multiplier - defender.getArmor)
  }

  override def processQuest(gameState: GameState, quest: Quest): Unit = {
    val damageDealtByHunter = computeDamageDealt(gameState.getHunter, quest.getMonster)
    val damageDealtByMonster = computeDamageDealt(quest.getMonster, gameState.getHunter)
    val durationMaxHunter = gameState.getHunter.getLife / damageDealtByMonster
    val durationMaxMonster = quest.getMonster.getLife / damageDealtByHunter
    val timeElapsed = math.min(gameConfig.getQuestDurationMax, math.min(durationMaxHunter, durationMaxMonster))
    val hunterDefeated = durationMaxHunter < durationMaxMonster && durationMaxHunter < gameConfig.getQuestDurationMax
    val monsterSlain = durationMaxMonster < durationMaxHunter && durationMaxMonster < gameConfig.getQuestDurationMax
    gameEventsHandler.questStarted(quest.getUniqueId)
    gameEventsHandler.questFinished(timeElapsed)
    gameEventsHandler.hunterDealtDamage(damageDealtByHunter * timeElapsed)
    gameEventsHandler.monsterDealtDamage(damageDealtByMonster * timeElapsed)
    gameEventsHandler.hunterDefeated(hunterDefeated)
    gameEventsHandler.monsterSlain(monsterSlain)
    if (hunterDefeated || !monsterSlain) gameEventsHandler.questFailed(quest.getUniqueId)
    if (!hunterDefeated && monsterSlain) {
      gameEventsHandler.questSucceeded(quest.getUniqueId)
      gameState.getHunter.getInventory.addItems(quest.createLoot: _*)
    }
  }

}