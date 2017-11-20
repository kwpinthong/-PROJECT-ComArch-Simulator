package instruction;

public class register {
    String r;
    int tmp;

    public register(String r)
    {
        this.r = r;
        tmp = Integer.parseInt(this.r);
        //System.out.println(tmp);
    }

    int convert(){
        return this.tmp;
    }
}
