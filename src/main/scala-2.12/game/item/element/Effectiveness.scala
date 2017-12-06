package game.item.element

import game.config.ConfigLoader

/** Attack effectiveness
  * Created by nol on 05/11/17.
  */
object Effectiveness extends Enumeration {

  private final val conf = ConfigLoader.loadEffectivenessConfig

  type Effectiveness = Value
  val INEFFECTIVE, NORMAL, EFFECTIVE = Value


  /** Get multiplier from effectiveness
    *
    * @param effectiveness of attack
    * @return multiplier >= 0.0
    */
  def multiplier(effectiveness: Effectiveness): Double = effectiveness match {
    case Effectiveness.NORMAL => conf.getNormalMultiplier
    case Effectiveness.INEFFECTIVE => conf.getIneffectiveMultiplier
    case Effectiveness.EFFECTIVE => conf.getEffectiveMultiplier
  }
}
