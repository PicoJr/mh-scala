package game.config

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters

/**
  * Created by nol on 18/11/17.
  */
private class DefaultGameConfig(conf: Config) extends GameConfig {

  override def getLevelMin: Int = conf.getInt("game.level.min")

  override def getLevelMax: Int = conf.getInt("game.level.max")

  override def getDamageMin: Int = conf.getInt("game.hunter.damageMin")

  override def getQuestDurationMax: Int = conf.getInt("game.quest.durationMax")

  override def getQuestsPerLevel: Int = conf.getInt("game.quest.perLevel")

  override def getPercentageVariation: Int = conf.getInt("game.random.percentageVariation")

  override def getPercentageBonus: Int = conf.getInt("game.random.percentageBonus")

  override def getDamageBase: Int = conf.getInt("game.item.damageBase")

  override def getArmorBase: Int = conf.getInt("game.item.armorBase")

  override def getArmorBonusBase: Int = conf.getInt("game.item.armorBonusBase")

  override def getDamageBonusBase: Int = conf.getInt("game.item.damageBonusBase")

  override def getHunterStatsGrowth: Double = conf.getDouble("game.hunter.statsGrowth")

  override def getHunterName: String = conf.getString("game.hunter.name")

  override def getHunterLifeMax: Int = conf.getInt("game.hunter.lifeMax")

  override def getMonsterStatsGrowth: Double = conf.getDouble("game.monster.statsGrowth")

  override def getMonsterLifeBase: Int = conf.getInt("game.monster.lifeBase")

  override def getMonsterArmorBase: Int = conf.getInt("game.monster.armorBase")

  override def getMonsterDamageBase: Int = conf.getInt("game.monster.damageBase")

  override def getNormalMultiplier: Double = conf.getDouble("game.effectiveness.normal")

  override def getIneffectiveMultiplier: Double = conf.getDouble("game.effectiveness.ineffective")

  override def getEffectiveMultiplier: Double = conf.getDouble("game.effectiveness.effective")

  override def getMonsters: Seq[String] = JavaConverters.asScalaBuffer(conf.getStringList("game.name.monsters"))

}

object DefaultGameConfig {
  private lazy val instance: GameConfig = {
    new DefaultGameConfig(ConfigFactory.load())
  } // yummy Scala

  def getInstance: GameConfig = instance

}
