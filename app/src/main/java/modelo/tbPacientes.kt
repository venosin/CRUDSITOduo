package modelo

data class tbPacientes(
    val id_paciente: Int,
    var nombre: String,
    val tipo_sangre: String,
    val telefono: String,
    val medicamentos: String,
    val numero_cama: Int,
    val fecha_nacimiento: String,
    val id_habitacion: Int,
    val id_enfermedad: Int
)
