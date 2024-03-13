package amedigital.challenge.backend

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrNull

@Service
class PlanetService(
    private val planetRepository: PlanetRepository,
    private val clientPlanetService: ClientPlanetService
) {

    fun add(planet: Planet) {
        searchByName(planet.name)?.let {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Planet always exist.")
        }
            ?: clientPlanetService.getPlanetByName(planet.name).let {
                planetRepository.save(
                    planet.copy(
                        filmAppearances = it?.films?.size
                    )
                )
            }
    }


    fun searchById(id: Long): Planet? =
        planetRepository.findById(id)
            .getOrNull()

    fun listAll(): List<Planet> =
        planetRepository.findAll()

    fun remove(id: Long) =
        planetRepository.deleteById(id)

    fun searchByName(name: String): Planet? =
        planetRepository.findByName(name)
}

@Service
class ClientPlanetService(
    private val swapiClient: SWAPIClient
) {

    fun getPlanetByName(name: String): ClientPlanetResponse? =
        swapiClient.getPlanets().find { it.name == name }
}