package scalix

import Requests._
import scalix.FullName

/**
 * Classe pour tester l'API
 */

@main def main() = {
  println(findActorId("Cillian", "Murphy"))
  println(findActorId("Cantin", "Caloone"))
  println(findActorMovies(2037))
  println(findMovieDirector(20526))
  println((collaboration(FullName("Brad", "Pitt"), FullName("Edward", "Norton"))))
  println((collaboration(FullName("Brad", "Pitt"), FullName("George", "Clooney"))))
}
