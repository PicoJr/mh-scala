package game.console

import game.GameState
import game.description.DescriptionFactory
import game.item.Item
import game.quest.QuestLogic

/**
  * Created by nol on 12/11/17.
  */
class ConsoleCommand extends Command {

  private def tryActionOnItem(gameState: GameState, itemId: Long, action: Item => Unit): Unit = {
    gameState.findItem(itemId) match {
      case Some(i) => action(i)
      case None => println(s"item with id $itemId not found")
    }
  }

  override def listQuests(gameState: GameState): Unit = {
    for (q <- gameState.getQuests) {
      println(DescriptionFactory.description(q))
    }
  }

  override def listInventory(gameState: GameState): Unit = {
    println(DescriptionFactory.description(gameState.getHunter.getInventory))
  }

  override def showQuest(gameState: GameState, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(q) => println(DescriptionFactory.description(q))
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showMonster(gameState: GameState, monsterId: Long): Unit = {
    gameState.findMonster(monsterId) match {
      case Some(m) => println(DescriptionFactory.description(m))
      case None => println(s"monster with id $monsterId not found")
    }
  }

  override def showHunter(gameState: GameState): Unit = {
    println(DescriptionFactory.description(gameState.getHunter))
  }

  override def equipItem(gameState: GameState, itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    tryActionOnItem(gameState, itemId, i => if (inventory.canBeEquipped(i)) inventory.equipItem(i.getUniqueId))
  }

  override def unEquipItem(gameState: GameState, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => gameState.getHunter.getInventory.unEquipItem(i.getUniqueId))
  }

  override def showItem(gameState: GameState, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => println(DescriptionFactory.description(i)))
  }

  override def renameHunter(gameState: GameState, newName: String): Unit = {
    gameState.getHunter.setName(newName)
    println(s"new name: $newName")
  }

  override def startQuest(gameState: GameState, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) =>
        val questResult = QuestLogic.processQuestResult(gameState, quest)
        println(DescriptionFactory.description(questResult))
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showCraft(gameState: GameState, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => println(DescriptionFactory.description(gameState.getCrafts.getRecipesWith(i))))
  }

  override def craftItem(gameState: GameState, itemId1: Long, itemId2: Long): Unit = {
    val optI1 = gameState.findItem(itemId1)
    val optI2 = gameState.findItem(itemId2)
    (optI1, optI2) match {
      case (Some(i1), Some(i2)) =>
        gameState.getCrafts.craftItemType(i1.getItemType, i2.getItemType) match {
          case Some(result) =>
            gameState.getHunter.getInventory.addItems(Item.createItem(result))
          case None => println("no matching craft recipe found")
        }
      case _ => println(s"item with id $itemId1 or $itemId2 not found")
    }
  }
}
