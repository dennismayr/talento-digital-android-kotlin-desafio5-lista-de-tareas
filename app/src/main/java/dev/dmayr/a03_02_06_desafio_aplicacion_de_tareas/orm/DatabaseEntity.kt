package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lista_tareas")
data class ListaTareas(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "titulo") val title: String,
  @ColumnInfo(name = "descripcion") val details: String,
  @ColumnInfo(name = "completado") var done: Boolean
)