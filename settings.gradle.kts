rootProject.name = "plutomc"

/*
include("framework-shared")
include("framework-bukkit")
include("framework-velocity")
include("common-whitelistmanager")
include("module-ironelevator")
include("module-cactusrotator")
include("module-voidtotem")
include("module-waxednotwaxed")
include("module-whitelist")
include("proxy-bootstrap")
include("survival-bootstrap")
include("common-profile")
include("module-economy")
*/

include("dutil")

project(":dutil").projectDir = File("./library-dutil")