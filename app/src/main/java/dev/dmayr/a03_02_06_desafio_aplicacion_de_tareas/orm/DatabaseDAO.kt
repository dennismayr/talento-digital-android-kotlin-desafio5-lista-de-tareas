package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDAO {

  // @Query("SELECT * from lista_tareas ORDER BY tarea_fecha DESC")
  @Query("SELECT * from lista_tareas")
  fun getTodosItemsLista(): List<DatabaseEntity>

  @Insert
  fun insertItem(tarea: List<DatabaseEntity>)

  @Update
  fun updateItem(tarea: List<DatabaseEntity>)

  @Query("DELETE FROM lista_tareas")
  fun deleteAll()
}