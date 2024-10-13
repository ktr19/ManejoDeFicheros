package tipoFicheros;

import modelo.FicheroBase;
import modelo.Jugador;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de manejar un archivo de datos para almacenar
 * información de los jugadores de forma aleatoria. Se basa en la clase
 * {@link FicheroBase}, que proporciona métodos comunes para gestionar los
 * jugadores.
 */
public class FicheroAleatorio extends FicheroBase {

    // Constructor que inicializa el archivo de jugadores
    public FicheroAleatorio() {
        super("./Datos/jugadoresAleatorio.dat");//sera el filepath
        crearArchivoSiNoExiste(); // Crea el archivo si no existe
    }

    // Método que verifica y crea el directorio y el archivo si no están presentes
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
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    // Método que carga los jugadores desde el archivo
    @Override
    protected List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        File archivo = new File(filePath); // Archivo donde se guardan los jugadores
        char nick_name[] = new char[15]; // Array para almacenar el nombre del jugador
        char aux; // Variable temporal para leer caracteres
        int posicion = 4; // Posición inicial para leer
        int coins, life_level, experience; // Variables para almacenar datos del jugador

        // Verifica si el archivo no existe o está vacío
        if (!archivo.exists()) {
            System.out.println("El archivo no se ha encontrado.");
            return jugadoresCargados; // Retorna lista vacía si no existe
        }
        if (archivo.length() == 0) {
            System.out.println("El archivo está vacío.");
            return jugadoresCargados; // Retorna lista vacía si está vacío
        }

        // Si el archivo existe y tiene contenido, procede a leer
        try (RandomAccessFile file = new RandomAccessFile(archivo, "r")) {
            while (true) {
                // Posiciona el puntero del archivo en la posición actual
                file.seek(posicion);
                
                // Lee el nombre del jugador
                for (int i = 0; i < nick_name.length; i++) {
                    aux = file.readChar();
                    nick_name[i] = aux; // Almacena los caracteres del nombre
                }
                String nick = new String(nick_name).trim(); // Convierte a String y elimina espacios

                // Lee los otros datos del jugador
                experience = file.readInt();
                life_level = file.readInt();
                coins = file.readInt();

                // Crea un nuevo objeto Jugador y lo añade a la lista
                Jugador jugador = new Jugador(nick, experience, life_level, coins);
                jugadoresCargados.add(jugador);

                // Actualiza la posición para el siguiente jugador
                posicion += 46; // 30 bytes (nombre) + 16 bytes (otros datos)

                // Verifica si se ha llegado al final del archivo
                if (file.getFilePointer() == file.length()) {
                    break; // Sale del bucle si se ha leído todo
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los jugadores: " + e.getMessage());
        }

        return jugadoresCargados; // Retorna la lista de jugadores cargados
    }

    // Método que guarda los jugadores en el archivo
    @Override
    protected void guardarJugadores() {
        try (RandomAccessFile jugadoresGuardados = new RandomAccessFile(filePath, "rw")) {
            // Limpia el archivo antes de escribir nuevos jugadores
            jugadoresGuardados.setLength(0); // Borra el contenido anterior

            for (Jugador jugador : jugadores) {
                jugadoresGuardados.writeInt(jugador.getId()); // Escribe el ID del jugador

                // Escribe el nombre del jugador (15 caracteres)
                String nick = jugador.getNick();
                StringBuilder sb = new StringBuilder(nick);
                while (sb.length() < 15) {
                    sb.append(" "); // Rellena con espacios si el nombre es corto
                }
                jugadoresGuardados.writeChars(sb.toString()); // Guarda el nombre en el archivo

                // Escribe los otros datos del jugador
                jugadoresGuardados.writeInt(jugador.getExperience());
                jugadoresGuardados.writeInt(jugador.getLifeLevel());
                jugadoresGuardados.writeInt(jugador.getCoins());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los jugadores: " + e.getMessage());
        }
    }
}
