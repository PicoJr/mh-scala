package config

/**
  * Created by nol on 05/11/17.
  */
object Config {

  /* */
  final val LEVEL_MIN: Int = 1

  final val LEVEL_MAX: Int = 5

  /* prevent damage to go under DAMAGE_MIN for each blow during fight */
  final val DAMAGE_MIN: Int = 1

  final val QUEST_DURATION_MAX: Int = 100

  /* > 0 */
  final val QUESTS_PER_LEVEL: Int = 6

  final val WEAPONS_PER_LEVEL: Int = 2

  final val ARMORS_PER_LEVEL: Int = 2

  final val CHARMS_PER_LEVEL: Int = 2

  final val MATERIALS_PER_LEVEL: Int = 8

  final val HUNTER_LIFE_MAX: Int = 100

  final val PERCENTAGE_VARIATION: Int = 5

  final val PERCENTAGE_BONUS: Int = 10

  final val DAMAGE_BASE: Int = 20

  final val ARMOR_BASE: Int = 20

  final val DAMAGE_BONUS_BASE: Int = 5

  final val ARMOR_BONUS_BASE: Int = 5

  final val STATS_GROWTH: Double = 1.2

  final val MONSTER_LIFE_BASE: Int = 100

  final val MONSTER_ARMOR_BASE: Int = 5

  final val MONSTER_DAMAGE_BASE: Int = 10

}
