package game.uiEvents

import game.gamestate.GameState
import game.item.Item
import game.ui.{DefaultDescription, Description}

/**
  * Created by nol on 19/12/17.
  */
class UIEventsHandler(gameState: GameState, description: Description) {

  def this(gameState: GameState) {
    this(gameState, new DefaultDescription(gameState))
  }

  type Id = Long

  def actionItemObtained(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println("obtained: " + description.descriptionItem(item))
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  def actionCraftNotFound(ids: (Id, Id)): Unit = {
    println("no matching craft recipe found")
  }

  def actionHunterRenamed(newName: String): Unit = {
    println(s"new name: $newName")
  }

  def actionItemEquipped(item: Item): Unit = {
    println(s"item with id ${item.getUniqueId} equipped")
  }

  def actionItemNotEquipped(item: Item): Unit = {
    println(s"item with id ${item.getUniqueId} could not be equipped")
  }

  def actionListQuests(): Unit = {
    for (q <- gameState.getQuests) {
      println(description.descriptionQuest(q))
    }
  }

  def actionListInventory(): Unit = {
    println(description.descriptionInventory(gameState.getHunter.getInventory))
  }

  def actionShowQuest(questId: Id): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) => println(description.descriptionQuest(quest))
      case None => UIEvents.questIdNotFound(questId)
    }
  }

  def actionQuestIdNotFound(questId: Id): Unit = {
    println(s"quest with id $questId not found")
  }

  def actionShowHunter(): Unit = {
    println(description.descriptionHunter(gameState.getHunter))
  }

  def actionShowStat(): Unit = {
    println(description.descriptionStatistics())
  }

  def actionShowCraft(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println(description.descriptionRecipesWith(item))
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  def actionShowItem(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println(description.descriptionItem(item))
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  UIEvents.itemObtained += {
    (itemId: Id) => actionItemObtained(itemId)
  }

  UIEvents.craftNotFound += {
    (ids: (Id, Id)) => actionCraftNotFound(ids)
  }

  UIEvents.hunterRenamed += {
    (newName: String) => actionHunterRenamed(newName)
  }

  UIEvents.itemEquipped += {
    (item: Item) => actionItemEquipped(item)
  }

  UIEvents.itemNotEquipped += {
    (item: Item) => actionItemNotEquipped(item)
  }

  UIEvents.listQuests += {
    (_: Unit) => actionListQuests()
  }

  UIEvents.listInventory += {
    (_: Unit) => actionListInventory()
  }

  UIEvents.showQuest += {
    (questId: Id) => actionShowQuest(questId)
  }

  UIEvents.questIdNotFound += {
    (questId: Id) => actionQuestIdNotFound(questId)
  }

  UIEvents.showHunter += {
    (_: Unit) => actionShowHunter()
  }

  UIEvents.showStat += {
    (_: Unit) => actionShowStat()
  }

  UIEvents.showCraft += {
    (itemId: Id) => actionShowCraft(itemId)
  }

  UIEvents.showItem += {
    (itemId: Id) => actionShowItem(itemId)
  }

}
