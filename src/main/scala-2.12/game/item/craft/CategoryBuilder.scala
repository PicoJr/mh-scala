package game.item.craft

import game.item._
import game.item.craft.addOn.{AddOn, CharmSlotAddOn}
import game.item.craft.nature.{NatureType, WEAPON}

/**
  * Created by nol on 29/11/17.
  */
class CategoryBuilder() {

  private var natureType: NatureType = WEAPON
  private var addOns: Seq[AddOn] = Seq.empty

  def getNature: NatureType = natureType

  def getAddOns: Seq[AddOn] = addOns

  def withNature(natureType: NatureType): CategoryBuilder = {
    this.natureType = natureType
    this
  }

  def withAddOn(addOn: AddOn): CategoryBuilder = {
    addOns = addOns :+ addOn
    this
  }

  def withAddOns(addOns: AddOn*): CategoryBuilder = {
    this.addOns = this.addOns ++ Seq(addOns: _*)
    this
  }

  def copy: CategoryBuilder = {
    (new CategoryBuilder).withNature(natureType).withAddOns(addOns: _*)
  }

  def createDescription: DescriptionBuilder = {
    val descriptionBuilder = new DescriptionBuilder()
    descriptionBuilder.addNature(getNature.name)
    for (addOn <- getAddOns) {
      addOn match {
        case CharmSlotAddOn =>
        case _ => descriptionBuilder.addAdjective(addOn.name)
      }
    }
    descriptionBuilder
  }

  def createItemType(level: Int): ItemType = {
    var itemType = getNature.createItemType(level)
    for (addOn <- getAddOns) {
      itemType = addOn.createItemType(level, itemType)
    }
    itemType
  }
}
