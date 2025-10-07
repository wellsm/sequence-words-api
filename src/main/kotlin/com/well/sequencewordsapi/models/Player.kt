package com.well.sequencewordsapi.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(
    name = "players",
    indexes = [
        Index(columnList = "room_id, seat", unique = true, name = "IDX_ROOM_SEAT"),
    ],
)
class Player(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    var user: User? = null,
    @JoinColumn(name = "room_id", nullable = false)
    @ManyToOne(targetEntity = Room::class, fetch = FetchType.LAZY)
    var room: Room? = null,
    @Column(name = "room_id", insertable = false, updatable = false)
    var roomId: String? = null,
    @OneToMany(mappedBy = "player", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var words: MutableList<Word> = mutableListOf(),
    @Column(name = "name", nullable = false)
    var name: String = "",
    @Column(name = "is_owner", nullable = false)
    var isOwner: Boolean = false,
    @Column(name = "seat", nullable = false)
    var seat: Int = 0,
    @Column(name = "is_ready", nullable = false)
    var isReady: Boolean = false,
    @Transient
    var hash: String? = null,
    @CreationTimestamp
    var createdAt: Instant = Instant.now(),
    @UpdateTimestamp
    var updatedAt: Instant = Instant.now(),
)