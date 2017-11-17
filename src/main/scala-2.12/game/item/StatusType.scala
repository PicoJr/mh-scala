package game.item

import scala.util.Random

/** Status type provided attack or protected against by armor
  * Created by nol on 05/11/17.
  */
object StatusType extends Enumeration {
  type StatusType = Value
  val NONE, STUN, SLEEP = Value

  def getRandomStatusType: StatusType = {
    StatusType(Random.nextInt(StatusType.maxId))
  }

}
