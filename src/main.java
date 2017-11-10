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


        String test = "";
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


            if ((Integer.parseInt(mem[i]) > -32768 && (Integer.parseInt(mem[i])) < 32767))
            {
                System.out.println("fill : " + Integer.parseInt(mem[i]));
            }
            else {
                switch (op){
                    case "000" :
                        test = "add";

                        break;
                    case "001" :
                        test = "nand";
                        break;
                    case "010" :
                        test = "lw";
                        break;
                    case "011" :
                        test = "sw";
                        break;
                    case "100" :
                        test = "beq";
                        break;
                    case "101" :
                        test = "jalr";
                        break;
                    case "110" :
                        test = "halt";
                        break;
                    case "111" :
                        test = "noop";
                        break;
                    default:
                        System.out.println("Error : Put wrong OP CODE");
                }
                System.out.println(test);
            }


            //System.out.println("opcode : " + op + " rs : " + rs + " rt : " + rt);
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
