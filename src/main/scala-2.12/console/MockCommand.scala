package console

import game.GameState

/**
  * Created by nol on 11/11/17.
  */
class MockCommand extends Command {
  override def listQuests(gameState: GameState): Unit = println("listing quest")

  override def listInventory(gameState: GameState): Unit = println("listing inventory")

  override def showQuest(gameState: GameState, questID: Long): Unit = println(s"showing quest $questID")

  override def showMonster(gameState: GameState, monsterID: Long): Unit = println(s"showing monster $monsterID")

  override def showHunter(gameState: GameState): Unit = println("showing hunter")

  override def equipItem(gameState: GameState, itemID: Long): Unit = println(s"equipping item $itemID")

  override def unEquipItem(gameState: GameState, itemID: Long): Unit = println(s"unEquipping item $itemID")

  override def removeItem(gameState: GameState, itemID: Long): Unit = println(s"removing item $itemID")

  override def showItem(gameState: GameState, itemID: Long): Unit = println(s"showing item $itemID")

  override def renameHunter(gameState: GameState, newName: String): Unit = println(s"rename hunter $newName")

  override def startQuest(gameState: GameState, questID: Long): Unit = println(s"start quest $questID")
}
