rootProject.name = "plutomc"

include("common-whitelistmanager")
include("framework-shared")
include("framework-bukkit")
include("framework-velocity")
include("module-ironelevator")
include("module-cactusrotator")
include("module-voidtotem")
include("module-waxednotwaxed")
include("module-whitelist")
include("proxy-bootstrap")
include("survival-bootstrap")

include("dutil")

project(":dutil").projectDir = File("./library-dutil")
include("common-profile")
include("module-economy")

