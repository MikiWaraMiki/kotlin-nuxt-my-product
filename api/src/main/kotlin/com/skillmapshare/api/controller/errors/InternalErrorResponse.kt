package com.skillmapshare.api.controller.errors

import com.fasterxml.jackson.annotation.JsonProperty

data class InternalErrorResponse(
        @JsonProperty("status")
        val status : Number,
        @JsonProperty("error")
        val error : String?,
        @JsonProperty("traceback")
        val traceback : String?
)