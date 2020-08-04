package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class DatabaseRepository(private val databaseDAO: DatabaseDAO) {

  val item_lista: LiveData<List<ListaTareas>> = databaseDAO.getTodosItemsLista()

  @WorkerThread
  suspend fun insert(todo: ListaTareas) {
    databaseDAO.insertItem(todo)
  }

  @WorkerThread
  suspend fun update(todo: ListaTareas) {
    databaseDAO.updateItem(todo)
  }
}