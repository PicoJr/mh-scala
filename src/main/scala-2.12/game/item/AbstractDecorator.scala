package game.item

import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 28/12/17.
  */
trait AbstractDecorator[T <: ItemType] {
  def decorateWithCharmSlot(wrapped: T, slots: Int): T

  def decorateWithDamage(wrapped: T, damage: Int): T

  def decorateWithElement(wrapped: T, elementType: ElementType): T

  def decorateWithEquipment(wrapped: T, slotTypeRequirements: SlotTypeRequirements): T

  def decorateWithMaterial(wrapped: T): T

  def decorateWithProtection(wrapped: T, armor: Int): T

  def decorateWithStatus(wrapped: T, statusType: StatusType): T
}
