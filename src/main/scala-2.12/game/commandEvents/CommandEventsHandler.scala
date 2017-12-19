package game.commandEvents

import game.gameStateEvents.GameStateEvents
import game.gamestate.GameState
import game.item.{AbstractItemFactory, DefaultItemFactory}
import game.questEvents.QuestEvents
import game.ui.{DefaultDescription, Description}
import game.uiEvents.UIEvents

/**
  * Created by nol on 12/11/17.
  */
class CommandEventsHandler(
                            gameState: GameState,
                            description: Description,
                            itemFactory: AbstractItemFactory
                          ) {

  type Id = Long

  def this(gameState: GameState) {
    this(
      gameState,
      DefaultDescription,
      DefaultItemFactory.getDefaultItemFactory
    )
  }


  CommandEvents.listQuests += {
    (_: Unit) => UIEvents.listQuests(Unit)
  }

  CommandEvents.listInventory += {
    (_: Unit) => UIEvents.listInventory(Unit)
  }

  CommandEvents.showQuest += {
    (questId: Id) =>
      gameState.findQuest(questId) match {
        case Some(_) => UIEvents.showQuest(questId)
        case None => UIEvents.questIdNotFound(questId)
      }
  }

  CommandEvents.showHunter += {
    (_: Unit) => UIEvents.showHunter(Unit)
  }

  CommandEvents.equipItem += {
    (itemId: Id) => GameStateEvents.equipItem(itemId)
  }

  CommandEvents.unEquipItem += {
    (itemId: Id) => GameStateEvents.unEquipItem(itemId)
  }

  CommandEvents.showItem += {
    (itemId: Id) => UIEvents.showItem(itemId)
  }

  CommandEvents.renameHunter += {
    (newName: String) => GameStateEvents.hunterRenamed(newName)
  }

  CommandEvents.startQuest += {
    (questId: Id) =>
      gameState.findQuest(questId) match {
        case Some(quest) => QuestEvents.questStarted(quest)
        case None => UIEvents.questIdNotFound(questId)
      }
  }

  CommandEvents.showCraft += {
    (itemId: Id) => UIEvents.showCraft(itemId)
  }

  CommandEvents.craftItem += {
    (itemIds: (Id, Id)) => GameStateEvents.craftItem(itemIds)
  }

  CommandEvents.showStat += {
    (_: Unit) => UIEvents.showStat(Unit)
  }
}
