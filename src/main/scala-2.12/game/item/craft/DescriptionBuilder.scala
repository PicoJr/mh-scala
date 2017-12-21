package game.item.craft

/**
  * Created by nol on 20/11/17.
  */
class DescriptionBuilder {
  private var nature: String = "undefined"
  private var adjectives: Seq[String] = Seq.empty

  def getDescription: String = {
    val description = new StringBuilder()
    for (adjective <- adjectives) {
      description.append(adjective).append(" ")
    }
    description.append(nature)
    description.toString()
  }

  def getNature: String = nature

  def getAdjectives: Seq[String] = adjectives

  def addNature(nature: String): DescriptionBuilder = {
    this.nature = nature
    this
  }

  def addAdjective(adjective: String): DescriptionBuilder = {
    this.adjectives = adjectives :+ adjective
    this
  }
}
