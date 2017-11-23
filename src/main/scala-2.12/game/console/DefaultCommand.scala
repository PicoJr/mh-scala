package game.console

import game.description.Description
import game.gamestate.GameState
import game.item.DefaultItem

/**
  * Created by nol on 12/11/17.
  */
class DefaultCommand(description: Description) extends Command {

  def listQuests(gameState: GameState): Unit = {
    for (q <- gameState.getQuests) {
      println(description.descriptionQuest(gameState, q.getUniqueId))
    }
  }

  def listInventory(gameState: GameState): Unit = {
    println(description.descriptionInventory(gameState))
  }

  def showQuest(gameState: GameState, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(q) => println(description.descriptionQuest(gameState, q.getUniqueId))
      case None => println(s"quest with id $questId not found")
    }
  }

  def showMonster(gameState: GameState, monsterId: Long): Unit = {
    gameState.findMonster(monsterId) match {
      case Some(m) => println(description.descriptionMonster(gameState, m.getUniqueId))
      case None => println(s"monster with id $monsterId not found")
    }
  }

  def showHunter(gameState: GameState): Unit = {
    println(description.descriptionHunter(gameState))
  }

  def equipItem(gameState: GameState, itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    gameState.findItem(itemId) match {
      case Some(i) if inventory.canBeEquipped(i) => inventory.equipItem(i.getUniqueId)
      case Some(_) => println(s"item with id $itemId cannot be equipped")
      case None => println(s"item with id $itemId not found")
    }
  }

  def unEquipItem(gameState: GameState, itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    gameState.findItem(itemId) match {
      case Some(i) => inventory.unEquipItem(i.getUniqueId)
      case None => println(s"item with id $itemId not found")
    }
  }

  def showItem(gameState: GameState, itemId: Long): Unit = {
    println(description.descriptionItem(gameState, itemId))
  }

  def renameHunter(gameState: GameState, newName: String): Unit = {
    gameState.getHunter.setName(newName)
    println(s"new name: $newName")
  }

  def startQuest(gameState: GameState, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) =>
        val questResult = gameState.getQuestLogic.processQuestResult(gameState, quest)
        println(description.descriptionQuestResult(gameState, questResult))
      case None => println(s"quest with id $questId not found")
    }
  }

  def showCraft(gameState: GameState, itemId: Long): Unit = {
    println(description.descriptionRecipesWith(gameState, itemId))
  }

  def craftItem(gameState: GameState, itemId1: Long, itemId2: Long): Unit = {
    val optI1 = gameState.findItem(itemId1)
    val optI2 = gameState.findItem(itemId2)
    (optI1, optI2) match {
      case (Some(i1), Some(i2)) =>
        gameState.getCrafts.getRecipes.get((i1, i2)) match {
          case Some(result) =>
            val itemResult = DefaultItem.createItem(result)
            gameState.getHunter.getInventory.addItems(itemResult)
            println("obtained: " + description.descriptionItem(gameState, itemResult.getUniqueId))
          case None => println("no matching craft recipe found")
        }
      case _ => println(s"item with id $itemId1 or $itemId2 not found")
    }
  }
}