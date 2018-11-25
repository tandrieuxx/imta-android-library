package fr.android.tandrieux

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import android.content.Context
import android.view.Surface
import android.view.WindowManager


class LibraryActivity : AppCompatActivity(), BookListFragment.OnListFragmentInteractionListener {

    var openedBook : Book? = null

    override fun onListFragmentInteraction(book: Book?) {
        openedBook = book

        if (getScreenOrientation(this) == "LANDSCAPE") {

            val ft = supportFragmentManager.beginTransaction()
            book?.apply {
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.book_details_placeholder, BookDetailsFragment.newInstance(this))
            }
            ft.commit()

        } else {
            val ft = supportFragmentManager.beginTransaction()
            book?.apply {
                ft.addToBackStack(null)
                ft.replace(R.id.book_list_placeholder, BookDetailsFragment.newInstance(this))
            }
            ft.commit()
        }
    }

    private fun getScreenOrientation(context: Context): String {
        val screenOrientation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
        return when (screenOrientation) {
            Surface.ROTATION_0 -> "PORTRAIT"
            Surface.ROTATION_90 -> "LANDSCAPE"
            Surface.ROTATION_180 -> "PORTRAIT"
            else -> "LANDSCAPE"
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0 && getScreenOrientation(this) == "PORTRAIT") {
            supportFragmentManager.popBackStack()
            openedBook = null
        } else {
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.book_list_view)

        Timber.plant(Timber.DebugTree());

        val retrofit = Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(HenriPotierService::class.java)

        val books = service.getBookList()
        books.enqueue(object : Callback<Array<Book>> {
            override fun onFailure(call: Call<Array<Book>>, t: Throwable) {
                Timber.i(t)
            }

            override fun onResponse(call: Call<Array<Book>>,
                                    response: Response<Array<Book>>) {
                response.body()?.apply {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.book_list_placeholder, BookListFragment.newInstance(ArrayList(this.toList())))
                    ft.commit()
                }
            }
        })

    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("book", openedBook)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getParcelable<Book>("book")?.apply {
            onListFragmentInteraction(this)
        }
    }
}
