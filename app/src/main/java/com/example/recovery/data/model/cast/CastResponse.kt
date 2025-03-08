package com.example.recovery.data.model.cast

data class CastResponse(
    val adult: Boolean?,
    val cast_id: Int?,
    val character: String?,
    val credit_id: String?,
    val gender: Int?,
    val id: Int?,
    val known_for_department: String?,
    val name: String?,
    val order: Int?,
    val original_name: String?,
    val popularity: Double?,
    val profile_path: String?
)

fun CastResponse.toCast(): Cast {
    return Cast(
        adult?:false,
        cast_id?:0,
        character ?: "", // Default to empty string if null
        credit_id ?: "", // Default to empty string if null
        gender?:0,
        id?:0,
        known_for_department ?: "", // Default to empty string if null
        name ?: "", // Default to empty string if null
        order?:0,
        original_name ?: "", // Default to empty string if null
        popularity?:0.0,
        profile_path ?: "" // Default to empty string if null
    )
}
