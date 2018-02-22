package game.item

import game.id.{DefaultIdSupplier, IdSupplier}

/**
  * Created by nol on 17/12/17.
  */
class DefaultItemTypeFactory(idSupplier: IdSupplier) extends AbstractItemTypeFactory {
  override def createItemType(level: Int): ItemType = new DefaultItemType(level, idSupplier.getNextUniqueId)
}

object DefaultItemTypeFactory {
  private lazy val instance: AbstractItemTypeFactory = {
    new DefaultItemTypeFactory(new DefaultIdSupplier)
  }

  def getInstance: AbstractItemTypeFactory = instance
}
