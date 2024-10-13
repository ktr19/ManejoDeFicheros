package modelo;

import modelo.Jugador;
import modelo.JugadorDAO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que implementa la interfaz JugadorDAO y gestiona la
 * persistencia de los jugadores en un archivo. Proporciona métodos para crear,
 * eliminar, modificar y listar jugadores, así como para cargar y guardar los
 * datos en el almacenamiento correspondiente.
 */
public abstract class FicheroBase implements JugadorDAO {

    protected List<Jugador> jugadores = new ArrayList<>();
    protected String filePath;

    /**
     * Constructor de la clase FicheroBase.
     *
     * @param filePath Ruta del archivo donde se almacenarán los datos de los
     * jugadores.
     */
    public FicheroBase(String filePath) {
        this.filePath = filePath;
        this.jugadores = cargarJugadores();
    }

    /**
     * Método abstracto que carga los jugadores desde el almacenamiento.
     *
     * @return Lista de jugadores cargados.
     */
    protected abstract List<Jugador> cargarJugadores();

    /**
     * Método abstracto que guarda los jugadores en el almacenamiento.
     */
    protected abstract void guardarJugadores();

    /**
     * Crea un nuevo jugador y lo añade a la lista de jugadores. Valida los
     * atributos del jugador antes de añadirlo.
     *
     * @param jugador Jugador a crear.
     */
    @Override
    public void crearJugador(Jugador jugador) {
        // Validar el nick antes de crear el jugador
        if (jugador.getNick() == null || jugador.getNick().isEmpty()) {
            System.out.println("[Error]: El nick debe contener al menos un carácter.");
            return;
        }

        if (jugador.getNick().length() > 15) {
            System.out.println("[Error]: El nick no puede superar los 15 caracteres.");
            return;
        }

        // Validar experiencia, nivel de vida y monedas
        if (jugador.getExperience() < 0 || jugador.getExperience() > Integer.MAX_VALUE) {
            System.out.println("[Error]: La experiencia no puede ser negativa ni superar " + Integer.MAX_VALUE);
            return;
        }

        if (jugador.getLifeLevel() < 0 || jugador.getLifeLevel() > Integer.MAX_VALUE) {
            System.out.println("[Error]: El nivel de vida no puede ser negativo ni superar " + Integer.MAX_VALUE);
            return;
        }

        if (jugador.getCoins() < 0 || jugador.getCoins() > Integer.MAX_VALUE) {
            System.out.println("[Error]: Las monedas no pueden ser negativas ni superar " + Integer.MAX_VALUE);
            return;
        }

        int nuevoId = generarNuevoId();
        for (Jugador jugadorExistente : jugadores) {
            if (jugadorExistente.getNick().equalsIgnoreCase(jugador.getNick())) {
                System.out.println("[Error]: Ya existe un jugador con el nick '" + jugador.getNick() + "'");
                return;
            }
        }

        jugador.setId(nuevoId);
        jugadores.add(jugador);

        guardarJugadores();
        System.out.println("");
        System.out.println("Jugador creado con exito");
        System.out.println("");
    }

    /**
     * Elimina un jugador de la lista de jugadores dado su ID.
     *
     * @param id ID del jugador a eliminar.
     */
    @Override
    public void eliminarJugador(int id) {
        for (Jugador jugadorEliminar : jugadores) {
            if (jugadorEliminar.getId() == id) {
                jugadores.remove(jugadorEliminar);
                System.out.println("");
                System.out.println("Jugador eliminado con exito");
                System.out.println("");
                break;
            }
        }
        guardarJugadores();
    }

    /**
     * Modifica los datos de un jugador existente.
     *
     * @param id ID del jugador a modificar.
     * @param jugadorModificado Jugador con los nuevos datos.
     */
    @Override
    public void modificarJugador(int id, Jugador jugadorModificado) {
        // Verificar si el nuevo nick ya existe en otro jugador
        for (Jugador jugadorExistente : jugadores) {
            if (jugadorExistente.getNick().equalsIgnoreCase(jugadorModificado.getNick())
                    && jugadorExistente.getId() != id) { // Comprobar que no sea el mismo jugador
                System.out.println("[Error]: Ya existe un jugador con el nick '" + jugadorModificado.getNick() + "'");
                return; // Salir si el nick ya está en uso
            }
        }

        // Si no existe un conflicto de nick, proceder a modificar
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                System.out.println(jugador.toString());
                jugador.setNick(jugadorModificado.getNick());
                jugador.setExperience(jugadorModificado.getExperience());
                jugador.setLifeLevel(jugadorModificado.getLifeLevel());
                jugador.setCoins(jugadorModificado.getCoins());
                System.out.println("");
                System.out.println("Jugador modificado con éxito");
                System.out.println("");
                System.out.println(jugador.toString());
                break;
            }
        }
        guardarJugadores();
    }

    /**
     * Obtiene un jugador a partir de su ID.
     *
     * @param id ID del jugador a obtener.
     * @return Jugador correspondiente al ID o null si no se encuentra.
     */
    @Override
    public Jugador obtenerJugadorPorId(int id) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Lista todos los jugadores registrados.
     *
     * @return Lista de jugadores.
     */
    @Override
    public List<Jugador> listarJugadores() {
        return jugadores;
    }

    /**
     * Genera un nuevo ID para un jugador basado en los IDs existentes.
     *
     * @return Un nuevo ID único para el jugador.
     */
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
