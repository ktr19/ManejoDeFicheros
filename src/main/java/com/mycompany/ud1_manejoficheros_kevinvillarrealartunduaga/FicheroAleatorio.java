/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Vespertino
 */
public class FicheroAleatorio implements JugadorDAO {

    private static final String FILE_PATH = "./Datos/jugadoresAleatorio.dat";
    private List<Jugador> jugadores;

    public FicheroAleatorio() {
        crearArchivoSiNoExiste();
        this.jugadores = cargarJugadores();

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
            System.out.println("Error al crear el archivo: " + e.getMessage());
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

    private List<Jugador> cargarJugadores() { // 46 bytes en total
        List<Jugador> jugadoresCargados = new ArrayList<>();
        char nick_name[] = new char[15], aux; //30
        int posicion = 0, userId, coins, life_level, experience; // 16
        try (RandomAccessFile file = new RandomAccessFile(FILE_PATH, "r")) {
            for (;;) {
                file.seek(posicion);
            
                for (int i = 0; i < nick_name.length; i++) {
                    aux = file.readChar();
                    nick_name[i] = aux; //los voy guardando en el array
                }
                String nick = Arrays.toString(nick_name);
                experience = file.readInt();
                life_level = file.readInt();
                coins = file.readInt();
                Jugador jugador = new Jugador(nick, experience, life_level, coins);
                jugadoresCargados.add(jugador);
                posicion = posicion + 46;
                if (file.getFilePointer() == file.length()) {
                    break;
                }
            }
            file.close();
        } catch (IOException e) {
            System.out.println("No se encontró el archivo o hubo un error al cargar los jugadores.");
        }

        return jugadoresCargados;
    }

    private void guardarJugadores(List<Jugador> jugadores) {
    
        try (RandomAccessFile jugadoresGuardados = new RandomAccessFile(FILE_PATH, "rw")) {
            for (Jugador jugador : jugadores) {
                jugadoresGuardados.writeInt(jugador.getId());
                jugadoresGuardados.writeChars(jugador.getNick());
                jugadoresGuardados.writeInt(jugador.getExperience());
                jugadoresGuardados.writeInt(jugador.getLifeLevel());
                jugadoresGuardados.writeInt(jugador.getCoins());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
