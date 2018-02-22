package game

import game.item._
import game.item.craft.bonus.{DamageBonus, ProtectionBonus}
import game.item.craft.nature.{Armor, Charm, Weapon}
import game.item.element._
import game.item.status.{NEUTRAL, SLEEP, STUN}

/**
  * Created by nol on 02/01/18.
  */
object GameDefaults {

  val decorator = new DefaultDecorator()
  val elementTypes = Seq(FIRE, WATER, ELECTRIC, NORMAL)
  val statusTypes = Seq(STUN, SLEEP, NEUTRAL)
  val bonusTypes = Seq(DamageBonus, ProtectionBonus)
  val natureTypes = Seq(Weapon(), Charm(), Armor(ArmorPart.HEAD), Armor(ArmorPart.BODY), Armor(ArmorPart.ARMS), Armor(ArmorPart.LEGS))
  val eEResolver: EEResolver =
    new DefaultEEResolverBuilder()
      .withEE(WATER, FIRE, Effectiveness.EFFECTIVE)
      .withEE(ELECTRIC, WATER, Effectiveness.EFFECTIVE)
      .withEE(FIRE, ELECTRIC, Effectiveness.EFFECTIVE)
      .withEE(FIRE, FIRE, Effectiveness.INEFFECTIVE)
      .withEE(WATER, WATER, Effectiveness.INEFFECTIVE)
      .withEE(ELECTRIC, ELECTRIC, Effectiveness.INEFFECTIVE)
      .withEE(FIRE, WATER, Effectiveness.INEFFECTIVE)
      .withEE(ELECTRIC, FIRE, Effectiveness.INEFFECTIVE)
      .build
}
