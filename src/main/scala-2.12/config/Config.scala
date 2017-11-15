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

  final val WEAPONS_PER_LEVEL: Int = 4

  final val ARMORS_PER_LEVEL: Int = 4

  final val CHARMS_PER_LEVEL: Int = 8

  final val MATERIALS_PER_LEVEL: Int = 8

  final val HUNTER_LIFE_MAX: Int = 100

}
