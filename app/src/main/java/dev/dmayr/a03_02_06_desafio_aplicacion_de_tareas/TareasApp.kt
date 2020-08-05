package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.TareasDatabase

class TareasApp : Application() {
  companion object {
    var tareasDatabase: TareasDatabase? = null
  }

  val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
      database.execSQL("ALTER TABLE lista_tareas" + "ADD COLUMN created_at INTEGER NOT NULL")
    }
  }

  override fun onCreate() {
    super.onCreate()
    tareasDatabase = Room
      .databaseBuilder(
        this, TareasDatabase::class.java,
        "lista-tareas-db"
      )
      .build()
  }
}