package amedigital.challenge.backend

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "planets")
data class Planet(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null,
    @Column(unique = true) val name: String,
    val climate: String,
    val terrain: String,
    val filmAppearances: Int? = 0
)
