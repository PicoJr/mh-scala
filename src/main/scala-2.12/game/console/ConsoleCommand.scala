package game.console

import game.GameStateTrait
import game.description.DescriptionFactory
import game.item.{Item, ItemTrait}
import game.quest.QuestLogic

/**
  * Created by nol on 12/11/17.
  */
class ConsoleCommand extends Command {

  private def tryActionOnItem(gameState: GameStateTrait, itemId: Long, action: ItemTrait => Unit): Unit = {
    gameState.findItem(itemId) match {
      case Some(i) => action(i)
      case None => println(s"item with id $itemId not found")
    }
  }

  def listQuests(gameState: GameStateTrait): Unit = {
    for (q <- gameState.getQuests) {
      println(DescriptionFactory.description(q))
    }
  }

  def listInventory(gameState: GameStateTrait): Unit = {
    println(DescriptionFactory.description(gameState.getHunter.getInventory))
  }

  def showQuest(gameState: GameStateTrait, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(q) => println(DescriptionFactory.description(q))
      case None => println(s"quest with id $questId not found")
    }
  }

  def showMonster(gameState: GameStateTrait, monsterId: Long): Unit = {
    gameState.findMonster(monsterId) match {
      case Some(m) => println(DescriptionFactory.description(m))
      case None => println(s"monster with id $monsterId not found")
    }
  }

  def showHunter(gameState: GameStateTrait): Unit = {
    println(DescriptionFactory.description(gameState.getHunter))
  }

  def equipItem(gameState: GameStateTrait, itemId: Long): Unit = {
    val inventory = gameState.getHunter.getInventory
    tryActionOnItem(gameState, itemId, i => if (inventory.canBeEquipped(i)) inventory.equipItem(i.getUniqueId))
  }

  def unEquipItem(gameState: GameStateTrait, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => gameState.getHunter.getInventory.unEquipItem(i.getUniqueId))
  }

  def showItem(gameState: GameStateTrait, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => println(DescriptionFactory.description(i)))
  }

  def renameHunter(gameState: GameStateTrait, newName: String): Unit = {
    gameState.getHunter.setName(newName)
    println(s"new name: $newName")
  }

  def startQuest(gameState: GameStateTrait, questId: Long): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) =>
        val questResult = QuestLogic.processQuestResult(gameState, quest)
        println(DescriptionFactory.description(questResult))
      case None => println(s"quest with id $questId not found")
    }
  }

  override def showCraft(gameState: GameStateTrait, itemId: Long): Unit = {
    tryActionOnItem(gameState, itemId, i => println(DescriptionFactory.description(gameState.getCrafts.getRecipesWith(i))))
  }

  override def craftItem(gameState: GameStateTrait, itemId1: Long, itemId2: Long): Unit = {
    val optI1 = gameState.findItem(itemId1)
    val optI2 = gameState.findItem(itemId2)
    (optI1, optI2) match {
      case (Some(i1), Some(i2)) =>
        gameState.getCrafts.getRecipes.get((i1, i2)) match {
          case Some(result) =>
            gameState.getHunter.getInventory.addItems(Item.createItem(result))
          case None => println("no matching craft recipe found")
        }
      case _ => println(s"item with id $itemId1 or $itemId2 not found")
    }
  }
}
