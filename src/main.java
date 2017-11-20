import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.functions.FuncSubstring;
import instruction.add;
import instruction.nand;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
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
        String offset;
        int state=0;

        int state_num=1;
        int Register[] = new int[8];

        //Rule 7(simulator) Initialize all Register to 0
        for(int i = 0;i<Register.length;i++) Register[i] = 0;

        System.out.println("Example Run of Simulator");

        String test = "";
        //----Read file and input each line in array-list----//
        Scanner file = new Scanner(new File("output.txt"));
        List<String> line = new ArrayList<>();
        while (file.hasNextLine()){line.add(file.nextLine());}
        String[] mem = line.toArray(new String[0]); //Add every lines form array-list to array
        for (int i=0;i<mem.length;i++) {
            System.out.println("mem[ "+i+" ] " + mem[i]);
        }
        //---------------------------------------------------//
        for(; state < mem.length ; state++){
            printState(mem,Register,state);

            bin = toBinary(Integer.toBinaryString(Integer.parseInt(mem[state])));

            op = bin.substring(7,10);


            if ((Integer.parseInt(mem[state]) > -32768 && (Integer.parseInt(mem[state])) < 32767))
            {
                break;
            }
            else {
                switch (op){
                    case "000" :
                        //test = "add";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        rd = bin.substring(29,32);
                        //System.out.println(Register[Integer.parseInt(rt)]);
                        int tmprs = Register[convertBinarytoDecimal(rs)];
                        int tmprt = Register[convertBinarytoDecimal(rt)];

                        //System.out.println(tmprs);
                        add adds = new add(tmprs,tmprt);
                        Register[Integer.parseInt(rd)] = adds.doAdd();
                        //Done
                        break;
                    case "001" :
                        //test = "nand";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        rd = bin.substring(29,32);



                        nand nand;
                        break;
                    case "010" :
                        //test = "lw";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        offset = bin.substring(16,32);

                        Register[convertBinarytoDecimal(rt)] = Integer.parseInt(mem[convertBinarytoDecimal(offset) + Register[convertBinarytoDecimal(rs)]]);
                        //Done
                        break;
                    case "011" :
                        //test = "sw";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        offset = bin.substring(16,32);
                        break;
                    case "100" :
                        //test = "beq";
                        rs = bin.substring(10,13);
                        rt = bin.substring(13,16);
                        offset = bin.substring(16,32);
                        //System.out.println(bin.substring(16,32));
                        //offset = convertBinarytoDecimal(bin.substring(16,32));

                        if(Register[convertBinarytoDecimal(rs)] == Register[convertBinarytoDecimal(rt)]) state = state + convertBinarytoDecimal(offset);
                        else break;

                    case "101" :
                        //test = "jalr";
                        rs = bin.substring(10,13);
                        rd = bin.substring(13,16);
                        break;
                    case "110" :
                        test = "halt";
                        System.out.println("machine halted");
                        System.out.println("total of "+ state_num +" instructions executed\n");
                        System.out.println("final state of machine:\n");

                        break;
                    case "111" :
                        test = "noop";
                        break;
                    default:
                        System.out.println("Error : Put wrong OP CODE");
                }

            }


            state_num++;


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
        //System.out.println(tmp);
        return tmp;
    }
    
    public static int convertBinarytoDecimal(String num)
    {
        int dec = 0;
        long number = Long.parseLong(num,10) ;
        long remainder;
        for (int i=0;number != 0;i++)
        {
            remainder = number %10;
            number /= 10;

            if(i==num.length()-1)dec += (int)remainder* Math.pow(-2,i);
            else dec += (int)remainder* Math.pow(2,i);
        }
        return dec;
    }

    public static String convertNum(String num)
    {
        String tmp=num;
        for (int i = num.length(); i<32;i++)
        {
            tmp = num.substring(0,1) + tmp;
        }

        return tmp;
    }

    public static void printState(String []mem,int []Register,int count){

        System.out.println("\n@@@\n" + "\tpc " + count +"\n\tmemory:");
        for (int i=0;i<mem.length;i++) {
            System.out.println("\t\tmem[ "+i+" ] " + mem[i]);
        }
        System.out.println("\tregister:");
        for (int i=0;i<Register.length;i++) {
            System.out.println("\t\treg[ "+i+" ] " + Register[i]);
        }
        System.out.println("end state");
    }

}
