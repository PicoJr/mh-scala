package game.console

import game.commandEvents.CommandEvents
import org.docopt.{Docopt, DocoptExitException}

import scala.collection.JavaConverters

/**
  * Created by nol on 18/11/17.
  */
class CommandParser(commandEvents: CommandEvents) {

  private final val doc: String =
    """ Monster Hunter
      |Usage:
      |  mh hunter show
      |  mh item ls
      |  mh item (equip | unequip) <itemId>...
      |  mh item show <itemId>...
      |  mh quest (ls | show <questId>... | start <questId>...)
      |  mh craft show <itemId>...
      |  mh craft new <materialId> <itemId>...
      |  mh stat
      |  mh quit
      |  mh (-h | --help)
      |  mh --version
      |Options:
      |  -h --help    Show this screen.
      |  --version    Show version
    """.stripMargin

  def runCommand(args: Seq[String]): Unit = {
    val docopt = new Docopt(doc).withVersion("MH 1.0").withExit(false).withHelp(false)
    try {
      val simpleOpts = new SimpleOpts(docopt.parse(args: _*))
      if (simpleOpts.asBoolean("--help")) {
        println(doc)
      }
      else if (simpleOpts.asBoolean("hunter")) {
        if (simpleOpts.asBoolean("show")) {
          commandEvents.showHunter(Unit)
        }
      }
      else if (simpleOpts.asBoolean("item")) {
        if (simpleOpts.asBoolean("show")) {
          commandEvents.showItem(simpleOpts.asLong("<itemId>"))
        }
        if (simpleOpts.asBoolean("equip")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            commandEvents.equipItem(itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("unequip")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            commandEvents.unEquipItem(itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("ls")) {
          commandEvents.listInventory(Unit)
        }
      }
      else if (simpleOpts.asBoolean("craft")) {
        if (simpleOpts.asBoolean("show")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            commandEvents.showCraft(itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("new")) {
          val materialId = simpleOpts.asLong("<materialId>")
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            commandEvents.craftItem(materialId, itemId.toString.toLong)
          }
        }
      }
      else if (simpleOpts.asBoolean("quest")) {
        if (simpleOpts.asBoolean("start")) {
          for (questId <- simpleOpts.asSeq("<questId>")) {
            commandEvents.startQuest(questId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("ls")) {
          commandEvents.listQuests(Unit)
        }
        if (simpleOpts.asBoolean("show")) {
          for (questId <- simpleOpts.asSeq("<questId>")) {
            commandEvents.showQuest(questId.toString.toLong)
          }
        }
      }
      else if (simpleOpts.asBoolean("stat")) {
        commandEvents.showStat(Unit)
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
