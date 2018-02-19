package game.item.craft.nature

import game.config.{DefaultGameConfig, GameConfig}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
abstract class DefaultNatureType(val gameConfig: GameConfig = DefaultGameConfig.getInstance) extends NatureType {

  protected def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, gameConfig.getHunterStatsGrowth, gameConfig.getPercentageVariation)
  }

}
