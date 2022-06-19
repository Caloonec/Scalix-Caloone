package scalix

import scalix.FullName
import org.json4s.native.JsonMethods.parse
import org.json4s.*
import scalix.APIConfig.{apiKey, baseUrl}


import scala.collection.mutable.Set
import scala.io.Source

object Requests {

  def getResponse(uri: String): JValue = {
    val url = s"$baseUrl$uri${"api_key="}$apiKey"
    val sourceFromUrl = Source.fromURL(url).mkString
    val jsonResponse = parse(sourceFromUrl)

    jsonResponse

  }

  def findActorId(name: String, surname: String): Option[Int] = {
    val res = getResponse(s"/search/person?query=$name+$surname&")
    try {
      val results = (res \ "results")
      val JInt(id) = (results \ "id").apply(0)
      Some(id.intValue)
    } catch {
      case e: Throwable => Option.empty[Int]
    }
  }

  def findActorMovies(actorId: Int): Set[(Int, String)] = {
    val res = getResponse(s"/person/$actorId/movie_credits?")
    val setActorMovies: Set[(Int, String)] = Set()

    for {
      JArray(cast) <- res
      JObject(film) <- cast
      JField("id", JInt(id)) <- film
      JField("title", JString(title)) <- film
    } setActorMovies.add(id.intValue, title)

    return setActorMovies
  }

  def findMovieDirector(movieId: Int): Option[(Int, String)] = {
    val res = getResponse(s"/movie/$movieId/credits?")
    val crew = res \ "crew"

    for {
      JObject(person) <- crew if (person contains JField("job", JString("Director")))
      JField("id", JInt(id)) <- person
      JField("name", JString(name)) <- person
    } return Some((id.intValue, name))

    return Option.empty[(Int, String)]
  }

  def collaboration(actor1: FullName, actor2: FullName): Set[(String, String)] = {
    val firstActorId = findActorId(actor1.firstName, actor1.lastName)
    val secondActorId = findActorId(actor2.firstName, actor2.lastName)

    if (firstActorId.isEmpty || secondActorId.isEmpty) {
      return Set()
    }

    val firstActorMovies = findActorMovies(firstActorId.get)
    val secondActorMovies = findActorMovies(secondActorId.get)

    val actorsCollab = firstActorMovies.intersect(secondActorMovies)
    val collaborations: Set[(String, String)] = Set()
    actorsCollab.foreach(movie => {
      val movieDirector = findMovieDirector(movie._1.intValue())
      if (movieDirector.nonEmpty) {
        collaborations.add((movieDirector.get._2, movie._2))
      }
    })


    return collaborations


  }


}
