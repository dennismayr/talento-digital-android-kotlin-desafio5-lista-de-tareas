package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas

import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseDAO
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseEntity
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.TareasDatabase
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task.OnItemClickListener
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task.TaskListAdapter
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task.TaskUIDataHolder
import kotlinx.android.synthetic.main.add_task.view.*
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity(), OnItemClickListener {

  private lateinit var list: RecyclerView
  private lateinit var adapter: TaskListAdapter

  // crear las variables para utilizar la base de datos
  private lateinit var dataBase: TareasDatabase
  private lateinit var dao: DatabaseDAO
  //

  private val job: CompletableJob = Job()
  private val coroutineContextElement: CoroutineScope = CoroutineScope(Dispatchers.Main + job)

  override fun onItemClick(taskItem: TaskUIDataHolder) {
    val dialogView: View = layoutInflater.inflate(R.layout.add_task, null)
    val taskText: TextInputEditText = dialogView.task_input
    taskText.setText(taskItem.text)
    val dialogBuilder: AlertDialog.Builder = AlertDialog
      .Builder(this)
      .setTitle("Editar una Tarea")
      .setView(dialogView)
      .setNegativeButton("Cerrar") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
      .setPositiveButton("Editar") { _: DialogInterface, _: Int ->
        //generar código para editar/actualizar la tarea
        if (taskText.text!!.isNotEmpty()) {
          // dao.agregarTarea(createEntity(taskText.text.toString()))
          AsyncTask.execute {
            dao.modificarTarea(updateEntity(taskItem, taskItem.text))
          }
        }
        //
      }
    dialogBuilder.create().show()
  }

  private fun addTask() {
    val dialogView = layoutInflater.inflate(R.layout.add_task, null)
    val taskText = dialogView.task_input
    val dialogBuilder = AlertDialog
      .Builder(this)
      .setTitle("Agrega una Tarea")
      .setView(dialogView)
      .setNegativeButton("Cerrar") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
      .setPositiveButton("Agregar") { dialog: DialogInterface, _: Int ->
        if (taskText.text?.isNotEmpty()!!) {
          //Completar para agregar una tarea a la base de datos
          AsyncTask.execute {
            dao.agregarTarea(createEntity(taskText.text.toString()))
            val newItems =
              createEntityListFromDatabase(dao.listarTareasTodas())
            runOnUiThread {
              adapter.updateData(newItems)
              dialog.dismiss()
            }
          }
        }
      }
    dialogBuilder.create().show()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    //inicializar lo necesario para usar la base de datos
    dataBase = TareasApp.tareasDatabase!!
    dao = dataBase.databaseDAO()
    //
    setUpViews()
  }

  override fun onResume() {
    super.onResume()
    AsyncTask.execute {
      val newItems = mutableListOf<TaskUIDataHolder>()
      runOnUiThread {
        adapter.updateData(newItems)
      }
    }
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
    list.layoutManager = LinearLayoutManager(this)
    adapter = TaskListAdapter(mutableListOf(), this, this)
    list.adapter = adapter
  }

  private fun removeAll() {
    val dialog = AlertDialog
      .Builder(this)
      .setTitle("Borrar Todo")
      .setMessage("¿Desea Borrar todas las tareas?")
      .setNegativeButton("Cerrar") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
      .setPositiveButton("Aceptar") { dialog: DialogInterface, _: Int ->
        //Código para eliminar las tareas de la base de datos
        AsyncTask.execute {
          dao.deleteAll()
          adapter.updateData(emptyList())

        }
      }
    dialog.show()
  }

  private fun updateEntity(
    taskItem: TaskUIDataHolder,
    newText: String
  ): MutableList<DatabaseEntity> {
    //completar método para actualizar una tarea en la base de datos
    val updateEntity: MutableList<DatabaseEntity> = mutableListOf()
    if (updateEntity.isNotEmpty()) {
      updateEntity.remove(
        DatabaseEntity(
          id = taskItem.id,
          tarea = newText
        )
      )
    } else updateEntity.add(DatabaseEntity(id = taskItem.id, tarea = newText))
    return updateEntity
    //
  }

  private fun createEntity(text: String): MutableList<DatabaseEntity> {
    //completar este método para retornar un Entity
    val createEntity: MutableList<DatabaseEntity> = mutableListOf()
    createEntity.add(
      DatabaseEntity(
        tarea = text
      )
    )
    return createEntity
  }

  private fun createEntityListFromDatabase(entities: List<DatabaseEntity>): MutableList<TaskUIDataHolder> {
    val dataList: MutableList<TaskUIDataHolder> = mutableListOf()
    //completar método para crear una lista de datos compatibles con el adaptador, mire lo que
    //retorna el método. Este método debe recibir un parámetro también.
    if (entities.isNotEmpty()) {
      for (entity in entities) {
        val dataView = TaskUIDataHolder(
          entity.id,
          entity.tarea
        )
        dataList.add(dataView)
      }
    }
    return dataList
  }
}