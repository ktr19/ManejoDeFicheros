package tipoFicheros;

import modelo.FicheroBase;
import modelo.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroBinario extends FicheroBase{


    public FicheroBinario() {
        super("./Datos/jugadoresBinario.dat");
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
            System.out.println("[Error] Error al crear el archivo: " + e.getMessage());
        }
    }

   

    @Override
    protected List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        int coins, experience, life_level, id;
        String nick;
        try (DataInputStream jugadoresCarga = new DataInputStream(new FileInputStream(filePath))) {
            while (true) {
                id = jugadoresCarga.readInt();
                nick = jugadoresCarga.readUTF();
                experience = jugadoresCarga.readInt();
                life_level = jugadoresCarga.readInt();
                coins = jugadoresCarga.readInt();
                Jugador jugador = new Jugador(nick, experience, life_level, coins);
                jugadoresCargados.add(jugador);
            }
        } catch (EOFException e) {
            // Fin del archivo alcanzado, se puede ignorar
        } catch (IOException e) {
            System.out.println("No se encontró el archivo o hubo un error al cargar los jugadores.");
        }
        return jugadoresCargados;
    }

    @Override
  protected void guardarJugadores() {
    try (DataOutputStream jugadoresGuardados = new DataOutputStream(new FileOutputStream(filePath))) {
        for (Jugador jugador : jugadores) {
            jugadoresGuardados.writeInt(jugador.getId());
            jugadoresGuardados.writeUTF(jugador.getNick());
            jugadoresGuardados.writeInt(jugador.getExperience());
            jugadoresGuardados.writeInt(jugador.getLifeLevel());
            jugadoresGuardados.writeInt(jugador.getCoins());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
