import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static java.lang.System.exit;

/**
 * @author Bonnet Kilian
 * @author IMAMI Ayoub
 *
 * VOD_RMI_project
 */
public class Main {

    public static void main(String[] args) {
        try{
            // Retrieve RMI registry
            Registry registry = LocateRegistry.getRegistry(2000);

            // Searching the remote object stub on the registry
            IConnection connectionStub = (IConnection) registry.lookup("IConnection");

            // Adding user interface
            UserInterface userInterface = new UserInterface();

            // UserUI login loop
            IVODService vodServiceStub = null;
            while(vodServiceStub == null){
                int answer = userInterface.loginRegisterAsk();
                if (answer == 0) return;

                String[] mailPass = userInterface.getMailPass();
                if(answer == 1) {
                    vodServiceStub = connectionStub.login(mailPass[0], mailPass[1]);
                    if(vodServiceStub != null)
                        System.out.println("You are logged in!");
                    else
                        System.out.println("Access denied: wrong mail or password!");
                }

                if(answer == 2){
                    if (connectionStub.signIn(mailPass[0], mailPass[1]))
                        System.out.println("Successfully registered");
                    else
                        System.out.println("Mail already exists!");
                }
            }

            System.out.println();

            // Client provides a stub of its "internet boxStub" (for the server to stream back the video)
            IClientBox boxStub = new ClientBox(2003);

            // UserUI VodService loop
            while(true){
                int answer = userInterface.serviceAsk();
                if (answer == 0) exit(0); //exiting the program

                if(answer == 1) {
                    List<MovieDesc> catalog = vodServiceStub.viewCatalog();
                    userInterface.printCatalog(catalog);
                    String selectedMovie = userInterface.selectMovie(catalog);
                    Bill movieBill = vodServiceStub.playMovie(selectedMovie, boxStub);
                    System.out.println("(A payment of " + movieBill.getOutrageousPrice() + " euros has been done for this movie)\n");
                }
            }

        }
        catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }

    }
}
