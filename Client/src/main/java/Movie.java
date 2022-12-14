import java.io.Serializable;

public class Movie extends MovieDesc implements Serializable {

    //chunk used to stream the movie
    private byte[] chunk;

    //empty constructor needed for JsonHelper
    Movie() {}

    Movie(String movieName, String isbn, String synopsis, byte[] chunk) {
        super(movieName, isbn, synopsis);
        this.chunk = chunk;
    }

    public byte[] getChunk() {
        return chunk;
    }

    /**
     * Setter is mandatory for Jackson serialisation
     * @param chunk The given chuck
     */
    public void setChunk(byte[] chunk) {
        this.chunk = chunk;
    }
}
