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
  lazy val phraseDroid =
    project("examples" / "FoodsDroid",     // Project path
            "FoodsDroid",                  // Project name
            new AndroidApp(_),             // Project class
            library)                       // Dependencies

  // lazy val foodDroid =
  //   project("examples" / "fooddroid",
  //           "FoodDroid",
  //           new FoodApp(_),
  //           library)

  // lazy val foodDroid =
  //   project("examples" / "fooddroid", "FoodDroid", new App(_), library)

  class AndroidApp(info: ProjectInfo) extends AndroidProject(info)
  with AndroidDefaults




//  lazy val
//   lazy val ui = project("ui", "Simulator User Interface", // core)

   // lazy val examples = project("examples", "Simulator Examples", new Examples(_))

   // class Examples(info: ProjectInfo) extends ParentProject(info)
   // {
   //    lazy val embedded = project("embedded", "Embedded Simulator Example", core)
   //    lazy val standalone = project("standalone", "Standalone Simulator Example", ui)
   // }
}
