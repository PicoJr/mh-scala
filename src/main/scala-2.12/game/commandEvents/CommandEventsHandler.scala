package game.commandEvents

import game.gameStateEvents.GameStateEvents
import game.uiEvents.UIEvents

/**
  * Created by nol on 12/11/17.
  */
class CommandEventsHandler() {

  type Id = Long

  CommandEvents.listQuests += {
    (_: Unit) => UIEvents.listQuests(Unit)
  }

  CommandEvents.listInventory += {
    (_: Unit) => UIEvents.listInventory(Unit)
  }

  CommandEvents.showQuest += {
    (questId: Id) => UIEvents.showQuest(questId)
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
    (questId: Id) => GameStateEvents.questStarted(questId)
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
