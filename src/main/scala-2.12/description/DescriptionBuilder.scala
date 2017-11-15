package description

import item.{ArmorPart, _}
import quest.{Quest, QuestResult}
import unit.{Hunter, Monster}

/**
  * Created by nol on 11/11/17.
  */
object DescriptionBuilder {


  def description(recipes: Map[(ItemType, ItemType), ItemType]): String = {
    val desc = new StringBuilder()
    for (m <- recipes) {
      m match {
        case ((i1, i2), result) =>
          desc.append(i1.getName).append(" + ").append(i2.getName).append(" -> ").append(description(result)).append("\n")
      }
    }
    desc.toString()
  }

  def description(i: ItemType): String = {
    val desc = new StringBuilder()
    desc.append(i.getName)
    desc.append("[" + i.getLevel + "]")
    if (i.isWeapon) desc.append("[W]")
    if (i.isArmor) desc.append("[A]")
    if (i.isCharm) desc.append("[C]")
    if (i.isArmor) {
      i.getSlotTypeRequirement match {
        case ARMOR_SLOT(ArmorPart.HEAD) => desc.append("[HEAD]")
        case ARMOR_SLOT(ArmorPart.BODY) => desc.append("[BODY]")
        case ARMOR_SLOT(ArmorPart.ARMS) => desc.append("[ARMS]")
        case ARMOR_SLOT(ArmorPart.LEGS) => desc.append("[LEGS]")
        case _ =>
      }
    }
    if (i.getRawDamage > 0) desc.append(" dmg:").append(i.getRawDamage)
    if (i.getArmor > 0) desc.append(" armor:").append(i.getArmor)
    if (i.getCharmSlotsRequired > 0) desc.append("-:").append(i.getCharmSlotsRequired)
    if (i.getCharmSlotsProvided > 0) desc.append("+:").append(i.getCharmSlotsProvided)
    if (i.getElementType != ElementType.NONE) desc.append("{").append(i.getElementType).append("}")
    if (i.getStatusType != StatusType.NONE) desc.append("<").append(i.getStatusType).append(">")
    desc.toString()
  }

  def description(i: Item): String = {
    val desc = new StringBuilder()
    desc.append("[").append(i.getUniqueId).append("]")
    desc.append(description(i.asInstanceOf[ItemType]))
    desc.toString()
  }

  def description(inventory: Inventory): String = {
    val desc = new StringBuilder()
    desc.append("\nweapon\n")
    inventory.getWeaponEquipped match {
      case Some(w) => desc.append(description(w)).append("\n")
      case None =>
    }
    desc.append("\narmor\n")
    for (armorPart <- Seq(ArmorPart.HEAD, ArmorPart.BODY, ArmorPart.ARMS, ArmorPart.LEGS)) {
      inventory.getArmorEquipped(armorPart) match {
        case Some(a) => desc.append(description(a)).append("\n")
        case None =>
      }
    }
    desc.append("\n-----\n")
    for (i <- inventory.getItems.filter(i => !inventory.isEquipped(i))) {
      desc.append(description(i)).append("\n")
    }
    desc.toString()
  }

  def description(hunter: Hunter): String = {
    val desc = new StringBuilder()
    desc.append(hunter.getName)
    desc.append("\n")
    desc.append("dmg:").append(hunter.getDamage)
    desc.append(" armor:").append(hunter.getArmor)
    desc.toString()
  }

  def description(questResult: QuestResult): String = {
    val desc = new StringBuilder()
    if (questResult.isMonsterSlain) desc.append("monster slain")
    if (questResult.isHunterDefeated) desc.append("hunter defeated")
    if (!questResult.isMonsterSlain & !questResult.isHunterDefeated) desc.append("quest max duration reached")
    desc.toString()
  }

  def description(monster: Monster): String = {
    val desc = new StringBuilder()
    desc.append(monster.getName)
    desc.append("[").append(monster.getUniqueID).append("]")
    desc.toString()
  }

  def description(quest: Quest): String = {
    val desc = new StringBuilder()
    desc.append("quest[").append(quest.getUniqueId).append("]")
    desc.append("\n")
    desc.append(description(quest.getMonster))
    desc.toString()
  }
}
