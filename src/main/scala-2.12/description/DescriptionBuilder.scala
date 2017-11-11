package description

import item.{ElementType, Item, StatusType}
import quest.QuestResult
import unit.Hunter

/**
  * Created by nol on 11/11/17.
  */
object DescriptionBuilder {

  def description(i: Item): String = {
    val description = new StringBuilder()
    description.append(i.getName)
    if (Item.isWeapon(i)) description.append("[W]")
    if (Item.isArmor(i)) description.append("[A]")
    if (Item.isCharm(i)) description.append("[C]")
    if (Item.isEquipped(i)) description.append("(E)") else description.append("( )")
    description.append("\n")
    if (Item.getRawDamage(i) > 0) description.append("dmg:").append(Item.getRawDamage(i))
    if (Item.getArmor(i) > 0) description.append("armor:").append(Item.getArmor(i))
    if (Item.getCharmSlotsRequired(i) > 0) description.append("-:").append(Item.getCharmSlotsRequired(i))
    if (Item.getCharmSlotsProvided(i) > 0) description.append("+:").append(Item.getCharmSlotsProvided(i))
    if (Item.getElementType(i) != ElementType.NONE) description.append("{").append(Item.getElementType(i)).append("}")
    if (Item.getStatusType(i) != StatusType.NONE) description.append("<").append(Item.getStatusType(i)).append(">")
    description.toString()
  }

  def description(hunter: Hunter): String = {
    val description = new StringBuilder()
    description.append(hunter.getName)
    description.append("\n")
    description.append("dmg:").append(hunter.getDamage)
    description.append("armor:").append(hunter.getArmor)
    description.toString()
  }

  def description(questResult: QuestResult): String = {
    val description = new StringBuilder()
    if (questResult.isMonsterSlain) description.append("monster slain")
    if (questResult.isHunterDefeated) description.append("hunter defeated")
    if (!questResult.isMonsterSlain & !questResult.isHunterDefeated) description.append("quest max duration reached")
    description.toString()
  }

}
