package item

import scala.util.Random

/**
  * Created by nol on 05/11/17.
  */
object StatusType extends Enumeration {
  type StatusType = Value
  val NONE, STUNNED, ASLEEP = Value

  def getRandomStatusType: StatusType = {
    StatusType(Random.nextInt(StatusType.maxId))
  }

}
