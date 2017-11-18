package game

import game.item.ElementType
import game.quest.{Quest, QuestResult}
import game.unit.{GameUnit, Hunter}

/**
  * Created by nol on 06/11/17.
  */
object QuestLogic {

  private def computeDamageDealt(attacker: GameUnit, defender: GameUnit): Double = {
    val multiplier = defender.getArmorElementTypes.foldLeft(1.0)((m, e) => m * ElementType.multiplier(attacker.getAttackElementType, e))
    math.max(Config.DAMAGE_MIN, attacker.getDamage * multiplier - defender.getArmor)
  }

  private def computeQuestResult(durationMaxHunter: Double, durationMaxMonster: Double, durationMaxQuest: Double): QuestResult = {
    val questShortEnoughForHunterToSurvive = durationMaxHunter > durationMaxQuest
    val questShortEnoughForMonsterToSurvive = durationMaxMonster > durationMaxQuest
    val hunterWeakerThanMonster = durationMaxHunter < durationMaxMonster
    val hunterDefeated = hunterWeakerThanMonster && !questShortEnoughForHunterToSurvive
    val monsterSlain = !hunterWeakerThanMonster && !questShortEnoughForMonsterToSurvive
    new QuestResultDefault(monsterSlain, hunterDefeated)
  }

  private def computeQuestResult(hunter: Hunter, quest: Quest): QuestResult = {
    val damageHunter = computeDamageDealt(hunter, quest.getMonster)
    val damageMonster = computeDamageDealt(quest.getMonster, hunter)
    val durationMaxHunter = hunter.getLife / damageMonster
    val durationMaxMonster = quest.getMonster.getLife / damageHunter
    computeQuestResult(durationMaxHunter, durationMaxMonster, quest.getMaxDuration)
  }

  def processQuest(gameState: GameState, questId: Long): QuestResult = {
    val hunter = gameState.getHunter
    val quest = gameState.findQuest(questId).get
    val questResult = computeQuestResult(hunter, quest)
    if (questResult.isSuccessful) hunter.getInventory.addItems(quest.getLoot: _*)
    questResult
  }

  private class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {
    override def isMonsterSlain: Boolean = monsterSlain

    override def isHunterDefeated: Boolean = hunterDefeated
  }


}
