package game

import config.Config
import item.ElementType
import quest.{Quest, QuestResult}
import unit.{GameUnit, Hunter}

/**
  * Created by nol on 06/11/17.
  */
object GameLogic {

  def computeDamageDealt(attacker: GameUnit, defender: GameUnit) : Double = {
    val multiplier = defender.getArmorElementType.foldLeft(1.0)((m, e) => m * ElementType.multiplier(attacker.getAttackElementType, e))
    math.max(Config.getMinDamage, attacker.getDamage * multiplier - defender.getArmor) // an attack deals at least 1 dmg
  }

  def computeQuestResult(hunter: Hunter, quest: Quest): QuestResult = {
    val damageHunter = computeDamageDealt(hunter, quest.getMonster)
    val damageMonster = computeDamageDealt(quest.getMonster, hunter)
    val durationMaxHunter = hunter.getLife / damageMonster
    val durationMaxMonster = quest.getMonster.getLife / damageHunter
    computeQuestResult(durationMaxHunter, durationMaxMonster, quest.getMaxDuration)
  }

  private def computeQuestResult(durationMaxHunter: Double, durationMaxMonster: Double, durationMaxQuest: Double): QuestResult = {
    val questShortEnoughForHunterToSurvive = durationMaxHunter > durationMaxQuest
    val questShortEnoughForMonsterToSurvive = durationMaxMonster > durationMaxQuest
    val hunterWeakerThanMonster = durationMaxHunter < durationMaxMonster
    val hunterDefeated = hunterWeakerThanMonster && !questShortEnoughForHunterToSurvive
    val monsterSlain = !hunterWeakerThanMonster && !questShortEnoughForMonsterToSurvive
    new QuestResultDefault(monsterSlain, hunterDefeated)
  }

  def processQuestResult(hunter: Hunter, quest: Quest, result: QuestResult): Unit = {
    if (!result.isHunterDefeated && result.isMonsterSlain) {
      hunter.getInventory.addItems(quest.getLoot: _*)
      quest.complete()
    }
  }

  class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {
    override def isMonsterSlain: Boolean = monsterSlain

    override def isHunterDefeated: Boolean = hunterDefeated
  }


}
