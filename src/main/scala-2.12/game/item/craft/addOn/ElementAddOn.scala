package game.item.craft.addOn

import game.item.element.ElementType
import game.item.{AbstractDecorator, ItemType}

/** Provides elemental protection/damage.
  *
  * @param elementType added
  */
class ElementAddOn[TItemType <: ItemType](elementType: ElementType, decorator: AbstractDecorator[TItemType]) extends DefaultAddOn[TItemType](elementType.name, decorator) {

  override def create(level: Int, itemType: TItemType): TItemType = {
    decorator.decorateWithElement(itemType, elementType)
  }
}

