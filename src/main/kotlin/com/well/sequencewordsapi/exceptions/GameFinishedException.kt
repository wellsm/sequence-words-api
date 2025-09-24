package com.well.sequencewordsapi.exceptions

class GameFinishedException (
    id: String
): RuntimeException("The game with code $id has already finished")