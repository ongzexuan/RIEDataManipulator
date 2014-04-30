/**
 * Created by ongzexuan on 27/4/14.
 */
public class DataUnit {

    private String uID="",name="",pID="",Category="",Title="",Desc1="",Desc2="",Award="";
    private int Year = 0;

    public DataUnit(String uID, String name, String pID, String Category, String Title, String Desc1, String Desc2, String Award, int Year) {
        this.uID = uID;
        this.name = name;
        this.pID = pID;
        this.Category = Category;
        this.Title = Title;
        this.Desc1 = Desc1;
        this.Desc2 = Desc2;
        this.Award = Award;
        this.Year = Year;
    }

    public String toString() {
        return uID+", "+name+", "+pID+", "+Category+", "+Title+", "+Desc1+", "+Desc2+", "+Award+", "+Year;
    }

    //GETTERS
    public String getUID() {
        return this.uID;
    }
    public String getName() {
        return this.name;
    }
    public String getPID() {
        return this.pID;
    }
    public String getCategory() {
        return this.Category;
    }
    public String getTitle() {
        return this.Title;
    }
    public String getDesc1() {
        return this.Desc1;
    }
    public String getDesc2() {
        return this.Desc2;
    }
    public String getAward() {
        return this.Award;
    }
    public int getYear() {
        return this.Year;
    }

    //SETTERS
    public void setuID(String s) {
        this.uID = s;
    }
    public void setName(String s) {
        this.name = s;
    }
    public void setpIDID(String s) {
        this.pID = s;
    }
    public void setCategory(String s) {
        this.Category = s;
    }
    public void setTitle(String s) {
        this.Title = s;
    }
    public void setDesc1(String s) {
        this.Desc1 = s;
    }
    public void setDesc2(String s) {
        this.Desc2 = s;
    }
    public void setAward(String s) {
        this.Award = s;
    }
    public void setYear(int i) {
        this.Year = i;
    }

}
