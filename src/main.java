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
        int sizeofline;
        String offset;
        int state=0;

        boolean ishalt=false;
        int state_num=1;
        int Register[] = new int[8];

        //Rule 7(simulator) Initialize all Register to 0
        for(int i = 0;i<Register.length;i++) Register[i] = 0;

        String test = "";
        //----Read file and input each line in array-list----//
        Scanner file = new Scanner(new File("inputComb.txt"));
        List<String> line = new ArrayList<>();
        while (file.hasNextLine()){line.add(file.nextLine());}
        String[] mem = new String[65536];
        for(int i = 0;i<mem.length;i++)mem[i] = "0";
        String[] mem1 = line.toArray(new String[0]); //Add every lines form array-list to array
        sizeofline = mem1.length;
        for(int i=0; i < mem1.length; i++) mem[i] = mem1[i];

        for (int i=0;i<mem1.length;i++) System.out.println("mem[ "+i+" ] " + mem[i]);


        //---------------------------------------------------//
        for(; state < sizeofline ; state++) {
            printState(mem, Register, state, sizeofline);
            if (ishalt){
                break;
            } else {
                bin = toBinary(Integer.toBinaryString(Integer.parseInt(mem[state])));

                op = bin.substring(7, 10);


                if ((Integer.parseInt(mem[state]) > -32768 && (Integer.parseInt(mem[state])) < 32767)) {
                    break;
                } else {
                    switch (op) {
                        case "000":
                            //test = "add";
                            rs = bin.substring(10, 13);
                            rt = bin.substring(13, 16);
                            rd = bin.substring(29, 32);
                            //System.out.println("this register" + rd);
                            //System.out.println(Register[Integer.parseInt(rt)]);
                            int tmprs = Register[convertBinarytoDecimal(rs)];
                            int tmprt = Register[convertBinarytoDecimal(rt)];
                            Register[convertBinarytoDecimal(rd)] = tmprs + tmprt;

                            //Done
                            break;
                        case "001":
                            //test = "nand";
                            rs = bin.substring(10, 13);
                            rt = bin.substring(13, 16);
                            rd = bin.substring(29, 32);

                            int valrs = Register[convertBinarytoDecimal(rs)];
                            int valrt = Register[convertBinarytoDecimal(rt)];


                            Register[convertBinarytoDecimal(rd)] = ~(valrs & valrt);

                            break;
                        case "010":
                            //test = "lw";
                            rs = bin.substring(10, 13);
                            rt = bin.substring(13, 16);
                            offset = bin.substring(16, 32);

                            Register[convertBinarytoDecimal(rt)] = Integer.parseInt(mem[twosCompliment(offset) + Register[convertBinarytoDecimal(rs)]]);
                            //Done
                            break;
                        case "011":
                            //test = "sw";

                            rs = bin.substring(10, 13);
                            rt = bin.substring(13, 16);
                            offset = bin.substring(16, 32);


                            mem[convertBinarytoDecimal(rs) + twosCompliment(offset)] = String.valueOf(Register[convertBinarytoDecimal(rt)]);
                            break;
                        case "100":
                            //test = "beq";
                            rs = bin.substring(10, 13);
                            rt = bin.substring(13, 16);
                            offset = bin.substring(16, 32);

                            if (rs == rt) {
                                state = state + twosCompliment(offset);
                                break;
                            } else break;

                        case "101":
                            //test = "jalr";
                            rs = bin.substring(10, 13);
                            rd = bin.substring(13, 16);

                            Register[convertBinarytoDecimal(rd)] = state + 1;
                            if (rs == rd) {
                                break;
                            } else {
                                state = Register[convertBinarytoDecimal(rs)];
                            }
                        case "110":
                            test = "halt";
                            ishalt =true;
                            System.out.println("machine halted");
                            System.out.println("total of " + state_num + " instructions executed\n");
                            System.out.println("final state of machine:\n");

                            break;
                        case "111":
                            test = "noop";
                            break;
                        default:
                            System.out.println("Error : Put wrong OP CODE");
                    }


                }


                state_num++;


            }

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
    
    public static int convertBinarytoDecimal(String num)
    {
        int dec = 0;
        long number = Long.parseLong(num,10) ;
        long remainder;
        for (int i=0;number != 0;i++)
        {
            remainder = number %10;
            number /= 10;

            dec += (int)remainder* Math.pow(2,i);
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

    public static void printState(String []mem,int []Register,int count,int size){

        System.out.println("\n@@@\n" + "\tpc " + count +"\n\tmemory:");
        for (int i=0;i<size;i++) {
            System.out.println("\t\tmem[ "+i+" ] " + mem[i]);
        }
        System.out.println("\tregister:");
        for (int i=0;i<Register.length;i++) {
            System.out.println("\t\treg[ "+i+" ] " + Register[i]);
        }
        System.out.println("end state");
    }

    public static int twosCompliment(String bin) {
        String bin32 = convertNum(bin);
        int tmp;
        if (Integer.parseInt(bin32.substring(0,1)) == 1)
        {
            tmp = convertBinarytoDecimal(negconvert(bin32)) + 1;
            tmp = -tmp;
        }
        else tmp = convertBinarytoDecimal(bin32);
        return tmp;
    }

    public static String negconvert(String bin)
    {
        String tmp="";
        for(int i = 0; i<bin.length();i++)
        {
            if(Integer.parseInt(bin.substring(i,i+1)) == 1) tmp +="0";
            else tmp += "1";
        }
        return tmp;
    }

}
