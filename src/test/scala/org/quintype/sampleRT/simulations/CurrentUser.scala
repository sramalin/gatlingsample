package  org.quintype.sampleRT.simulations;

import java.io.File

import akka.actor.Status
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.typesafe.config._
import io.gatling.commons.stats.KO





class CurrentUser extends Simulation {

  // val targetEnv = System.getProperty("targetEnv")
  // var file:File = new File(targetEnv+".conf")
  // val conf = ConfigFactory.parseFile(file)
  // //val uri1 =conf.getString("BaseURL")
  // val talktype_token=conf.getString("talktype_session")
  // val header1 = Map("Referer" -> uri1)
  // val userName = conf.getString("userName")
  // val contentType = Map("Content-Type" -> "application/json")
  // val activityID = Map("ActivityId" -> "ee099ac7-2959-4688-9fa1-dbb11ad7e8ff")
  // var errorSet = scala.collection.immutable.HashSet("")



  val homepageTest = scenario("homepag")
      .exec(http("Hitting homepage")
      .get("http://en-uat.prothomalo.qtstage.io/")
      .check(status.is(200)))



  setUp(homepageTest.inject(rampUsers(10000) over (10)))
    .assertions(global.failedRequests.count.is(0))
    .assertions(global.responseTime.max.lt(5000))





}