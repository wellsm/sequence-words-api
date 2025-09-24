package com.well.sequencewordsapi.exceptions

class InvalidWordCountException (
    howManyWords: Int
): RuntimeException("The number of words selected must be $howManyWords")