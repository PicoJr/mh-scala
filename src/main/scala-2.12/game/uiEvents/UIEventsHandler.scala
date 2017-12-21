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

  UIEvents.itemObtained += {
    (itemId: Long) =>
      gameState.findItem(itemId) match {
        case Some(item) => println("obtained: " + description.descriptionItem(item))
        case None => UIEvents.itemIdNotFound(itemId)
      }
  }

  UIEvents.craftNotFound += {
    (_: (Id, Id)) => println("no matching craft recipe found")
  }

  UIEvents.hunterRenamed += {
    (newName: String) => println(s"new name: $newName")
  }

  UIEvents.itemEquipped += {
    (item: Item) => println(s"item with id ${item.getUniqueId} equipped")
  }

  UIEvents.itemNotEquipped += {
    (item: Item) => println(s"item with id ${item.getUniqueId} could not be equipped")
  }

  UIEvents.listQuests += {
    (_: Unit) =>
      for (q <- gameState.getQuests) {
        println(description.descriptionQuest(q))
      }
  }

  UIEvents.listInventory += {
    (_: Unit) => println(description.descriptionInventory(gameState.getHunter.getInventory))
  }

  UIEvents.showQuest += {
    (questId: Id) =>
      gameState.findQuest(questId) match {
        case Some(quest) => println(description.descriptionQuest(quest))
        case None => UIEvents.questIdNotFound(questId)
      }

  }

  UIEvents.questIdNotFound += {
    (questId: Id) => println(s"quest with id $questId not found")
  }

  UIEvents.showHunter += {
    (_: Unit) => println(description.descriptionHunter(gameState.getHunter))
  }

  UIEvents.showStat += {
    (_: Unit) => println(description.descriptionStatistics())
  }

  UIEvents.showCraft += {
    (itemId: Id) =>
      gameState.findItem(itemId) match {
        case Some(item) => println(description.descriptionRecipesWith(item))
        case None => UIEvents.itemIdNotFound(itemId)
      }
  }

  UIEvents.showItem += {
    (itemId: Id) =>
      gameState.findItem(itemId) match {
        case Some(item) => println(description.descriptionItem(item))
        case None => UIEvents.itemIdNotFound(itemId)
      }
  }

}
