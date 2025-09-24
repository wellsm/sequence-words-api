package com.well.sequencewordsapi.models

import com.well.sequencewordsapi.enums.RoomState
import com.well.sequencewordsapi.http.responses.PlayerResponse
import com.well.sequencewordsapi.http.responses.RoomResponse
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "rooms")
class Room(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: String = "",
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    var state: RoomState = RoomState.CREATED,
    @Column(name = "how_many_words", nullable = false)
    var howManyWords: Int = 5,
    @Column(name = "turn", nullable = false)
    var turn: Int = 0,
    @CreationTimestamp
    var createdAt: Instant = Instant.now(),
    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var players: MutableList<Player> = mutableListOf(),
    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var words: MutableList<Word> = mutableListOf(),
)
