import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val android = "org.scala-tools.sbt" % "android-plugin" % "0.4.1"
  val proguard = "org.scala-tools.sbt" % "sbt-proguard-plugin" % "0.0.2"
}
