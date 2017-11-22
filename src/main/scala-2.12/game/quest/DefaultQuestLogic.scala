package game.quest

import game.config.ConfigLoader
import game.gamestate.GameState
import game.item.element.EEResolver
import game.unit.{GameUnit, Hunter}

/**
  * Created by nol on 22/11/17.
  */
class DefaultQuestLogic(eEResolver: EEResolver) extends QuestLogic {

  private def computeDamageDealt(attacker: GameUnit, defender: GameUnit): Double = {
    val multiplier = defender.getArmorElementTypes.foldLeft(1.0)((m, e) => m * eEResolver.multiplier(attacker.getAttackElementType, e))
    math.max(DefaultQuestLogic.config.getDamageMin, attacker.getDamage * multiplier - defender.getArmor)
  }

  private def computeQuestResult(hunter: Hunter, quest: Quest): QuestResult = {
    val durationMaxHunter = hunter.getLife / computeDamageDealt(quest.getMonster, hunter)
    val durationMaxMonster = quest.getMonster.getLife / computeDamageDealt(hunter, quest.getMonster)
    val questShortEnoughForHunterToSurvive = durationMaxHunter > DefaultQuestLogic.config.getQuestDurationMax
    val questShortEnoughForMonsterToSurvive = durationMaxMonster > DefaultQuestLogic.config.getQuestDurationMax
    val hunterWeakerThanMonster = durationMaxHunter < durationMaxMonster
    val hunterDefeated = hunterWeakerThanMonster && !questShortEnoughForHunterToSurvive
    val monsterSlain = !hunterWeakerThanMonster && !questShortEnoughForMonsterToSurvive
    new QuestResultDefault(monsterSlain, hunterDefeated)
  }

  def processQuestResult(gameState: GameState, quest: Quest): QuestResult = {
    val questResult = computeQuestResult(gameState.getHunter, quest)
    if (questResult.isSuccessful) gameState.getHunter.getInventory.addItems(quest.createLoot: _*)
    questResult
  }

}

object DefaultQuestLogic {
  private final val config = ConfigLoader.loadGameConfig
}
