package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;


import java.util.List;

public interface JugadorDAO{
    void crearJugador(Jugador jugador);
    void eliminarJugador(int id);
    void modificarJugador(int id, Jugador jugadorModificado);
    Jugador obtenerJugadorPorId(int id);
    List<Jugador> listarJugadores();
}
