package amedigital.challenge.backend

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

interface PlanetApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody request: PlanetRequest)

    @GetMapping("/{id}")
    fun searchById(@PathVariable id: Long): PlanetResponse

    @GetMapping("/list")
    fun listAll(): List<PlanetResponse>

    @GetMapping("/search")
    fun searchByName(@RequestParam name: String): PlanetResponse

    @DeleteMapping("{id}")
    fun removeById(@PathVariable id: Long)
}

@RestController
@RequestMapping("/api/planet")
class PlanetController(
    private val planetService: PlanetService
) : PlanetApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody request: PlanetRequest) =
        planetService.add(request.toModel())


    @GetMapping("/{id}")
    override fun searchById(@PathVariable id: Long): PlanetResponse =
        planetService.searchById(id)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Planet not found")

    @GetMapping("/list")
    override fun listAll(): List<PlanetResponse> =
        planetService.listAll().map { it.toResponse() }

    @GetMapping("/search")
    override fun searchByName(@RequestParam name: String): PlanetResponse =
        planetService.searchByName(name)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Planet not found")

    @DeleteMapping("{id}")
    override fun removeById(@PathVariable id: Long) =
        planetService.remove(id)
}

private fun PlanetRequest.toModel(): Planet =
    Planet(
        name = this.name,
        climate = this.climate,
        terrain = this.terrain
    )

private fun Planet.toResponse(): PlanetResponse =
    PlanetResponse(
        id = this.id,
        name = this.name,
        climate = this.climate,
        terrain = this.terrain,
        filmAppearances = this.filmAppearances
    )