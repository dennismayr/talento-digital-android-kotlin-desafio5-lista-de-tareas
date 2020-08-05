package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseDAO
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseEntity
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseTareas
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task.OnItemClickListener
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task.TaskListAdapter
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task.TaskUIDataHolder
import kotlinx.android.synthetic.main.add_task.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), OnItemClickListener {

  private lateinit var dataBase: DatabaseTareas
  private lateinit var list: RecyclerView
  private lateinit var adapter: TaskListAdapter
  private lateinit var dao: DatabaseDAO
  // private lateinit var addButton: FloatingActionButton

  private val job = Job()
  private val coroutineContextElement = CoroutineScope(Dispatchers.Main + job)

  override fun onItemClick(taskItem: TaskUIDataHolder) {
    val dialogView: View = layoutInflater.inflate(R.layout.add_task, null)
    val taskText: TextInputEditText = dialogView.task_input
    taskText.setText(taskItem.tarea_nombre)
    val dialogBuilder: AlertDialog.Builder = AlertDialog
      .Builder(this)
      .setTitle("Editar una Tarea")
      .setView(dialogView)
      .setNegativeButton("Cerrar") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
      .setPositiveButton("Editar") { _: DialogInterface, _: Int ->
        //generar código para editar/actualizar la tarea
        if (taskText.text!!.isNotEmpty()) {
          coroutineContextElement.launch {
            withContext(Dispatchers.IO) {
              //Completar para agregar una tarea a la base de datos
              dao.updateItem(updateEntity(taskText.text.toString()))
              val newItems: MutableList<TaskUIDataHolder> =
                createEntityListFromDatabase(dao.updateItem())

              runOnUiThread {
                adapter.updateData(newItems)
                dialog.dismiss()
              }
              //
            }
          }
        }
        //
      }
    dialogBuilder.create().show()
  }

  // crear las variables para utilizar la base de datos

  //
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)
    setUpViews()
    //inicializar lo necesario para usar la base de datos
    dataBase = AplicacionListaTareas.databaseTareas!!
    dao = dataBase.getDatabaseDAO()
    addTask()
    //
  }

  override fun onResume() {
    super.onResume()
    // Cambiado AsyncTask por Coroutine
    coroutineContextElement.launch {
      withContext(Dispatchers.IO) {
        val newItems: MutableList<TaskUIDataHolder> = mutableListOf()
        runOnUiThread {
          adapter.updateData(newItems)
        }
      }
    }
    //
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    super.onCreateOptionsMenu(menu)
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    super.onOptionsItemSelected(item)
    when (item.itemId) {
      R.id.add -> addTask()
      R.id.remove_all -> removeAll()
    }
    return true
  }

  private fun setUpViews() {

    list = findViewById(R.id.task_list)
    list.layoutManager = LinearLayoutManager(this as Context)
    adapter = TaskListAdapter(mutableListOf(), this, this)
    list.adapter = adapter
  }

  private fun updateEntity(tarea: String): List<DatabaseEntity> {
    val listEntities: MutableList<DatabaseEntity> = mutableListOf()

    listEntities.add(
      DatabaseEntity(
        tarea_nombre = tarea
      )
    )

    return listEntities
  }
  //

  private fun addTask() {
    val dialogView: View = layoutInflater.inflate(R.layout.add_task, null)
    val taskText: TextView = dialogView.findViewById(R.id.task_input)
    val dialogBuilder: AlertDialog.Builder = AlertDialog
      .Builder(this)
      .setTitle("Agrega una Tarea")
      .setView(dialogView)
      .setNegativeButton("Cerrar") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
      .setPositiveButton("Agregar") { dialog: DialogInterface, _: Int ->
        // Probando coroutines
        if (taskText.text.isNotEmpty()) {
          coroutineContextElement.launch {
            withContext(Dispatchers.IO) {
              //Completar para agregar una tarea a la base de datos
              dao.insertItem(createEntity(taskText.text.toString()))
              val newItems: MutableList<TaskUIDataHolder> =
                createEntityListFromDatabase(dao.getTodosItemsLista())

              runOnUiThread {
                adapter.updateData(newItems)
                dialog.dismiss()
              }
              //
            }
          }
        }
      }
    dialogBuilder.create().show()
  }

  private fun removeAll() {
    val dialog: AlertDialog.Builder = AlertDialog
      .Builder(this)
      .setTitle("Borrar Todo")
      .setMessage("¿Desea Borrar todas las tareas?")
      .setNegativeButton("Cerrar") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
      .setPositiveButton("Aceptar") { dialog: DialogInterface, _: Int ->
        //Código para eliminar las tareas de la base de datos
        coroutineContextElement.launch {
          withContext(Dispatchers.IO) {
            //Completar para agregar una tarea a la base de datos
            dao.deleteAll()
            val newItems: MutableList<TaskUIDataHolder> = deleteAllEntities(dao.deleteAll())
          }
        }
        dao.deleteAll()

        runOnUiThread {
          adapter.updateData(emptyList())
          dialog.dismiss()
        }
      }
    dialog.show()
  }

  private fun deleteEntity(id: Long, tarea: String): List<DatabaseEntity> {
    //completar este método para retornar un Entity
    val deleteEntities: MutableList<DatabaseEntity> = mutableListOf()
    // Probando Coroutine
    coroutineContextElement.launch {
      withContext(Dispatchers.IO) {
        deleteEntities.remove(
          DatabaseEntity(
            tarea_id = id,
            tarea_nombre = tarea
          )
        )
      } //
    } //
    return deleteEntities
    //
  }

  private fun deleteAllEntitiesInDatabase(datos: List<DatabaseEntity>): MutableList<TaskUIDataHolder> {
    val dataList: MutableList<TaskUIDataHolder> = mutableListOf()
    //completar este método para retornar un Entity
    val deleteAllEntities: MutableList<DatabaseEntity> = mutableListOf()
    // Probando Coroutine
    coroutineContextElement.launch {
      withContext(Dispatchers.IO) {
        deleteAllEntities.removeAll(listOf(DatabaseEntity(tarea_id = id, tarea_nombre = tarea)))
      } //
    } //
    return deleteAllEntities
    //
  }

  private fun createEntity(tarea: String): List<DatabaseEntity> {
    //completar este método para retornar un Entity
    val tareaEntities: MutableList<DatabaseEntity> = mutableListOf()
    // Probando Coroutine
    coroutineContextElement.launch {
      withContext(Dispatchers.IO) {

        tareaEntities.add(
          DatabaseEntity(
            tarea_nombre = tarea
          )
        )
      } //
    } //
    return tareaEntities
    //
  }

  private fun createEntityListFromDatabase(datos: List<DatabaseEntity>): MutableList<TaskUIDataHolder> {
    val dataList: MutableList<TaskUIDataHolder> = mutableListOf()
    //completar método para crear una lista de datos compatibles con el adaptador, mire lo que
    //retorna el método. Este método debe recibir un parámetro también.
    // Probando Coroutines
    coroutineContextElement.launch {
      withContext(Dispatchers.IO) {
        for (dato: DatabaseEntity in datos) {
          if (datos.isNotEmpty()) {
            val dataView = TaskUIDataHolder(
              dato.tarea_id,
              dato.tarea_nombre
            )
            dataList.add(dataView)
            //
          }
        }
      }
    }
    return dataList
  }
}
