package game.config

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters

/**
  * Created by nol on 18/11/17.
  */
object ConfigLoader {

  def loadGameConfig: GameConfig = {
    GameConfig(ConfigFactory.load())
  }

  def loadNameConfig: NameConfig = {
    NameConfig(ConfigFactory.load())
  }

  def loadEffectivenessConfig: EffectivenessConfig = {
    EffectivenessConfig(ConfigFactory.load())
  }

  def loadMonsterConfig: MonsterConfig = {
    MonsterConfig(ConfigFactory.load())
  }

  def loadHunterConfig: HunterConfig = {
    HunterConfig(ConfigFactory.load())
  }

  def loadItemConfig: ItemConfig = {
    ItemConfig(ConfigFactory.load())
  }

  case class GameConfig(conf: Config) {
    def getLevelMin: Int = conf.getInt("game.level.min")

    def getLevelMax: Int = conf.getInt("game.level.max")

    def getDamageMin: Int = conf.getInt("game.hunter.damageMin")

    def getQuestDurationMax: Int = conf.getInt("game.quest.durationMax")

    def getQuestsPerLevel: Int = conf.getInt("game.quest.perLevel")

    def getPercentageVariation: Int = conf.getInt("game.random.percentageVariation")

    def getPercentageBonus: Int = conf.getInt("game.random.percentageBonus")
  }

  case class ItemConfig(conf: Config) {
    def getDamageBase: Int = conf.getInt("game.item.damageBase")

    def getArmorBase: Int = conf.getInt("game.item.armorBase")

    def getArmorBonusBase: Int = conf.getInt("game.item.armorBonusBase")

    def getDamageBonusBase: Int = conf.getInt("game.item.damageBonusBase")
  }

  case class HunterConfig(conf: Config) {
    def getHunterStatsGrowth: Double = conf.getDouble("game.hunter.statsGrowth")

    def getHunterName: String = conf.getString("game.hunter.name")

    def getHunterLifeMax: Int = conf.getInt("game.hunter.lifeMax")
  }

  case class MonsterConfig(conf: Config) {
    def getMonsterStatsGrowth: Double = conf.getDouble("game.monster.statsGrowth")

    def getMonsterLifeBase: Int = conf.getInt("game.monster.lifeBase")

    def getMonsterArmorBase: Int = conf.getInt("game.monster.armorBase")

    def getMonsterDamageBase: Int = conf.getInt("game.monster.damageBase")
  }

  case class EffectivenessConfig(conf: Config) {
    def getNormalMultiplier: Double = conf.getDouble("game.effectiveness.normal")

    def getIneffectiveMultiplier: Double = conf.getDouble("game.effectiveness.ineffective")

    def getEffectiveMultiplier: Double = conf.getDouble("game.effectiveness.effective")
  }

  case class NameConfig(conf: Config) {

    def getMonsters: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.monsters"))

  }


}
