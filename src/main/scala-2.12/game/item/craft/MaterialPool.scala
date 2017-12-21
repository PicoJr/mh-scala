package game.item.craft

import game.item.craft.addOn.AddOn
import game.item.{AbstractItemTypeFactory, DefaultItemTypeFactory, ItemType}

/**
  * Created by nol on 29/11/17.
  */
class MaterialPool(itemTypeFactory: AbstractItemTypeFactory) {

  def this() {
    this(DefaultItemTypeFactory.getDefaultItemFactory)
  }

  private var materials = Map.empty[(AddOn, Int), ItemType]

  def getMaterial(addOn: AddOn, level: Int): ItemType = {
    materials.get(addOn, level) match {
      case Some(m) => m
      case None =>
        val m = createMaterialFromAddOn(addOn, level)
        materials += ((addOn, level) -> m)
        m
    }
  }

  private def createMaterialFromAddOn(addOn: AddOn, level: Int): ItemType = {
    val descriptionBuilder = new DescriptionBuilder().addNature("material")
    descriptionBuilder.addAdjective(addOn.name)
    itemTypeFactory.createMaterial(descriptionBuilder.getDescription, level)
  }

}

