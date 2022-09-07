import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

lazy val microservice = Project("cip-email-stubs", file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(
    majorVersion        := 0,
    scalaVersion        := "2.13.8",
    libraryDependencies ++= AppDependencies.compile,
    // https://www.scala-lang.org/2021/01/12/configuring-and-suppressing-warnings.html
    // suppress warnings in generated routes files
    scalacOptions += "-Wconf:src=routes/.*:s",
  )
  .settings(
    PlayKeys.playDefaultPort := 6199
  )
  .settings(publishingSettings: _*)
  .settings(resolvers += Resolver.jcenterRepo)
