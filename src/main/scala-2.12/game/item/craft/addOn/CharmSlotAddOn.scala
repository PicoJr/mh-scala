package game.item.craft.addOn

import game.item.{AbstractDecorator, ItemType}

/** Provides charm slot.
  * Created by nol on 21/12/17.
  */
class CharmSlotAddOn[TItemType <: ItemType](decorator: AbstractDecorator[TItemType]) extends DefaultAddOn[TItemType]("charmed", decorator) {
  override val name: String = "charmed"

  override def create(level: Int, itemType: TItemType): TItemType = {
    decorator.decorateWithCharmSlot(itemType, getRandomSlot)
  }
}
