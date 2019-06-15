package br.com.walter.walter.core.persistence

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.walter.walter.features.categories.data.CATEGORY_INITIAL_SETUP
import br.com.walter.walter.features.transactiontypes.data.TRANSACTION_TYPE_INITIAL_SETUP
import java.util.concurrent.Executors

object AppDatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        try {
            Executors.newSingleThreadExecutor().execute {
                db.execSQL(TRANSACTION_TYPE_INITIAL_SETUP)
                db.execSQL(CATEGORY_INITIAL_SETUP)
            }
        } catch (e: Exception) {
            Log.d(AppDatabaseCallback::class.java.simpleName, e.message)
        }
    }
}