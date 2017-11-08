package item
import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 06/11/17.
  */
class InventoryDefault extends Inventory {
  var items: Seq[Item] = Seq.empty

  override def getItems: Seq[Item] = items

  override def getCharmSlots: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + Item.getCharmSlots(i))
  }

  override def getWeaponEquipped: Option[Item] = {
    items.find(i => Item.isWeapon(i) & Item.isEquipped(i))
  }

  override def getItemsEquipped: Seq[Item] = {
    items.filter(i => Item.isEquipped(i))
  }

  override def getArmorProvided: Int = {
    val equipped : Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum, i) => sum + Item.getArmor(i))
  }

  override def getDamageProvided: Int = {
    val equipped : Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum, i) => sum + Item.getRawDamage(i))
  }

  override def getAttackElementType: ElementType = {
    if (getWeaponEquipped.nonEmpty) {
      Item.getElementType(getWeaponEquipped.get)
    } else {
      ElementType.NONE
    }
  }

  override def getArmorElementTypes: Seq[ElementType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => Item.isArmor(i) && Item.isEquipped(i))
    armorPartsEquipped.map(i => Item.getElementType(i))
  }

  override def getAttackStatusType: StatusType = {
    if (getWeaponEquipped.nonEmpty) {
      Item.getStatusType(getWeaponEquipped.get)
    } else {
      StatusType.NONE
    }
  }

  override def getArmorStatusTypes: Seq[StatusType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => Item.isArmor(i) && Item.isEquipped(i))
    armorPartsEquipped.map(i => Item.getStatusType(i))
  }

  override def addItems(items: Item*): Unit = {
    this.items ++ items
  }
}
