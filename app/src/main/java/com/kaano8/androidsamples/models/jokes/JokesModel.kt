package com.kaano8.androidsamples.models.jokes

data class JokesModel(
    val category: String,
    val type: String,
    val setup: String="thats a bad setup",
    val delivery: String = "really bad man",
    val flags: Flags,
    val id: Int,
    val error: Boolean
)


data class Flags(
    val nsfw: Boolean,
    val religious: Boolean,
    val political: Boolean,
    val racist: Boolean,
    val sexist: Boolean
)