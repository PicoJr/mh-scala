package game.item.craft.bonus

import game.item.OpenEnum

/**
  * Created by nol on 06/12/17.
  */
class DefaultBonusTypeEnum extends OpenEnum[BonusType] {
  override def getValues: Seq[BonusType] = Seq(DAMAGE, PROTECTION)
}
