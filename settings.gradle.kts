rootProject.name = "tic-tac-toe"
// This is an empty umbrella build including all the component builds.
// This build is not necessarily needed. The component builds work independently.

includeBuild("build-logic")
include("game")