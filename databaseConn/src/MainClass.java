import PresentationLayer.FileParser;

import java.io.File;
import java.io.FileNotFoundException;


public class MainClass {
    public static void main(String[] args)  {

        try {
            File myObj = new File(args[0]);
            FileParser f = new FileParser();
            f.readFromFile(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("here");
        }
    }
}
