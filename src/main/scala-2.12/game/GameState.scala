package game

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
    val hunter = new Hunter("unnamed")
    val quests = Seq(Quest.createQuest(1))
    new DefaultGameState(hunter, quests)
  }
}
