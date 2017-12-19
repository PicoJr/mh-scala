package game.console

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
      |  mh item (equip | unequip) <itemId>...
      |  mh item show <itemId>...
      |  mh quest (ls | start <questId>...)
      |  mh craft show <itemId>...
      |  mh craft new <materialId> <itemId>...
      |  mh score
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
          command.showHunter()
        }
      }
      else if (simpleOpts.asBoolean("item")) {
        if (simpleOpts.asBoolean("show")) {
          command.showItem(simpleOpts.asLong("<itemId>"))
        }
        if (simpleOpts.asBoolean("equip")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            command.equipItem(itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("unequip")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            command.unEquipItem(itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("ls")) {
          command.listInventory()
        }
      }
      else if (simpleOpts.asBoolean("craft")) {
        if (simpleOpts.asBoolean("show")) {
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            command.showCraft(itemId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("new")) {
          val materialId = simpleOpts.asLong("<materialId>")
          for (itemId <- simpleOpts.asSeq("<itemId>")) {
            command.craftItem(materialId, itemId.toString.toLong)
          }
        }
      }
      else if (simpleOpts.asBoolean("quest")) {
        if (simpleOpts.asBoolean("start")) {
          for (questId <- simpleOpts.asSeq("<questId>")) {
            command.startQuest(questId.toString.toLong)
          }
        }
        if (simpleOpts.asBoolean("ls")) {
          command.listQuests()
        }
      }
      else if (simpleOpts.asBoolean("score")) {
        command.showScore()
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
