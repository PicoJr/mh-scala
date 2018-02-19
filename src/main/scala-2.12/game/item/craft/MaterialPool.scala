package game.item.craft

import game.item.craft.addOn.AddOn
import game.item.{AbstractDecorator, AbstractItemTypeFactory, ItemType}

/**
  * Created by nol on 29/11/17.
  */
class MaterialPool(decorator: AbstractDecorator, itemTypeFactory: AbstractItemTypeFactory) {

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

  def createMaterialFromAddOn(addOn: AddOn, level: Int): ItemType = {
    val descriptionBuilder = new DescriptionBuilder("material").addAdjective(addOn.name)
    val material = decorator.decorateWithMaterial(itemTypeFactory.createItemType(level))
    material.setName(descriptionBuilder.getDescription)
    material
  }

}

