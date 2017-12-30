package game.item

import game.id.IdSupplier

/**
  * Created by nol on 17/12/17.
  */
class DefaultItemFactory(idSupplier: IdSupplier) extends AbstractItemFactory[Item, ItemType] {
  override def createItem(itemType: ItemType): Item = {
    new DefaultItem(itemType, idSupplier.getNextUniqueId)
  }
}
