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

  def onItemObtained(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println("obtained: " + description.descriptionItem(item))
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  def onCraftNotFound(ids: (Id, Id)): Unit = {
    println("no matching craft recipe found")
  }

  def onHunterRenamed(newName: String): Unit = {
    println(s"new name: $newName")
  }

  def onItemEquipped(item: Item): Unit = {
    println(s"item with id ${item.getUniqueId} equipped")
  }

  def onItemNotEquipped(item: Item): Unit = {
    println(s"item with id ${item.getUniqueId} could not be equipped")
  }

  def onListQuests(): Unit = {
    for (q <- gameState.getQuests) {
      println(description.descriptionQuest(q))
    }
  }

  def onListInventory(): Unit = {
    println(description.descriptionInventory(gameState.getHunter.inventory))
  }

  def onShowQuest(questId: Id): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) => println(description.descriptionQuest(quest))
      case None => UIEvents.questIdNotFound(questId)
    }
  }

  def onQuestIdNotFound(questId: Id): Unit = {
    println(s"quest with id $questId not found")
  }

  def onShowHunter(): Unit = {
    println(description.descriptionHunter(gameState.getHunter))
  }

  def onShowStat(): Unit = {
    println(description.descriptionStatistics())
  }

  def onShowCraft(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println(description.descriptionRecipesWith(item))
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  def onShowItem(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println(description.descriptionItem(item))
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  UIEvents.itemObtained += {
    (itemId: Id) => onItemObtained(itemId)
  }

  UIEvents.craftNotFound += {
    (ids: (Id, Id)) => onCraftNotFound(ids)
  }

  UIEvents.hunterRenamed += {
    (newName: String) => onHunterRenamed(newName)
  }

  UIEvents.itemEquipped += {
    (item: Item) => onItemEquipped(item)
  }

  UIEvents.itemNotEquipped += {
    (item: Item) => onItemNotEquipped(item)
  }

  UIEvents.listQuests += {
    (_: Unit) => onListQuests()
  }

  UIEvents.listInventory += {
    (_: Unit) => onListInventory()
  }

  UIEvents.showQuest += {
    (questId: Id) => onShowQuest(questId)
  }

  UIEvents.questIdNotFound += {
    (questId: Id) => onQuestIdNotFound(questId)
  }

  UIEvents.showHunter += {
    (_: Unit) => onShowHunter()
  }

  UIEvents.showStat += {
    (_: Unit) => onShowStat()
  }

  UIEvents.showCraft += {
    (itemId: Id) => onShowCraft(itemId)
  }

  UIEvents.showItem += {
    (itemId: Id) => onShowItem(itemId)
  }

}
