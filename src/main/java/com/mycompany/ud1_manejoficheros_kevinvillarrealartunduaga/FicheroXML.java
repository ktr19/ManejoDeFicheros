package com.mycompany.ud1_manejoficheros_kevinvillarrealartunduaga;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FicheroXML implements JugadorDAO {

    private static final String FILE_PATH = "jugadores.xml";
    private List<Jugador> jugadores;

    public FicheroXML() {
        crearArchivoSiNoExiste();
        this.jugadores = cargarJugadores();

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

    private void crearArchivoSiNoExiste() {
        try {
            File dir = new File("./Datos");
            if (!dir.exists()) {
                dir.mkdirs(); // Crear el directorio si no existe
                System.out.println("Directorio 'Datos' creado.");
            }

            File file = new File(dir + FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Archivo de jugadores creado: " + FILE_PATH);

                // Crear la estructura básica del XML
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.newDocument();
                Element root = document.createElement("jugadores");
                document.appendChild(root);

                // Guardar la estructura inicial en el archivo
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            System.out.println("[Error] al crear el archivo: " + e.getMessage());
        }
    }

    @Override
    public void crearJugador(Jugador jugador) {
        int nuevoId = generarNuevoId();

        // Verificar duplicados
        for (Jugador jugadorExistente : jugadores) {
            if (jugadorExistente.getNick().equalsIgnoreCase(jugador.getNick())) {
                System.out.println("[Error]: Ya existe un jugador con el nick '" + jugador.getNick() + "'");
                return;
            }
        }

        jugador.setId(nuevoId);
        jugadores.add(jugador);
        guardarJugadores(jugadores);
    }

    @Override
    public void eliminarJugador(int id) {
        for (Jugador jugadorEliminar : jugadores) {
            if (jugadorEliminar.getId() == id) {
                jugadores.remove(jugadorEliminar);
            }
        }
        guardarJugadores(jugadores);
    }

    @Override
    public void modificarJugador(int id, Jugador jugadorModificado) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                jugador.setNick(jugadorModificado.getNick());
                jugador.setExperience(jugadorModificado.getExperience());
                jugador.setLifeLevel(jugadorModificado.getLifeLevel());
                jugador.setCoins(jugadorModificado.getCoins());
            }
        }
        guardarJugadores(jugadores);
    }

    @Override
    public Jugador obtenerJugadorPorId(int id) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                // Encontramos al jugador
                return jugador;
            }
        }
        // No se encuentra
        return null;
    }

    @Override
    public List<Jugador> listarJugadores() {
        return jugadores;
    }

    private List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return jugadoresCargados; // Archivo no encontrado
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("jugador");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    String nick = element.getElementsByTagName("nick").item(0).getTextContent();
                    int experience = Integer.parseInt(element.getElementsByTagName("experience").item(0).getTextContent());
                    int lifeLevel = Integer.parseInt(element.getElementsByTagName("lifeLevel").item(0).getTextContent());
                    int coins = Integer.parseInt(element.getElementsByTagName("coins").item(0).getTextContent());
                    jugadoresCargados.add(new Jugador(id, nick, experience, lifeLevel, coins));
                }
            }
        } catch (Exception e) {
            System.out.println("[Error]No se encontró el archivo o hubo un error al cargar los jugadores.");
        }
        return jugadoresCargados;
    }

    private void guardarJugadores(List<Jugador> jugadores) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element raiz = document.createElement("jugadores");
            document.appendChild(raiz);

            for (Jugador jugador : jugadores) {
                Element jugadorElement = document.createElement("jugador");

                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(String.valueOf(jugador.getId())));
                jugadorElement.appendChild(id);

                Element nick = document.createElement("nick");
                nick.appendChild(document.createTextNode(jugador.getNick()));
                jugadorElement.appendChild(nick);

                Element experience = document.createElement("experience");
                experience.appendChild(document.createTextNode(String.valueOf(jugador.getExperience())));
                jugadorElement.appendChild(experience);

                Element lifeLevel = document.createElement("lifeLevel");
                lifeLevel.appendChild(document.createTextNode(String.valueOf(jugador.getLifeLevel())));
                jugadorElement.appendChild(lifeLevel);

                Element coins = document.createElement("coins");
                coins.appendChild(document.createTextNode(String.valueOf(jugador.getCoins())));
                jugadorElement.appendChild(coins);

                raiz.appendChild(jugadorElement);
            }

            // Guardar el documento XML actualizado
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Source source = new DOMSource(document);

            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
