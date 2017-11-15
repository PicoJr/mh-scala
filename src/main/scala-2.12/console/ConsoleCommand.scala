package console

import description.DescriptionBuilder
import game.{GameLogic, GameState}
import item.Item
import quest.Quest
import unit.Monster

/**
  * Created by nol on 12/11/17.
  */
class ConsoleCommand extends Command {

  private def getQuest(gameState: GameState, questID: Long): Option[Quest] = {
    gameState.getQuests.find(q => q.getUniqueId == questID)
  }

  private def getMonster(gameState: GameState, monsterID: Long): Option[Monster] = {
    gameState.getQuests.find(q => q.getMonster.getUniqueID == monsterID) match {
      case Some(q) => Some(q.getMonster)
      case None => None
    }
  }

  private def getItem(gameState: GameState, itemID: Long): Option[Item] = {
    gameState.getHunter.getInventory.getItem(itemID)
  }

  private def tryActionOnItem(gameState: GameState, itemId: Long, action: Item => Unit): Unit = {
    getItem(gameState, itemId) match {
      case Some(i) => action(i)
      case None => println(s"item with id $itemId not found")
    }
  }

  override def listQuests(gameState: GameState): Unit = {
    for (q <- gameState.getQuests) {
      println(DescriptionBuilder.description(q))
    }
  }

  override def listInventory(gameState: GameState): Unit = {
    println(DescriptionBuilder.description(gameState.getHunter.getInventory))
  }

  override def showQuest(gameState: GameState, questID: Long): Unit = {
    getQuest(gameState, questID) match {
      case Some(q) => println(DescriptionBuilder.description(q))
      case None => println(s"quest with id $questID not found")
    }
  }

  override def showMonster(gameState: GameState, monsterID: Long): Unit = {
    getMonster(gameState, monsterID) match {
      case Some(m) => println(DescriptionBuilder.description(m))
      case None => println(s"monster with id $monsterID not found")
    }
  }

  override def showHunter(gameState: GameState): Unit = {
    println(DescriptionBuilder.description(gameState.getHunter))
  }

  override def equipItem(gameState: GameState, itemID: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    tryActionOnItem(gameState, itemID, i => if (inventory.canBeEquipped(i)) inventory.equipItem(i.getUniqueId))
  }

  override def unEquipItem(gameState: GameState, itemID: Long): Unit = {
    tryActionOnItem(gameState, itemID, i => gameState.getHunter.getInventory.unEquipItem(i.getUniqueId))
  }

  override def removeItem(gameState: GameState, itemID: Long): Unit = {
    notImplementedYet()
  }

  override def showItem(gameState: GameState, itemID: Long): Unit = {
    tryActionOnItem(gameState, itemID, i => println(DescriptionBuilder.description(i)))
  }

  override def renameHunter(gameState: GameState, newName: String): Unit = {
    gameState.getHunter.setName(newName)
    println(s"new name: $newName")
  }

  override def startQuest(gameState: GameState, questID: Long): Unit = {
    getQuest(gameState, questID) match {
      case Some(quest) =>
        val hunter = gameState.getHunter
        val questResult = GameLogic.computeQuestResult(hunter, quest)
        println(DescriptionBuilder.description(questResult))
        GameLogic.processQuestResult(hunter, quest, questResult)
      case None => println(s"quest with id $questID not found")
    }
  }

  private def notImplementedYet(): Unit = {
    println("not implemented yet")
  }

  override def showCraft(gameState: GameState, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => println(DescriptionBuilder.description(gameState.getCrafts.getRecipesWith(i))))
  }
}
