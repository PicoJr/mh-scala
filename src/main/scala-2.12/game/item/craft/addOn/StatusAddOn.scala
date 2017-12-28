package game.item.craft.addOn

import game.item.status.StatusType
import game.item.{AbstractDecorator, ItemType}

/** Provides status attack effect/protection.
  * Created by nol on 21/12/17.
  */
class StatusAddOn[TItemType <: ItemType](statusType: StatusType, decorator: AbstractDecorator[TItemType]) extends DefaultAddOn[TItemType](statusType.name, decorator) {

  override def create(level: Int, itemType: TItemType): TItemType = {
    decorator.decorateWithStatus(itemType, statusType)
  }
}

