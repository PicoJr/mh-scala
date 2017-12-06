package game.quest

import game.id.{DefaultIdSupplier, IdSupplier}
import game.item.ItemType
import game.unit.{DefaultMonsterFactory, MonsterFactory}
import game.util.LoopingRandomPool

/**
  * Created by nol on 06/12/17.
  */
class DefaultQuestFactory(idSupplier: IdSupplier, monsterFactory: MonsterFactory, monsterNamePool: LoopingRandomPool[String]) extends QuestFactory {

  def this(monsterNamePool: LoopingRandomPool[String]) = {
    this(new DefaultIdSupplier, new DefaultMonsterFactory, monsterNamePool)
  }

  override def createQuest(level: Int, loot: Seq[ItemType]): Quest = {
    val monster = monsterFactory.createMonster(level, monsterNamePool.next)
    new DefaultQuest(monster, loot, level, idSupplier.getNextUniqueId)
  }
}
