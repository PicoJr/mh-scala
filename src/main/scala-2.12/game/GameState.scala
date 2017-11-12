package game

import config.Config
import item.Item
import quest.Quest
import unit.Hunter

/**
  * Created by nol on 11/11/17.
  */
trait GameState {
  def getHunter: Hunter

  def getQuests: Seq[Quest]
}

private class DefaultGameState(hunter: Hunter, quests: Seq[Quest]) extends GameState {
  override def getHunter: Hunter = hunter

  override def getQuests: Seq[Quest] = quests
}


object GameState {
  def createNewGameState: GameState = {
    val hunter = createHunter
    val quests = createQuests
    new DefaultGameState(hunter, quests)
  }

  private def createHunter: Hunter = {
    val hunter = new Hunter("unnamed")
    val weapon = Item.createWeapon("fists", 500)
    hunter.getInventory.addItems(weapon)
    hunter.getInventory.equipItem(weapon.getUniqueID)
    hunter
  }

  private def createQuests: Seq[Quest] = {
    var quests: Seq[Quest] = Seq.empty
    for (level <- 1 to Config.getLevelMax) {
      for (_ <- 1 to Config.getQuestsAtLevel(level)) {
        quests = quests :+ Quest.createQuest(level)
      }
    }
    quests
  }
}
