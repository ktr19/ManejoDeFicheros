package tipoFicheros;

import modelo.FicheroBase;
import modelo.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroTexto extends FicheroBase{


    public FicheroTexto() {
        super("./Datos/jugadores.txt");
        crearArchivoSiNoExiste();
    }
    
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
                System.out.println("\nArchivo de jugadores creado: " + filePath+"\n");
            }
        } catch (IOException e) {
            System.out.println("\n{Error} Error al crear el archivo: " + e.getMessage()+"\n");
        }
    }


    // Cargar jugadores desde el archivo al iniciar
    @Override
   protected List<Jugador> cargarJugadores() {
    List<Jugador> jugadoresCargados = new ArrayList<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.replaceAll("[\\[\\]]", "").split(", ");
            int id = Integer.parseInt(partes[0].split(" = ")[1]);
            String nick = partes[1].split(" = ")[1];
            int experience = Integer.parseInt(partes[2].split(" = ")[1]);
            int lifeLevel = Integer.parseInt(partes[3].split(" = ")[1]);
            int coins = Integer.parseInt(partes[4].split(" = ")[1]);
            jugadoresCargados.add(new Jugador(id, nick, experience, lifeLevel, coins));
        }
    } catch (FileNotFoundException e) {
        // Error específico cuando el archivo no existe
        System.out.println("\n{Error} No se encontró el archivo: " + filePath+"\n");
    } catch (IOException e) {
        // Error al intentar cargar o leer el archivo
        System.out.println("\n{Error} Hubo un problema al cargar los jugadores: " + e.getMessage()+"\n");
    }
    
    return jugadoresCargados;
}


    // Guardar la lista completa de jugadores en el archivo (usado en eliminar y modificar)
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
