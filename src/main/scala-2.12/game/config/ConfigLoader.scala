package game.config

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by nol on 18/11/17.
  */
object ConfigLoader {

  def loadConfig: GameConfig = {
    val conf = ConfigFactory.load()
    new GameConfig(conf)
  }

  class GameConfig(conf: Config) {

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

}
