// A Java program for a Client
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream in = null;

    private DataOutputStream out = null;

    public Client(String address, int port) throws IOException {
        // Establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to server");

            input = new DataInputStream(System.in);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));


            // Sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }

        // String to read message from user input
        String line = "";
        int number = 0;

        while(!(line.equalsIgnoreCase("no")))
        {
            System.out.println("Choose an option by entering corresponding number. " + '\n' + "1. Create a contact." + '\n' +  "2. Print book. " +
                    '\n' + "3. Sort book" + '\n' + "4. Delete a contact");
            line = input.readLine();
            out.writeUTF(line);

            try {
                if(line.equalsIgnoreCase("1")) {
                        System.out.println("enter their first name");
                        line = input.readLine();
                        out.writeUTF(line);

                        System.out.println("enter their number");
                        number = input.readInt();
                        out.writeInt(number);

                        System.out.println("enter their address");
                        line = input.readLine();
                        out.writeUTF(line);
                }
                else if(line.equalsIgnoreCase("2"))
                {
                    int sizeofBook = in.readInt();
                    if(sizeofBook == 0)
                        System.out.println("BOOK IS EMPTY");
                    else {
                        for(int i = 0; i < sizeofBook; i++) {
                            String output = in.readUTF();
                            System.out.println(output);
                        }
                    }
                }
                else if (line.equalsIgnoreCase("3"))
                {
                    System.out.println("Contact book has been sorted alphabetically");
                }
                else if (line.equalsIgnoreCase("4"))
                {
                    System.out.println("What is the name of the contact you want to delete?");
                    line = input.readLine();
                    out.writeUTF(line);
                    System.out.println(line + " has been removed from your contact book.");
                }


                System.out.println("Would you like to continue? Yes or no?");
                line = input.readLine();
                out.writeUTF(line);

            }
            catch (IOException i) {
                System.out.println(i);
            }

        }
        // Close the connections
        try {
            input.close();
            out.close();
            in.close();
            socket.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    //Main function
    public static void main(String args[]) throws IOException {
        Client client = new Client("127.0.0.1", 5000);
    }
}