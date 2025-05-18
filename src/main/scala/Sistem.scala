import scala.collection.mutable.{LinkedHashMap, HashSet, ArrayBuffer}
import java.time.{LocalDate, LocalTime, LocalDateTime}

class SistemaAsistencia {
  // Estructuras de datos principales
  private val empleadosValidos = HashSet[String]()  // Para verificación rápida de nombres
  private val registros = LinkedHashMap[LocalDate, ArrayBuffer[RegistroAsistencia]]()  // Para mantener orden por fecha
  private val horaEntradaLimite = LocalTime.of(7, 0)  // Hora límite para puntualidad

  // Clase interna para representar registros
  case class RegistroAsistencia(nombre: String, horaEntrada: LocalTime, llegoTarde: Boolean)

  // Inicializar con datos de ejemplo
  def inicializarDatosEjemplo(): Unit = {
    // Empleados válidos
    val nombresEjemplo = List("María García", "Carlos Rodríguez", "Ana López", "Pedro Martínez", "Luisa Fernández")
    nombresEjemplo.foreach(empleadosValidos.add)
    
    // Registros de ejemplo para hoy
    val hoy = LocalDate.now()
    val registrosHoy = ArrayBuffer[RegistroAsistencia](
      RegistroAsistencia("María García", LocalTime.of(6, 45), false),
      RegistroAsistencia("Carlos Rodríguez", LocalTime.of(7, 15), true),
      RegistroAsistencia("Ana López", LocalTime.of(6, 58), false)
    )
    registros.put(hoy, registrosHoy)
  }

  // Registrar asistencia
  def registrarAsistencia(nombre: String, hora: LocalTime): Boolean = {
    if (!empleadosValidos.contains(nombre)) {
      false  // Nombre no válido
    } else {
      val hoy = LocalDate.now()
      val llegoTarde = hora.isAfter(horaEntradaLimite)
      val nuevoRegistro = RegistroAsistencia(nombre, hora, llegoTarde)
      
      registros.getOrElseUpdate(hoy, ArrayBuffer()) += nuevoRegistro
      true
    }
  }

  // Generar reporte en formato similar a Excel
  def generarReporte(): String = {
    val sb = new StringBuilder
    sb.append("Fecha\tNombre\tHora Entrada\t¿Llegó tarde?\n")
    
    registros.foreach { case (fecha, registrosDia) =>
      registrosDia.foreach { registro =>
        sb.append(s"${fecha}\t${registro.nombre}\t${registro.horaEntrada}\t${registro.llegoTarde}\n")
      }
    }
    
    sb.toString()
  }

  // Verificar puntualidad de un empleado en un período
  def porcentajePuntualidad(nombre: String): Double = {
    val totalRegistros = registros.values.flatten.count(_.nombre == nombre)
    if (totalRegistros == 0) return 0.0
    
    val puntuales = registros.values.flatten.count(r => r.nombre == nombre && !r.llegoTarde)
    (puntuales.toDouble / totalRegistros) * 100
  }
}