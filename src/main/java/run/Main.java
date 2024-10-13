package run;

import modelo.JugadorDAO;
import modelo.Jugador;
import ficheroConfi.FicheroConfig;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            String tipoFichero = elegirTipoAlmacenamiento(scanner);
            JugadorDAO jugadorTipo = FicheroConfig.obtenerServicio(tipoFichero);

            int opcion;
            do {
                opcion = mostrarMenu(scanner);
                procesarOpcion(opcion, jugadorTipo, scanner);
            } while (opcion != 6);
        }
    }

    private static String elegirTipoAlmacenamiento(Scanner scanner) {
        String tipoFichero;
        String[] tiposValidos = {"texto", "binario", "objeto", "xml", "aleatorio"};

        while (true) {
            System.out.print("Elige el tipo de almacenamiento (texto, binario, objeto, xml, aleatorio): ");
            tipoFichero = scanner.nextLine().trim().toLowerCase(); // Convertir a minúsculas y eliminar espacios

            // Comprobar si el tipo de fichero es válido
            for (String tipo : tiposValidos) {
                if (tipo.equals(tipoFichero)) {
                    return tipoFichero; // Retornar el tipo válido
                }
            }
            System.out.println("Tipo de almacenamiento no válido. Debe ser uno de: texto, binario, objeto, xml, aleatorio.");
        }
    }

    private static int mostrarMenu(Scanner scanner) {
        int opcion;
        while (true) {
            System.out.println("\n---- Menú ----");
            System.out.println("1. Alta de jugador");
            System.out.println("2. Baja de jugador");
            System.out.println("3. Modificación de jugador");
            System.out.println("4. Listado por ID");
            System.out.println("5. Listado general");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                // Verificar que la opción esté dentro del rango
                if (opcion >= 1 && opcion <= 6) {
                    return opcion; // Retornar la opción válida
                } else {
                    System.out.println("Opción no válida. Debe ser un número entre 1 y 6.");
                }
            } catch (Exception e) {
                System.out.println("[Error]: Entrada no válida. Debes ingresar un número entero.");
                scanner.nextLine(); // Limpiar el buffer en caso de error
            }
        }
    }

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
                System.out.println("Saliendo...");
                break;
            default:
                // Este caso no debería ocurrir debido a la validación previa.
                System.out.println("Opción no válida");
                break;
        }
    }

    private static void crearJugador(JugadorDAO jugadorTipo, Scanner scanner) {
        String nick;
        int coins, experience, lifeLevel;

        // Validación del Nick
        while (true) {
            System.out.print("Nick: ");
            nick = scanner.nextLine();
            if (nick.length() >= 1 && nick.length() <= 15) {
                break;
            } else {
                System.out.println("Valor no válido. El nick debe tener entre 1 y 15 caracteres.");
            }
        }

        // Validación de la experiencia
        experience = obtenerEntradaEntera(scanner, "Experiencia: ");
        if (experience < 0) return;

        // Validación del nivel de vida
        lifeLevel = obtenerEntradaEntera(scanner, "Nivel de vida: ");
        if (lifeLevel < 0) return;

        // Validación de las monedas
        coins = obtenerEntradaEntera(scanner, "Monedas: ");
        if (coins < 0) return;

        Jugador nuevoJugador = new Jugador(nick, experience, lifeLevel, coins);
        jugadorTipo.crearJugador(nuevoJugador);  // El ID se asigna en el servicio
    }

    private static void eliminarJugador(JugadorDAO jugadorTipo, Scanner scanner) {
        int idEliminar = obtenerEntradaEntera(scanner, "ID del jugador a eliminar: ");
        if (idEliminar >= 0) {
            jugadorTipo.eliminarJugador(idEliminar);
        }
    }

    private static void modificarJugador(JugadorDAO jugadorTipo, Scanner scanner) {
        int id;
        String nuevoNick;
        int nuevasMonedas, nuevaExperiencia, nuevoLifeLevel;

        // Validación del ID
        while (true) {
            System.out.print("ID del jugador a modificar: ");
            id = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer
            if (id >= 0) {
                break;
            } else {
                System.out.println("ID no puede ser negativo.");
            }
        }

        // Validación del Nick
        while (true) {
            System.out.print("Nuevo Nick: ");
            nuevoNick = scanner.nextLine();
            if (nuevoNick.length() >= 1 && nuevoNick.length() <= 15) {
                break;
            } else {
                System.out.println("Valor no válido. El nick debe tener entre 1 y 15 caracteres.");
            }
        }

        // Validación de la nueva experiencia
        nuevaExperiencia = obtenerEntradaEntera(scanner, "Nueva experiencia: ");
        if (nuevaExperiencia < 0) return;

        // Validación del nuevo nivel de vida
        nuevoLifeLevel = obtenerEntradaEntera(scanner, "Nuevo nivel de vida: ");
        if (nuevoLifeLevel < 0) return;

        // Validación de las nuevas monedas
        nuevasMonedas = obtenerEntradaEntera(scanner, "Nuevas monedas: ");
        if (nuevasMonedas < 0) return;

        Jugador jugadorModificado = new Jugador(nuevoNick, nuevaExperiencia, nuevoLifeLevel, nuevasMonedas);
        jugadorTipo.modificarJugador(id, jugadorModificado);
    }

    private static void listarJugadorPorId(JugadorDAO jugadorTipo, Scanner scanner) {
        int id = obtenerEntradaEntera(scanner, "ID del jugador: ");
        if (id >= 0) {
            Jugador jugador = jugadorTipo.obtenerJugadorPorId(id);
            System.out.println(jugador != null ? jugador : "Jugador no encontrado.");
        }
    }

    private static void listarJugadores(JugadorDAO jugadorTipo) {
        List<Jugador> jugadores = jugadorTipo.listarJugadores();
        System.out.println("\n---- Listado de Jugadores ----");
        for (Jugador j : jugadores) {
            System.out.println(j);
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
                if (valor < 0) {
                    System.out.println("[Error]: El valor no puede ser negativo. Inténtalo de nuevo.");
                    continue;
                }
                return valor; // Retornar el valor si es válido
            } catch (Exception e) {
                System.out.println("[Error]: Entrada no válida. Debes ingresar un número entero.");
                scanner.nextLine(); // Limpiar el buffer en caso de error
            }
        }
    }
}
