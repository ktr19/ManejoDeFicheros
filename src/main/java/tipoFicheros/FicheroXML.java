package tipoFicheros;

import modelo.FicheroBase;
import modelo.Jugador;
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

/**
 * Clase que maneja la lectura y escritura de jugadores en un archivo XML.
 * Extiende la clase {@link FicheroBase} para proporcionar funcionalidad específica
 * para archivos XML. 
 */
public class FicheroXML extends FicheroBase {

    /**
     * Constructor de la clase FicheroXML. 
     * Inicializa la ruta del archivo XML de jugadores y crea el archivo si no existe,
     * generando la estructura básica del XML si es necesario.
     */
    public FicheroXML() {
        super("./Datos/jugadoresXML.xml");
        crearArchivoSiNoExiste();
    }

    /**
     * Crea el directorio y el archivo XML si no existen.
     * Se asegura de que la estructura de directorios esté disponible y que el archivo XML
     * tenga una estructura básica con un nodo raíz.
     */
    private void crearArchivoSiNoExiste() {
        try {
            // Crear el directorio Datos si no existe
            File dir = new File("./Datos");
            if (!dir.exists()) {
                dir.mkdirs(); // Crear el directorio si no existe
                System.out.println("Directorio 'Datos' creado.");
            }

            // Crear el archivo XML dentro del directorio Datos
            File file = new File(filePath); // Ajustar la ruta al archivo dentro del directorio
            if (!file.exists()) {
                System.out.println("Archivo de jugadores no encontrado. Creando archivo...");

                // Crear la estructura básica del XML
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.newDocument();
                Element root = document.createElement("jugadores"); // Nodo raíz del XML
                document.appendChild(root);

                // Guardar la estructura inicial en el archivo
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Formato con sangrías
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);

                System.out.println("Archivo XML creado en: " + file.getPath());
            }
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("{Error} Error al crear la estructura XML: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("{Error} Error al crear el archivo: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de jugadores desde el archivo XML.
     * Si el archivo no existe, devuelve una lista vacía.
     *
     * @return Una lista de objetos {@link Jugador} cargados desde el archivo XML.
     */
    @Override
    protected List<Jugador> cargarJugadores() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        try {
            File file = new File(filePath);
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
            System.out.println("{Error} No se encontró el archivo o hubo un error al cargar los jugadores.");
        }
        return jugadoresCargados;
    }

    /**
     * Guarda la lista de jugadores en el archivo XML.
     * Crea un nuevo documento XML y agrega cada jugador como un nodo dentro del nodo raíz.
     */
    @Override
    protected void guardarJugadores() {
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

            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
