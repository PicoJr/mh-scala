package game.description

import game.item.{ArmorPart, _}
import game.quest.{QuestResult, QuestTrait}
import game.unit.{Hunter, Monster}

/**
  * Created by nol on 11/11/17.
  */
object DescriptionFactory {


  def description(recipes: Map[(ItemTypeTrait, ItemTypeTrait), ItemTypeTrait]): String = {
    val desc = new StringBuilder()
    for (m <- recipes) {
      m match {
        case ((i1, i2), result) =>
          desc.append(i1.getName).append(" + ").append(i2.getName)
          desc.append(" -> ").append(description(result)).append("\n")
      }
    }
    desc.toString()
  }

  def description(i: ItemTypeTrait): String = {
    val desc = new StringBuilder()
    desc.append(i.getName)
    desc.append("[" + i.getLevel + "]")
    if (i.hasDamage) desc.append(" dmg:").append(i.getDamage)
    if (i.hasArmor) desc.append(" armor:").append(i.getArmor)
    if (i.requiresSlot) desc.append("-:").append(i.getCharmSlotsRequired)
    if (i.providesSlot) desc.append("+:").append(i.getCharmSlotsProvided)
    if (i.hasElementType) desc.append("{").append(i.getElementType).append("}")
    if (i.hasStatusType) desc.append("<").append(i.getStatusType).append(">")
    desc.toString()
  }

  def description(i: ItemTrait): String = {
    val desc = new StringBuilder()
    desc.append("[").append(i.getUniqueId).append("]")
    desc.append(description(i.asInstanceOf[ItemTypeTrait]))
    desc.toString()
  }

  def description(inventory: InventoryTrait): String = {
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
    desc.append("[").append(monster.getUniqueId).append("]")
    desc.toString()
  }

  def description(quest: QuestTrait): String = {
    val desc = new StringBuilder()
    desc.append("quest[").append(quest.getUniqueId).append("]")
    desc.append("\n")
    desc.append(description(quest.getMonster))
    desc.toString()
  }
}
