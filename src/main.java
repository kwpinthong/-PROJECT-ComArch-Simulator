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
        String bin ;
        String rs;
        String rt;
        String rd;
        String op;

        //----Read file and input each line in array-list----//
        Scanner file = new Scanner(new File("output.txt"));
        List<String> line = new ArrayList<>();
        while (file.hasNextLine()){line.add(file.nextLine());}
        String[] mem = line.toArray(new String[0]); //Add every lines form array-list to array
        //---------------------------------------------------//
        for(int i = 0 ; i < mem.length ; i++){
            bin = toBinary(Integer.toBinaryString(Integer.parseInt(mem[i])));

            op = bin.substring(7,10);
            rs = bin.substring(10,13);
            rt = bin.substring(13,16);

            System.out.println("opcode : " + op + " rs : " + rs + " rt : " + rt);
            //System.out.println("op code : " + op + "bin left : " + bin);
        }
    }

    public static String toBinary(String int1){
        int count;
        String tmp = int1;
        count = tmp.length();
        while (count < 32) {
            tmp = "0" + tmp;
            count++;
        }
        return tmp;
    }
}
