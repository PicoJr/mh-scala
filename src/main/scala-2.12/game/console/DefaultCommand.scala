package game.console

import game.description.Description
import game.gameEventsHandler.{DefaultGameEventsHandler, GameEventsHandler}
import game.gamestate.GameState
import game.item.{AbstractItemFactory, DefaultItemFactory, Item}

/**
  * Created by nol on 12/11/17.
  */
class DefaultCommand(gameState: GameState, description: Description, itemFactory: AbstractItemFactory, gameEventsHandler: GameEventsHandler) extends Command {

  gameEventsHandler.itemIdNotFound += { (itemId: Long) => println(s"item with id $itemId not found") }
  gameEventsHandler.itemCrafted += {
    (result: Item) =>
      gameState.getHunter.getInventory.addItems(result)
      println("obtained: " + description.descriptionItem(gameState, result.getUniqueId))
  }
  gameEventsHandler.craftNotFound += {
    (_) => println("no matching craft recipe found")
  }

  def this(gameState: GameState, description: Description) {
    this(gameState, description, DefaultItemFactory.getDefaultItemFactory, DefaultGameEventsHandler.getGameEventsHandler)
  }

  override def listQuests(): Unit = {
    for (q <- gameState.getQuests) {
      println(description.descriptionQuest(gameState, q.getUniqueId))
    }
  }

  override def listInventory(): Unit = {
    println(description.descriptionInventory(gameState))
  }

  override def showQuest(questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(q) => println(description.descriptionQuest(gameState, q.getUniqueId))
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showMonster(monsterId: Long): Unit = {
    gameState.findMonster(monsterId) match {
      case Some(m) => println(description.descriptionMonster(gameState, m.getUniqueId))
      case None => println(s"monster with id $monsterId not found")
    }
  }

  override def showHunter(): Unit = {
    println(description.descriptionHunter(gameState))
  }

  override def equipItem(itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    gameState.findItem(itemId) match {
      case Some(i) =>
        val equipped = inventory.tryEquipItem(i.getUniqueId, force = true)
        if (!equipped) {
          println(s"item with id $itemId cannot be equipped")
        }
      case None => gameEventsHandler.itemIdNotFound(itemId)
    }
  }

  override def unEquipItem(itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    gameState.findItem(itemId) match {
      case Some(i) => inventory.unEquipItem(i.getUniqueId)
      case None => gameEventsHandler.itemIdNotFound(itemId)
    }
  }

  override def showItem(itemId: Long): Unit = {
    println(description.descriptionItem(gameState, itemId))
  }

  override def renameHunter(newName: String): Unit = {
    gameState.getHunter.setName(newName)
    println(s"new name: $newName")
  }

  override def startQuest(questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) =>
        gameState.getQuestLogic.processQuest(gameState, quest)
        println(description.descriptionQuestResult(gameState))
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showCraft(itemId: Long): Unit = {
    println(description.descriptionRecipesWith(gameState, itemId))
  }

  override def craftItem(itemId1: Long, itemId2: Long): Unit = {
    val optI1 = gameState.findItem(itemId1)
    val optI2 = gameState.findItem(itemId2)
    (optI1, optI2) match {
      case (Some(i1), Some(i2)) =>
        gameState.getCrafts.findCraftResult(i1.getItemType, i2.getItemType) match {
          case Some(result) =>
            gameEventsHandler.itemCrafted(itemFactory.createItem(result))
          case None => gameEventsHandler.craftNotFound(itemId1, itemId2)
        }
      case (None, _) => gameEventsHandler.itemIdNotFound(itemId1)
      case (_, None) => gameEventsHandler.itemIdNotFound(itemId2)
    }
  }

  override def showScore(): Unit = {
    println(description.descriptionScore(gameState))
  }
}
