package item
import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 06/11/17.
  */
class Inventory {
  var items: Seq[Item] = Seq.empty

  def getItems: Seq[Item] = items

  def getCharmSlots: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + Item.getCharmSlots(i))
  }

  def getWeaponEquipped: Option[Item] = {
    items.find(i => Item.isWeapon(i) & Item.isEquipped(i))
  }

  def getItemsEquipped: Seq[Item] = {
    items.filter(i => Item.isEquipped(i))
  }

  def getArmorProvided: Int = {
    val equipped : Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum, i) => sum + Item.getArmor(i))
  }

  def getDamageProvided: Int = {
    val equipped : Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum, i) => sum + Item.getRawDamage(i))
  }

  def getAttackElementType: ElementType = {
    if (getWeaponEquipped.nonEmpty) {
      Item.getElementType(getWeaponEquipped.get)
    } else {
      ElementType.NONE
    }
  }

  def getArmorElementTypes: Seq[ElementType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => Item.isArmor(i) && Item.isEquipped(i))
    armorPartsEquipped.map(i => Item.getElementType(i))
  }

  def getAttackStatusType: StatusType = {
    if (getWeaponEquipped.nonEmpty) {
      Item.getStatusType(getWeaponEquipped.get)
    } else {
      StatusType.NONE
    }
  }

  def getArmorStatusTypes: Seq[StatusType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => Item.isArmor(i) && Item.isEquipped(i))
    armorPartsEquipped.map(i => Item.getStatusType(i))
  }

  def addItems(items: Item*): Unit = {
    this.items ++ items
  }
}
