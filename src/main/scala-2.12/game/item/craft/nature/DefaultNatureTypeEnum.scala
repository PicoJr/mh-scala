package game.item.craft.nature

import game.item.{ArmorPart, OpenEnum}

/**
  * Created by nol on 06/12/17.
  */
class DefaultNatureTypeEnum extends OpenEnum[NatureType] {
  override def getValues: Seq[NatureType] = Seq(
    WEAPON, ARMOR(ArmorPart.HEAD), ARMOR(ArmorPart.BODY),
    ARMOR(ArmorPart.ARMS), ARMOR(ArmorPart.LEGS), CHARM)
}
