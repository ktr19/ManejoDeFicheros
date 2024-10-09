package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroTexto implements JugadorDAO {

    private static final String FILE_PATH = "./Datos/jugadores.txt";
    private List<Jugador> jugadores;  // Lista en memoria de jugadores

    public FicheroTexto() {
        crearArchivoSiNoExiste();
        this.jugadores = cargarJugadores();

    }
    
        private int generarNuevoId() {
        int maxId = -1; // Inicializamos maxId a -1 (no hay jugadores)

        // Iteramos sobre la lista de jugadores
        for (Jugador jugador : jugadores) {
            // Comparamos el ID de cada jugador con maxId
            if (jugador.getId() > maxId) {
                maxId = jugador.getId(); // Actualizamos maxId si encontramos un ID mayor
            }
        }

        // Retornamos el siguiente ID (maxId + 1)
        return maxId + 1;
    }

    private void crearArchivoSiNoExiste() {
        try {
            File dir = new File("./Datos");
            if (!dir.exists()) {
                dir.mkdirs(); // Crea el directorio y cualquier directorio padre necesario
                System.out.println("Directorio 'Datos' creado.");
            }

            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Archivo de jugadores creado: " + FILE_PATH);
            }
        } catch (IOException e) {
            System.out.println("[Error] Error al crear el archivo: " + e.getMessage());
        }
    }

    @Override
    public void crearJugador(Jugador jugador) {
        int nuevoId = generarNuevoId();

        // Verificar duplicados
        for (Jugador jugadorExistente : jugadores) {
            if (jugadorExistente.getNick().equalsIgnoreCase(jugador.getNick())) {
                System.out.println("Error: Ya existe un jugador con el nick '" + jugador.getNick() + "'");
                return;
            }
        }

        jugador.setId(nuevoId);
        jugadores.add(jugador);
        guardarJugadores(jugadores);
    }

    @Override
    public void eliminarJugador(int id) {
        for(Jugador jugadorEliminar : jugadores){
            if(jugadorEliminar.getId() == id){
                jugadores.remove(jugadorEliminar);
            }
        }
        guardarJugadores(jugadores);
    }

    @Override
    public void modificarJugador(int id, Jugador jugadorModificado) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                jugador.setNick(jugadorModificado.getNick());
                jugador.setExperience(jugadorModificado.getExperience());
                jugador.setLifeLevel(jugadorModificado.getLifeLevel());
                jugador.setCoins(jugadorModificado.getCoins());
            }
        }
        guardarJugadores(jugadores);
    }

    @Override
    public Jugador obtenerJugadorPorId(int id) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                // Encontramos al jugador
                return jugador;
            }
        }
        // No se encuentra
        return null;
    }

    @Override
    public List<Jugador> listarJugadores() {
        return jugadores;
    }

    // Cargar jugadores desde el archivo al iniciar
    private List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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
        } catch (IOException e) {
            // Si no existe el archivo o hay un error al cargar, devolver una lista vacía
            System.out.println("No se encontró el archivo o hubo un error al cargar los jugadores.");
        }
        return jugadoresCargados;
    }

    // Guardar la lista completa de jugadores en el archivo (usado en eliminar y modificar)
    private void guardarJugadores(List<Jugador> jugadores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugador.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generar el siguiente ID a partir del último jugador creado


}
