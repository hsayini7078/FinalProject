// A Java program for a Server
import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
    //Create an empty list
    List<Person> list = new ArrayList<Person>(10);

    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    private DataOutputStream out       =  null;

    //Method to create connection
    public Server(int port) throws IOException
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started up");
            System.out.println("Waiting for a client... ");
            socket = server.accept();
            System.out.println("Accepted connection from the client");
            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            // Used to send output
            out = new DataOutputStream(socket.getOutputStream());
            String line = "";
            String name = "";
            int number = 0;

            // Will read until user decides to stop
            while (!(line.equalsIgnoreCase("No")))
            {
                line = in.readUTF();
                System.out.println("USER CHOICE WAS: " + line);
                try
                {
                    if(line.equalsIgnoreCase("1")){
                        list.add(createPerson(in));
                    } else if (line.equalsIgnoreCase("2")) {
                        printBook((ArrayList<Person>) list, out);
                    } else if (line.equalsIgnoreCase("3")) {
                        list = sortBook((ArrayList<Person>) list);
                    } else if (line.equalsIgnoreCase("4")) {
                        String namee = in.readUTF();
                        list = deleteContact((ArrayList<Person>) list, namee);
                    }

                    line = in.readUTF();
                }
                catch(IOException i)
                {
                    System.out.println(line);
                }
            }
            System.out.println("Closing connection");

            // close connections
            socket.close();
            in.close();
            out.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    class SortByName implements Comparator<Person> {

        public int compare(Person a, Person b)
        {
            return a.getName().compareTo(b.getName());
        }
    }

    public ArrayList<Person> deleteContact(ArrayList<Person> list, String name) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equalsIgnoreCase(name))
                list.remove(i);
        }
        return list;
    }

    public Person createPerson(DataInputStream in) throws IOException {
        //Get name
        String name = in.readUTF();

        //Get number
        int number = in.readInt();
        String address = in.readUTF();

        Person person1 = new Person(name, number, address);
        return person1;
    }

    public void printBook(ArrayList<Person> list, DataOutputStream out) throws IOException {
        out.writeInt(list.size());
        if(list.size() > 0) {
            for(int i = 0; i < list.size(); i++) {
                out.writeUTF(list.get(i).toString());
            }
        }
    }

    public ArrayList<Person> sortBook(ArrayList<Person> list) throws IOException {
        Collections.sort(list, new SortByName());
        return list;
    }

    public static void main(String args[]) throws IOException {
        Server server = new Server(5000);
    }
}