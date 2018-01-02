package game.item

/**
  * Created by nol on 02/01/18.
  */
trait AbstractItemTypeFactory[TItemType <: ItemType] {

  def createItemType(level: Int): TItemType

}
