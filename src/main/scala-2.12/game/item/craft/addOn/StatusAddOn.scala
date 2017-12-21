package game.item.craft.addOn

import game.item.status.StatusType
import game.item.{ItemType, Status}

/** Provides status attack effect/protection.
  * Created by nol on 21/12/17.
  */
class StatusAddOn(statusType: StatusType) extends DefaultAddOn {
  override val name: String = statusType.name

  override def createItemType(level: Int, itemType: ItemType): ItemType = {
    Status(itemType, statusType)
  }
}

