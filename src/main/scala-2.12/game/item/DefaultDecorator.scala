package game.item

import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 28/12/17.
  */
class DefaultDecorator extends AbstractDecorator {

  override def decorateWithCharmSlot(wrapped: ItemType, charmSlotsProvided: Int): ItemType = CharmSlot(wrapped, charmSlotsProvided)

  override def decorateWithDamage(wrapped: ItemType, damage: Int): ItemType = Damage(wrapped, damage)

  override def decorateWithElement(wrapped: ItemType, elementType: ElementType): ItemType = Element(wrapped, elementType)

  override def decorateWithEquipment(wrapped: ItemType, slotTypeRequirements: SlotTypeRequirements): ItemType = Equipment(wrapped, slotTypeRequirements)

  override def decorateWithMaterial(wrapped: ItemType): ItemType = Material(wrapped)

  override def decorateWithProtection(wrapped: ItemType, armor: Int): ItemType = Protection(wrapped, armor)

  override def decorateWithStatus(wrapped: ItemType, statusType: StatusType): ItemType = Status(wrapped, statusType)
}

object DefaultDecorator {
  private lazy val instance: AbstractDecorator = {
    new DefaultDecorator
  }

  def getInstance: AbstractDecorator = instance
}
