package game.item

import game.id.IdSupplier

/**
  * Created by nol on 17/12/17.
  */
class DefaultItemTypeFactory(idSupplier: IdSupplier) extends AbstractItemTypeFactory[ItemType] {
  override def createItemType(level: Int): ItemType = new DefaultItemType(level, idSupplier.getNextUniqueId)
}
