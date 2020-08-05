package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lista_tareas")
data class DatabaseEntity(
  @ColumnInfo(name = "id")
  @PrimaryKey(autoGenerate = true)
  val tarea_id: Long = 0,
  @ColumnInfo(name = "tarea")
  val tarea_nombre: String
  // ,
  // @ColumnInfo(name = "descripcion")
  // val tarea_descripcion: String,
  // @ColumnInfo(name="completado")
  // val tarea_completado: Boolean,
  // @ColumnInfo(name = "fecha")
  // val tarea_fecha: Date?
)