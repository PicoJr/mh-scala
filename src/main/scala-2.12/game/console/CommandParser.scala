package game.console

import game.gamestate.GameState
import org.docopt.{Docopt, DocoptExitException}

import scala.collection.JavaConverters

/**
  * Created by nol on 18/11/17.
  */
class CommandParser(command: Command) {

  private final val doc: String =
    """ Monster Hunter
      |Usage:
      |  mh hunter show
      |  mh item ls
      |  mh item (equip | unequip | show) <itemId>...
      |  mh quest (ls | start <questId>...)
      |  mh craft show <itemId>...
      |  mh craft new <itemId1> <itemId2>
      |  mh score
      |  mh quit
      |  mh (-h | --help)
      |  mh --version
      |Options:
      |  -h --help    Show this screen.
      |  --version    Show version
    """.stripMargin

  def runCommand(args: Seq[String], gameState: GameState): Unit = {
    val docopt = new Docopt(doc).withVersion("MH 1.0").withExit(false).withHelp(false)
    try {
      val simpleOpts = new SimpleOpts(docopt.parse(args: _*))
      if (simpleOpts.asBoolean("--help")) {
        println(doc)
      }
      else if (simpleOpts.asBoolean("hunter")) {
        if (simpleOpts.asBoolean("show")) {
          command.showHunter(gameState)
        }
      }
      else if (simpleOpts.asBoolean("item")) {
        if (simpleOpts.asBoolean("show")) {
          command.showItem(gameState, simpleOpts.asLong("<itemId>"))
        }
        if (simpleOpts.asBoolean("equip")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            command.equipItem(gameState, itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("unequip")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            command.unEquipItem(gameState, itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("ls")) {
          command.listInventory(gameState)
        }
      }
      else if (simpleOpts.asBoolean("craft")) {
        if (simpleOpts.asBoolean("show")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            command.showCraft(gameState, itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("new")) {
          command.craftItem(gameState, simpleOpts.asLong("<itemId1>"), simpleOpts.asLong("<itemId2>"))
        }
      }
      else if (simpleOpts.asBoolean("quest")) {
        if (simpleOpts.asBoolean("start")) {
          for (questId <- simpleOpts.asSeq("<questId>")) {
            command.startQuest(gameState, questId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("ls")) {
          command.listQuests(gameState)
        }
      }
      else if (simpleOpts.asBoolean("score")) {
        command.showScore(gameState)
      }
    } catch {
      case _: DocoptExitException =>
      case e: IllegalArgumentException => System.err.println(e.getMessage)
    }
  }

  private class SimpleOpts(opts: java.util.Map[String, AnyRef]) {
    def asSeq(paramName: String): Seq[AnyRef] = {
      JavaConverters.asScalaBuffer(opts.get(paramName).asInstanceOf[java.util.List[String]])
    }

    def asBoolean(paramName: String): Boolean = {
      opts.get(paramName).asInstanceOf[Boolean]
    }

    def asLong(paramName: String): Long = {
      opts.get(paramName).toString.toLong
    }
  }

}
