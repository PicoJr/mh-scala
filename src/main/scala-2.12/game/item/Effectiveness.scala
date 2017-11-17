package game.item

/** Attack effectiveness
  * Created by nol on 05/11/17.
  */
object Effectiveness extends Enumeration {
  type Effectiveness = Value
  val INEFFECTIVE, NORMAL, EFFECTIVE = Value


  /** Get multiplier from effectiveness
    * TODO get values from Config instead of hard code
    *
    * @param effectiveness of attack
    * @return multiplier >= 0.0
    */
  def multiplier(effectiveness: Effectiveness): Double = effectiveness match {
    case Effectiveness.NORMAL => 1.0
    case Effectiveness.INEFFECTIVE => 0.5
    case Effectiveness.EFFECTIVE => 2.0
  }
}
