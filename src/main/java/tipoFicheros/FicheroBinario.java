package tipoFicheros;

import modelo.FicheroBase;
import modelo.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja la carga y guardado de datos de jugadores en un archivo
 * binario. Extiende la clase {@link FicheroBase} para reutilizar la funcionalidad
 * común.
 */
public class FicheroBinario extends FicheroBase {

    // Constructor que inicializa el archivo de jugadores en formato binario
    public FicheroBinario() {
        super("./Datos/jugadoresBinario.dat"); // este seria la variable filePath
        crearArchivoSiNoExiste(); // Crea el archivo si no existe
    }

    // Método que verifica la existencia del directorio y archivo, y los crea si no están presentes
    private void crearArchivoSiNoExiste() {
        try {
            File dir = new File("./Datos");
            if (!dir.exists()) {
                dir.mkdirs(); // Crea el directorio 'Datos' si no existe
                System.out.println("Directorio 'Datos' creado.");
            }

            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile(); // Crea el archivo de jugadores
                System.out.println("Archivo de jugadores creado: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("[Error] Error al crear el archivo: " + e.getMessage());
        }
    }

    // Método que carga los jugadores desde el archivo binario
    @Override
    protected List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>(); // Lista para almacenar los jugadores cargados
        int coins, experience, life_level, id; // Variables para almacenar datos del jugador
        String nick; // Variable para almacenar el nombre del jugador

        try (DataInputStream jugadoresCarga = new DataInputStream(new FileInputStream(filePath))) {
            // Lee cada jugador hasta llegar al final del archivo
            while (true) {
                id = jugadoresCarga.readInt(); // Lee el ID del jugador
                nick = jugadoresCarga.readUTF(); // Lee el nombre del jugador
                experience = jugadoresCarga.readInt(); // Lee la experiencia
                life_level = jugadoresCarga.readInt(); // Lee el nivel de vida
                coins = jugadoresCarga.readInt(); // Lee la cantidad de monedas

                // Crea un nuevo objeto Jugador y lo añade a la lista
                Jugador jugador = new Jugador(nick, experience, life_level, coins);
                jugadoresCargados.add(jugador);
            }
        } catch (EOFException e) {
            // Fin del archivo alcanzado, no es un error, se puede ignorar
        } catch (IOException e) {
            System.out.println("No se encontró el archivo o hubo un error al cargar los jugadores.");
        }

        return jugadoresCargados; // Retorna la lista de jugadores cargados
    }

    // Método que guarda la información de los jugadores en el archivo binario
    @Override
    protected void guardarJugadores() {
        try (DataOutputStream jugadoresGuardados = new DataOutputStream(new FileOutputStream(filePath))) {
            // Itera sobre la lista de jugadores y guarda sus datos en el archivo
            for (Jugador jugador : jugadores) {
                jugadoresGuardados.writeInt(jugador.getId()); // Escribe el ID
                jugadoresGuardados.writeUTF(jugador.getNick()); // Escribe el nombre
                jugadoresGuardados.writeInt(jugador.getExperience()); // Escribe la experiencia
                jugadoresGuardados.writeInt(jugador.getLifeLevel()); // Escribe el nivel de vida
                jugadoresGuardados.writeInt(jugador.getCoins()); // Escribe la cantidad de monedas
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime el error 
        }
    }
}
