object MyCompileOptions {

  val optsV3_0: Seq[String] =
    Seq(
      "-source:3",
      "-deprecation",           // Emit warning and location for usages of deprecated APIs.
      "-encoding",
      "utf-8",                  // Specify character encoding used by source files.
      "-explain-types",         // Explain type errors in more detail.
      "-print-lines",
      "-feature",               // Emit warning and location for usages of features that should be imported explicitly.
      "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
      "-language:higherKinds",  // Allow higher-kinded types
      "-unchecked"              // Enable additional warnings where generated code depends on assumptions.
      // "-Xfatal-warnings"
    )

  val warningsV3_0: Seq[String] = Seq()

  val lintersV3_0: Seq[String] = Seq()

}
