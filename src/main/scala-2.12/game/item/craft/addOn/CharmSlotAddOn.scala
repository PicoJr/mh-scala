package game.item.craft.addOn

import game.item.{CharmSlot, ItemType}

/** Provides charm slot.
  * Created by nol on 21/12/17.
  */
object CharmSlotAddOn extends DefaultAddOn {
  override val name: String = "charmed"

  override def createItemType(level: Int, itemType: ItemType): ItemType = {
    CharmSlot(itemType, getRandomSlot)
  }
}
