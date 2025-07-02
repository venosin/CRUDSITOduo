package steven.flores.danielstevenfernandomerino

import RecyclerViewHelper.Adaptador
import android.annotation.SuppressLint
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

class activity_registrar : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mando a llamar a todos los elementos
        val txtidPaciente = findViewById<EditText>(R.id.txtidPaciente)
        val txtNombre = findViewById<EditText>(R.id.txtNombrePaciente)
        val txtTipoSangre = findViewById<EditText>(R.id.txtTipoSangre)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefono)
        val txtMedicamentos = findViewById<EditText>(R.id.txtMedicamentos)
        val txtNumeroCama = findViewById<EditText>(R.id.txtNumeroCama)
        val txtFechaNacimiento = findViewById<EditText>(R.id.txtFechaNacimiento)
        val txtidHabitacion = findViewById<EditText>(R.id.txtidHabitacion)
        val txtidEnfermedad = findViewById<EditText>(R.id.txtidEnfermedad)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarPaciente)


        //2- Programar el boton
        btnRegistrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                //1 crear obj clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2 Crear variable que contenga prepareStatement
                val addPaciente = objConexion?.prepareStatement("insert into pacientes (id_paciente, nombre, tipo_sangre, telefono, medicamentos, numero_cama, fecha_nacimiento, id_habitacion, id_enfermedad) values(?, ?, ?, ?, ?, ?, ?, ?, ?) ")!!
                addPaciente.setInt(1, txtidPaciente.text.toString().toInt())
                addPaciente.setString(2, txtNombre.text.toString())
                addPaciente.setString(3, txtTipoSangre.text.toString())
                addPaciente.setString(4, txtTelefono.text.toString())
                addPaciente.setString(5, txtMedicamentos.text.toString())
                addPaciente.setInt(6, txtNumeroCama.text.toString().toInt())
                addPaciente.setString(7, txtFechaNacimiento.text.toString())
                addPaciente.setInt(8, txtidHabitacion.text.toString().toInt())
                addPaciente.setInt(9, txtidEnfermedad.text.toString().toInt())
                addPaciente.executeUpdate()
            }
        }

    }
}