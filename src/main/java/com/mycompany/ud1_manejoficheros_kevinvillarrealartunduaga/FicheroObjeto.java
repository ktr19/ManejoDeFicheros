package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroObjeto extends FicheroBase {

    public FicheroObjeto() {
        super("./Datos/jugadoresObjetos.dat");
        crearArchivoSiNoExiste();
    }
    
    
     private void crearArchivoSiNoExiste() {
        try {
            File dir = new File("./Datos");
            if (!dir.exists()) {
                boolean dirCreado = dir.mkdirs(); // Crear el directorio si no existe
                System.out.println("Directorio 'Datos' creado: " + dirCreado);
            }

            File file = new File(filePath);
            if (!file.exists()) {
                boolean archivoCreado = file.createNewFile();
                System.out.println("Archivo de jugadores creado: " + archivoCreado + " en " + filePath);
            }
        } catch (IOException e) {
            System.out.println("[Error] Error al crear el archivo: " + e.getMessage());
        }
    }


    
   protected List<Jugador> cargarJugadores() {
    List<Jugador> jugadoresCargados = new ArrayList<>();
   File file = new File(filePath);
    // Verifica si el archivo existe
    if (!file.exists()) {
        System.out.println("El archivo no existe, no se cargarán jugadores.");
        return jugadoresCargados; // Retornar una lista vacía si el archivo no existe
    }

    // Verifica si el archivo está vacío
    if (file.length() == 0) {
        System.out.println("El archivo está vacío, no se cargarán jugadores.");
        return jugadoresCargados; // Retornar una lista vacía si el archivo está vacío
    }

    // Si el archivo existe y tiene contenido, proceder a cargar los jugadores
    try (ObjectInputStream datosJugadores = new ObjectInputStream(new FileInputStream(filePath))) {
        while (true) {
            try {
                Jugador jugador = (Jugador) datosJugadores.readObject();
                jugadoresCargados.add(jugador);
            } catch (EOFException e) {
                break; // Fin del archivo alcanzado, salir del bucle
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error al cargar los jugadores: " + e.getMessage());
    }

    return jugadoresCargados;
}



    protected void guardarJugadores() {
        try (ObjectOutputStream jugadoresGuardados = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Jugador jugador : jugadores) {
                jugadoresGuardados.writeObject(jugador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
