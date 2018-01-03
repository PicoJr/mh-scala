package game

import game.id.DefaultIdSupplier
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
  val itemTypeFactory = new DefaultItemTypeFactory(new DefaultIdSupplier)
  val itemFactory = new DefaultItemFactory(new DefaultIdSupplier)
  val elementTypes = Seq(FIRE, WATER, ELECTRIC, NORMAL)
  val statusTypes = Seq(STUN, SLEEP, NEUTRAL)
  val bonusTypes = Seq(DamageBonus, ProtectionBonus)
  val natureTypes = Seq(Weapon[ItemType](decorator, itemTypeFactory), Charm[ItemType](decorator, itemTypeFactory), Armor[ItemType](ArmorPart.HEAD, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.BODY, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.ARMS, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.LEGS, decorator, itemTypeFactory))
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
