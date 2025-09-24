package com.well.sequencewordsapi.repositories

import com.well.sequencewordsapi.models.Word
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WordRepository: JpaRepository<Word, Long> {
}