package com.giyas.newsfeed.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article (
                     @ColumnInfo(name = "title")
                    var title: String? = "1",

                     @ColumnInfo(name = "author")
                     var author: String? = null,

                     @ColumnInfo(name = "description")
                    var description: String? = null,

                     @ColumnInfo(name = "source")
                    var source: String? = null,

                     @ColumnInfo(name = "url")
                    var url: String? = null,

                     @ColumnInfo(name = "url_to_image")
                    var urlToImage: String? = null,

                     @ColumnInfo(name = "published_at")
                    var publishedAt: String? = null,

                    @PrimaryKey(autoGenerate = true)  val articleId: Long = 0L): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(description)
        parcel.writeString(source)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeLong(articleId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}