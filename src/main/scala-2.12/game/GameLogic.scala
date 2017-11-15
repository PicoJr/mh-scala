package game

import config.Config
import item.{ElementType, Item}
import quest.{Quest, QuestResult}
import unit.{GameUnit, Hunter}

/**
  * Created by nol on 06/11/17.
  */
object GameLogic {

  /**
    *
    * @param attacker deal damage
    * @param defender receive damage
    * @return damage > 0
    */
  def computeDamageDealt(attacker: GameUnit, defender: GameUnit) : Double = {
    val multiplier = defender.getArmorElementType.foldLeft(1.0)((m, e) => m * ElementType.multiplier(attacker.getAttackElementType, e))
    math.max(Config.DAMAGE_MIN, attacker.getDamage * multiplier - defender.getArmor)
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
      val items = quest.getLoot.map(itemType => new Item(itemType))
      hunter.getInventory.addItems(items: _*)
    }
  }

  class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {
    override def isMonsterSlain: Boolean = monsterSlain

    override def isHunterDefeated: Boolean = hunterDefeated
  }


}
