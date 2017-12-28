package game.ui

import game.gameStatistics.DefaultGameStatistics
import game.gamestate.GameState
import game.item.element._
import game.item.inventory.Inventory
import game.item.status.StatusType
import game.item.{ArmorPart, _}
import game.quest.Quest
import game.unit.{GameUnit, Hunter, Monster}

/** Default Description.
  * Created by nol on 11/11/17.
  */
class DefaultDescription(gameState: GameState) extends Description {

  override def descriptionRecipesWith(item: Item): String = {
    val desc = new StringBuilder()
    val recipes = gameState.crafts.getRecipesWith(item)
    for (m <- recipes) {
      m match {
        case ((i1, i2), result) =>
          desc.append(DefaultDescription.descriptionItemType(i1))
          desc.append(" + ").append(DefaultDescription.descriptionItemType(i2))
          desc.append(" -> ").append(DefaultDescription.descriptionItemType(result)).append("\n")
      }
    }
    desc.toString()
  }

  override def descriptionItem(item: Item): String = {
    DefaultDescription.descriptionItem(item)
  }

  override def descriptionInventory(inventory: Inventory): String = {
    DefaultDescription.descriptionInventory(inventory)
  }

  override def descriptionHunter(hunter: Hunter): String = {
    DefaultDescription.descriptionHunter(hunter)
  }

  override def descriptionMonster(monster: Monster): String = {
    DefaultDescription.descriptionMonster(monster)
  }

  override def descriptionQuest(quest: Quest): String = {
    val desc = new StringBuilder()
    if (gameState.isCompletedQuest(quest.getUniqueId)) {
      desc.append("<completed>\n")
    }
    desc.append(DefaultDescription.descriptionQuest(quest))
    desc.toString()
  }

  override def descriptionStatistics(): String = {
    val desc = new StringBuilder()
    if (gameState.allQuestsCompleted) desc.append("All Quests Completed!\n")
    desc.append("quests completed:").append(gameState.getCompletedQuests.size)
    desc.append("/").append(gameState.quests.size).append("\n")
    desc.append("quests failures: ").append(DefaultGameStatistics.questFailedCount.now).append("\n")
    desc.append("quests successes: ").append(DefaultGameStatistics.questSucceededCount.now).append("\n")
    desc.append("quests attempts: ").append(DefaultGameStatistics.questStartedCount.now).append("\n")
    desc.append("items crafted: ").append(DefaultGameStatistics.itemCraftedCount.now).append("\n")
    desc.toString()
  }
}

object DefaultDescription {

  def descriptionElementType(elementType: ElementType): String = {
    elementType.name.substring(0, 4)
  }

  def descriptionStatusType(statusType: StatusType): String = {
    statusType.name.substring(0, 4)
  }

  def descriptionItemType(i: ItemType): String = {
    val desc = new StringBuilder()
    desc.append(i.getName)
    desc.append("[lvl " + i.getLevel + "]")
    if (i.hasDamage) desc.append(" dmg:").append(i.getDamage)
    if (i.hasArmor) desc.append(" armor:").append(i.getArmor)
    if (i.requiresSlot) desc.append("-:").append(i.getCharmSlotsRequired)
    if (i.providesSlot) desc.append("+:").append(i.getCharmSlotsProvided)
    if (!i.isMaterial) desc.append("{").append(descriptionElementType(i.getElementType)).append("}")
    if (!i.isMaterial) desc.append("<").append(descriptionStatusType(i.getStatusType)).append(">")
    desc.toString()
  }

  def descriptionItem(i: Item): String = {
    val desc = new StringBuilder()
    desc.append("[").append(i.getUniqueId).append("]")
    desc.append(descriptionItemType(i))
    desc.toString()
  }

  def descriptionInventory(inventory: Inventory): String = {
    val desc = new StringBuilder()
    desc.append("slots: ").append(inventory.getCharmSlotsUsed)
    desc.append("/").append(inventory.getCharmSlotsProvided).append("\n")
    inventory.getWeaponEquipped match {
      case Some(w) => desc.append(descriptionItem(w)).append("\n")
      case None =>
    }
    for (armorPart <- ArmorPart.values) {
      inventory.getArmorEquipped(armorPart) match {
        case Some(a) => desc.append(descriptionItem(a)).append("\n")
        case None =>
      }
    }
    for (c <- inventory.getCharmsEquipped) {
      desc.append(descriptionItem(c)).append("\n")
    }
    desc.append("\n-----\n")
    for (i <- inventory.getItems.filter(i => !inventory.isEquipped(i))) {
      desc.append(descriptionItem(i)).append("\n")
    }
    desc.toString()
  }

  def descriptionGameUnit(gameUnit: GameUnit): String = {
    val desc = new StringBuilder()
    desc.append(gameUnit.name)
    desc.append("\n")
    desc.append("life:  ").append(gameUnit.life)
    desc.append("\n")
    desc.append("dmg:   ").append(gameUnit.damage).append(" ")
    desc.append("{").append(descriptionElementType(gameUnit.attackElementType)).append("}")
    desc.append("<").append(descriptionStatusType(gameUnit.attackStatusType)).append(">")
    desc.append("\n")
    desc.append("armor: ").append(gameUnit.armor).append(" ")
    for (element <- gameUnit.elementalResistances) {
      desc.append("{").append(descriptionElementType(element)).append("}")
    }
    for (status <- gameUnit.statusResistances) {
      desc.append("<").append(descriptionStatusType(status)).append(">")
    }
    desc.toString()
  }

  def descriptionHunter(hunter: Hunter): String = {
    descriptionGameUnit(hunter)
  }

  def descriptionMonster(monster: Monster): String = {
    val desc = new StringBuilder()
    desc.append("[").append(monster.getUniqueId).append("]")
    desc.append(descriptionGameUnit(monster))
    desc.toString()
  }

  def descriptionQuest(quest: Quest): String = {
    val desc = new StringBuilder()
    desc.append("quest[").append(quest.getUniqueId).append("]")
    desc.append(" level: ").append(quest.level)
    desc.append("\n")
    desc.append(descriptionMonster(quest.monster))
    desc.append("\n")
    desc.toString()
  }

}
