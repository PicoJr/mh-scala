package game.item

import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 28/12/17.
  */
trait AbstractDecorator {
  def decorateWithCharmSlot(wrapped: ItemType, slots: Int): ItemType

  def decorateWithDamage(wrapped: ItemType, damage: Int): ItemType

  def decorateWithElement(wrapped: ItemType, elementType: ElementType): ItemType

  def decorateWithEquipment(wrapped: ItemType, slotTypeRequirements: SlotTypeRequirements): ItemType

  def decorateWithMaterial(wrapped: ItemType): ItemType

  def decorateWithProtection(wrapped: ItemType, armor: Int): ItemType

  def decorateWithStatus(wrapped: ItemType, statusType: StatusType): ItemType
}
