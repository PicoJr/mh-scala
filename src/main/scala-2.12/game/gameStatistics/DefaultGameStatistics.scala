package game.gameStatistics

import rescala._

/**
  * Created by nol on 19/12/17.
  */
object DefaultGameStatistics {

  val questSucceededCount: Var[Int] = Var(0)

  val questFailedCount: Var[Int] = Var(0)

  val questStartedCount: Var[Int] = Var(0)

  val itemCraftedCount: Var[Int] = Var(0)
}
