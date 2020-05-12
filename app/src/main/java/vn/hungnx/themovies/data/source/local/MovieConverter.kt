package vn.hungnx.themovies.data.source.local

import androidx.room.TypeConverter

class MovieConverter {
    @TypeConverter
    fun fromListIntToString(ids:List<Int>):String{
        ids.let {
            return it.joinToString (separator = " "){value->
                value.toString()
            }
        }
    }

    @TypeConverter
    fun fromStringToListInt(data:String):List<Int>{
        data.let {
            return it.split(" ").map { str->
                Integer.parseInt(str)
            }.toList()
        }
    }
}