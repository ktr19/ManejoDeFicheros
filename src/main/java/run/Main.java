package run;

import modelo.JugadorDAO;
import modelo.Jugador;
import ficheroConfi.FicheroConfig;
import static ficheroConfi.FicheroConfig.obtenerServicio;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que se encarga de ejecutar el programa para gestionar
 * jugadores.
 */
public class Main {

    /**
     * Método principal que inicia el programa.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en este programa).
     * @throws IOException Si ocurre algún error relacionado con la entrada/salida de datos.
     */
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            String tipoFichero = elegirTipoAlmacenamiento(scanner);
            JugadorDAO jugadorTipo = obtenerServicio(tipoFichero);

            int opcion;
            do {
                opcion = mostrarMenu(scanner);
                procesarOpcion(opcion, jugadorTipo, scanner);
            } while (opcion != 6);
        }
    }

    /**
     * Solicita al usuario que elija el tipo de almacenamiento a utilizar.
     *
     * @param scanner Objeto Scanner para leer la entrada del usuario.
     * @return El tipo de almacenamiento elegido por el usuario (texto, binario, etc.).
     */
    private static String elegirTipoAlmacenamiento(Scanner scanner) {
        String tipoFichero;
        String[] tiposValidos = {"texto", "binario", "objeto", "xml", "aleatorio"};

        while (true) {
            System.out.print("Elige el tipo de almacenamiento (texto, binario, objeto, xml, aleatorio): ");
            tipoFichero = scanner.nextLine().trim().toLowerCase(); // Limpiar espacios y convertir a minúsculas

            // Verificar si el tipo de fichero es válido
            for (String tipo : tiposValidos) {
                if (tipo.equals(tipoFichero)) {
                    return tipoFichero; // Retornar si es un tipo válido
                }
            }
            System.out.println("Tipo de almacenamiento no válido. Elige entre: texto, binario, objeto, xml, aleatorio.");
        }
    }

    /**
     * Muestra el menú principal y devuelve la opción elegida por el usuario.
     *
     * @param scanner Objeto Scanner para capturar la entrada del usuario.
     * @return La opción seleccionada por el usuario (número del 1 al 6).
     */
    private static int mostrarMenu(Scanner scanner) {
        int opcion;
        while (true) {
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Alta de jugador");
            System.out.println("2. Baja de jugador");
            System.out.println("3. Modificación de jugador");
            System.out.println("4. Listado por ID");
            System.out.println("5. Listado general");
            System.out.println("6. Salir");
            System.out.print("Elige una opción (1-6): ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                // Validar que la opción esté dentro del rango
                if (opcion >= 1 && opcion <= 6) {
                    return opcion;
                } else {
                    System.out.println("Opción no válida. Introduce un número entre 1 y 6.");
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada no válida. Introduce un número entero.");
                scanner.nextLine(); // Limpiar el buffer tras el error
            }
        }
    }

    /**
     * Procesa la opción seleccionada del menú y realiza la acción correspondiente.
     *
     * @param opcion Opción seleccionada por el usuario.
     * @param jugadorTipo Implementación de JugadorDAO para realizar las operaciones de datos.
     * @param scanner Scanner para obtener entradas adicionales del usuario.
     */
    private static void procesarOpcion(int opcion, JugadorDAO jugadorTipo, Scanner scanner) {
        switch (opcion) {
            case 1:
                crearJugador(jugadorTipo, scanner);
                break;
            case 2:
                eliminarJugador(jugadorTipo, scanner);
                break;
            case 3:
                modificarJugador(jugadorTipo, scanner);
                break;
            case 4:
                listarJugadorPorId(jugadorTipo, scanner);
                break;
            case 5:
                listarJugadores(jugadorTipo);
                break;
            case 6:
                System.out.println("\nGracias por usar el programa. ¡Hasta luego!");
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    /**
     * Solicita los datos de un nuevo jugador al usuario y lo añade a la base de datos.
     *
     * @param jugadorTipo Implementación de JugadorDAO para gestionar jugadores.
     * @param scanner Scanner para la entrada del usuario.
     */
    private static void crearJugador(JugadorDAO jugadorTipo, Scanner scanner) {
        String nick;
        int coins, experience, lifeLevel;

        // Validación del Nick
        while (true) {
            System.out.print("Nick (o ingresa -1 para cancelar): ");
            nick = scanner.nextLine().trim();
            if (nick.equals("-1")) {
                System.out.println("Operación de alta cancelada.");
                return; // Cancelar la operación
            }
            if (nick.length() >= 1 && nick.length() <= 15) {
                break;
            } else {
                System.out.println("Nick no válido. Debe tener entre 1 y 15 caracteres.");
            }
        }

        // Validación de la experiencia
        experience = obtenerEntradaEntera(scanner, "Experiencia (o ingresa -1 para cancelar): ");
        if (experience == -1) {
            System.out.println("Operación de alta cancelada.");
            return; // Cancelar la operación
        }

        // Validación del nivel de vida
        lifeLevel = obtenerEntradaEntera(scanner, "Nivel de vida (o ingresa -1 para cancelar): ");
        if (lifeLevel == -1) {
            System.out.println("Operación de alta cancelada.");
            return; // Cancelar la operación
        }

        // Validación de las monedas
        coins = obtenerEntradaEntera(scanner, "Monedas (o ingresa -1 para cancelar): ");
        if (coins == -1) {
            System.out.println("Operación de alta cancelada.");
            return; // Cancelar la operación
        }

        Jugador nuevoJugador = new Jugador(nick, experience, lifeLevel, coins);
        jugadorTipo.crearJugador(nuevoJugador);
    }

    /**
     * Solicita el ID de un jugador al usuario y lo elimina del sistema.
     *
     * @param jugadorTipo Implementación de JugadorDAO utilizada para gestionar jugadores.
     * @param scanner Scanner para la entrada del usuario.
     */
    private static void eliminarJugador(JugadorDAO jugadorTipo, Scanner scanner) {
        int idEliminar = obtenerEntradaEntera(scanner, "ID del jugador a eliminar (o ingresa -1 para cancelar): ");
        if (idEliminar == -1) {
            System.out.println("Operación de eliminación cancelada.");
            return; // Cancelar la operación
        }
        if (idEliminar >= 0) {
            jugadorTipo.eliminarJugador(idEliminar);
        }
    }

    /**
     * Modifica los datos de un jugador existente según los valores proporcionados por el usuario.
     *
     * @param jugadorTipo Implementación de JugadorDAO para realizar las modificaciones.
     * @param scanner Scanner para la entrada del usuario.
     */
    private static void modificarJugador(JugadorDAO jugadorTipo, Scanner scanner) {
        int id;
        String nuevoNick;
        int nuevasMonedas, nuevaExperiencia, nuevoLifeLevel;

        // Validación del ID
        while (true) {
            System.out.print("ID del jugador a modificar (o ingresa -1 para cancelar): ");
            id = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            if (id < -1) {
                System.out.println("ID no puede ser negativo.");
                continue; // Volver a solicitar el ID
            }

            if (id == -1) {
                System.out.println("Operación de modificación cancelada.");
                return; // Cancelar la operación
            }

            Jugador jugadorExistente = jugadorTipo.obtenerJugadorPorId(id);
            if (jugadorExistente == null) {
                System.out.println("No existe un jugador con ese ID. Inténtalo de nuevo.");
            } else {
                break; // ID válido y el jugador existe
            }
        }

        // Validación del Nick
        while (true) {
            System.out.print("Nuevo Nick (o ingresa -1 para cancelar): ");
            nuevoNick = scanner.nextLine();
            if (nuevoNick.equals("-1")) {
                System.out.println("Operación de modificación cancelada.");
                return; // Cancelar la operación
            }
            if (nuevoNick.length() >= 1 && nuevoNick.length() <= 15) {
                break;
            } else {
                System.out.println("Nick no válido. Debe tener entre 1 y 15 caracteres.");
            }
        }

        // Validación de la nueva experiencia
        nuevaExperiencia = obtenerEntradaEntera(scanner, "Nueva experiencia (o ingresa -1 para cancelar): ");
        if (nuevaExperiencia == -1) {
            System.out.println("Operación de modificación cancelada.");
            return; // Cancelar la operación
        }

        // Validación del nuevo nivel de vida
        nuevoLifeLevel = obtenerEntradaEntera(scanner, "Nuevo nivel de vida (o ingresa -1 para cancelar): ");
        if (nuevoLifeLevel == -1) {
            System.out.println("Operación de modificación cancelada.");
            return; // Cancelar la operación
        }

        // Validación de las nuevas monedas
        nuevasMonedas = obtenerEntradaEntera(scanner, "Nuevas monedas (o ingresa -1 para cancelar): ");
        if (nuevasMonedas == -1) {
            System.out.println("Operación de modificación cancelada.");
            return; // Cancelar la operación
        }

        Jugador jugadorModificado = new Jugador(nuevoNick, nuevaExperiencia, nuevoLifeLevel, nuevasMonedas);
        jugadorTipo.modificarJugador(id, jugadorModificado);
    }

    /**
     * Muestra la información de un jugador específico basándose en su ID.
     *
     * @param jugadorTipo Implementación de JugadorDAO para obtener los datos
     * del jugador.
     * @param scanner Scanner para la entrada del usuario.
     */
    private static void listarJugadorPorId(JugadorDAO jugadorTipo, Scanner scanner) {
        int id = obtenerEntradaEntera(scanner, "ID del jugador (o ingresa -1 para cancelar): ");
        if (id == -1) {
            System.out.println("Operación de consulta cancelada.");
            return; // Cancelar la operación
        }

        if (id >= 0) {
            Jugador jugador = jugadorTipo.obtenerJugadorPorId(id);
            System.out.println(jugador != null ? jugador : "Jugador no encontrado.");
        }
    }

    /**
     * Muestra una lista completa de todos los jugadores almacenados.
     *
     * @param jugadorTipo Implementación de JugadorDAO para gestionar los jugadores.
     */
    private static void listarJugadores(JugadorDAO jugadorTipo) {
        List<Jugador> jugadores = jugadorTipo.listarJugadores();
        System.out.println("\n==== Listado de Jugadores ====");
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
        } else {
            for (Jugador j : jugadores) {
                System.out.println(j);
            }
        }
    }

    private static int obtenerEntradaEntera(Scanner scanner, String mensaje) {
        int valor;
        while (true) {
            try {
                System.out.print(mensaje);
                valor = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                // Verificar que el valor no sea negativo
                if (valor < -1) { // Permitir -1 para cancelar
                    System.out.println("Error: El valor no puede ser negativo. Inténtalo de nuevo.");
                    continue;
                }
                return valor;
            } catch (Exception e) {
                System.out.println("Error: Entrada no válida. Debes ingresar un número entero.");
                scanner.nextLine(); // Limpiar el buffer en caso de error
            }
        }
    }
}