package RecyclerViewHelper

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbPacientes
import steven.flores.danielstevenfernandomerino.R

class Adaptador (var Datos: List<tbPacientes>): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Unir RecyclerView con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    fun eliminarDatos(id_paciente: Int,posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            //Variable de conexion
            val objConexion = ClaseConexion().cadenaConexion()

            // prepare statement -- Ayuda = ejecutar consultas de la base de datos

            val deletePaciente = objConexion?.prepareStatement( "delete from pacientes where id_paciente = ?")!!
            deletePaciente.setInt( 1, id_paciente)
            deletePaciente.executeUpdate()

            val commit = objConexion.prepareStatement( "commit")
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()

    }

    fun ActualizarListaDespuesDeEditar(id_paciente: Int, nuevoNombre: String){
        var index = Datos.indexOfFirst {it.id_paciente == id_paciente}
        Datos[index].nombre = nuevoNombre
        notifyItemChanged(index)
    }

    fun actualizarDato(nuevoNombre: String, id_paciente: Int){
        GlobalScope.launch (Dispatchers.IO){
            //1- Creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Creo una variable que contenga un PrepareStatement
            val updatePacientes = objConexion?.prepareStatement("update pacientes set nombre = ? where id_paciente = ?")!!
            updatePacientes.setString(1, nuevoNombre)
            updatePacientes.setInt(2, id_paciente)
            updatePacientes.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarListaDespuesDeEditar(id_paciente, nuevoNombre)
            }
        }
    }





    //Devolver la cantidad de datos que se muestran
    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Controlar la card
        val item = Datos[position]
        holder.txtNombreCard.text = item.nombre

        //Todo: clic al icono borrar
        holder.imgDelete.setOnClickListener{

            //Creo la alerta para confirmar la eliminacion
            //1- Invoco al contexto
            val context = holder.itemView.context

            //-2 Creo la alerta
            //  [Usando los tres pasos: titulo, mensaje y botones]
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmacion")
            builder.setMessage("Estas seguro que quiere borrar?")

            builder.setPositiveButton("Si"){ dialog, wich ->
                eliminarDatos(item.id_paciente, position)

            }
            builder.setNegativeButton("No"){ dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        //Todo: clic al icono de editar
        holder.imgEdit.setOnClickListener {
            //Creo mi Alerta para editar
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")

            //Agregarle un cuadro de texto
            //donde el usuario va a escribir el nuevo nombre
            val cuadroTexto = EditText(context)
            cuadroTexto.setHint(item.nombre)
            builder.setView(cuadroTexto)

            //Botones

            builder.setPositiveButton("Actualizar"){
                    dialog, wich ->
                actualizarDato(cuadroTexto.text.toString(), item.id_paciente)
            }
            builder.setNegativeButton("cancelar"){
                    dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }

    }


}