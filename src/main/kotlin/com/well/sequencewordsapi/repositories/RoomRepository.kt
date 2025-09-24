package com.well.sequencewordsapi.repositories

import com.well.sequencewordsapi.models.Room
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository: JpaRepository<Room, String> {
}