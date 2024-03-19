package amedigital.challenge.backend

import feign.FeignException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.cloud.openfeign.FeignClientBuilder
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.server.ResponseStatusException

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("/data/remove.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PlanetControllerTestWithOpenFeign(
    @LocalServerPort private val localServerPort: Int,
    @Autowired private val applicationContext: ApplicationContext
) {

    private val planetApi = FeignTestClientFactory
        .createClientApi(
            apiClass = PlanetApi::class.java,
            port = localServerPort,
            clientContext = applicationContext
        )

    @Nested
    inner class SearchByName {
        @Sql("/data/insert_one_planet.sql")
        @Test
        fun success() {
            val expectedPlanet = PlanetFactory.createExistingPlanet()
            planetApi.searchByName(expectedPlanet.name).also {
                assertEquals(expectedPlanet.name, it.name)
                assertEquals(expectedPlanet.id, it.id)
                assertEquals(expectedPlanet.climate, it.climate)
                assertEquals(expectedPlanet.terrain, it.terrain)
                assertEquals(expectedPlanet.filmAppearances, it.filmAppearances)
            }
        }
    }

    @Nested
    inner class SearchById {
        @Sql("/data/insert_one_planet.sql")
        @Test
        fun success() {
            val expectedPlanet = PlanetFactory.createExistingPlanet()
            planetApi.searchById(1).also {
                assertEquals(expectedPlanet.name, it.name)
                assertEquals(expectedPlanet.id, it.id)
                assertEquals(expectedPlanet.climate, it.climate)
                assertEquals(expectedPlanet.terrain, it.terrain)
                assertEquals(expectedPlanet.filmAppearances, it.filmAppearances)
            }
        }
    }

    @Nested
    inner class ListAll {
        @Sql("/data/insert_multiple_planets.sql")
        @Test
        fun success() {
            planetApi.listAll().also {
                assertTrue(it.isNotEmpty())
                assertEquals(3, it.size)
            }
        }
    }

    @Nested
    inner class RemoveById {
        @Test
        @Sql("/data/insert_one_planet.sql")
        fun `Given id Then delete successfully`() {
            val expectedPlanet = PlanetFactory.createExistingPlanet()
            planetApi.searchById(1).also {
                assertEquals(expectedPlanet.name, it.name)
                assertEquals(expectedPlanet.id, it.id)
                assertEquals(expectedPlanet.climate, it.climate)
                assertEquals(expectedPlanet.terrain, it.terrain)
                assertEquals(expectedPlanet.filmAppearances, it.filmAppearances)
            }

            assertDoesNotThrow { planetApi.removeById(1) }

            assertThrows<FeignException.NotFound> { planetApi.searchById(1) }
        }
    }

    object PlanetFactory {
        fun createNewPlanetRequest(): PlanetRequest =
            PlanetRequest(
                name = "Geonosis",
                climate = "temperate, arid",
                terrain = "rock, desert, mountain, barren"
            )

        fun createExistingPlanet(): Planet =
            Planet(
                id = 1,
                name = "Polis Massa",
                climate = "artificial temperate",
                terrain = "airless asteroid"
            )

        fun createExistingPlanetRequest(): PlanetRequest =
            PlanetRequest(
                name = "Polis Massa",
                climate = "artificial temperate",
                terrain = "airless asteroid"
            )
    }

    object FeignTestClientFactory {
        fun <T> createClientApi(apiClass: Class<T>, port: Int, clientContext: ApplicationContext): T =
            FeignClientBuilder(clientContext)
                .forType(apiClass, apiClass.canonicalName)
                .url("http://localhost:$port/api/planet")
                .build()
    }
}