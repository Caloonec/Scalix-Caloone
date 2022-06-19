import APIConfig._
import org.json4s._
import org.json4s.native.JsonMethods._
import scala.io.Source

object Requests{

  def getResponse(uri: String): JValue = {
    val url = s"$baseUrl$uri&api_key=$apiKey"
    val sourceFromUrl = Source.fromURL(url).mkString
    val jsonResponse = parse(sourceFromUrl)

    jsonResponse

  }

  def findActorId(name: String, surname: String): Option[Int] = {
    val res = getResponse(s"/search/person?query=$name+$surname")

    println(res)
    return Option.empty[Int]
  }
}

