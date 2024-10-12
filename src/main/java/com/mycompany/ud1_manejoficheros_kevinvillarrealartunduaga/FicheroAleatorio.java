/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vespertino
 */
public class FicheroAleatorio extends FicheroBase {

    public FicheroAleatorio() {
        super("./Datos/jugadoresAleatorio.dat");
        crearArchivoSiNoExiste();
    }

    private void crearArchivoSiNoExiste() {
        try {
            File dir = new File("./Datos");
            if (!dir.exists()) {
                dir.mkdirs(); // Crear el directorio si no existe
                System.out.println("Directorio 'Datos' creado.");
            }

            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Archivo de jugadores creado: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    @Override
    protected List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        File archivo = new File(filePath); // Mismo FILE_PATH
        char nick_name[] = new char[15], aux; // 30 bytes en total para el nick
        int posicion = 4, coins, life_level, experience; // 16 bytes restantes

        // Verifica si el archivo no existe
        if (!archivo.exists()) {
            System.out.println("El archivo no se ha encontrado.");
            return jugadoresCargados; // Retornar lista vacía si no existe
        }

        // Verifica si el archivo está vacío
        if (archivo.length() == 0) {
            System.out.println("El archivo está vacío.");
            return jugadoresCargados; // Retornar lista vacía si está vacío
        }

        // Si el archivo existe y no está vacío, proceder a leer
        try (RandomAccessFile file = new RandomAccessFile(archivo, "r")) {
            while (true) {
                // Ubica el puntero del archivo en la posición actual
                file.seek(posicion);
                
                // Leer el nickname (15 caracteres, 30 bytes)
                for (int i = 0; i < nick_name.length; i++) {
                    aux = file.readChar();
                    nick_name[i] = aux; // Guardar los caracteres en el array
                }
                String nick = new String(nick_name).trim(); // Convertir a String y quitar espacios en blanco

                // Leer los otros valores del jugador (4 enteros, 16 bytes en total)
                experience = file.readInt();
                life_level = file.readInt();
                coins = file.readInt();

                // Crear un nuevo objeto Jugador y añadirlo a la lista
                Jugador jugador = new Jugador(nick, experience, life_level, coins);
                jugadoresCargados.add(jugador);

                // Actualizar la posición para el siguiente jugador
                posicion += 46; // 30 bytes (nick) + 16 bytes (otros valores)

                // Verifica si se ha llegado al final del archivo
                if (file.getFilePointer() == file.length()) {
                    break; // Salir del bucle si se ha leído todo el archivo
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los jugadores: " + e.getMessage());
        }

        return jugadoresCargados;
    }

    @Override
    protected void guardarJugadores() {
        try (RandomAccessFile jugadoresGuardados = new RandomAccessFile(filePath, "rw")) {
            // Limpiar el archivo antes de escribir nuevos jugadores
            jugadoresGuardados.setLength(0); // Limpia el contenido previo

            for (Jugador jugador : jugadores) {
                jugadoresGuardados.writeInt(jugador.getId());

                // Escribir el nickname (15 caracteres, rellenar si es necesario)
                String nick = jugador.getNick();
                StringBuilder sb = new StringBuilder(nick);
                while (sb.length() < 15) {
                    sb.append(" "); // Rellenar con espacios
                }
                jugadoresGuardados.writeChars(sb.toString()); // Guardar el nick en caracteres

                // Escribir los otros valores del jugador
                jugadoresGuardados.writeInt(jugador.getExperience());
                jugadoresGuardados.writeInt(jugador.getLifeLevel());
                jugadoresGuardados.writeInt(jugador.getCoins());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los jugadores: " + e.getMessage());
        }
    }

}
