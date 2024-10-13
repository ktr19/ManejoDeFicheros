package ficheroConfi;

import modelo.JugadorDAO;
import tipoFicheros.FicheroAleatorio;
import tipoFicheros.FicheroBinario;
import tipoFicheros.FicheroObjeto;
import tipoFicheros.FicheroTexto;
import tipoFicheros.FicheroXML;
import java.io.IOException;

/**
 * Clase encargada de configurar y devolver el servicio adecuado para el manejo
 * de archivos según el tipo especificado.
 */
public class FicheroConfig {

    /**
     * Devuelve una implementación de {@link JugadorDAO} basada en el tipo de
     * fichero proporcionado.
     *
     * @param tipoFichero Tipo de almacenamiento solicitado (texto, binario,
     * objeto, xml, aleatorio).
     * @return Una instancia de {@link JugadorDAO} correspondiente al tipo de
     * fichero.
     * @throws IOException Si ocurre un error al crear o acceder al archivo.
     * @throws IllegalArgumentException Si el tipo de fichero no es soportado.
     */
    public static JugadorDAO obtenerServicio(String tipoFichero) throws IOException {
        switch (tipoFichero.toLowerCase()) {
            case "texto":
                return new FicheroTexto();  // Maneja archivos de texto
            case "binario":
                return new FicheroBinario();  // Maneja archivos binarios
            case "objeto":
                return new FicheroObjeto();  // Maneja objetos serializados
            case "xml":
                return new FicheroXML();  // Maneja archivos en formato XML
            case "aleatorio":
                return new FicheroAleatorio();  // Maneja archivos con acceso aleatorio
            default:
                throw new IllegalArgumentException("Tipo de fichero no soportado: " + tipoFichero);
        }
    }
}
