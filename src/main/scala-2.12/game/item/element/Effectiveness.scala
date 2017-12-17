package game.item.element

import game.config.GameConfig

/** Attack effectiveness
  * Created by nol on 05/11/17.
  */
object Effectiveness extends Enumeration {

  type Effectiveness = Value
  val INEFFECTIVE, NORMAL, EFFECTIVE = Value


  /** Get multiplier from effectiveness
    *
    * @param effectiveness of attack
    * @return multiplier >= 0.0
    */
  def multiplier(effectiveness: Effectiveness, gameConfig: GameConfig): Double = effectiveness match {
    case Effectiveness.NORMAL => gameConfig.getNormalMultiplier
    case Effectiveness.INEFFECTIVE => gameConfig.getIneffectiveMultiplier
    case Effectiveness.EFFECTIVE => gameConfig.getEffectiveMultiplier
  }
}
