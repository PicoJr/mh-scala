package game.item

import game.id.{DefaultIdSupplier, IdSupplier}

/**
  * Created by nol on 17/12/17.
  */
class DefaultItemTypeFactory(idSupplier: IdSupplier) extends AbstractItemTypeFactory[ItemType] {
  override def createItemType(level: Int): ItemType = new DefaultItemType(level, idSupplier.getNextUniqueId)
}

object DefaultItemTypeFactory {
  private final val idSupplier = new DefaultIdSupplier()
  private final val itemTypeFactory = new DefaultItemTypeFactory(idSupplier)

  def getDefaultItemFactory: AbstractItemTypeFactory[ItemType] = itemTypeFactory
}
