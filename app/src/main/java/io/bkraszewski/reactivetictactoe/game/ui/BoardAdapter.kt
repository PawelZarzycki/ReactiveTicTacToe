package io.bkraszewski.reactivetictactoe.game.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.bkraszewski.reactivetictactoe.R
import io.bkraszewski.reactivetictactoe.game.GameContract
import kotlinx.android.synthetic.main.item_board.view.*

class BoardAdapter(val items: List<String>, val context: Context) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>(){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BoardViewHolder?, position: Int) {
        holder?.textView?.text = items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_board, parent, false)
        return BoardViewHolder(view)
    }

    class BoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.itemBoardText
    }
}
