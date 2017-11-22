package game.quest

import game.config.ConfigLoader
import game.gamestate.GameStateTrait
import game.item.ElementType
import game.unit.{GameUnitTrait, Hunter}

/**
  * Created by nol on 06/11/17.
  */
object QuestLogic {

  private final val config = ConfigLoader.loadGameConfig

  private def computeDamageDealt(attacker: GameUnitTrait, defender: GameUnitTrait): Double = {
    val multiplier = defender.getArmorElementTypes.foldLeft(1.0)((m, e) => m * ElementType.multiplier(attacker.getAttackElementType, e))
    math.max(config.getDamageMin, attacker.getDamage * multiplier - defender.getArmor)
  }

  private def computeQuestResult(hunter: Hunter, quest: QuestTrait): QuestResult = {
    val durationMaxHunter = hunter.getLife / computeDamageDealt(quest.getMonster, hunter)
    val durationMaxMonster = quest.getMonster.getLife / computeDamageDealt(hunter, quest.getMonster)
    val questShortEnoughForHunterToSurvive = durationMaxHunter > config.getQuestDurationMax
    val questShortEnoughForMonsterToSurvive = durationMaxMonster > config.getQuestDurationMax
    val hunterWeakerThanMonster = durationMaxHunter < durationMaxMonster
    val hunterDefeated = hunterWeakerThanMonster && !questShortEnoughForHunterToSurvive
    val monsterSlain = !hunterWeakerThanMonster && !questShortEnoughForMonsterToSurvive
    new QuestResultDefault(monsterSlain, hunterDefeated)
  }

  def processQuestResult(gameState: GameStateTrait, quest: QuestTrait): QuestResult = {
    val questResult = computeQuestResult(gameState.getHunter, quest)
    if (questResult.isSuccessful) gameState.getHunter.getInventory.addItems()
    questResult
  }

  private class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {
    override def isMonsterSlain: Boolean = monsterSlain

    override def isHunterDefeated: Boolean = hunterDefeated
  }


}
