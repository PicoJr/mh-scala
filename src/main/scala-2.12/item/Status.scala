package item

import unit.StatusType.StatusType

/**
  * Created by nol on 06/11/17.
  */
class Status(i: Item, statusType: StatusType) extends ItemDecorator(i: Item) {
  def getStatusType: StatusType = statusType
}
