import item.ElementType
import quest.{Quest, QuestResult}
import unit.{GameUnit, Hunter}

/**
  * Created by nol on 06/11/17.
  */
object GameLogic {

  def computeDamageDealt(attacker: GameUnit, defender: GameUnit) : Double = {
    val multiplier = defender.getArmorElementType.foldLeft(1.0)((m, e) => m * ElementType.multiplier(attacker.getAttackElementType, e))
    math.max(0, attacker.getDamage * multiplier - defender.getArmor)
  }

  def simulateQuest(hunter: Hunter, quest: Quest): QuestResult = {
    val damageHunter = computeDamageDealt(hunter, quest.getMonster)
    val damageMonster = computeDamageDealt(quest.getMonster, hunter)
    val durationMaxHunter = hunter.getLife / damageMonster
    val durationMaxMonster = quest.getMonster.getLife / damageHunter
    val hunterDefeated = durationMaxHunter < durationMaxMonster && durationMaxMonster < Quest.MAX_DURATION
    val monsterSlain = durationMaxMonster < durationMaxHunter && durationMaxHunter < Quest.MAX_DURATION
    new QuestResultDefault(monsterSlain, hunterDefeated)
  }

  def distributeLoot(hunter: Hunter, quest: Quest, result: QuestResult): Unit = {
    if (result.isHunterDefeated & result.isMonsterSlain) {
      hunter.getInventory.addItems(quest.getLoot: _*)
    }
  }

  class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {
    override def isMonsterSlain: Boolean = monsterSlain

    override def isHunterDefeated: Boolean = hunterDefeated
  }


}
