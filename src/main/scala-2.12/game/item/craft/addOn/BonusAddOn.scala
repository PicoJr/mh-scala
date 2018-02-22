package game.item.craft.addOn

import game.item.craft.bonus.{BonusType, DamageBonus}
import game.item.{AbstractDecorator, DefaultDecorator, ItemType}

/** Provides additional bonus.
  * Created by nol on 21/12/17.
  */

case class BonusAddOn(bonusType: BonusType, decorator: AbstractDecorator = DefaultDecorator.getInstance) extends DefaultAddOn(bonusType.name) {

  override def decorate(level: Int, itemType: ItemType): ItemType = bonusType match {
    case DamageBonus => decorator.decorateWithDamage(itemType, getRandomValue(level, gameConfig.getDamageBonusBase))
    case _ => decorator.decorateWithProtection(itemType, getRandomValue(level, gameConfig.getArmorBonusBase))
  }
}

