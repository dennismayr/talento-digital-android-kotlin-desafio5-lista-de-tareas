package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListaTareas::class], version = 2, exportSchema = true)
abstract class DatabaseTareas : RoomDatabase() {
  abstract fun databaseDAO(): DatabaseDAO

  companion object { // Singleton: clase instanciada sólo 1 vez (evita procesos duplicados)
    @Volatile
    private var INSTANCE: DatabaseTareas? = null

    fun getDatabase(context: Context): DatabaseTareas {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          DatabaseTareas::class.java,
          "todo_database"
        ).build()
        INSTANCE = instance
        return instance
      }
    }
  }
}