package game.gameStatistics

import game.gameStateEvents.GameStateEvents
import game.questEvents.QuestEvents
import rescala._

/**
  * Created by nol on 19/12/17.
  */
object DefaultGameStatistics {

  val questSucceededCount: Signal[Int] = QuestEvents.questSucceeded.count

  val questFailedCount: Signal[Int] = QuestEvents.questFailed.count

  val questStartedCount: Signal[Int] = QuestEvents.questStarted.count

  val itemCraftedCount: Signal[Int] = GameStateEvents.itemCrafted.count

}
