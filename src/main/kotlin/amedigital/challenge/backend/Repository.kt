package amedigital.challenge.backend

import org.springframework.data.jpa.repository.JpaRepository

interface PlanetRepository : JpaRepository<Planet, Long> {
    fun findByName(name: String): Planet?
}