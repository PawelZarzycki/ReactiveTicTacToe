package io.bkraszewski.reactivetictactoe.model

import java.io.Serializable

data class Game(val id: String, val hostName : String, val playerName: String?, val startPlayerIndex: Int, val board: MutableMap<Int, String> ) : Serializable
