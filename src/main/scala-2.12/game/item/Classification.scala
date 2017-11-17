package game.item

/** Describes what features an item type instance holds
  * Created by nol on 14/11/17.
  */
object Classification extends Enumeration {
  type Classification = Value
  val CHARM_SLOT, DAMAGE, ELEMENT, EQUIPMENT, MATERIAL, PROTECTION, STATUS = Value

}
