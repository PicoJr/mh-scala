package description

import item._
import quest.{Quest, QuestResult}
import unit.{Hunter, Monster}

/**
  * Created by nol on 11/11/17.
  */
object DescriptionBuilder {

  def description(i: Item): String = {
    val desc = new StringBuilder()
    desc.append(i.getName)
    desc.append("[").append(i.getUniqueID).append("]")
    if (Item.isWeapon(i)) desc.append("[W]")
    if (Item.isArmor(i)) desc.append("[A]")
    if (Item.isCharm(i)) desc.append("[C]")
    desc.append("\n")
    if (Item.getRawDamage(i) > 0) desc.append("dmg:").append(Item.getRawDamage(i))
    if (Item.getArmor(i) > 0) desc.append(" armor:").append(Item.getArmor(i))
    if (Item.getCharmSlotsRequired(i) > 0) desc.append("-:").append(Item.getCharmSlotsRequired(i))
    if (Item.getCharmSlotsProvided(i) > 0) desc.append("+:").append(Item.getCharmSlotsProvided(i))
    if (Item.getElementType(i) != ElementType.NONE) desc.append("{").append(Item.getElementType(i)).append("}")
    if (Item.getStatusType(i) != StatusType.NONE) desc.append("<").append(Item.getStatusType(i)).append(">")
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
    desc.append("quest[").append(quest.getUniqueID).append("]")
    if (quest.isCompleted) desc.append(" completed")
    desc.append("\n")
    desc.append(description(quest.getMonster))
    desc.toString()
  }
}
