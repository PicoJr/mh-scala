package game.console

import game.description.DescriptionBuilder
import game.item.Item
import game.{GameState, QuestLogic}

/**
  * Created by nol on 12/11/17.
  */
class ConsoleCommand extends Command {

  private def tryActionOnItem(gameState: GameState, itemId: Long, action: Item => Unit): Unit = {
    gameState.getItem(itemId) match {
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

  override def showQuest(gameState: GameState, questId: Long): Unit = {
    gameState.getQuest(questId) match {
      case Some(q) => println(DescriptionBuilder.description(q))
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showMonster(gameState: GameState, monsterId: Long): Unit = {
    gameState.getMonster(monsterId) match {
      case Some(m) => println(DescriptionBuilder.description(m))
      case None => println(s"monster with id $monsterId not found")
    }
  }

  override def showHunter(gameState: GameState): Unit = {
    println(DescriptionBuilder.description(gameState.getHunter))
  }

  override def equipItem(gameState: GameState, itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    tryActionOnItem(gameState, itemId, i => if (inventory.canBeEquipped(i)) inventory.equipItem(i.getUniqueId))
  }

  override def unEquipItem(gameState: GameState, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => gameState.getHunter.getInventory.unEquipItem(i.getUniqueId))
  }

  override def removeItem(gameState: GameState, itemId: Long): Unit = {
    notImplementedYet()
  }

  override def showItem(gameState: GameState, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => println(DescriptionBuilder.description(i)))
  }

  override def renameHunter(gameState: GameState, newName: String): Unit = {
    gameState.getHunter.setName(newName)
    println(s"new name: $newName")
  }

  override def startQuest(gameState: GameState, questId: Long): Unit = {
    gameState.getQuest(questId) match {
      case Some(_) =>
        val questResult = QuestLogic.processQuest(gameState, questId)
        println(DescriptionBuilder.description(questResult))
      case None => println(s"quest with id $questId not found")
    }
  }

  private def notImplementedYet(): Unit = {
    println("not implemented yet")
  }

  override def showCraft(gameState: GameState, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => println(DescriptionBuilder.description(gameState.getCrafts.getRecipesWith(i))))
  }
}