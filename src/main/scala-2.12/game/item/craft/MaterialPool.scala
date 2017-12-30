package game.item.craft

import game.item.craft.addOn.AddOn
import game.item.{AbstractItemTypeFactory, ItemType}

/**
  * Created by nol on 29/11/17.
  */
class MaterialPool[TItemType <: ItemType](itemTypeFactory: AbstractItemTypeFactory[TItemType]) {

  private var materials = Map.empty[(AddOn[TItemType], Int), TItemType]

  def getMaterial(addOn: AddOn[TItemType], level: Int): TItemType = {
    materials.get(addOn, level) match {
      case Some(m) => m
      case None =>
        val m = createMaterialFromAddOn(addOn, level)
        materials += ((addOn, level) -> m)
        m
    }
  }

  def createMaterialFromAddOn(addOn: AddOn[TItemType], level: Int): TItemType = {
    val descriptionBuilder = new DescriptionBuilder("material")
    descriptionBuilder.addAdjective(addOn.name)
    itemTypeFactory.createMaterial(descriptionBuilder.getDescription, level)
  }

}

