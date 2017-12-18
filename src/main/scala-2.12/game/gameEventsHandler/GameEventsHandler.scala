package game.gameEventsHandler

import rescala._

/**
  * Created by nol on 18/12/17.
  */
trait GameEventsHandler {

  val hunterDefeated: rescala.Evt[Boolean] = Evt[Boolean]()

  val monsterSlain: rescala.Evt[Boolean] = Evt[Boolean]()

  val questStarted: rescala.Evt[Long] = Evt[Long]() // value: questId

  val questFailed: rescala.Evt[Long] = Evt[Long]() // values: questId

  val questSucceeded: rescala.Evt[Long] = Evt[Long]() // values: quest

  val questCompleted: rescala.Evt[Long] = Evt[Long]() // values: quest

  val questFinished: rescala.Evt[Double] = Evt[Double]() // values: time elapsed

  val hunterDealtDamage: rescala.Evt[Double] = Evt[Double]() // values: damage

  val monsterDealtDamage: rescala.Evt[Double] = Evt[Double]() // values: damage

  val questSuccesses: Signal[Int] = questSucceeded.count

  val questFailures: Signal[Int] = questFailed.count

  val questAttempts: Signal[Int] = questStarted.count

  val damageDealtByHunter: Signal[Double] = hunterDealtDamage.latest(0)

  val damageDealtByMonster: Signal[Double] = monsterDealtDamage.latest(0)

  val timeElapsed: Signal[Double] = questFinished.latest(0)

  val monsterWasSlain: Signal[Boolean] = monsterSlain.latest()

  val hunterWasDefeated: Signal[Boolean] = hunterDefeated.latest()
}
