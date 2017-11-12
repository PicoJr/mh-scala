package console

import description.DescriptionBuilder
import game.GameState

/**
  * Created by nol on 12/11/17.
  */
class ConsoleCommand extends Command {
  override def listQuests(gameState: GameState): Unit = {
    for (q <- gameState.getQuests) {
      println(DescriptionBuilder.description(q))
    }
  }

  override def listInventory(gameState: GameState): Unit = {
    notImplementedYet()
  }

  override def showQuest(gameState: GameState, questID: Long): Unit = {
    notImplementedYet()
  }

  override def showMonster(gameState: GameState, monsterID: Long): Unit = {
    notImplementedYet()
  }

  override def showHunter(gameState: GameState): Unit = {
    println(DescriptionBuilder.description(gameState.getHunter))
  }

  override def equipItem(gameState: GameState, itemID: Long): Unit = {
    notImplementedYet()
  }

  override def unEquipItem(gameState: GameState, itemID: Long): Unit = {
    notImplementedYet()
  }

  override def removeItem(gameState: GameState, itemID: Long): Unit = {
    notImplementedYet()
  }

  override def showItem(gameState: GameState, itemID: Long): Unit = {
    notImplementedYet()
  }

  override def renameHunter(gameState: GameState, newName: String): Unit = {
    gameState.getHunter.rename(newName)
  }

  override def startQuest(gameState: GameState, questID: Long): Unit = {
    notImplementedYet()
  }

  private def notImplementedYet(): Unit = {
    println("not implemented yet")
  }
}
