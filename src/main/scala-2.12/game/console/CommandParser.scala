package game.console

import game.GameState
import org.docopt.{Docopt, DocoptExitException}

/**
  * Created by nol on 18/11/17.
  */
class CommandParser(command: Command) {

  private final val doc: String =
    """ Monster Hunter
      |Usage:
      |  mh hunter show
      |  mh item ls
      |  mh item (equip | unequip | show) <itemId>
      |  mh quest (ls | start <questId>)
      |  mh craft show <itemId>
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
      val opts = docopt.parse(args: _*)
      if (opts.get("--help").asInstanceOf[Boolean]) {
        println(doc)
      } else if (opts.get("hunter").asInstanceOf[Boolean]) {
        if (opts.get("show").asInstanceOf[Boolean]) {
          command.showHunter(gameState)
        }
      } else if (opts.get("item").asInstanceOf[Boolean]) {
        if (opts.get("show").asInstanceOf[Boolean]) {
          command.showItem(gameState, opts.get("<itemId>").toString.toLong)
        }
        if (opts.get("equip").asInstanceOf[Boolean]) {
          command.equipItem(gameState, opts.get("<itemId>").toString.toLong)
        }
        if (opts.get("unequip").asInstanceOf[Boolean]) {
          command.unEquipItem(gameState, opts.get("<itemId>").toString.toLong)
        }
        if (opts.get("ls").asInstanceOf[Boolean]) {
          command.listInventory(gameState)
        }
      } else if (opts.get("craft").asInstanceOf[Boolean]) {
        if (opts.get("show").asInstanceOf[Boolean]) {
          command.showCraft(gameState, opts.get("<itemId>").toString.toLong)
        }
      } else if (opts.get("quest").asInstanceOf[Boolean]) {
        if (opts.get("start").asInstanceOf[Boolean]) {
          command.startQuest(gameState, opts.get("<questId>").toString.toLong)
        }
        if (opts.get("ls").asInstanceOf[Boolean]) {
          command.listQuests(gameState)
        }
      }
    } catch {
      case _: DocoptExitException =>
      case e: IllegalArgumentException => System.err.println(e.getMessage)
    }
  }

}
