package modelo;

import java.io.Serializable;

/**
 * La clase Jugador almacena información sobre un jugador, como su ID, nick,
 * experiencia, nivel de vida y monedas. Implementa Serializable para que los
 * objetos de esta clase se puedan guardar en archivos.
 */
public class Jugador implements Serializable {

    private int id;
    private String nick;
    private int experience;
    private int lifeLevel;
    private int coins;

    /**
     * Constructor que se utiliza cuando el ID del jugador ya está definido,
     *
     *
     * @param id El identificador único del jugador.
     * @param nick El nombre del jugador
     * @param experience El nivel de experiencia del jugador.
     * @param lifeLevel El nivel de vida del jugador.
     * @param coins La cantidad de monedas que posee el jugador.
     */
    public Jugador(int id, String nick, int experience, int lifeLevel, int coins) {
        this.id = id;
        this.nick = nick;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
    }

    /**
     * Constructor para crear un nuevo jugador sin ID asignado. El ID será
     * asignado más adelante.
     *
     * @param nick El nombre del jugador.
     * @param experience El nivel de experiencia del jugador.
     * @param lifeLevel El nivel de vida del jugador.
     * @param coins La cantidad de monedas que posee el jugador.
     */
    public Jugador(String nick, int experience, int lifeLevel, int coins) {
        this.nick = nick;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
    }

    // Métodos getter y setter para acceder y modificar los atributos
    /**
     * Devuelve el ID del jugador.
     *
     * @return El ID único del jugador.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del jugador.
     *
     * @param id El nuevo identificador para el jugador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return El nombre o apodo del jugador.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Establece el nombre del jugador.
     *
     * @param nick El nuevo nombre del jugador.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Devuelve la experiencia del jugador.
     *
     * @return La cantidad de experiencia del jugador.
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Establece la experiencia del jugador.
     *
     * @param experience El nuevo nivel de experiencia del jugador.
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Devuelve el nivel de vida del jugador.
     *
     * @return El nivel de vida actual del jugador.
     */
    public int getLifeLevel() {
        return lifeLevel;
    }

    /**
     * Establece el nivel de vida del jugador.
     *
     * @param lifeLevel El nuevo nivel de vida del jugador.
     */
    public void setLifeLevel(int lifeLevel) {
        this.lifeLevel = lifeLevel;
    }

    /**
     * Devuelve la cantidad de monedas del jugador.
     *
     * @return El número de monedas del jugador.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Establece la cantidad de monedas del jugador.
     *
     * @param coins La nueva cantidad de monedas.
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Devuelve un String, mostrando todos sus atributos clave.
     *
     * @return Cadena que representa los atributos del jugador.
     */
    @Override
    public String toString() {
        return String.format("[ID = %d, Nick = %s, Experience = %d, Life Level = %d, Coins = %d]",
                id, nick, experience, lifeLevel, coins);
    }
}
