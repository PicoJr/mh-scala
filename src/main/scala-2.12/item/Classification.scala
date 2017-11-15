package item

import scala.util.Random

/**
  * Created by nol on 14/11/17.
  */
object Classification extends Enumeration {
  type Classification = Value
  val CHARM_SLOT, DAMAGE, ELEMENT, EQUIPMENT, MATERIAL, PROTECTION, STATUS = Value

  def takeRandom(classifications: Classification*): Set[Classification] = {
    Random.shuffle(Set(classifications: _*))
  }

}
