package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseEntity::class], version = 2, exportSchema = true)
@TypeConverters(DatabaseConverter::class)
abstract class DatabaseTareas : RoomDatabase() {
  abstract fun getDatabaseDAO(): DatabaseDAO
}