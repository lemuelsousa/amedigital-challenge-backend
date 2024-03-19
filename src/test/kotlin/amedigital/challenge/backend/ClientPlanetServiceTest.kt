package amedigital.challenge.backend

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ClientPlanetServiceTest(
    @Autowired private val clientPlanetService: ClientPlanetService
) {

    @Test
    fun getPlanetByNameSuccessfully() {
        assertNotNull(clientPlanetService.getPlanetByName("Polis Massa")!!.also {
            assertEquals("Polis Massa", it.name)
            assertEquals("artificial temperate", it.climate.trim())
            assertEquals("airless asteroid", it.terrain)
            assertEquals(1, it.films.size)
        })
    }

}