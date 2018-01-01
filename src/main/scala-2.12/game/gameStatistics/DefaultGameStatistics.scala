package game.gameStatistics

import game.id.Identifiable
import rescala._

/**
  * Created by nol on 19/12/17.
  */
object DefaultGameStatistics {

  type Id = Identifiable.Id

  val questSucceeded: Var[Set[Id]] = Var(Set.empty[Id])

  val questSucceededCount: Var[Int] = Var(0)

  val questFailedCount: Var[Int] = Var(0)

  val questStartedCount: Var[Int] = Var(0)

  val itemCraftedCount: Var[Int] = Var(0)
}
