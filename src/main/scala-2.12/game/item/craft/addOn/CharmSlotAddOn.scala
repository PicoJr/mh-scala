package game.item.craft.addOn

import game.item.{AbstractDecorator, ItemType}

/** Provides charm slot.
  * Created by nol on 21/12/17.
  */
case class CharmSlotAddOn(decorator: AbstractDecorator) extends DefaultAddOn("charmed") {
  override val name: String = "charmed"

  override def decorate(level: Int, itemType: ItemType): ItemType = {
    decorator.decorateWithCharmSlot(itemType, getRandomSlot)
  }
}
