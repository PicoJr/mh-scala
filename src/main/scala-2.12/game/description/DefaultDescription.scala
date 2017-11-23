package game.description

import game.gamestate.GameState
import game.item.{ArmorPart, _}
import game.quest.{Quest, QuestResult}
import game.unit.{Hunter, Monster}

/**
  * Created by nol on 11/11/17.
  */
class DefaultDescription extends Description {


  private def description(i: ItemType): String = {
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

  private def description(i: Item): String = {
    val desc = new StringBuilder()
    desc.append("[").append(i.getUniqueId).append("]")
    desc.append(description(i.asInstanceOf[ItemType]))
    desc.toString()
  }

  private def descriptionInventory(inventory: Inventory): String = {
    val desc = new StringBuilder()
    desc.append("\nweapon\n")
    inventory.getWeaponEquipped match {
      case Some(w) => desc.append(description(w)).append("\n")
      case None =>
    }
    desc.append("\narmor\n")
    for (armorPart <- ArmorPart.values) {
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

  private def descriptionHunter(hunter: Hunter): String = {
    val desc = new StringBuilder()
    desc.append(hunter.getName)
    desc.append("\n")
    desc.append("dmg:").append(hunter.getDamage)
    desc.append(" armor:").append(hunter.getArmor)
    desc.toString()
  }

  private def descriptionQuestResult(questResult: QuestResult): String = {
    val desc = new StringBuilder()
    if (questResult.isMonsterSlain) desc.append("monster slain")
    if (questResult.isHunterDefeated) desc.append("hunter defeated")
    if (!questResult.isMonsterSlain & !questResult.isHunterDefeated) desc.append("quest max duration reached")
    desc.toString()
  }

  private def descriptionMonster(monster: Monster): String = {
    val desc = new StringBuilder()
    desc.append(monster.getName)
    desc.append("[").append(monster.getUniqueId).append("]")
    desc.toString()
  }

  private def descriptionQuest(quest: Quest): String = {
    val desc = new StringBuilder()
    desc.append("quest[").append(quest.getUniqueId).append("]")
    desc.append("\n")
    desc.append(descriptionMonster(quest.getMonster))
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
              desc.append(description(i1)).append(" + ").append(description(i2))
              desc.append(" -> ").append(description(result)).append("\n")
          }
        }
        desc.toString()
      case None => s"item with id $itemId not found"
    }
  }

  override def descriptionItem(gameState: GameState, itemId: Long): String = {
    gameState.findItem(itemId) match {
      case Some(i) => description(i)
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
      case Some(q) => descriptionQuest(q)
      case None => s"quest with id $questId not found"
    }
  }

  override def descriptionQuestResult(gameState: GameState, questResult: QuestResult): String = {
    descriptionQuestResult(questResult)
  }
}
