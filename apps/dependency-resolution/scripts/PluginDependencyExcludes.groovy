includeTargets << grailsScript("_GrailsInit")

target(main: "The description of the script goes here!") {

    try {
        println "http-builder present: ${classLoader.loadClass("groovyx.net.http.HTTPBuilder").name}"
        exit 1
    } catch (e) {
        println "http-builder not present"
    }
}

setDefaultTarget(main)
