package tipoFicheros;

import modelo.FicheroBase;
import modelo.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja la carga y guardado de datos de jugadores utilizando la
 * serialización de objetos. Extiende la clase {@link FicheroBase} para aprovechar
 * funcionalidades comunes.
 */
public class FicheroObjeto extends FicheroBase {

    // Constructor que inicializa el archivo de jugadores en formato de objetos
    public FicheroObjeto() {
        super("./Datos/jugadoresObjetos.dat");
        crearArchivoSiNoExiste(); // Crea el archivo si no existe
    }

    /**
     * Verifica la existencia del directorio y el archivo de jugadores. Si el
     * directorio o el archivo no existen, los crea.
     */
    private void crearArchivoSiNoExiste() {
        try {
            File dir = new File("./Datos");
            if (!dir.exists()) {
                boolean dirCreado = dir.mkdirs(); // Crea el directorio 'Datos' si no existe
                System.out.println("\nDirectorio 'Datos' creado: " + dirCreado+"\n");
            }

            File file = new File(filePath);
            if (!file.exists()) {
                boolean archivoCreado = file.createNewFile(); // Crea el archivo de jugadores
                System.out.println("\nArchivo de jugadores creado: " + archivoCreado + " en " + filePath+"\n");
            }
        } catch (IOException e) {
            System.out.println("\n[Error] Error al crear el archivo: " + e.getMessage()+"\n");
        }
    }

    // Método que carga los jugadores desde el archivo de objetos
    @Override
    protected List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>(); // Lista para almacenar los jugadores cargados
        File file = new File(filePath);

        // Verifica si el archivo existe
        if (!file.exists()) {
            System.out.println("\nEl archivo no existe, no se cargarán jugadores.+\n");
            return jugadoresCargados; // Retorna una lista vacía si el archivo no existe
        }

        // Verifica si el archivo está vacío
        if (file.length() == 0) {
            System.out.println("\nEl archivo está vacío, no se cargarán jugadores.\n");
            return jugadoresCargados; // Retorna una lista vacía si el archivo está vacío
        }

        // Si el archivo existe y tiene contenido, proceder a cargar los jugadores
        try (ObjectInputStream datosJugadores = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    // Lee un objeto Jugador del archivo y lo añade a la lista
                    Jugador jugador = (Jugador) datosJugadores.readObject();
                    jugadoresCargados.add(jugador);
                } catch (EOFException e) {
                    break; // Fin del archivo alcanzado, salir del bucle
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nError al cargar los jugadores: " + e.getMessage()+"\n");
        }

        return jugadoresCargados; // Retorna la lista de jugadores cargados
    }

    // Método que guarda la información de los jugadores en el archivo de objetos
    @Override
    protected void guardarJugadores() {
        try (ObjectOutputStream jugadoresGuardados = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Itera sobre la lista de jugadores y guarda cada uno en el archivo
            for (Jugador jugador : jugadores) {
                jugadoresGuardados.writeObject(jugador); // Serializa el objeto Jugador y lo guarda
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime el error en caso de que ocurra
        }
    }
}
