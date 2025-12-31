package compose.project.listingstowordconverter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform