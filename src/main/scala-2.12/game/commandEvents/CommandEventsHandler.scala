package game.commandEvents

import game.gameStateEvents.GameStateEvents
import game.id.Identifiable
import game.uiEvents.UIEvents

/**
  * Created by nol on 12/11/17.
  */
class CommandEventsHandler(commandEvents: CommandEvents, uIEvents: UIEvents, gameStateEvents: GameStateEvents) {

  type Id = Identifiable.Id

  def onListQuests(): Unit = {
    uIEvents.listQuests(Unit)
  }

  def onListInventory(): Unit = {
    uIEvents.listInventory(Unit)
  }

  def onShowQuest(questId: Id): Unit = {
    uIEvents.showQuest(questId)
  }

  def onShowHunter(): Unit = {
    uIEvents.showHunter(Unit)
  }

  def onEquipItem(itemId: Id): Unit = {
    gameStateEvents.equipItem(itemId)
  }

  def onUnEquipItem(itemId: Id): Unit = {
    gameStateEvents.unEquipItem(itemId)
  }

  def onShowItem(itemId: Id): Unit = {
    uIEvents.showItem(itemId)
  }

  def onRenameHunter(newName: String): Unit = {
    gameStateEvents.hunterRenamed(newName)
  }

  def onStartQuest(questId: Id): Unit = {
    gameStateEvents.questStarted(questId)
  }

  def onShowStat(): Unit = {
    uIEvents.showStat(Unit)
  }

  def onShowCraft(itemId: Id): Unit = {
    uIEvents.showCraft(itemId)
  }

  def onCraftItem(itemIds: (Id, Id)): Unit = {
    gameStateEvents.craftItem(itemIds)
  }

  commandEvents.listQuests += {
    (_: Unit) => onListQuests()
  }

  commandEvents.listInventory += {
    (_: Unit) => onListInventory()
  }

  commandEvents.showQuest += {
    (questId: Id) => onShowQuest(questId)
  }

  commandEvents.showHunter += {
    (_: Unit) => onShowHunter()
  }

  commandEvents.equipItem += {
    (itemId: Id) => onEquipItem(itemId)
  }

  commandEvents.unEquipItem += {
    (itemId: Id) => onUnEquipItem(itemId)
  }

  commandEvents.showItem += {
    (itemId: Id) => onShowItem(itemId)
  }

  commandEvents.renameHunter += {
    (newName: String) => onRenameHunter(newName)
  }

  commandEvents.startQuest += {
    (questId: Id) => onStartQuest(questId)
  }

  commandEvents.showCraft += {
    (itemId: Id) => onShowCraft(itemId)
  }

  commandEvents.craftItem += {
    (itemIds: (Id, Id)) => onCraftItem(itemIds)
  }

  commandEvents.showStat += {
    (_: Unit) => onShowStat()
  }
}
