import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ASUS on 10/27/2017.
 */
public class main {
    public static void main(String[] args) throws IOException {
        //----Read file and input each line in array-list----//
        Scanner file = new Scanner(new File("output.txt"));
        List<String> line = new ArrayList<>();
        while (file.hasNextLine()){line.add(file.nextLine());}
        String[] mem = line.toArray(new String[0]); //Add every lines form array-list to array
        //---------------------------------------------------//
        for(int i = 0 ; i < mem.length ; i++){
            System.out.println(mem[i]);
        }
    }
}
