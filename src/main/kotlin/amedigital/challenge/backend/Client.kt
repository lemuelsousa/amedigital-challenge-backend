package amedigital.challenge.backend

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    value = "SWAPI",
    url = "https://swapi.info/api/"
)
interface SWAPIClient {

    @GetMapping("/planets/")
    fun getPlanets(): List<ClientPlanetResponse>

}

data class ClientPlanetResponse(
    val name: String,
    val climate: String,
    val terrain: String,
    val films: List<String>
)