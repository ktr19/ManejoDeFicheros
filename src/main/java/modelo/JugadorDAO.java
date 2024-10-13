package modelo;

import modelo.Jugador;
import java.util.List;

/**
 * La interfaz JugadorDAO define los métodos que se necesitan para trabajar con
 * los jugadores en nuestro sistema. Cualquier clase que implemente esta
 * interfaz deberá contener dichos metodos.
 */
public interface JugadorDAO {

    /**
     * Añade un nuevo jugador al sistema.
     *
     * @param jugador El jugador que queremos crear.
     */
    void crearJugador(Jugador jugador);

    /**
     * Elimina a un jugador del sistema usando su ID.
     *
     * @param id El ID del jugador que queremos eliminar.
     */
    void eliminarJugador(int id);

    /**
     * Actualiza la información de un jugador existente.
     *
     * @param id El ID del jugador que queremos modificar.
     * @param jugadorModificado Un objeto Jugador con los nuevos datos.
     */
    void modificarJugador(int id, Jugador jugadorModificado);

    /**
     * Obtiene la información de un jugador específico por su ID.
     *
     * @param id El ID del jugador que queremos obtener.
     * @return El jugador correspondiente al ID, o null si no se encuentra.
     */
    Jugador obtenerJugadorPorId(int id);

    /**
     * Devuelve una lista con todos los jugadores en el sistema.
     *
     * @return Una lista de jugadores.
     */
    List<Jugador> listarJugadores();
}
