package com.skillmapshare.api.controller.errors

import com.fasterxml.jackson.annotation.JsonProperty

data class UserErrorResponse (
        @JsonProperty("status")
        val status : Number,
        @JsonProperty("error")
        val error : String?,
        @JsonProperty("stacktrace")
        val stacktrace : String?
)