package game.quest

import game.GameState
import game.config.ConfigLoader
import game.item.ElementType
import game.unit.{GameUnit, Hunter}

/**
  * Created by nol on 06/11/17.
  */
object QuestLogic {

  private final val config = ConfigLoader.loadConfig

  private def computeDamageDealt(attacker: GameUnit, defender: GameUnit): Double = {
    val multiplier = defender.getArmorElementTypes.foldLeft(1.0)((m, e) => m * ElementType.multiplier(attacker.getAttackElementType, e))
    math.max(config.getDamageMin, attacker.getDamage * multiplier - defender.getArmor)
  }

  private def computeQuestResult(hunter: Hunter, quest: Quest): QuestResult = {
    val durationMaxHunter = hunter.getLife / computeDamageDealt(quest.getMonster, hunter)
    val durationMaxMonster = quest.getMonster.getLife / computeDamageDealt(hunter, quest.getMonster)
    val questShortEnoughForHunterToSurvive = durationMaxHunter > config.getQuestDurationMax
    val questShortEnoughForMonsterToSurvive = durationMaxMonster > config.getQuestDurationMax
    val hunterWeakerThanMonster = durationMaxHunter < durationMaxMonster
    val hunterDefeated = hunterWeakerThanMonster && !questShortEnoughForHunterToSurvive
    val monsterSlain = !hunterWeakerThanMonster && !questShortEnoughForMonsterToSurvive
    new QuestResultDefault(monsterSlain, hunterDefeated)
  }

  def processQuestResult(gameState: GameState, quest: Quest): QuestResult = {
    val questResult = computeQuestResult(gameState.getHunter, quest)
    if (questResult.isSuccessful) gameState.getHunter.getInventory.addItems(quest.getLoot: _*)
    questResult
  }

  private class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {
    override def isMonsterSlain: Boolean = monsterSlain

    override def isHunterDefeated: Boolean = hunterDefeated
  }


}
