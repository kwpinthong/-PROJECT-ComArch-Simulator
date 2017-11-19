package instruction;

/**
 * Created by ASUS on 11/10/2017.
 */
public class add {
    int result;
    int rs,rt;
    public add(int rs ,int rt ){
        this.rs = rs;
        this.rt = rt;
    }

    public int doAdd(){
        return this.rs+this.rt;
    }
}
