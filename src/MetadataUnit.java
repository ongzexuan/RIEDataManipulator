/**
 * Created by ongzexuan on 28/4/14.
 */
public class MetadataUnit {

    private boolean uIDc=true;
    private boolean namec=true;
    private boolean pIDc=true;
    private boolean Categoryc=true;
    private boolean Titlec=true;
    private boolean Desc1c=true;
    private boolean Desc2c=true;
    private boolean Awardc=true;
    private boolean Yearc=true;
    
    
    public MetadataUnit(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, boolean i) {
        uIDc = a;
        namec = b;
        pIDc = c;
        Categoryc = d;
        Titlec = e;
        Desc1c = f;
        Desc2c = g;
        Awardc = h;
        Yearc = i;
    }
    
    public void setState(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, boolean i) {
        uIDc = a;
        namec = b;
        pIDc = c;
        Categoryc = d;
        Titlec = e;
        Desc1c = f;
        Desc2c = g;
        Awardc = h;
        Yearc = i;
    }
    public int countTrue() {
        int i = 0;
        if (uIDc) i++;
        if (namec) i++;
        if (pIDc) i++;
        if (Categoryc) i++;
        if (Titlec) i++;
        if (Desc1c) i++;
        if (Desc2c) i++;
        if (Awardc) i++;
        if (Yearc) i++;

        return i;
    }

    //GETTERS
    public boolean getUIDc() { 
        return this.uIDc; 
    }
    public boolean getNamec() {
        return this.namec;
    }
    public boolean getPIDc() {
        return this.pIDc;
    }
    public boolean getCategoryc() {
        return this.Categoryc;
    }
    public boolean getTitlec() {
        return this.Titlec;
    }
    public boolean getDesc1c() {
        return this.Desc1c;
    }
    public boolean getDesc2c() {
        return this.Desc2c;
    }
    public boolean getAwardc() {
        return this.Awardc;
    }
    public boolean getYearc() {
        return this.Yearc;
    }

    //SETTERS
    public void setuID(boolean b) {
        this.uIDc = b;
    }
    public void setName(boolean b) {
        this.namec = b;
    }
    public void setpIDID(boolean b) {
        this.pIDc = b;
    }
    public void setCategory(boolean b) {
        this.Categoryc = b;
    }
    public void setTitle(boolean b) {
        this.Titlec = b;
    }
    public void setDesc1(boolean b) {
        this.Desc1c = b;
    }
    public void setDesc2(boolean b) {
        this.Desc2c = b;
    }
    public void setAward(boolean b) {
        this.Awardc = b;
    }
    public void setYear(boolean b) {
        this.Yearc = b;
    }
    
}
