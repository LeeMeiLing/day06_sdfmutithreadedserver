package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    String dirPath = "\\data2";
    String fileName = "cookie.txt";

    List<String> cookieItems= null;


    
    public void readCookieFile() throws FileNotFoundException, IOException{

        cookieItems = new ArrayList<String>();
        File file = new File(dirPath + File.separator + fileName);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String readString;

        while((readString = br.readLine()) != null){
            cookieItems.add(readString);
        }

        br.close();
    }

    public String returnCookie(){

        Random rand = new Random();

        if(cookieItems != null){
            return cookieItems.get(rand.nextInt(cookieItems.size())); //rand.nextInt(10) return 0 to 9
        }else{
            return "There is no cookie found";
        }
    }

    public void showCookies(){

        if(cookieItems != null){

            cookieItems.forEach(s -> System.out.println(s));

        }

    }

}
