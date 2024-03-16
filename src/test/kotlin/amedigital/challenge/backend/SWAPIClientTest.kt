package amedigital.challenge.backend

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SWAPIClientTest(
    @Autowired private val swapiClient: SWAPIClient
) {
    @Test
    fun getPlanets(){
        assertThat(swapiClient.getPlanets().size).isEqualTo(60)
    }

}