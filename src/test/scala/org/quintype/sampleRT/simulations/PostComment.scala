package  org.thoughtworks.sampleRT.simulations;

import java.io.File

import akka.actor.Status
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.typesafe.config._
import io.gatling.commons.stats.KO





class PostComment extends Simulation {

  val targetEnv = System.getProperty("targetEnv")
  var file:File = new File(targetEnv+".conf")
  val conf = ConfigFactory.parseFile(file)
  val uri1 =conf.getString("BaseURL")
  val talktype_token=conf.getString("talktype_session")
  val header1 = Map("cookie" -> talktype_token)
  val userName = conf.getString("userName")
  val contentType = Map("Content-Type" -> "application/json")
  val activityID = Map("ActivityId" -> "ee099ac7-2959-4688-9fa1-dbb11ad7e8ff")
  var errorSet = scala.collection.immutable.HashSet("")



  val postComment = scenario("Post comment")
      .exec(http("Post comment")
      .post(uri1 + "/api/v1/accounts/2/pages/aHR0cHM6Ly9zdGFnaW5nLm1ldHlwZS5jb20vaWZyYW1lLXRlc3Qtd2lkZ2V0/comments.json")
      .headers(contentType)
      .headers(header1)
      .body(StringBody("""{"comment":{"body":{"ops":[{"insert":"I m running sample perf test - Ramalingam\n"}]}}}""")).asJSON
      .check(status.is(201)))



  setUp(postComment.inject(rampUsers(150) over (10)))
  	  .assertions(global.responseTime.max.lt(5000))
      .assertions(global.failedRequests.count.is(0))



}