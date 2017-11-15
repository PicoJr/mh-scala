package item

import id.Identifiable
import item.Classification.Classification
import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 15/11/17.
  */
class Item(name: String, level: Int, damage: Int, statusType: StatusType, armor: Int, slotTypeRequirements: SlotTypeRequirements, classifications: Set[Classification], elementType: ElementType, charmSlots: Int)
  extends ItemType(name: String, level: Int, damage: Int, statusType: StatusType, armor: Int, slotTypeRequirements: SlotTypeRequirements, classifications: Set[Classification], elementType: ElementType, charmSlots: Int)
    with Identifiable {

  def this(itemType: ItemType) {
    this(itemType.getName, itemType.getLevel, itemType.getRawDamage, itemType.getStatusType, itemType.getArmor, itemType.getSlotTypeRequirement, itemType.getClassifications, itemType.getElementType, itemType.getCharmSlotsProvided)
  }

  def is(itemType: ItemType): Boolean = {
    getLevel == itemType.getLevel &&
      getRawDamage == itemType.getRawDamage &&
      getStatusType == itemType.getStatusType &&
      getArmor == itemType.getArmor &&
      getSlotTypeRequirement == itemType.getSlotTypeRequirement &&
      getClassifications == itemType.getClassifications &&
      getElementType == itemType.getElementType &&
      getCharmSlotsRequired == itemType.getCharmSlotsRequired
  }

  private final val uniqueID = ItemType.getNewUniqueItemID

  def getUniqueId: Long = uniqueID
}
