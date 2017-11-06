import item.{ElementType, StatusType}
import unit.{GameUnit, Hunter}

/**
  * Created by nol on 06/11/17.
  */
object GameLogic {

  def computeDamageDealt(attacker: GameUnit, defender: GameUnit) : Double = {
    val multiplier = defender.getArmorElementType.foldLeft(1.0)((m, e) => m * ElementType.multiplier(attacker.getAttackElementType, e))
    math.max(0, attacker.getDamage * multiplier - defender.getArmor)
  }

  def canUnitFight(unit: GameUnit): Boolean = {
    unit.getStatus == StatusType.NORMAL
  }

  def simulateQuest(hunter: Hunter, quest: Quest) : FightResult = {
    val damageHunter = computeDamageDealt(hunter, quest.getMonster)
    val damageMonster = computeDamageDealt(quest.getMonster, hunter)
    val durationMaxHunter = hunter.getLife / damageMonster
    val durationMaxMonster = quest.getMonster.getLife / damageHunter
    val fightResult = new FightResult
    if (durationMaxHunter < durationMaxMonster && durationMaxMonster < quest.getMaxDuration) {
      fightResult.hunterDefeated = true
    } else if (durationMaxMonster < durationMaxHunter && durationMaxHunter < quest.getMaxDuration) {
      fightResult.monsterSlain = true
    }
    fightResult
  }
}
