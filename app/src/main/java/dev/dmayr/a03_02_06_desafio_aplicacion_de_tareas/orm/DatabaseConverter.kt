package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.orm

import androidx.room.TypeConverter
import java.util.*

class DatabaseConverter {
  @TypeConverter
  fun fechaDesdeTimestampLong(value: Long?): Date? {
    return value?.let { Date(it) }
  }

  @TypeConverter
  fun longTimeStampDesdeDate(date: Date?): Long? {
    return date?.time
  }
}