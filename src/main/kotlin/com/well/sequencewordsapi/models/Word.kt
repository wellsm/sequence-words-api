package com.well.sequencewordsapi.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(
    name = "words",
    indexes = [
        Index(columnList = "player_id, index", unique = true, name = "IDX_PLAYER_INDEX"),
        Index(columnList = "room_id, player_id", name = "IDX_ROOM_PLAYER")
    ]
)
class Word(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @JoinColumn(name = "room_id", nullable = false)
    @ManyToOne(targetEntity = Room::class, fetch = FetchType.LAZY)
    var room: Room,
    @JoinColumn(name = "player_id", nullable = false)
    @ManyToOne(targetEntity = Player::class, fetch = FetchType.LAZY)
    var player: Player,
    @Column(name = "index", nullable = false)
    var index: Int = 0,
    @Column(name = "value", nullable = false)
    var value: String,
    @Column(name = "revealed", nullable = false)
    var revealed: Int = 1,
    @CreationTimestamp
    var createdAt: Instant = Instant.now(),
)