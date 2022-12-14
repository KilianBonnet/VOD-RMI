import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VODService extends UnicastRemoteObject implements IVODService {

    //movies in the catalog
    private List<Movie> moviesList;
    //jsonHelper to deserialize movies
    private final JsonHelper jsonHelper;

    //price per movie: every movie has the same price and each times a movie is seen, this price must be paid
    //payment not represented in this project (only a print on client side)
    private final BigInteger outrageousPrice = BigInteger.valueOf(20);

    protected VODService(int port) throws IOException {
        super(port);

        // Deserialize movies.json
        jsonHelper = new JsonHelper();
        moviesList = jsonHelper.deserializeMovies();
    }

    /**
     * Client may ask the server to get the list of MovieDesc.
     * @return The list of MovieDesc present in our server.
     */
    @Override
    public List<MovieDesc> viewCatalog() {
        System.out.println("[INFO] the client is attempting to view the catalog.");
        return new ArrayList<>(moviesList);
    }

    /**
     * Send the chunk to the client boxStub
     * @param isbn of the movie
     * @param boxStub of the client
     * @return the movie Bill
     * @throws RemoteException exception
     * @throws InterruptedException exception
     */
    @Override
    public Bill playMovie(String isbn, IClientBox boxStub) throws RemoteException, InterruptedException {
        Bill movieBill = null;

        for(Movie movie : moviesList) {
            if(movie.getIsbn().equals(isbn)) {
                System.out.println("[INFO] the client is attempting to watch " + movie.getMovieName());
                boxStub.stream(movie.getChunk());
                String name = movie.getMovieName();
                movieBill = new Bill(name, outrageousPrice);
            }
        }

        return movieBill;
    }
}

