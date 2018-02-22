package game.item.craft.addOn

import game.item.status.StatusType
import game.item.{AbstractDecorator, DefaultDecorator, ItemType}

/** Provides status attack effect/protection.
  * Created by nol on 21/12/17.
  */
case class StatusAddOn(statusType: StatusType, decorator: AbstractDecorator = DefaultDecorator.getInstance) extends DefaultAddOn(statusType.name) {

  override def decorate(level: Int, itemType: ItemType): ItemType = {
    decorator.decorateWithStatus(itemType, statusType)
  }
}

