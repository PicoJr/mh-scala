package game.console

import game.description.Description
import game.gamestate.GameState
import game.item.DefaultItem

/**
  * Created by nol on 12/11/17.
  */
class DefaultCommand(description: Description) extends Command {

  override def listQuests(gameState: GameState): Unit = {
    for (q <- gameState.getQuests) {
      println(description.descriptionQuest(gameState, q.getUniqueId))
    }
  }

  override def listInventory(gameState: GameState): Unit = {
    println(description.descriptionInventory(gameState))
  }

  override def showQuest(gameState: GameState, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(q) => println(description.descriptionQuest(gameState, q.getUniqueId))
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showMonster(gameState: GameState, monsterId: Long): Unit = {
    gameState.findMonster(monsterId) match {
      case Some(m) => println(description.descriptionMonster(gameState, m.getUniqueId))
      case None => println(s"monster with id $monsterId not found")
    }
  }

  override def showHunter(gameState: GameState): Unit = {
    println(description.descriptionHunter(gameState))
  }

  override def equipItem(gameState: GameState, itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    gameState.findItem(itemId) match {
      case Some(i) =>
        val equipped = inventory.tryEquipItem(i.getUniqueId, force = true)
        if (!equipped) {
          println(s"item with id $itemId cannot be equipped")
        }
      case None => println(s"item with id $itemId not found")
    }
  }

  override def unEquipItem(gameState: GameState, itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    gameState.findItem(itemId) match {
      case Some(i) => inventory.unEquipItem(i.getUniqueId)
      case None => println(s"item with id $itemId not found")
    }
  }

  override def showItem(gameState: GameState, itemId: Long): Unit = {
    println(description.descriptionItem(gameState, itemId))
  }

  override def renameHunter(gameState: GameState, newName: String): Unit = {
    gameState.getHunter.setName(newName)
    println(s"new name: $newName")
  }

  override def startQuest(gameState: GameState, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) =>
        val questResult = gameState.getQuestLogic.processQuestResult(gameState, quest)
        println(description.descriptionQuestResult(gameState, questResult))
        gameState.getScore.incrQuestAttempts()
        if (questResult.isSuccessful) {
          gameState.setCompleted(quest.getUniqueId)
          gameState.getScore.incrQuestSuccesses()
        } else {
          gameState.getScore.incrQuestFailures()
        }
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showCraft(gameState: GameState, itemId: Long): Unit = {
    println(description.descriptionRecipesWith(gameState, itemId))
  }

  override def craftItem(gameState: GameState, itemId1: Long, itemId2: Long): Unit = {
    val optI1 = gameState.findItem(itemId1)
    val optI2 = gameState.findItem(itemId2)
    (optI1, optI2) match {
      case (Some(i1), Some(i2)) =>
        val result_i1_i2 = gameState.getCrafts.getRecipes.get((i1.getItemType, i2.getItemType))
        val result_i2_i1 = gameState.getCrafts.getRecipes.get((i2.getItemType, i1.getItemType))
        (result_i1_i2, result_i2_i1) match {
          case (Some(result), _) =>
            val itemResult = DefaultItem.createItem(result)
            gameState.getHunter.getInventory.addItems(itemResult)
            println("obtained: " + description.descriptionItem(gameState, itemResult.getUniqueId))
          case (_, Some(result)) =>
            val itemResult = DefaultItem.createItem(result)
            gameState.getHunter.getInventory.addItems(itemResult)
            println("obtained: " + description.descriptionItem(gameState, itemResult.getUniqueId))
          case _ => println("no matching craft recipe found")
        }
      case _ => println(s"item with id $itemId1 or $itemId2 not found")
    }
  }

  override def showScore(gameState: GameState): Unit = {
    println(description.descriptionScore(gameState))
  }
}
