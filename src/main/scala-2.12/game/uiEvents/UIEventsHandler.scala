package game.uiEvents

import game.gamestate.GameState
import game.id.Identifiable
import game.item.{Item, ItemType}
import game.ui.Description

/**
  * Created by nol on 19/12/17.
  */
class UIEventsHandler[TItem <: Item, TItemType <: ItemType](gameState: GameState[TItem, TItemType], uIEvents: UIEvents, description: Description[TItem, TItemType]) {

  type Id = Identifiable.Id

  def onItemObtained(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println("obtained: " + description.descriptionItem(item))
      case None => uIEvents.itemIdNotFound(itemId)
    }
  }

  def onCraftNotFound(ids: (Id, Id)): Unit = {
    println("no matching craft recipe found")
  }

  def onHunterRenamed(newName: String): Unit = {
    println(s"new name: $newName")
  }

  def onItemEquipped(itemId: Id): Unit = {
    println(s"item with id $itemId equipped")
  }

  def onItemNotEquipped(itemId: Id): Unit = {
    println(s"item with id $itemId could not be equipped")
  }

  def onListQuests(): Unit = {
    for (q <- gameState.quests) {
      println(description.descriptionQuest(q))
    }
  }

  def onListInventory(): Unit = {
    println(description.descriptionInventory(gameState.hunter.inventory))
  }

  def onShowQuest(questId: Id): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) => println(description.descriptionQuest(quest))
      case None => uIEvents.questIdNotFound(questId)
    }
  }

  def onQuestIdNotFound(questId: Id): Unit = {
    println(s"quest with id $questId not found")
  }

  def onShowHunter(): Unit = {
    println(description.descriptionHunter(gameState.hunter))
  }

  def onShowStat(): Unit = {
    println(description.descriptionStatistics())
  }

  def onShowCraft(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println(description.descriptionRecipesWith(item))
      case None => uIEvents.itemIdNotFound(itemId)
    }
  }

  def onShowItem(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(item) => println(description.descriptionItem(item))
      case None => uIEvents.itemIdNotFound(itemId)
    }
  }

  def onQuestCompleted(questId: Id): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) => println(s"${quest.monster.name} was defeated! ($questId completed)")
      case None => uIEvents.questIdNotFound(questId)
    }
  }

  def onQuestFailed(questId: Id): Unit = {
    gameState.findQuest(questId) match {
      case Some(_) => println(s"quest $questId failed!")
      case None => uIEvents.questIdNotFound(questId)
    }
  }

  uIEvents.itemObtained += {
    (itemId: Id) => onItemObtained(itemId)
  }

  uIEvents.craftNotFound += {
    (ids: (Id, Id)) => onCraftNotFound(ids)
  }

  uIEvents.hunterRenamed += {
    (newName: String) => onHunterRenamed(newName)
  }

  uIEvents.itemEquipped += {
    (itemId: Id) => onItemEquipped(itemId)
  }

  uIEvents.itemNotEquipped += {
    (itemId: Id) => onItemNotEquipped(itemId)
  }

  uIEvents.listQuests += {
    (_: Unit) => onListQuests()
  }

  uIEvents.listInventory += {
    (_: Unit) => onListInventory()
  }

  uIEvents.showQuest += {
    (questId: Id) => onShowQuest(questId)
  }

  uIEvents.questIdNotFound += {
    (questId: Id) => onQuestIdNotFound(questId)
  }

  uIEvents.showHunter += {
    (_: Unit) => onShowHunter()
  }

  uIEvents.showStat += {
    (_: Unit) => onShowStat()
  }

  uIEvents.showCraft += {
    (itemId: Id) => onShowCraft(itemId)
  }

  uIEvents.showItem += {
    (itemId: Id) => onShowItem(itemId)
  }

  uIEvents.questCompleted += {
    (questId: Id) => onQuestCompleted(questId)
  }

  uIEvents.questFailed += {
    (questId: Id) => onQuestFailed(questId)
  }

}
