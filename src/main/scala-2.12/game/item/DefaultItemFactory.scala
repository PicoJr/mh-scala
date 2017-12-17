package game.item

import game.id.{DefaultIdSupplier, IdSupplier}

/**
  * Created by nol on 17/12/17.
  */
class DefaultItemFactory(idSupplier: IdSupplier) extends AbstractItemFactory {
  override def createItem(itemType: ItemType): Item = {
    new DefaultItem(itemType, idSupplier.getNextUniqueId)
  }
}

object DefaultItemFactory {
  private final val idSupplier = new DefaultIdSupplier()
  private final val itemFactory: AbstractItemFactory = new DefaultItemFactory(idSupplier)

  def getDefaultItemFactory: AbstractItemFactory = itemFactory
}
