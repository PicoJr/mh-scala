package game.item.craft.nature

import game.config.{DefaultGameConfig, GameConfig}
import game.item.ItemType
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
abstract class DefaultNatureType[TItemType <: ItemType](val gameConfig: GameConfig = DefaultGameConfig.getInstance) extends NatureType[TItemType] {

  protected def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, gameConfig.getHunterStatsGrowth, gameConfig.getPercentageVariation)
  }

}
