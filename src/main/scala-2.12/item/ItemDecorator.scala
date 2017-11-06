package item

/**
  * Created by nol on 04/11/17.
  */
class ItemDecorator(i: Item) extends Item {
  var item: Item = i

  override def getName: String = item.getName

  override def getQuantity: Int = item.getQuantity

  override def isEquipable: Boolean = false
}
