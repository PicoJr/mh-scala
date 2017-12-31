package game.item.craft.addOn

import game.item.{AbstractDecorator, ItemType}

/** Provides charm slot.
  * Created by nol on 21/12/17.
  */
case class CharmSlotAddOn[TItemType <: ItemType](decorator: AbstractDecorator[TItemType]) extends DefaultAddOn[TItemType]("charmed") {
  override val name: String = "charmed"

  override def decorate(level: Int, itemType: TItemType): TItemType = {
    decorator.decorateWithCharmSlot(itemType, getRandomSlot)
  }
}
