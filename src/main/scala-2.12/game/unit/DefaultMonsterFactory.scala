package game.unit

import game.config.{DefaultGameConfig, GameConfig}
import game.id.{DefaultIdSupplier, IdSupplier}
import game.item.element._
import game.item.status.{NEUTRAL, SLEEP, STUN, StatusType}
import game.util.Procedural

/**
  * Created by nol on 17/11/17.
  */
class DefaultMonsterFactory(elementTypes: Seq[ElementType] = Seq(ELECTRIC, FIRE, NORMAL, WATER), statusTypes: Seq[StatusType] = Seq(NEUTRAL, SLEEP, STUN), idSupplier: IdSupplier = new DefaultIdSupplier, gameConfig: GameConfig = DefaultGameConfig.getInstance) {

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, gameConfig.getMonsterStatsGrowth, gameConfig.getPercentageVariation)

  private def getRandomElementType: ElementType = Procedural.pickRandomFromSeq(elementTypes).getOrElse(NORMAL)

  private def getRandomStatusType: StatusType = Procedural.pickRandomFromSeq(statusTypes).getOrElse(NEUTRAL)

  private def getRandomAttackStatusType(level: Int): StatusType = {
    if (level >= gameConfig.getLevelMin + 2) getRandomStatusType else NEUTRAL
  }

  private def getRandomAttackElementType(level: Int): ElementType = {
    if (level >= gameConfig.getLevelMin + 3) getRandomElementType else NORMAL
  }

  private def getRandomArmorStatusTypes(level: Int): Seq[StatusType] = {
    if (level >= gameConfig.getLevelMin + 4) Seq(getRandomStatusType) else Seq.empty
  }

  private def getRandomArmorElementTypes(level: Int): Seq[ElementType] = {
    var armorElementTypes = Seq.empty[ElementType]
    for (_ <- gameConfig.getLevelMin to math.min(level, 3)) {
      armorElementTypes = armorElementTypes :+ getRandomElementType
    }
    armorElementTypes
  }

  def createMonster(level: Int, name: String): Monster = {
    val life = getRandomValue(level, gameConfig.getMonsterLifeBase)
    val armor = getRandomValue(level, gameConfig.getMonsterArmorBase)
    val damage = getRandomValue(level, gameConfig.getMonsterDamageBase)
    val attackStatusType = getRandomAttackStatusType(level)
    val attackElementType = getRandomAttackElementType(level)
    val armorStatusTypes = getRandomArmorStatusTypes(level)
    val armorElementTypes = getRandomArmorElementTypes(level)
    DefaultMonster(name, idSupplier.getNextUniqueId, life, armor, damage, attackStatusType, attackElementType, armorStatusTypes, armorElementTypes)
  }

}
