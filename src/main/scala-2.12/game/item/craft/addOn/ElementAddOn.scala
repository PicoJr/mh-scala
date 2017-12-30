package game.item.craft.addOn

import game.item.element.ElementType
import game.item.{AbstractDecorator, ItemType}

/** Provides elemental protection/damage.
  *
  * @param elementType added
  */
case class ElementAddOn[TItemType <: ItemType](elementType: ElementType, decorator: AbstractDecorator[TItemType]) extends DefaultAddOn[TItemType](elementType.name) {

  override def decorate(level: Int, itemType: TItemType): TItemType = {
    decorator.decorateWithElement(itemType, elementType)
  }
}

