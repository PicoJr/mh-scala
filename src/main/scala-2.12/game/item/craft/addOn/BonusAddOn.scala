package game.item.craft.addOn

import game.item.craft.bonus.{BonusType, DAMAGE}
import game.item.{Damage, ItemType, Protection}

/** Provides additional bonus.
  * Created by nol on 21/12/17.
  */

class BonusAddOn(bonusType: BonusType) extends DefaultAddOn {
  override val name: String = bonusType.name

  override def createItemType(level: Int, itemType: ItemType): ItemType = {
    bonusType match {
      case DAMAGE => Damage(itemType, getRandomValue(level, gameConfig.getDamageBonusBase))
      case _ => Protection(itemType, getRandomValue(level, gameConfig.getArmorBonusBase))
    }
  }
}

