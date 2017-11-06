package item

/**
  * Created by nol on 06/11/17.
  */
class ItemDefault(name: String) extends Item(name: String) {
  override def getName: String = name

  override def getQuantity: Int = 1

  override def isEquipable: Boolean = false
}
