package item

/**
  * Created by nol on 04/11/17.
  */
trait Item {
  def getName: String

  def getQuantity: Int

  def isEquipable: Boolean
}
