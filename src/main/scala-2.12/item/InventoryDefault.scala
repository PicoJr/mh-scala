package item
import item.ElementType.ElementType

/**
  * Created by nol on 06/11/17.
  */
class InventoryDefault extends Inventory {
  val items: Seq[Item] = Seq.empty

  override def getItems: Seq[Item] = items

  override def getCharmSlots: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + ItemFactory.getCharmSlots(i))
  }

  override def getWeaponEquipped: Option[Item] = {
    items.find(i => ItemFactory.isWeapon(i) & ItemFactory.isEquipped(i))
  }

  override def getItemsEquipped: Seq[Item] = {
    items.filter(i => ItemFactory.isEquipped(i))
  }

  override def getArmorProvided: Int = {
    val equipped : Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum,i) => sum + ItemFactory.getArmor(i))
  }

  override def getDamageProvided: Int = {
    val equipped : Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum, i) => sum + ItemFactory.getRawDamage(i))
  }

  override def getAttackElementType: ElementType = {
    if (getWeaponEquipped.nonEmpty) {
      ItemFactory.getElementType(getWeaponEquipped.get)
    } else {
      ElementType.NONE
    }
  }

  override def getArmorElementTypes: Seq[ElementType] = {
    val armorPartsEquipped : Seq[Item] = items.filter(i => ItemFactory.isArmor(i) && ItemFactory.isEquipped(i))
    armorPartsEquipped.map(i => ItemFactory.getElementType(i))
  }
}
