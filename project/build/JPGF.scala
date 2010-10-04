import sbt._


trait AndroidDefaults {
  def androidPlatformName = "android-4"
}

class JPGFProject(info: ProjectInfo) extends ParentProject(info)
{
  override def shouldCheckOutputDirectories = false
  override def updateAction = task { None }

  lazy val library = project(info.projectPath, "JPGF library", new LibraryProject(_))

  // Examples
  lazy val foodDroid =
    project("examples" / "FoodsDroid",     // Project path
            "FoodsDroid",                  // Project name
            new AndroidApp(_),             // Project class
            library)                       // Dependencies

  lazy val multilingualPhraseDroid =
    project("examples" / "PhraseDroid",    // Project path
            "Multilingual PhraseDroid",                 // Project name
            new AndroidApp(_),             // Project class
            library)                       // Dependencies

  lazy val phrasedroid_eng2fre =
    project("examples" / "PhraseDroid-eng2fre",
            "PhraseDroid English to French",
            new AndroidApp(_),
            library)

  lazy val fridge =
    project("examples" / "Fridge",
            "Fridge",
            new AndroidApp(_),
            library)

  class AndroidApp(info: ProjectInfo) extends AndroidProject(info)
  with AndroidDefaults
  
  class LibraryProject(info: ProjectInfo) extends ProguardProject(info) {
    //proguard
    override def proguardOptions = List(
      "-keep class org.grammaticalframework.** { *; }",
      "-dontoptimize",
      "-dontobfuscate",
      proguardKeepLimitedSerializability,
      //proguardKeepAllScala,
      "-keep interface scala.ScalaObject"
    )
    override def proguardInJars = Path.fromFile(scalaLibraryJar) +++ super.proguardInJars
    
  }
}

