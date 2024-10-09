package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;



import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario que elija el tipo de almacenamiento
        System.out.print("Elige el tipo de almacenamiento (texto, binario, objeto, xml): ");
        String tipoFichero = scanner.nextLine();

        // Obtener el servicio correspondiente basado en el tipo de fichero
        JugadorDAO jugadorService = FicheroConfig.obtenerServicio(tipoFichero);

        int opcion;
        do {
            System.out.println("1. Alta de jugador");
            System.out.println("2. Baja de jugador");
            System.out.println("3. Modificación de jugador");
            System.out.println("4. Listado por ID");
            System.out.println("5. Listado general");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Alta de jugador
                    System.out.print("Nick: ");
                    String nick = scanner.nextLine();
                    System.out.print("Experiencia: ");
                    int experience = scanner.nextInt();
                    System.out.print("Nivel de vida: ");
                    int lifeLevel = scanner.nextInt();
                    System.out.print("Monedas: ");
                    int coins = scanner.nextInt();

                    // Crear un nuevo jugador sin asignar aún el ID
                    Jugador nuevoJugador = new Jugador(nick, experience, lifeLevel, coins);
                    jugadorService.crearJugador(nuevoJugador);  // El ID se asigna en el servicio
                    break;

                case 2:
                    // Baja de jugador
                    System.out.print("ID del jugador a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    jugadorService.eliminarJugador(idEliminar);
                    break;

                case 3:
                    // Modificación de jugador
                    System.out.print("ID del jugador a modificar: ");
                    int idModificar = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer

                    System.out.print("Nuevo nick: ");
                    String nuevoNick = scanner.nextLine();
                    System.out.print("Nueva experiencia: ");
                    int nuevaExperiencia = scanner.nextInt();
                    System.out.print("Nuevo nivel de vida: ");
                    int nuevoLifeLevel = scanner.nextInt();
                    System.out.print("Nuevas monedas: ");
                    int nuevasMonedas = scanner.nextInt();

                    // Crear un jugador con los nuevos datos para la modificación
                    Jugador jugadorModificado = new Jugador(nuevoNick, nuevaExperiencia, nuevoLifeLevel, nuevasMonedas);
                    jugadorService.modificarJugador(idModificar, jugadorModificado);
                    break;

                case 4:
                    // Listado por ID
                    System.out.print("ID del jugador: ");
                    int idBuscar = scanner.nextInt();
                    Jugador jugador = jugadorService.obtenerJugadorPorId(idBuscar);
                    System.out.println(jugador != null ? jugador : "Jugador no encontrado.");
                    break;

                case 5:
                    // Listado general
                    List<Jugador> jugadores = jugadorService.listarJugadores();
                    for (Jugador j : jugadores) {
                        System.out.println(j);
                    }
                    break;

                case 6:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcion != 6);

        scanner.close();
    }
}
