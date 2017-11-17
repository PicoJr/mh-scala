package game.unit

import game.Config
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType
import game.item.{ElementType, StatusType}
import game.util.Procedural

/**
  * Created by nol on 17/11/17.
  */
object RandomMonsterFactory {

  def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, Config.STATS_GROWTH, Config.PERCENTAGE_VARIATION)

  def generateName(level: Int): String = {
    var name = "unnamed"
    if (level == Config.LEVEL_MIN) name = Procedural.pickRandom("gloupix", "trumf", "patkir").get
    if (level == (Config.LEVEL_MIN + 1)) name = Procedural.pickRandom("tulpor", "dackdack", "plossy").get
    if (level == (Config.LEVEL_MIN + 2)) name = Procedural.pickRandom("grusk", "scoptr", "phyga").get
    if (level == (Config.LEVEL_MIN + 3)) name = Procedural.pickRandom("acknack", "devian", "slendr").get
    if (level >= (Config.LEVEL_MIN + 4)) name = Procedural.pickRandom("prolog", "sysmic", "cratOS").get
    name
  }

  def getRandomAttackStatusType(level: Int): StatusType = {
    var statusType = StatusType.NONE
    if (level >= Config.LEVEL_MIN + 2) {
      statusType = StatusType.getRandomStatusType
    }
    statusType
  }

  def getRandomAttackElementType(level: Int): ElementType = {
    var elementType = ElementType.NONE
    if (level >= Config.LEVEL_MIN + 3) {
      elementType = ElementType.getRandomElementType
    }
    elementType
  }

  def getRandomArmorStatusTypes(level: Int): Seq[StatusType] = {
    var statusTypes: Seq[StatusType] = Seq.empty
    if (level >= Config.LEVEL_MIN + 4) {
      statusTypes = statusTypes :+ StatusType.getRandomStatusType
    }
    statusTypes
  }

  def generateMonster(level: Int): Monster = {
    val name = generateName(level)
    val life = getRandomValue(level, Config.MONSTER_LIFE_BASE)
    val armor = getRandomValue(level, Config.MONSTER_ARMOR_BASE)
    val damage = getRandomValue(level, Config.MONSTER_DAMAGE_BASE)
    val attackStatusType = getRandomAttackStatusType(level)
    val attackElementType = getRandomAttackElementType(level)
    val armorStatusTypes = getRandomArmorStatusTypes(level)
    val armorElementTypes = Seq(attackElementType, ElementType.getRandomElementType)
    Monster(name, armor, life, damage, attackStatusType, attackElementType, armorStatusTypes, armorElementTypes)
  }

}
