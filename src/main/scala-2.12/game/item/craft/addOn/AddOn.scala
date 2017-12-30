package game.item.craft.addOn

import game.item.ItemType

/** Generic AddOn for ItemTypes.
  * Stacked up to make increasingly complex item types.
  * Created by nol on 29/11/17.
  */
trait AddOn[TItemType <: ItemType] {
  val name: String

  def decorate(level: Int, itemType: TItemType): TItemType
}
