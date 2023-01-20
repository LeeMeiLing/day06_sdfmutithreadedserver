package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CookieClientHandler implements Runnable {

    final Socket socket; // use final Socket socket; dont assign null if use final

    public CookieClientHandler(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run(){

        Cookie cookie = new Cookie();

        // read from cookie file & store in " List<String> cookieItems " of the cookie instance
        try{
            cookie.readCookieFile();
        }catch (IOException e){
            e.printStackTrace();
        }


        try(InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            String msgReceived = "";

            
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            while(!msgReceived.equals("close")){
                System.out.println(Thread.currentThread().getName() + " is running - " + "Socket: " + this.socket);
                msgReceived = dis.readUTF();

                if(msgReceived.equalsIgnoreCase("get-cookie")){
                    String cookieValue = cookie.returnCookie();
                    dos.writeUTF(cookieValue);
                    dos.flush();
                }else if(msgReceived.equalsIgnoreCase("close")){
                    dos.writeUTF("Closing program.");
                    dos.flush();
                    socket.close();
                }
                else{
                    dos.writeUTF("Invalid command. Please enter 'get-cookie' to get a fortune cookie or 'close' to exit program.");
                    dos.flush();
                }
            }

            dos.close();
            bos.close();
            os.close();

            dis.close();
            bis.close();
            is.close();

            socket.close();

        }catch(IOException ex){
           
            if(socket != null){
                try{
                    socket.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

}

