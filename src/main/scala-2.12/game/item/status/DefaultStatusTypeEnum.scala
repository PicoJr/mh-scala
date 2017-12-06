package game.item.status

import game.item.OpenEnum

/**
  * Created by nol on 06/12/17.
  */
class DefaultStatusTypeEnum extends OpenEnum[StatusType] {
  override def getValues: Seq[StatusType] = Seq(NEUTRAL, SLEEP, STUN)
}
