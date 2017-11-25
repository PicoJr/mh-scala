package game.item

import game.id.Identifiable

/**
  * Created by nol on 21/11/17.
  */
trait Item extends ItemType with Identifiable {

  /** Get item type
    *
    * @return item type
    */
  def getItemType: ItemType

  /** Same as getItemType.getUniqueId == itemType.getUniqueId
    *
    * @param itemType checked
    * @return getItemType == itemType
    */
  def isItemType(itemType: ItemType): Boolean = {
    getItemType.getUniqueId == itemType.getUniqueId
  }

}
