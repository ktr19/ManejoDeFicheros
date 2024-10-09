package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroObjeto implements JugadorDAO {

    private static final String FILE_PATH = "./Datos/jugadores.dat";
    private List<Jugador> jugadores;

    public FicheroObjeto() {
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
                dir.mkdirs(); // Crear el directorio si no existe
                System.out.println("Directorio 'Datos' creado.");
            }

            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Archivo de jugadores creado: " + FILE_PATH);
            }
        } catch (IOException e) {
            System.out.println("[Error]Error al crear el archivo: " + e.getMessage());
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
        for (Jugador jugadorEliminar : jugadores) {
            if (jugadorEliminar.getId() == id) {
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

    private List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        try (ObjectInputStream datosJugadores = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            while (true) {
                Jugador jugador = (Jugador) datosJugadores.readObject();
                jugadoresCargados.add(jugador);
            }
        } catch (EOFException e) {
            // Fin del archivo alcanzado, se puede ignorar
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se encontró el archivo o hubo un error al cargar los jugadores.");
        }
        return jugadoresCargados;
    }

    private void guardarJugadores(List<Jugador> jugadores) {
        try (ObjectOutputStream jugadoresGuardados = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            for (Jugador jugador : jugadores) {
                jugadoresGuardados.writeObject(jugador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
