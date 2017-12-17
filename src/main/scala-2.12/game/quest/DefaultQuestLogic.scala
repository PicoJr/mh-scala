package game.quest

import game.config.{DefaultGameConfig, GameConfig}
import game.gamestate.GameState
import game.item.element.{DefaultEEResolver, EEResolver}
import game.unit.{GameUnit, Hunter}

/**
  * Created by nol on 22/11/17.
  */
class DefaultQuestLogic(eEResolver: EEResolver, gameConfig: GameConfig) extends QuestLogic {

  def this() = {
    this(new DefaultEEResolver(), DefaultGameConfig.getGameConfig)
  }

  private def computeDamageDealt(attacker: GameUnit, defender: GameUnit): Double = {
    val multiplier = defender.getElementalResistances.foldLeft(1.0)((m, e) => m * eEResolver.multiplier(attacker.getAttackElementType, e))
    math.max(gameConfig.getDamageMin, attacker.getDamage * multiplier - defender.getArmor)
  }

  private def computeQuestResult(hunter: Hunter, quest: Quest): QuestResult = {
    val questResultDefault = new QuestResultDefault
    val damageDealtByHunter = computeDamageDealt(hunter, quest.getMonster)
    val damageDealtByMonster = computeDamageDealt(quest.getMonster, hunter)
    val durationMaxHunter = hunter.getLife / damageDealtByMonster
    val durationMaxMonster = quest.getMonster.getLife / damageDealtByHunter
    val timeElapsed = math.min(gameConfig.getQuestDurationMax, math.min(durationMaxHunter, durationMaxMonster))
    questResultDefault.withTimeElapsed(timeElapsed)
    questResultDefault.withDamageDealtByHunter(damageDealtByHunter * timeElapsed)
    questResultDefault.withDamageDealtByMonster(damageDealtByMonster * timeElapsed)
    val hunterDefeated = durationMaxHunter < durationMaxMonster && durationMaxHunter < gameConfig.getQuestDurationMax
    questResultDefault.withHunterDefeated(hunterDefeated)
    val monsterSlain = durationMaxMonster < durationMaxHunter && durationMaxMonster < gameConfig.getQuestDurationMax
    questResultDefault.withMonsterSlain(monsterSlain)
    questResultDefault
  }

  override def processQuestResult(gameState: GameState, quest: Quest): QuestResult = {
    val questResult = computeQuestResult(gameState.getHunter, quest)
    if (questResult.isSuccessful) gameState.getHunter.getInventory.addItems(quest.createLoot: _*)
    questResult
  }

}