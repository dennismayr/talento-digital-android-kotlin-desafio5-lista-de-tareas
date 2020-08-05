package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseTareas

class AplicacionListaTareas : Application() {
  companion object {
    var databaseTareas: DatabaseTareas? = null
  }

  val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // database.execSQL("CREATE TABLE `lista_tareas` (`id` INTEGER, `tarea_nombre` TEXT, `tarea_descripcion` TEXT, `tarea_completado` BOOLEAN, `tarea_fecha` DATE" + "PRIMARY KEY(`id`))")
      database.execSQL("CREATE TABLE `lista_tareas` (`id` INTEGER, `tarea_nombre` TEXT" + "PRIMARY KEY(`id`))")
    }
  }
  val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // database.execSQL("ALTER TABLE lista_tareas ADD COLUMN fecha_item TEXT")
      database.execSQL("ALTER TABLE lista_tareas ADD COLUMN tarea_fecha TEXT")
    }
  }

  override fun onCreate() {
    super.onCreate()
    databaseTareas = Room.databaseBuilder(
      applicationContext, DatabaseTareas::
      class.java, "lista-tareas"
    ).addMigrations(
      MIGRATION_1_2
      , MIGRATION_2_3
    ).build()
  }
}