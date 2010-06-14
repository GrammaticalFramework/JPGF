import sbt._


trait AndroidDefaults {
  def androidPlatformName = "android-4"
}

class JPGFProject(info: ProjectInfo) extends ParentProject(info)
{
  override def shouldCheckOutputDirectories = false
  override def updateAction = task { None }

  lazy val library = project(info.projectPath, "JPGF library")

  // Examples
  lazy val foodDroid =
    project("examples" / "FoodsDroid",     // Project path
            "FoodsDroid",                  // Project name
            new AndroidApp(_),             // Project class
            library)                       // Dependencies

  lazy val phraseDroid =
    project("examples" / "PhraseDroid",    // Project path
            "PhraseDroid",                 // Project name
            new AndroidApp(_),             // Project class
            library)                       // Dependencies

  lazy val fridge =
    project("examples" / "Fridge",
            "Fridge",
            new AndroidApp(_),
            library)

  class AndroidApp(info: ProjectInfo) extends AndroidProject(info)
  with AndroidDefaults
}
