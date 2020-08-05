package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(DatabaseEntity::class)], version = 2, exportSchema = true)
// @TypeConverters(RecommendationsConverter::class)
abstract class TareasDatabase : RoomDatabase() {
  abstract fun databaseDAO(): DatabaseDAO
}