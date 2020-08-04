package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDAO {

  @Query("SELECT * from lista_tareas order by completado desc")
  fun getTodosItemsLista(): LiveData<List<ListaTareas>>

  @Insert
  suspend fun insertItem(listaTareas: ListaTareas) // Coroutine en lugar de AsyncTask

  @Update
  suspend fun updateItem(listaTareas: ListaTareas) // Coroutine en lugar de AsyncTask

  @Query("DELETE FROM lista_tareas")
  fun deleteAllItems()
}