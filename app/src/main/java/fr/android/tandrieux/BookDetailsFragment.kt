package fr.android.tandrieux


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_book.view.*

class BookDetailsFragment : Fragment() {
    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            book = it.getParcelable("book")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_book, container, false)

        view.book_detail_title.text = book?.title
        view.book_detail_isbn.text = book?.isbn
        view.book_detail_price.text = book?.price + " €"

        book?.apply { Picasso.get().load(this.cover).into(view.book_detail_cover) }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(book: Book) =
                BookDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("book" , book)
                    }
                }
    }
}
