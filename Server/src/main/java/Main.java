import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bonnet Kilian
 * @author IMAMI Ayoub
 *
 * VOD_RMI_project
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Create RMI registry
            Registry registry = LocateRegistry.createRegistry(2000);

            // Creating remote object
            Connexion connStub = new Connexion(2001);

            // Binding the stub in the registry
            registry.rebind("IConnection", connStub);

            System.out.println("Server ready!");

            // Initializing the movies catalogue: filling up the list of Movies
            Movies movies = new Movies();
            movies.initializeMoviesCatalogue();

            // Serializing the movies: filling up the json
            JsonHelper jsonHelper = new JsonHelper();
            jsonHelper.serializeMovies(movies.getMoviesList());

            Client clienta = new Client("mail", "password");
            Client clientb = new Client("mailb", "passwordbbb");
            List<Client> clientList = new ArrayList<>();
            clientList.add(clienta);
            clientList.add(clientb);
            jsonHelper.serializeClients(clientList);

        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
