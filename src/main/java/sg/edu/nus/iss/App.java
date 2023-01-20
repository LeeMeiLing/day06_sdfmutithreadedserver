package sg.edu.nus.iss;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws IOException{
        
        String dirPath = "\\data2";

        File newDirectory = new File(dirPath);

        if(newDirectory.exists()){
            System.out.println("Directory already exists");
        }else{
            newDirectory.mkdir();
        }

        
        // establish connection and wait for client to connect

        try( ServerSocket serversocket = new ServerSocket(7000);){
            
            while(true){
                
                Socket socket = serversocket.accept();
                System.out.println("New client connected");

                CookieClientHandler clienthandler = new CookieClientHandler(socket);
                Thread thread = new Thread(clienthandler);
                thread.start();
            }
                
        }
        
    }
}
