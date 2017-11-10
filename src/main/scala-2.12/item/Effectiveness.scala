package item

/** Attack effectiveness
  * Created by nol on 05/11/17.
  */
object Effectiveness extends Enumeration {
  type Effectiveness = Value
  val INEFFECTIVE, NORMAL, EFFECTIVE = Value


  def multiplier(effectiveness: Effectiveness): Double = effectiveness match {
    case Effectiveness.NORMAL => 1.0
    case Effectiveness.INEFFECTIVE => 0.5
    case Effectiveness.EFFECTIVE => 2.0
  }
}
