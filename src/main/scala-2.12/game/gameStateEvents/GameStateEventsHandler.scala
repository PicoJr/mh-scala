package game.gameStateEvents

import game.gameStatistics.DefaultGameStatistics
import game.gamestate.GameState
import game.item.{AbstractItemFactory, DefaultItemFactory, Item}
import game.questEvents.QuestEvents
import game.uiEvents.UIEvents

/**
  * Created by nol on 19/12/17.
  */
class GameStateEventsHandler(gameState: GameState, abstractItemFactory: AbstractItemFactory) {

  def this(gameState: GameState) {
    this(gameState, DefaultItemFactory.getDefaultItemFactory)
  }

  type Id = Long

  GameStateEvents.itemCrafted += {
    (result: Item) =>
      gameState.getHunter.getInventory.addItems(result)
      DefaultGameStatistics.itemCraftedCount() = DefaultGameStatistics.itemCraftedCount.now + 1
      UIEvents.itemObtained(result.getUniqueId)
  }

  GameStateEvents.hunterRenamed += {
    (newName: String) =>
      gameState.getHunter.setName(newName)
      UIEvents.hunterRenamed(newName)
  }

  GameStateEvents.itemEquipped += {
    (item: Item) => UIEvents.itemEquipped(item)
  }

  GameStateEvents.itemNotEquipped += {
    (item: Item) => UIEvents.itemNotEquipped(item)
  }

  GameStateEvents.questStarted += {
    (questId: Id) =>
      gameState.findQuest(questId) match {
        case Some(quest) => QuestEvents.questStarted(quest)
        case None => UIEvents.questIdNotFound(questId)
      }
  }

  GameStateEvents.questCompleted += {
    (questId: Id) =>
      gameState.addCompletedQuest(questId)
      UIEvents.questCompleted(questId)
  }

  GameStateEvents.questSucceeded += {
    (questId: Id) => if (!gameState.isCompletedQuest(questId)) GameStateEvents.questCompleted(questId)
  }

  GameStateEvents.equipItem += {
    (itemId: Id) =>
      gameState.findItem(itemId) match {
        case Some(i) =>
          val equipped = gameState.getHunter.getInventory.tryEquipItem(i.getUniqueId, force = true)
          if (equipped) {
            GameStateEvents.itemEquipped(i)
          } else {
            GameStateEvents.itemNotEquipped(i)
          }
        case None => UIEvents.itemIdNotFound(itemId)
      }
  }

  GameStateEvents.unEquipItem += {
    (itemId: Id) =>
      gameState.findItem(itemId) match {
        case Some(i) => gameState.getHunter.getInventory.unEquipItem(i.getUniqueId)
        case None => UIEvents.itemIdNotFound(itemId)
      }
  }

  GameStateEvents.craftItem += {
    (itemIds: (Id, Id)) =>
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
}
