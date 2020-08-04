package dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.task

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.dmayr.a03_02_06_desafio_aplicacion_de_tareas.R

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val taskText: TextView = view.findViewById<TextView>(R.id.task_text)
}