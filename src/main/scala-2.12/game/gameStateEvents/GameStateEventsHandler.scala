package game.gameStateEvents

import game.gameStatistics.DefaultGameStatistics
import game.gamestate.GameState
import game.id.Identifiable
import game.item.{AbstractItemFactory, Item, ItemType}
import game.questEvents.QuestEvents
import game.uiEvents.UIEvents

/**
  * Created by nol on 19/12/17.
  */
class GameStateEventsHandler[TItem <: Item, TItemType <: ItemType](gameState: GameState[TItem, TItemType], gameStateEvents: GameStateEvents, questEvents: QuestEvents, uIEvents: UIEvents, abstractItemFactory: AbstractItemFactory[TItem, TItemType]) {

  type Id = Identifiable.Id

  def onItemCrafted(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(result) =>
        DefaultGameStatistics.itemCraftedCount() = DefaultGameStatistics.itemCraftedCount.now + 1
        uIEvents.itemObtained(result.getUniqueId)
      case None => uIEvents.itemIdNotFound(itemId)
    }
  }

  def onHunterRenamed(newName: String): Unit = {
    gameState.hunter.name = newName
    uIEvents.hunterRenamed(newName)
  }

  def onItemEquipped(itemId: Id): Unit = {
    uIEvents.itemEquipped(itemId)
  }

  def onItemNotEquipped(itemId: Id): Unit = {
    uIEvents.itemNotEquipped(itemId)
  }

  def onQuestStarted(questId: Id): Unit = {
    questEvents.questStarted(questId)
  }

  def onQuestCompleted(questId: Id): Unit = {
    gameState.addCompletedQuest(questId)
    uIEvents.questCompleted(questId)
  }

  def onQuestSucceeded(questId: Id): Unit = {
    if (!gameState.isCompletedQuest(questId)) gameStateEvents.questCompleted(questId)
  }

  def onQuestFailed(questId: Id): Unit = {
    uIEvents.questFailed(questId)
  }

  def onEquipItem(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(i) =>
        val equipped = gameState.hunter.inventory.tryEquipItem(i.getUniqueId, force = true)
        if (equipped) {
          gameStateEvents.itemEquipped(i.getUniqueId)
        } else {
          gameStateEvents.itemNotEquipped(i.getUniqueId)
        }
      case None => uIEvents.itemIdNotFound(itemId)
    }
  }

  def onUnEquipItem(itemId: Id): Unit = {
    gameState.findItem(itemId) match {
      case Some(i) => gameState.hunter.inventory.unEquipItem(i.getUniqueId)
      case None => uIEvents.itemIdNotFound(itemId)
    }
  }

  def onCraftItem(itemIds: (Id, Id)): Unit = {
    (gameState.findItem(itemIds._1), gameState.findItem(itemIds._2)) match {
      case (Some(i1), Some(i2)) =>
        val craftResult =
          if (i2.isMaterial) {
            gameState.crafts.findCraftResult(i1.getItemTypeId, i2.getItemTypeId)
          } else {
            gameState.crafts.findCraftResult(i2.getItemTypeId, i1.getItemTypeId)
          }
        craftResult match {
          case Some(result) =>
            val resultItem = abstractItemFactory.createItem(result)
            gameState.hunter.inventory.addItems(resultItem)
            gameStateEvents.itemCrafted(resultItem.getUniqueId)
          case None => uIEvents.craftNotFound(itemIds._1, itemIds._2)
        }
      case (None, _) => uIEvents.itemIdNotFound(itemIds._1)
      case (_, None) => uIEvents.itemIdNotFound(itemIds._2)
    }
  }

  gameStateEvents.itemCrafted += {
    (itemId: Id) => onItemCrafted(itemId)
  }

  gameStateEvents.hunterRenamed += {
    (newName: String) => onHunterRenamed(newName)
  }

  gameStateEvents.itemEquipped += {
    (itemId: Id) => onItemEquipped(itemId)
  }

  gameStateEvents.itemNotEquipped += {
    (itemId: Id) => onItemNotEquipped(itemId)
  }

  gameStateEvents.questStarted += {
    (questId: Id) => onQuestStarted(questId)
  }

  gameStateEvents.questCompleted += {
    (questId: Id) => onQuestCompleted(questId)
  }

  gameStateEvents.questSucceeded += {
    (questId: Id) => onQuestSucceeded(questId)
  }

  gameStateEvents.questFailed += {
    (questId: Id) => onQuestFailed(questId)
  }

  gameStateEvents.equipItem += {
    (itemId: Id) => onEquipItem(itemId)
  }

  gameStateEvents.unEquipItem += {
    (itemId: Id) => onUnEquipItem(itemId)
  }

  gameStateEvents.craftItem += {
    (itemIds: (Id, Id)) => onCraftItem(itemIds)
  }
}
