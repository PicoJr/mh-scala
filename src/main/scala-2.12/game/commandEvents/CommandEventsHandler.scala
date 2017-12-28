package game.commandEvents

import game.gameStateEvents.GameStateEvents
import game.id.Identifiable
import game.uiEvents.UIEvents

/**
  * Created by nol on 12/11/17.
  */
class CommandEventsHandler() {

  type Id = Identifiable.Id

  def onListQuests(): Unit = {
    UIEvents.listQuests(Unit)
  }

  def onListInventory(): Unit = {
    UIEvents.listInventory(Unit)
  }

  def onShowQuest(questId: Id): Unit = {
    UIEvents.showQuest(questId)
  }

  def onShowHunter(): Unit = {
    UIEvents.showHunter(Unit)
  }

  def onEquipItem(itemId: Id): Unit = {
    GameStateEvents.equipItem(itemId)
  }

  def onUnEquipItem(itemId: Id): Unit = {
    GameStateEvents.unEquipItem(itemId)
  }

  def onShowItem(itemId: Id): Unit = {
    UIEvents.showItem(itemId)
  }

  def onRenameHunter(newName: String): Unit = {
    GameStateEvents.hunterRenamed(newName)
  }

  def onStartQuest(questId: Id): Unit = {
    GameStateEvents.questStarted(questId)
  }

  def onShowStat(): Unit = {
    UIEvents.showStat(Unit)
  }

  def onShowCraft(itemId: Id): Unit = {
    UIEvents.showCraft(itemId)
  }

  def onCraftItem(itemIds: (Id, Id)): Unit = {
    GameStateEvents.craftItem(itemIds)
  }

  CommandEvents.listQuests += {
    (_: Unit) => onListQuests()
  }

  CommandEvents.listInventory += {
    (_: Unit) => onListInventory()
  }

  CommandEvents.showQuest += {
    (questId: Id) => onShowQuest(questId)
  }

  CommandEvents.showHunter += {
    (_: Unit) => onShowHunter()
  }

  CommandEvents.equipItem += {
    (itemId: Id) => onEquipItem(itemId)
  }

  CommandEvents.unEquipItem += {
    (itemId: Id) => onUnEquipItem(itemId)
  }

  CommandEvents.showItem += {
    (itemId: Id) => onShowItem(itemId)
  }

  CommandEvents.renameHunter += {
    (newName: String) => onRenameHunter(newName)
  }

  CommandEvents.startQuest += {
    (questId: Id) => onStartQuest(questId)
  }

  CommandEvents.showCraft += {
    (itemId: Id) => onShowCraft(itemId)
  }

  CommandEvents.craftItem += {
    (itemIds: (Id, Id)) => onCraftItem(itemIds)
  }

  CommandEvents.showStat += {
    (_: Unit) => onShowStat()
  }
}
