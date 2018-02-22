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
  private lazy val instance: AbstractItemFactory = {
    new DefaultItemFactory(new DefaultIdSupplier)
  }

  def getInstance: AbstractItemFactory = instance
}


