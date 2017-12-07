package game.unit

import game.config.ConfigLoader
import game.id.{DefaultIdSupplier, IdSupplier}
import game.item.element._
import game.item.status.{NEUTRAL, SLEEP, STUN, StatusType}
import game.util.Procedural

/**
  * Created by nol on 17/11/17.
  */
class DefaultMonsterFactory(
                             elementTypes: Seq[ElementType],
                             statusTypes: Seq[StatusType],
                             idSupplier: IdSupplier
                           ) {

  def this() {
    this(
      Seq(ELECTRIC, FIRE, NORMAL, WATER),
      Seq(NEUTRAL, SLEEP, STUN),
      new DefaultIdSupplier)
  }

  private final val config = ConfigLoader.loadGameConfig
  private final val monsterConfig = ConfigLoader.loadMonsterConfig

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, monsterConfig.getMonsterStatsGrowth, config.getPercentageVariation)

  private def getRandomElementType: ElementType = Procedural.pickRandomFromSeq(elementTypes).getOrElse(NORMAL)

  private def getRandomStatusType: StatusType = Procedural.pickRandomFromSeq(statusTypes).getOrElse(NEUTRAL)

  private def getRandomAttackStatusType(level: Int): StatusType = {
    if (level >= config.getLevelMin + 2) getRandomStatusType else NEUTRAL
  }

  private def getRandomAttackElementType(level: Int): ElementType = {
    if (level >= config.getLevelMin + 3) getRandomElementType else NORMAL
  }

  private def getRandomArmorStatusTypes(level: Int): Seq[StatusType] = {
    if (level >= config.getLevelMin + 4) Seq(getRandomStatusType) else Seq.empty
  }

  private def getRandomArmorElementTypes(level: Int): Seq[ElementType] = {
    var armorElementTypes = Seq.empty[ElementType]
    if (level >= config.getLevelMin) {
      armorElementTypes = armorElementTypes :+ getRandomElementType
    }
    if (level >= config.getLevelMin + 1) {
      armorElementTypes = armorElementTypes :+ getRandomElementType
    }
    if (level >= config.getLevelMin + 2) {
      armorElementTypes = armorElementTypes :+ getRandomElementType
    }
    if (level >= config.getLevelMin + 3) {
      armorElementTypes = armorElementTypes :+ getRandomElementType
    }
    armorElementTypes
  }

  def createMonster(level: Int, name: String): Monster = {
    val life = getRandomValue(level, monsterConfig.getMonsterLifeBase)
    val armor = getRandomValue(level, monsterConfig.getMonsterArmorBase)
    val damage = getRandomValue(level, monsterConfig.getMonsterDamageBase)
    val attackStatusType = getRandomAttackStatusType(level)
    val attackElementType = getRandomAttackElementType(level)
    val armorStatusTypes = getRandomArmorStatusTypes(level)
    val armorElementTypes = getRandomArmorElementTypes(level)
    DefaultMonster(name, idSupplier.getNextUniqueId, life, armor, damage, attackStatusType, attackElementType, armorStatusTypes, armorElementTypes)
  }

}
