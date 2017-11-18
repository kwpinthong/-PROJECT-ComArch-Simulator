import instruction.add;
import instruction.nand;

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

        int Register[] = new int[8];

        System.out.println("Example Run of Simulator");

        String test = "";
        //----Read file and input each line in array-list----//
        Scanner file = new Scanner(new File("output.txt"));
        List<String> line = new ArrayList<>();
        while (file.hasNextLine()){line.add(file.nextLine());}
        String[] mem = line.toArray(new String[0]); //Add every lines form array-list to array
        //---------------------------------------------------//
        for(int i = 0 ; i < mem.length ; i++){
            System.out.println("@@@");
            System.out.println("state:");
            bin = toBinary(Integer.toBinaryString(Integer.parseInt(mem[i])));

            op = bin.substring(7,10);



            if ((Integer.parseInt(mem[i]) > -32768 && (Integer.parseInt(mem[i])) < 32767))
            {
                System.out.println("fill : " + Integer.parseInt(mem[i]));
            }
            else {
                switch (op){
                    case "000" :
                        //test = "add";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        add adds = new add(rs);

                        break;
                    case "001" :
                        //test = "nand";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        nand nand;
                        break;
                    case "010" :
                        //test = "lw";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        break;
                    case "011" :
                        //test = "sw";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        break;
                    case "100" :
                        //test = "beq";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        break;
                    case "101" :
                        //test = "jalr";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
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
                //System.out.println(test);
                for (int j = 0; j<mem.length;j++)
                    System.out.println("    memory[" + j + "]" + "= " + mem[j] );
                System.out.println("registers:");
                for (int k= 0; k<Register.length;k++) {
                    System.out.println("    register[" + k + "]" + "= " + Register[k]);
                }
            }

            System.out.println("end state\n");



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
