package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import java.io.IOException;

public class FicheroConfig {

    public static JugadorDAO obtenerServicio(String tipoFichero) throws IOException {
        switch (tipoFichero.toLowerCase()) {
            case "texto":
                return new FicheroTexto();  // Maneja archivos de texto
            case "binario":
                return new FicheroBinario();  // Maneja archivos binarios
            case "objeto":
                return new FicheroObjeto();  // Maneja objetos binarios
            case "xml":
                return new FicheroXML();  // Maneja archivos XML
            case "aleatorio":
                return new FicheroAleatorio();
            default:
                throw new IllegalArgumentException("Tipo de fichero no soportado: " + tipoFichero);
        }
    }
}
