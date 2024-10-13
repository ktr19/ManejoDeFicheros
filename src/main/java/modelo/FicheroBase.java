package modelo;

import modelo.Jugador;
import modelo.JugadorDAO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta base para gestionar jugadores desde archivos. Define la
 * estructura común para el manejo de listas de jugadores y la interacción con
 * los archivos.
 */
public abstract class FicheroBase implements JugadorDAO {

    protected List<Jugador> jugadores = new ArrayList<>();
    protected String filePath;

    /**
     * Constructor que recibe la ruta del archivo a manejar. Carga los jugadores
     * existentes al iniciar.
     *
     * @param filePath Ruta del archivo donde se almacenan los datos de
     * jugadores.
     */
    public FicheroBase(String filePath) {
        this.filePath = filePath;
        this.jugadores = cargarJugadores();
    }

    /**
     * Método abstracto que debe implementar la lógica para cargar los jugadores
     * desde el archivo correspondiente.
     *
     * @return Una lista con los jugadores cargados.
     */
    protected abstract List<Jugador> cargarJugadores();

    /**
     * Método abstracto que debe implementar la lógica para guardar los
     * jugadores en el archivo correspondiente.
     */
    protected abstract void guardarJugadores();

    /**
     * Crea un nuevo jugador después de realizar una serie de validaciones sobre
     * los atributos del mismo.
     *
     * @param jugador Objeto jugador que se desea añadir.
     */
    @Override
    public void crearJugador(Jugador jugador) {
        // Validación del nick
        if (jugador.getNick() == null || jugador.getNick().isEmpty()) {
            System.out.println("[Error]: El nick debe contener al menos un carácter.");
            return;
        }

        if (jugador.getNick().length() > 15) {
            System.out.println("[Error]: El nick no puede superar los 15 caracteres.");
            return;
        }

        // Validación de atributos
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

        // Verifica si el nick ya existe
        for (Jugador jugadorExistente : jugadores) {
            if (jugadorExistente.getNick().equalsIgnoreCase(jugador.getNick())) {
                System.out.println("[Error]: Ya existe un jugador con el nick '" + jugador.getNick() + "'");
                return;
            }
        }

        // Asigna un ID único y guarda el jugador
        jugador.setId(generarNuevoId());
        jugadores.add(jugador);
        guardarJugadores();
    }

    /**
     * Elimina un jugador de la lista según su ID.
     *
     * @param id ID del jugador que se desea eliminar.
     */
    @Override
    public void eliminarJugador(int id) {
        jugadores.removeIf(jugador -> jugador.getId() == id);
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
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                jugador.setNick(jugadorModificado.getNick());
                jugador.setExperience(jugadorModificado.getExperience());
                jugador.setLifeLevel(jugadorModificado.getLifeLevel());
                jugador.setCoins(jugadorModificado.getCoins());
                break;
            }
        }
        guardarJugadores();
    }

    /**
     * Obtiene un jugador según su ID.
     *
     * @param id ID del jugador buscado.
     * @return El jugador encontrado o null si no existe.
     */
    @Override
    public Jugador obtenerJugadorPorId(int id) {
        return jugadores.stream()
                .filter(jugador -> jugador.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Lista todos los jugadores almacenados.
     *
     * @return Una lista con todos los jugadores.
     */
    @Override
    public List<Jugador> listarJugadores() {
        return jugadores;
    }

    /**
     * Genera un nuevo ID para un jugador, tomando el valor más alto entre los
     * existentes y sumándole uno.
     *
     * @return El nuevo ID generado.
     */
    private int generarNuevoId() {
        return jugadores.stream()
                .mapToInt(Jugador::getId)
                .max()
                .orElse(0) + 1;
    }
}
