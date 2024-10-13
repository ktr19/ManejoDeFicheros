package modelo;

import modelo.Jugador;
import modelo.JugadorDAO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class FicheroBase implements JugadorDAO {
    protected List<Jugador> jugadores = new ArrayList<>();
    protected String filePath;

    public FicheroBase(String filePath) {
        this.filePath = filePath;
        this.jugadores = cargarJugadores();
    }

    // Declarar los métodos abstractos para cargar y guardar jugadores
    protected abstract List<Jugador> cargarJugadores();
    protected abstract void guardarJugadores();

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
    }


    @Override
    public void eliminarJugador(int id) {
        for (Jugador jugadorEliminar : jugadores) {
            if (jugadorEliminar.getId() == id) {
                jugadores.remove(jugadorEliminar);
            }
        }
        guardarJugadores();
    }

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

    @Override
    public Jugador obtenerJugadorPorId(int id) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                return jugador;
            }
        }
        return null;
    }

    @Override
    public List<Jugador> listarJugadores() {
        return jugadores;
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
