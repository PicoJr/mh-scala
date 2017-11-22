package game.unit

import game.config.ConfigLoader
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType
import game.item.{ElementType, StatusType}
import game.util.Procedural

/**
  * Created by nol on 17/11/17.
  */
object RandomMonsterFactory {

  private final val config = ConfigLoader.loadGameConfig
  private final val nameConfig = ConfigLoader.loadNameConfig

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, config.getStatsGrowth, config.getPercentageVariation)

  private def generateName(level: Int): String = {
    var name = "unnamed"
    if (level == config.getLevelMin) name = Procedural.pickRandom("gloupix", "trumf", "patkir").get
    if (level == (config.getLevelMin + 1)) name = Procedural.pickRandom("tulpor", "dackdack", "plossy").get
    if (level == (config.getLevelMin + 2)) name = Procedural.pickRandom("grusk", "scoptr", "phyga").get
    if (level == (config.getLevelMin + 3)) name = Procedural.pickRandom("acknack", "devian", "slendr").get
    if (level >= (config.getLevelMin + 4)) name = Procedural.pickRandom("prolog", "sysmic", "cratOS").get
    name
  }

  private def getRandomAttackStatusType(level: Int): StatusType = {
    var statusType = StatusType.NONE
    if (level >= config.getLevelMin + 2) {
      statusType = StatusType.getRandomStatusType
    }
    statusType
  }

  private def getRandomAttackElementType(level: Int): ElementType = {
    var elementType = ElementType.NONE
    if (level >= config.getLevelMin + 3) {
      elementType = ElementType.getRandomElementType
    }
    elementType
  }

  private def getRandomArmorStatusTypes(level: Int): Seq[StatusType] = {
    var statusTypes: Seq[StatusType] = Seq.empty
    if (level >= config.getLevelMin + 4) {
      statusTypes = statusTypes :+ StatusType.getRandomStatusType
    }
    statusTypes
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
    Monster(name, armor, life, damage, attackStatusType, attackElementType, armorStatusTypes, armorElementTypes)
  }

}
