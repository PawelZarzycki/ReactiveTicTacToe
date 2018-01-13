package io.bkraszewski.reactivetictactoe.model

data class Game(val id: String, val hostName : String, val playerName: String?, val startPlayerIndex: Int, val board: MutableMap<Int, String> )
