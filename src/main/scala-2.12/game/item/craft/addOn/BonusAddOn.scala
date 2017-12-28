package game.item.craft.addOn

import game.item.craft.bonus.{BonusType, DAMAGE}
import game.item.{AbstractDecorator, ItemType}

/** Provides additional bonus.
  * Created by nol on 21/12/17.
  */

class BonusAddOn[TItemType <: ItemType](bonusType: BonusType, decorator: AbstractDecorator[TItemType]) extends DefaultAddOn[TItemType](bonusType.name, decorator) {

  override def create(level: Int, itemType: TItemType): TItemType = bonusType match {
    case DAMAGE => decorator.decorateWithDamage(itemType, getRandomValue(level, gameConfig.getDamageBonusBase))
    case _ => decorator.decorateWithProtection(itemType, getRandomValue(level, gameConfig.getArmorBonusBase))
  }
}

