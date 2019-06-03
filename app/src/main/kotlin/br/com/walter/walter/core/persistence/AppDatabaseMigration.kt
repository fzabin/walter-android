package br.com.walter.walter.core.persistence

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.walter.walter.features.addtransaction.data.CATEGORY_INITIAL_SETUP

fun getDatabaseMigrations(): Array<Migration> =
    arrayOf(
        MigrationFrom1To2
    )

object MigrationFrom1To2 : Migration(DATABASE_VERSION_1, DATABASE_VERSION_2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        logMigration()

        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `category` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`description` TEXT NOT NULL, " +
                    "`transaction_type_id` INTEGER NOT NULL, " +
                    "FOREIGN KEY(`transaction_type_id`) REFERENCES `transaction_type`(`id`) ON DELETE CASCADE);"
        )

        database.execSQL(CATEGORY_INITIAL_SETUP)
    }
}

// region Logcat
private fun Migration.tag(): String = javaClass.simpleName

private fun Migration.logMigration() {
    Log.d(tag(), "Running migration ${tag()}")
}

private fun Migration.logLegacyError() {
    Log.d(tag(), "Legacy table not found: no action required")
}

private fun Migration.logLegacySuccess() {
    Log.d(tag(), "Legacy table found: data migrated successfully")
}
// endregion