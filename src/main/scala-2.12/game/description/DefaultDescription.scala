package game.description

import game.gamestate.GameState
import game.item.inventory.Inventory
import game.item.{ArmorPart, _}
import game.quest.{Quest, QuestResult}
import game.unit.{GameUnit, Hunter, Monster}

/**
  * Created by nol on 11/11/17.
  */
class DefaultDescription extends Description {

  def descriptionItemType(i: ItemType): String = {
    val desc = new StringBuilder()
    desc.append("[").append(i.getUniqueId).append("]")
    desc.append(i.getName)
    desc.append("[" + i.getLevel + "]")
    if (i.hasDamage) desc.append(" dmg:").append(i.getDamage)
    if (i.hasArmor) desc.append(" armor:").append(i.getArmor)
    if (i.requiresSlot) desc.append("-:").append(i.getCharmSlotsRequired)
    if (i.providesSlot) desc.append("+:").append(i.getCharmSlotsProvided)
    if (!i.isMaterial) desc.append("{").append(i.getElementType).append("}")
    if (!i.isMaterial) desc.append("<").append(i.getStatusType).append(">")
    desc.toString()
  }

  private def descriptionItem(i: Item): String = {
    val desc = new StringBuilder()
    desc.append("[").append(i.getItemType.getUniqueId).append("]")
    desc.append(descriptionItemType(i))
    desc.toString()
  }

  private def descriptionInventory(inventory: Inventory): String = {
    val desc = new StringBuilder()
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
    desc.append(" life: ").append(gameUnit.getLife)
    desc.append(" dmg: ").append(gameUnit.getDamage)
    desc.append("{").append(gameUnit.getAttackElementType).append("}")
    desc.append("<").append(gameUnit.getAttackStatusType).append(">")
    desc.append(" armor: ").append(gameUnit.getArmor)
    for (element <- gameUnit.getArmorElementTypes) {
      desc.append("{").append(element).append("}")
    }
    for (status <- gameUnit.getArmorStatusTypes) {
      desc.append("<").append(status).append(">")
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

  private def descriptionQuestResult(questResult: QuestResult): String = {
    val desc = new StringBuilder()
    if (questResult.isMonsterSlain) desc.append("monster slain\n")
    if (questResult.isHunterDefeated) desc.append("hunter defeated\n")
    if (!questResult.isMonsterSlain & !questResult.isHunterDefeated) desc.append("quest max duration reached\n")
    desc.append("time elapsed: ").append(questResult.getTimeElapsed.toInt).append("\n")
    desc.append("hunter dmg dealt ").append(questResult.getDamageDealtByHunter.toInt).append("\n")
    desc.append("hunter dmg received ").append(questResult.getDamageDealtByMonster.toInt).append("\n")
    desc.toString()
  }

  private def descriptionQuest(quest: Quest): String = {
    val desc = new StringBuilder()
    desc.append("quest[").append(quest.getUniqueId).append("]")
    desc.append(" level: ").append(quest.getLevel)
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

  override def descriptionQuestResult(gameState: GameState, questResult: QuestResult): String = {
    descriptionQuestResult(questResult)
  }

  override def descriptionScore(gameState: GameState): String = {
    val desc = new StringBuilder()
    if (gameState.allQuestsCompleted) desc.append("All Quests Completed!\n")
    desc.append("quests completed:").append(gameState.getCompletedQuests.size)
    desc.append("/").append(gameState.getQuests.size).append("\n")
    desc.append("quests failures: ").append(gameState.getScore.getQuestsFailures).append("\n")
    desc.append("quests successes: ").append(gameState.getScore.getQuestsSuccesses).append("\n")
    desc.append("quests attempts: ").append(gameState.getScore.getQuestAttempts).append("\n")
    desc.toString()
  }
}
