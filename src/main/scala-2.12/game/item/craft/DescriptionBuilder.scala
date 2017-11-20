package game.item.craft

/**
  * Created by nol on 20/11/17.
  */
class DescriptionBuilder {
  var nature: String = "undefined"
  var element: Option[String] = Option.empty
  var adjectives: Seq[String] = Seq.empty

  def getDescription: String = {
    val description = new StringBuilder()
    for (adjective <- adjectives) {
      description.append(adjective).append(" ")
    }
    element match {
      case Some(e) => description.append(e).append(" ")
      case None =>
    }
    description.append(nature)
    description.toString()
  }

  def getNature: String = nature

  def getElement: Option[String] = element

  def getAdjectives: Seq[String] = adjectives

  def addNature(nature: String): DescriptionBuilder = {
    this.nature = nature
    this
  }

  def addAdjective(adjective: String): DescriptionBuilder = {
    this.adjectives = adjectives :+ adjective
    this
  }

  def addElement(element: String): DescriptionBuilder = {
    this.element = Option(element)
    this
  }
}
