package game.item.craft.addOn

import game.item.element.ElementType
import game.item.{AbstractDecorator, DefaultDecorator, ItemType}

/** Provides elemental protection/damage.
  *
  * @param elementType added
  */
case class ElementAddOn(elementType: ElementType, decorator: AbstractDecorator = DefaultDecorator.getInstance) extends DefaultAddOn(elementType.name) {

  override def decorate(level: Int, itemType: ItemType): ItemType = {
    decorator.decorateWithElement(itemType, elementType)
  }
}

