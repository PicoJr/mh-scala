package game.item

import game.id.Identifiable

/**
  * Created by nol on 21/11/17.
  */
trait ItemTrait extends ItemTypeTrait with Identifiable {

  /** Get item type
    *
    * @return item type
    */
  def getItemType: ItemTypeTrait

  /** Same as getItemType == itemType
    *
    * @param itemType checked
    * @return getItemType == itemType
    */
  def isItemType(itemType: ItemTypeTrait): Boolean = {
    getItemType == itemType
  }

}
