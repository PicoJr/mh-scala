package game.item

/**
  * Created by nol on 17/12/17.
  */
trait AbstractItemFactory {

  /** Create item from itemType
    *
    * @param itemType of item
    * @return item from itemType
    */
  def createItem(itemType: ItemType): Item
}
