
import java.time.LocalTime

object Main extends App {
  val sistema = new SistemaAsistencia()
  
  // 1. Inicializar con datos de ejemplo
  sistema.inicializarDatosEjemplo()
  
  // 2. Registrar nueva asistencia
  val registroExitoso1 = sistema.registrarAsistencia("Pedro Martínez", LocalTime.of(7, 5))
  val registroExitoso2 = sistema.registrarAsistencia("Juan Pérez", LocalTime.of(8, 0))  // No existente
  
  println(s"Registro 1: ${if (registroExitoso1) "Éxito" else "Falló"}")
  println(s"Registro 2: ${if (registroExitoso2) "Éxito" else "Falló"}")
  
  // 3. Generar reporte
  println("\n=== Reporte de Asistencia ===")
  println(sistema.generarReporte())
  
  // 4. Calcular puntualidad
  println("\n=== Estadísticas de Puntualidad ===")
  println(s"María García: ${sistema.porcentajePuntualidad("María García")}% puntual")
  println(s"Carlos Rodríguez: ${sistema.porcentajePuntualidad("Carlos Rodríguez")}% puntual")
}