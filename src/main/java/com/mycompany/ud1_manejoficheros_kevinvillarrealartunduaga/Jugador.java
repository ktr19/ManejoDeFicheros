package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import java.io.Serializable;

public class Jugador implements Serializable {
    private int id;  // Eliminamos la lógica de autoincremento aquí
    private String nick;
    private int experience;
    private int lifeLevel;
    private int coins;

    // Constructor con ID (usado cuando cargamos jugadores desde el archivo)
    public Jugador(int id, String nick, int experience, int lifeLevel, int coins) {
        this.id = id;
        this.nick = nick;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
    }

    // Constructor sin ID (usado al crear un nuevo jugador sin saber el ID aún)
    public Jugador(String nick, int experience, int lifeLevel, int coins) {
        this.nick = nick;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLifeLevel() {
        return lifeLevel;
    }

    public void setLifeLevel(int lifeLevel) {
        this.lifeLevel = lifeLevel;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return String.format("[ID = %d, Nick = %s, Experience = %d, Life Level = %d, Coins = %d]", 
                              id, nick, experience, lifeLevel, coins);
    }
}
