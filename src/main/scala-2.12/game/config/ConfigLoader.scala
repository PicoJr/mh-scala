package game.config

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters

/**
  * Created by nol on 18/11/17.
  */
object ConfigLoader {

  def loadGameConfig: GameConfig = {
    val conf = ConfigFactory.load()
    GameConfig(conf)
  }

  def loadNameConfig: NameConfig = {
    val conf = ConfigFactory.load()
    NameConfig(conf)
  }

  case class GameConfig(conf: Config) {
    def getLevelMin: Int = conf.getInt("game.level.min")

    def getLevelMax: Int = conf.getInt("game.level.max")

    def getStatsGrowth: Double = conf.getDouble("game.level.statsGrowth")

    def getHunterLifeMax: Int = conf.getInt("game.hunter.lifeMax")

    def getDamageMin: Int = conf.getInt("game.hunter.damageMin")

    def getQuestDurationMax: Int = conf.getInt("game.quest.durationMax")

    def getQuestsPerLevel: Int = conf.getInt("game.quest.perLevel")

    def getWeaponsPerLevel: Int = conf.getInt("game.craft.weaponsPerLevel")

    def getArmorsPerLevel: Int = conf.getInt("game.craft.armorsPerLevel")

    def getCharmsPerLevel: Int = conf.getInt("game.craft.charmsPerLevel")

    def getMaterialsPerLevel: Int = conf.getInt("game.craft.materialsPerLevel")

    def getPercentageVariation: Int = conf.getInt("game.random.percentageVariation")

    def getPercentageBonus: Int = conf.getInt("game.random.percentageBonus")

    def getDamageBase: Int = conf.getInt("game.item.damageBase")

    def getArmorBase: Int = conf.getInt("game.item.armorBase")

    def getArmorBonusBase: Int = conf.getInt("game.item.armorBonusBase")

    def getDamageBonusBase: Int = conf.getInt("game.item.damageBonusBase")

    def getMonsterLifeBase: Int = conf.getInt("game.monster.lifeBase")

    def getMonsterArmorBase: Int = conf.getInt("game.monster.armorBase")

    def getMonsterDamageBase: Int = conf.getInt("game.monster.damageBase")

  }

  case class NameConfig(conf: Config) {

    def getWeaponNatures: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.weaponNatures"))

    def getCharmNatures: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.charmNatures"))

    def getMaterialNatures: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.materialNatures"))

    def getProtectionAdjectives: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.protectionAdjectives"))

    def getDamageAdjectives: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.damageAdjectives"))

    def getFireAdjectives: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.fireAdjectives"))

    def getWaterAdjectives: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.waterAdjectives"))

    def getArmorHeadNatures: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.armorHeadNatures"))

    def getArmorBodyNatures: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.armorBodyNatures"))

    def getArmorArmsNatures: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.armorArmsNatures"))

    def getArmorLegsNatures: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.armorLegsNatures"))

  }


}
