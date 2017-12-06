package game.item.element

import game.item.OpenEnum

/**
  * Created by nol on 06/12/17.
  */
class DefaultElementTypeEnum extends OpenEnum[ElementType] {
  override def getValues: Seq[ElementType] = Seq(ELECTRIC, FIRE, NORMAL, WATER)
}
