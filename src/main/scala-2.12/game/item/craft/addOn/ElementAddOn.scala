package game.item.craft.addOn

import game.item.element.ElementType
import game.item.{Element, ItemType}

/** Provides elemental protection/damage.
  *
  * @param elementType added
  */
class ElementAddOn(elementType: ElementType) extends DefaultAddOn {
  override val name: String = elementType.name

  override def createItemType(level: Int, itemType: ItemType): ItemType = {
    Element(itemType, elementType)
  }
}

