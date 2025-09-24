package com.well.sequencewordsapi.exceptions

class RoomFullException(
    id: String
): RuntimeException("The room with code $id is full")