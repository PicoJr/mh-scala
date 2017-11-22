package game.unit

import game.config.ConfigLoader
import game.item.StatusType
import game.item.StatusType.StatusType
import game.item.element.ElementType
import game.item.element.ElementType.ElementType
import game.util.Procedural

/**
  * Created by nol on 17/11/17.
  */
object RandomMonsterFactory {

  private final val config = ConfigLoader.loadGameConfig
  private final val nameConfig = ConfigLoader.loadNameConfig

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, config.getStatsGrowth, config.getPercentageVariation)

  private def getRandomAttackStatusType(level: Int): StatusType = {
    if (level >= config.getLevelMin + 2) StatusType.getRandomStatusType else StatusType.NONE
  }

  private def getRandomAttackElementType(level: Int): ElementType = {
    if (level >= config.getLevelMin + 3) ElementType.getRandomElementType else ElementType.NONE
  }

  private def getRandomArmorStatusTypes(level: Int): Seq[StatusType] = {
    if (level >= config.getLevelMin + 4) Seq(StatusType.getRandomStatusType) else Seq.empty
  }

  def generateMonster(level: Int): Monster = {
    val name = Procedural.pickRandomFromSeq(nameConfig.getMonsters).get
    val life = getRandomValue(level, config.getMonsterLifeBase)
    val armor = getRandomValue(level, config.getMonsterArmorBase)
    val damage = getRandomValue(level, config.getMonsterDamageBase)
    val attackStatusType = getRandomAttackStatusType(level)
    val attackElementType = getRandomAttackElementType(level)
    val armorStatusTypes = getRandomArmorStatusTypes(level)
    val armorElementTypes = Seq(attackElementType, ElementType.getRandomElementType)
    DefaultMonster(name, armor, life, damage, attackStatusType, attackElementType, armorStatusTypes, armorElementTypes)
  }

}
