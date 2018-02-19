package game.item.craft

import game.item._
import game.item.craft.addOn.{AddOn, CharmSlotAddOn}
import game.item.craft.nature.NatureType

/**
  * Created by nol on 29/11/17.
  */
class CategoryBuilder(natureType: NatureType) {

  private var addOns: Seq[AddOn] = Seq.empty

  def getNature: NatureType = natureType

  def getAddOns: Seq[AddOn] = addOns

  def withAddOn(addOn: AddOn): CategoryBuilder = {
    addOns = addOns :+ addOn
    this
  }

  def withAddOns(addOns: AddOn*): CategoryBuilder = {
    this.addOns = this.addOns ++ Seq(addOns: _*)
    this
  }

  def copy: CategoryBuilder = {
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

  def create(level: Int): ItemType = {
    var itemType = getNature.create(level)
    for (addOn <- getAddOns) {
      itemType = addOn.decorate(level, itemType)
    }
    itemType
  }
}
