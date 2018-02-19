package game.item

/**
  * Created by nol on 02/01/18.
  */
trait AbstractItemTypeFactory {

  def createItemType(level: Int): ItemType

}
