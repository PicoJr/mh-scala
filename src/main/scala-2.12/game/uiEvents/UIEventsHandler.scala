package game.uiEvents

import game.gamestate.GameState
import game.item.Item
import game.ui.{DefaultDescription, Description}

/**
  * Created by nol on 19/12/17.
  */
class UIEventsHandler(gameState: GameState, description: Description) {

  def this(gameState: GameState) {
    this(gameState, DefaultDescription)
  }

  type Id = Long

  UIEvents.itemObtained += {
    (itemId: Long) => println("obtained: " + description.descriptionItem(gameState, itemId))
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
        println(description.descriptionQuest(gameState, q.getUniqueId))
      }
  }

  UIEvents.listInventory += {
    (_: Unit) => println(description.descriptionInventory(gameState))
  }

  UIEvents.showQuest += {
    (questId: Id) => println(description.descriptionQuest(gameState, questId))
  }

  UIEvents.questIdNotFound += {
    (questId: Id) => println(s"quest with id $questId not found")
  }

  UIEvents.showHunter += {
    (_: Unit) => println(description.descriptionHunter(gameState))
  }

  UIEvents.showStat += {
    (_: Unit) => println(description.descriptionStatistics(gameState))
  }

  UIEvents.showCraft += {
    (itemId: Id) => println(description.descriptionRecipesWith(gameState, itemId))
  }

  UIEvents.showItem += {
    (itemId: Id) => println(description.descriptionItem(gameState, itemId))
  }

}
