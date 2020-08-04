package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task
//
// import android.app.Application
// import androidx.lifecycle.AndroidViewModel
// import androidx.lifecycle.LiveData
// import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseRepository
// import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.DatabaseTareas
// import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm.ListaTareas
// import kotlinx.coroutines.CoroutineScope
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.Job
// import kotlinx.coroutines.launch
// import kotlin.coroutines.CoroutineContext
//
// class TaskViewModel(application: Application) : AndroidViewModel(application) {
//   private val databaseRepository: DatabaseRepository
//   private val tareas: LiveData<List<ListaTareas>>
//   private var job = Job()
//   private val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
//   private val scope = CoroutineScope(coroutineContext)
//
//   init {
//     val databaseDAO = DatabaseTareas.getDatabase(application).databaseDAO()
//     databaseRepository = DatabaseRepository(databaseDAO)
//     tareas = databaseRepository.item_lista
//   }
//
//   fun insertItem(item_lista: ListaTareas) = scope.launch(Dispatchers.IO) {
//     databaseRepository.insert(item_lista)
//   }
//
//   fun toggleListo(item_lista: ListaTareas, isChecked: Boolean) = scope.launch(Dispatchers.IO) {
//     item_lista.done = isChecked
//     databaseRepository.update(item_lista)
//   }
//
//   override fun onCleared() {
//     super.onCleared()
//     job.cancel()
//   }
// }