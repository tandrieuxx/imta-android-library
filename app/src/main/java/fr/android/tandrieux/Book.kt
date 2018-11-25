package fr.android.tandrieux

import android.os.Parcel
import android.os.Parcelable

data class Book(val isbn: String, val title: String, val price: String, val cover: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(isbn)
        dest?.writeString(title)
        dest?.writeString(price)
        dest?.writeString(cover)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Book?> {
        override fun createFromParcel(parcel: Parcel): Book? {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}