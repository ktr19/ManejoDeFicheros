package tipoFicheros;

import modelo.FicheroBase;
import modelo.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja la lectura y escritura de jugadores en un archivo de texto.
 * Extiende la clase {@link FicheroBase} para proporcionar funcionalidad
 * específica para archivos de texto. Los jugadores se almacenan en un formato
 * legible por humanos.
 */
public class FicheroTexto extends FicheroBase {

    /**
     * Constructor de la clase FicheroTexto. Inicializa la ruta del archivo de
     * jugadores y crea el archivo si no existe.
     */
    public FicheroTexto() {
        super("./Datos/jugadores.txt");
        crearArchivoSiNoExiste();
    }

    /**
     * Crea el directorio y el archivo si no existen. Se asegura de que la
     * estructura de directorios esté disponible para almacenar los datos.
     */
    private void crearArchivoSiNoExiste() {
        try {
            File dir = new File("./Datos");
            if (!dir.exists()) {
                dir.mkdirs(); // Crea el directorio y cualquier directorio padre necesario
                System.out.println("Directorio 'Datos' creado.");
            }

            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Archivo de jugadores creado: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("{Error} Error al crear el archivo: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de jugadores desde el archivo de texto. Cada línea del
     * archivo debe contener la información del jugador en el formato
     * especificado.
     *
     * @return Una lista de objetos {@link Jugador} cargados desde el archivo.
     */
    @Override
    protected List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Procesar la línea usando split y asignar directamente
                String[] partes = linea.split(", ");
                int id = Integer.parseInt(partes[0].split(" = ")[1]);
                String nick = partes[1].split(" = ")[1];
                int experience = Integer.parseInt(partes[2].split(" = ")[1]);
                int lifeLevel = Integer.parseInt(partes[3].split(" = ")[1]);
                int coins = Integer.parseInt(partes[4].split(" = ")[1]);

                // Crear el objeto Jugador y añadirlo a la lista
                jugadoresCargados.add(new Jugador(id, nick, experience, lifeLevel, coins));
            }
        } catch (FileNotFoundException e) {
            System.out.println("{Error} No se encontró el archivo: " + filePath);
        } catch (IOException e) {
            System.out.println("{Error} Hubo un problema al cargar los jugadores: " + e.getMessage());
        }

        return jugadoresCargados;
    }

    /**
     * Guarda la lista de jugadores en el archivo de texto. Escribe cada jugador
     * en una nueva línea utilizando el formato definido en el método
     * {@link Jugador#toString()}.
     */
    protected void guardarJugadores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugador.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
