package br.com.walter.walter.core.persistence

import android.util.Log
import androidx.room.migration.Migration

fun getDatabaseMigrations(): Array<Migration> =
    arrayOf()

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