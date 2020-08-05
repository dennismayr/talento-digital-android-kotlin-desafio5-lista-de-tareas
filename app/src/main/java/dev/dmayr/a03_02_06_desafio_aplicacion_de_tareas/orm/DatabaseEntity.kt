package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lista_tareas")
data class DatabaseEntity(
  @ColumnInfo(name = "id")
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  @ColumnInfo(name = "tarea")
  val tarea: String
)