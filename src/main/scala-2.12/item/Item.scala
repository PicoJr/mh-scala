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

  private final val uniqueID = ItemType.getNewUniqueItemID

  def getUniqueId: Long = uniqueID
}
