import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Connexion extends UnicastRemoteObject implements IConnection {
    private final VODService vodService = new VODService(2002);

    protected Connexion(int port) throws RemoteException {
        super(port);
    }

    @Override
    public boolean signIn(String mail, String password) throws RemoteException {
        return false;
    }

    @Override
    public IVODService login(String mail, String password) throws RemoteException {
        return vodService;
    }
}
