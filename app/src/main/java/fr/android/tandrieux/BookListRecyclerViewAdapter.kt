package fr.android.tandrieux

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


import fr.android.tandrieux.BookListFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.item_view_book.view.*

class BookListRecyclerViewAdapter(
        private val mValues: List<Book>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<BookListRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Book
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.title.text = item.title
        holder.isbn.text = item.isbn
        holder.price.text = item.price + " €"

        Picasso.get().load(item.cover).into(holder.cover)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val title: TextView = mView.book_item_title
        val isbn: TextView = mView.book_item_isbn
        val price: TextView = mView.book_item_price
        val cover: ImageView = mView.book_item_cover


    }
}
