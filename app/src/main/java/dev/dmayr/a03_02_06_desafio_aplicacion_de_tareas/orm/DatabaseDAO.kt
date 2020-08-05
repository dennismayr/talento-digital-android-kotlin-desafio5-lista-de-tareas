package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDAO {
  @Query("SELECT * FROM lista_tareas")
  fun listarTareasTodas(): List<DatabaseEntity>

  @Insert
  fun agregarTarea(tarea: List<DatabaseEntity>)

  @Update(onConflict = REPLACE)
  fun modificarTarea(tarea: List<DatabaseEntity>)

  @Query("DELETE FROM lista_tareas")
  fun deleteAll()
}