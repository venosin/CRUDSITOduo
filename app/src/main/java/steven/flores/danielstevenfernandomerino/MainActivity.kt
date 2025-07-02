package steven.flores.danielstevenfernandomerino

import RecyclerViewHelper.Adaptador
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbPacientes

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val pantallaRegistrar = Intent(this, activity_registrar::class.java)
            startActivity(pantallaRegistrar)

         //Mandar a llamar al recyclerView
            val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)

            //Agrego un Layout al RecyclerView
            rcvPacientes.layoutManager = LinearLayoutManager(this)

            /////////// TODO: mostrar datos

            fun obtenerPaciente(): List<tbPacientes>{

                //-1 Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2- Creo un Statement
                val statement = objConexion?.createStatement()
                val resultSet = statement?.executeQuery("SELECT * FROM pacientes")!!

                val listaPacientes = mutableListOf<tbPacientes>()

                while (resultSet.next()){

                    val idPaciente = resultSet.getInt("id_paciente")
                    val Nombre = resultSet.getString("nombre")
                    val TipoSangre = resultSet.getString("tipo_sangre")
                    val Telefono = resultSet.getString("telefono")
                    val Medicamentos = resultSet.getString("medicamentos")
                    val NumeroCama = resultSet.getInt("numero_cama")
                    val FechaNacimiento = resultSet.getString("fecha_nacimiento")
                    val idHabitacion = resultSet.getInt("id_habitacion")
                    val idEnfermedad = resultSet.getInt("id_enfermedad")

                    val valoresJuntos = tbPacientes(idPaciente, Nombre, TipoSangre, Telefono, Medicamentos, NumeroCama, FechaNacimiento, idHabitacion, idEnfermedad)

                    listaPacientes.add(valoresJuntos)

                }
                return listaPacientes
            }

            //Asignarle el adaptador al RecyclerView
            CoroutineScope(Dispatchers.IO).launch {
                val pacientesDB = obtenerPaciente()
                withContext(Dispatchers.Main){
                    val adapter = Adaptador(pacientesDB)
                    rcvPacientes.adapter = adapter
                }
            }


        }
    }
}