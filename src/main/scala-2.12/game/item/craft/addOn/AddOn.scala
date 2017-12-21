package game.item.craft.addOn

import game.item.ItemType

/** ItemType AddOn.
  * Stacked up to make increasingly complex itemTypes.
  * Created by nol on 29/11/17.
  */
trait AddOn {
  val name: String

  def createItemType(level: Int, itemType: ItemType): ItemType
}
