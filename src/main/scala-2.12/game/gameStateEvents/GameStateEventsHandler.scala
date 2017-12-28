package game.gameStateEvents

import game.gameStatistics.DefaultGameStatistics
import game.gamestate.GameState
import game.id.IdSupplier
import game.item.{AbstractItemFactory, DefaultItemFactory, Item}
import game.questEvents.QuestEvents
import game.uiEvents.UIEvents

/**
  * Created by nol on 19/12/17.
  */
class GameStateEventsHandler(gameState: GameState, abstractItemFactory: AbstractItemFactory = DefaultItemFactory.getDefaultItemFactory) {

  type Id = IdSupplier.Id

  def onItemCrafted(result: Item): Unit = {
    gameState.getHunter.inventory.addItems(result)
    DefaultGameStatistics.itemCraftedCount() = DefaultGameStatistics.itemCraftedCount.now + 1
    UIEvents.itemObtained(result.getUniqueId)
  }

  def onHunterRenamed(newName: String): Unit = {
    gameState.getHunter.setName(newName)
    UIEvents.hunterRenamed(newName)
  }

  def onItemEquipped(item: Item): Unit = {
    UIEvents.itemEquipped(item)
  }

  def onItemNotEquipped(item: Item): Unit = {
    UIEvents.itemNotEquipped(item)
  }

  def onQuestStarted(questId: Id): Unit = {
    gameState.findQuest(questId) match {
      case Some(quest) => QuestEvents.questStarted(quest)
      case None => UIEvents.questIdNotFound(questId)
    }
  }

  def onQuestCompleted(questId: Id): Unit = {
    gameState.addCompletedQuest(questId)
    UIEvents.questCompleted(questId)
  }

  def onQuestSucceeded(questId: Id): Unit = {
    if (!gameState.isCompletedQuest(questId)) GameStateEvents.questCompleted(questId)
  }

  def onEquipItem(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(i) =>
        val equipped = gameState.getHunter.inventory.tryEquipItem(i.getUniqueId, force = true)
        if (equipped) {
          GameStateEvents.itemEquipped(i)
        } else {
          GameStateEvents.itemNotEquipped(i)
        }
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  def onUnEquipItem(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(i) => gameState.getHunter.inventory.unEquipItem(i.getUniqueId)
      case None => UIEvents.itemIdNotFound(itemId)
    }
  }

  def onCraftItem(itemIds: (Id, Id)): Unit = {
    (gameState.findItem(itemIds._1), gameState.findItem(itemIds._2)) match {
      case (Some(i1), Some(i2)) =>
        val craftResult =
          if (i2.isMaterial) {
            gameState.getCrafts.findCraftResult(i1.getItemType, i2.getItemType)
          } else {
            gameState.getCrafts.findCraftResult(i2.getItemType, i1.getItemType)
          }
        craftResult match {
          case Some(result) =>
            GameStateEvents.itemCrafted(abstractItemFactory.createItem(result))
          case None => UIEvents.craftNotFound(itemIds._1, itemIds._2)
        }
      case (None, _) => UIEvents.itemIdNotFound(itemIds._1)
      case (_, None) => UIEvents.itemIdNotFound(itemIds._2)
    }
  }

  GameStateEvents.itemCrafted += {
    (result: Item) => onItemCrafted(result)
  }

  GameStateEvents.hunterRenamed += {
    (newName: String) => onHunterRenamed(newName)
  }

  GameStateEvents.itemEquipped += {
    (item: Item) => onItemEquipped(item)
  }

  GameStateEvents.itemNotEquipped += {
    (item: Item) => onItemNotEquipped(item)
  }

  GameStateEvents.questStarted += {
    (questId: Id) => onQuestStarted(questId)
  }

  GameStateEvents.questCompleted += {
    (questId: Id) => onQuestCompleted(questId)
  }

  GameStateEvents.questSucceeded += {
    (questId: Id) => onQuestSucceeded(questId)
  }

  GameStateEvents.equipItem += {
    (itemId: Id) => onEquipItem(itemId)
  }

  GameStateEvents.unEquipItem += {
    (itemId: Id) => onUnEquipItem(itemId)
  }

  GameStateEvents.craftItem += {
    (itemIds: (Id, Id)) => onCraftItem(itemIds)
  }
}
