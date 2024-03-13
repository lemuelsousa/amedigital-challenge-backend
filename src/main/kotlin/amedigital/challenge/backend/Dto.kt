package amedigital.challenge.backend

data class PlanetRequest(
    val name: String,
    val climate: String,
    val terrain: String
)

data class PlanetResponse(
    val id: Long?,
    val name: String,
    val climate: String,
    val terrain: String,
    val filmAppearances: Int? = 0
)
