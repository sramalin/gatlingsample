package  org.thoughtworks.sampleRT.simulations;

import java.io.File

import akka.actor.Status
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.typesafe.config._
import io.gatling.commons.stats.KO





class Loadiframe extends Simulation {

  val targetEnv = System.getProperty("targetEnv")
  var file:File = new File(targetEnv+".conf")
  val conf = ConfigFactory.parseFile(file)
  val uri1 =conf.getString("BaseURL")
  val talktype_token=conf.getString("talktype_session")
  val header1 = Map("Referer" -> uri1)
  val userName = conf.getString("userName")
  val contentType = Map("Content-Type" -> "application/json")
  val activityID = Map("ActivityId" -> "ee099ac7-2959-4688-9fa1-dbb11ad7e8ff")
  var errorSet = scala.collection.immutable.HashSet("")



  val loadiFrame = scenario("Load iframe")
      .exec(http("Post comment")
      .get(uri1 + "/iframe?account_id=2&page_url=aHR0cHM6Ly9zdGFnaW5nLm1ldHlwZS5jb20vaWZyYW1lLXRlc3Qtd2lkZ2V0")
      .headers(contentType)
      .headers(header1)
        .check(status.is(200)))



  setUp(loadiFrame.inject(rampUsers(100) over (1)))
  	  .assertions(global.responseTime.max.lt(5000))
      .assertions(global.failedRequests.count.is(0))



}