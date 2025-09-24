package com.well.sequencewordsapi.exceptions

class RoomNotFoundException(
    id: String
): RuntimeException("The room with code $id was not found")