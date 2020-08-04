package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDAO {

  @Query("SELECT * from lista_tareas order by completado desc")
  fun getTodosItemsLista(): List<ListaTareas>

  @Insert
  fun insertItem(listaTareas: ListaTareas) // por hacer: suspend fun() Coroutine en lugar de AsyncTask

  @Update
  fun updateItem(listaTareas: ListaTareas) // por hacer: suspend fun()Coroutine en lugar de AsyncTask

  @Query("DELETE FROM lista_tareas")
  fun deleteAll()
}