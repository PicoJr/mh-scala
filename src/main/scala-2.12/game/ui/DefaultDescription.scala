package game.ui

import game.gameStatistics.DefaultGameStatistics
import game.gamestate.GameState
import game.item.element._
import game.item.inventory.Inventory
import game.item.status.{NEUTRAL, SLEEP, STUN, StatusType}
import game.item.{ArmorPart, _}
import game.quest.Quest
import game.unit.{GameUnit, Hunter, Monster}

/** Default Description.
  * Created by nol on 11/11/17.
  */
object DefaultDescription extends Description {

  def descriptionElementType(elementType: ElementType): String = elementType match {
    case ELECTRIC => "E"
    case FIRE => "F"
    case NORMAL => "N"
    case WATER => "W"
  }

  def descriptionStatusType(statusType: StatusType): String = statusType match {
    case NEUTRAL => "N"
    case SLEEP => "Sl"
    case STUN => "St"
  }

  def descriptionItemType(i: ItemType): String = {
    val desc = new StringBuilder()
    desc.append(i.getName)
    desc.append("[" + i.getLevel + "]")
    if (i.hasDamage) desc.append(" dmg:").append(i.getDamage)
    if (i.hasArmor) desc.append(" armor:").append(i.getArmor)
    if (i.requiresSlot) desc.append("-:").append(i.getCharmSlotsRequired)
    if (i.providesSlot) desc.append("+:").append(i.getCharmSlotsProvided)
    if (!i.isMaterial) desc.append("{").append(descriptionElementType(i.getElementType)).append("}")
    if (!i.isMaterial) desc.append("<").append(descriptionStatusType(i.getStatusType)).append(">")
    desc.toString()
  }

  private def descriptionItem(i: Item): String = {
    val desc = new StringBuilder()
    desc.append("[").append(i.getUniqueId).append("]")
    desc.append(descriptionItemType(i))
    desc.toString()
  }

  private def descriptionInventory(inventory: Inventory): String = {
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

  private def descriptionGameUnit(gameUnit: GameUnit): String = {
    val desc = new StringBuilder()
    desc.append(gameUnit.getName)
    desc.append("\n")
    desc.append("life:  ").append(gameUnit.getLife)
    desc.append("\n")
    desc.append("dmg:   ").append(gameUnit.getDamage).append(" ")
    desc.append("{").append(descriptionElementType(gameUnit.getAttackElementType)).append("}")
    desc.append("<").append(descriptionStatusType(gameUnit.getAttackStatusType)).append(">")
    desc.append("\n")
    desc.append("armor: ").append(gameUnit.getArmor).append(" ")
    for (element <- gameUnit.getElementalResistances) {
      desc.append("{").append(descriptionElementType(element)).append("}")
    }
    for (status <- gameUnit.getStatusResistances) {
      desc.append("<").append(descriptionStatusType(status)).append(">")
    }
    desc.toString()
  }

  private def descriptionHunter(hunter: Hunter): String = {
    descriptionGameUnit(hunter)
  }

  private def descriptionMonster(monster: Monster): String = {
    val desc = new StringBuilder()
    desc.append("[").append(monster.getUniqueId).append("]")
    desc.append(descriptionGameUnit(monster))
    desc.toString()
  }

  private def descriptionQuest(quest: Quest): String = {
    val desc = new StringBuilder()
    desc.append("quest[").append(quest.getUniqueId).append("]")
    desc.append(" level: ").append(quest.getLevel)
    desc.append("\n")
    desc.append(descriptionMonster(quest.getMonster))
    desc.append("\n")
    desc.toString()
  }

  override def descriptionRecipesWith(gameState: GameState, itemId: Long): String = {
    val desc = new StringBuilder()
    gameState.findItem(itemId) match {
      case Some(i) =>
        val recipes = gameState.getCrafts.getRecipesWith(i)
        for (m <- recipes) {
          m match {
            case ((i1, i2), result) =>
              desc.append(descriptionItemType(i1)).append(" + ").append(descriptionItemType(i2))
              desc.append(" -> ").append(descriptionItemType(result)).append("\n")
          }
        }
        desc.toString()
      case None => s"item with id $itemId not found"
    }
  }

  override def descriptionItem(gameState: GameState, itemId: Long): String = {
    gameState.findItem(itemId) match {
      case Some(i) => descriptionItem(i)
      case None => s"item with id $itemId not found"
    }
  }

  override def descriptionInventory(gameState: GameState): String = {
    descriptionInventory(gameState.getHunter.getInventory)
  }

  override def descriptionHunter(gameState: GameState): String = {
    descriptionHunter(gameState.getHunter)
  }

  override def descriptionMonster(gameState: GameState, monsterId: Long): String = {
    gameState.findMonster(monsterId) match {
      case Some(m) => descriptionMonster(m)
      case None => s"monster with id $monsterId not found"
    }
  }

  override def descriptionQuest(gameState: GameState, questId: Long): String = {
    gameState.findQuest(questId) match {
      case Some(q) =>
        val desc = new StringBuilder()
        if (gameState.isCompletedQuest(questId)) {
          desc.append("<completed>\n")
        }
        desc.append(descriptionQuest(q))
        desc.toString()
      case None => s"quest with id $questId not found"
    }
  }

  override def descriptionStatistics(gameState: GameState): String = {
    val desc = new StringBuilder()
    if (gameState.allQuestsCompleted) desc.append("All Quests Completed!\n")
    desc.append("quests completed:").append(gameState.getCompletedQuests.size)
    desc.append("/").append(gameState.getQuests.size).append("\n")
    desc.append("quests failures: ").append(DefaultGameStatistics.questFailedCount.now).append("\n")
    desc.append("quests successes: ").append(DefaultGameStatistics.questSucceededCount.now).append("\n")
    desc.append("quests attempts: ").append(DefaultGameStatistics.questStartedCount.now).append("\n")
    desc.append("items crafted: ").append(DefaultGameStatistics.itemCraftedCount.now).append("\n")
    desc.toString()
  }
}
