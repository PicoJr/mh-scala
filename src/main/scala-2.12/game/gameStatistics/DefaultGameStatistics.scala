package game.gameStatistics

import game.id.Identifiable
import rescala._

/**
  * Created by nol on 19/12/17.
  */
object DefaultGameStatistics {

  type Id = Identifiable.Id

  /**
    * Set of quests id successfully completed.
    */
  val questSucceeded: Var[Set[Id]] = Var(Set.empty[Id])

  /**
    * Successfully completed quests count.
    */
  val questSucceededCount: Var[Int] = Var(0)

  /**
    * Failed quests count.
    */
  val questFailedCount: Var[Int] = Var(0)

  /**
    * Attempts count.
    */
  val questStartedCount: Var[Int] = Var(0)

  /**
    * Item crafted count.
    */
  val itemCraftedCount: Var[Int] = Var(0)
}
