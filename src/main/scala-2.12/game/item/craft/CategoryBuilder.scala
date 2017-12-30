package game.item.craft

import game.item._
import game.item.craft.addOn.{AddOn, CharmSlotAddOn}
import game.item.craft.nature.NatureType

/**
  * Created by nol on 29/11/17.
  */
class CategoryBuilder[TItemType <: ItemType](natureType: NatureType[TItemType]) {

  private var addOns: Seq[AddOn[TItemType]] = Seq.empty

  def getNature: NatureType[TItemType] = natureType

  def getAddOns: Seq[AddOn[TItemType]] = addOns

  def withAddOn(addOn: AddOn[TItemType]): CategoryBuilder[TItemType] = {
    addOns = addOns :+ addOn
    this
  }

  def withAddOns(addOns: AddOn[TItemType]*): CategoryBuilder[TItemType] = {
    this.addOns = this.addOns ++ Seq(addOns: _*)
    this
  }

  def copy: CategoryBuilder[TItemType] = {
    new CategoryBuilder(natureType).withAddOns(addOns: _*)
  }

  def createDescription: DescriptionBuilder = {
    val descriptionBuilder = new DescriptionBuilder(getNature.name)
    for (addOn <- getAddOns) {
      addOn match {
        case CharmSlotAddOn(_) =>
        case _ => descriptionBuilder.addAdjective(addOn.name)
      }
    }
    descriptionBuilder
  }

  def create(level: Int): TItemType = {
    var itemType = getNature.create(level)
    for (addOn <- getAddOns) {
      itemType = addOn.decorate(level, itemType)
    }
    itemType
  }
}
