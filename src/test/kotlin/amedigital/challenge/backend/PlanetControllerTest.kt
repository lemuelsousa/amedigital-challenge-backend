package amedigital.challenge.backend

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql("/data/remove.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PlanetControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var mapper: ObjectMapper

    val baseUri = "/api/planet"

    @Nested
    inner class AddPlanet {

        @Test
        @DirtiesContext
        fun `Given planet data Then Add Planet Successfully`() {
            val request = createNewPlanetRequest()

            postPlanet(request)
                .andExpect {
                    status { isCreated() }
                }
        }

        @Test
        fun `Given invalid format data Then Return BadRequest`() {
            val request = "invalid-data"

            postPlanet(request)
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Sql("/data/insert_one_planet.sql")
        @Test
        fun `Given existing planet Then Return BadRequest`() {
            val existingPlanet = createExistingPlanetRequest()

            postPlanet(existingPlanet)
                .andExpect {
                    status { isBadRequest() }
                }
        }

        private fun postPlanet(request: Any) =
            mockMvc.post(baseUri) {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
                content = mapper.writeValueAsString(request)
            }

        private fun createNewPlanetRequest(): PlanetRequest =
            PlanetRequest(
                name = "Geonosis",
                climate = "temperate, arid",
                terrain = "rock, desert, mountain, barren"
            )

        private fun createExistingPlanetRequest(): PlanetRequest =
            PlanetRequest(
                name = "Polis Massa",
                climate = "artificial temperate",
                terrain = "airless asteroid"
            )
    }

    @Nested
    inner class SearchById {
        @Test
        fun `Given non-existent id Then Return NotFound`() {
            val id = 999999

            getPlanetById(id).andExpect {
                status { isNotFound() }
            }
        }

        @Test
        fun `Given invalid format id Then Return BadRequest`() {
            val id = "inv+alid-id"

            getPlanetById(id).andExpect {
                status { isBadRequest() }
            }
        }

        @Sql("/data/insert_one_planet.sql")
        @Test
        fun `Given id Then Return a Planet`() {
            val id = 1
            getPlanetById(id).andExpect {
                status { isOk() }
                content {
                    jsonPath("$.length()").value(1)
                    jsonPath("$.id").exists()
                    jsonPath("$.name").value("Polis Massa")
                    jsonPath("$.climate").value("artificial temperate")
                    jsonPath("$.terrain").value("airless asteroid")
                    jsonPath("$.filmAppearances").value(0)
                }
            }
        }

        private fun getPlanetById(id: Any) =
            mockMvc.get("$baseUri/$id") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
            }
    }

    @Nested
    inner class SearchByName {

        @Test
        fun `Given invalid name Then Return NotFound`() {
            val planetName = "some planet"

            getPlanetByName(planetName)
                .andExpect {
                    status { isNotFound() }
                }
        }

        @Test
        @Sql("/data/insert_one_planet.sql")
        fun `Given name Then Return a Planet successfully`() {
            val planetName = "Polis Massa"

            getPlanetByName("Polis Massa")
                .andExpect {
                    status { isOk() }
                    content {
                        jsonPath("$.length()").value(1)
                        jsonPath("$.id").exists()
                        jsonPath("$.name").value("Polis Massa")
                        jsonPath("$.climate").value("artificial temperate")
                        jsonPath("$.terrain").value("airless asteroid")
                        jsonPath("$.filmAppearances").value(0)
                    }
                }
        }

        private fun getPlanetByName(planetName: Any) =
            mockMvc.get("$baseUri/search") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
                param("name", mapper.writeValueAsString(planetName))
            }
    }

    @Nested
    inner class ListAll {
        @Test
        fun `ListAll successfully`() {
            mockMvc.get("$baseUri/list") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
            }
        }
    }
}