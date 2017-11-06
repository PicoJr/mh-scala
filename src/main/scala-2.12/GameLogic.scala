import item.ElementType
import unit.{GameUnit, StatusType}

/**
  * Created by nol on 06/11/17.
  */
object GameLogic {
  def computeDamageDealt(attacker: GameUnit, defender: GameUnit) : Double = {
    val m = ElementType.multiplier(attacker.getAttackElementType, defender.getElement)
    math.max(0, attacker.getDamage * m - defender.getArmor)
  }

  def canUnitFight(unit: GameUnit): Boolean = {
    unit.getStatus == StatusType.NORMAL
  }
}
