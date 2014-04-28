import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;


/**
 * Created by ongzexuan on 27/4/14.
 */
public class RIEDataManipulatorManager {

    //BOOLEANS
    //private boolean uIDb, nameb, pIDb, Categoryb, Titleb, Desc1b, Desc2b, Awardb, Yearb;


    private String url = "jdbc:mysql://lorongpisang.dyndns.org:3306/rie_transcript";
    private String user = "maki";
    private String pass = "bestgirl";
    private String query = "SELECT * FROM Students s, Records r, Student_Records sr WHERE s.user_ID = sr.uID AND r.project_ID = sr.pID;";

    public RIEDataManipulatorManager() {
        //uIDb = nameb = pIDb = Categoryb = Titleb = Desc1b = Desc2b = Awardb = Yearb = true;

        //getConnection(url, user, pass, query);
        //getColumns();
        //getData();

    }

    public ArrayList<DataUnit> getConnection(String url, String user, String pass, String query) {

        //should try validating the input first
        //System.out.println("getConnection()");

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection(url,user,pass);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Connection Successful!");

            ArrayList<DataUnit> dataArray = new ArrayList<DataUnit>();

            while(resultSet.next()) {
                String uID = resultSet.getString("uID");
                String name = resultSet.getString("name");
                String pID = resultSet.getString("pID");
                String Category = resultSet.getString("Category");
                String Title = resultSet.getString("Title");
                String Desc1 = resultSet.getString("Desc1");
                String Desc2 = resultSet.getString("Desc2");
                String Award = resultSet.getString("Award");
                int Year = resultSet.getInt("Year");

                if (uID==null) uID = "";
                if (name==null) name = "";
                if (pID==null) pID = "";
                if (Category==null) Category = "";
                if (Title==null) Title = "";
                if (Desc1==null) Desc1 = "";
                if (Desc2==null) Desc2 = "";
                if (Award==null) Award = "";



                DataUnit du = new DataUnit(uID,name,pID,Category,Title,Desc1,Desc2,Award,Year);
                //System.out.println(du.toString());
                dataArray.add(du);
            }


            connection.close();

            return dataArray;
        } catch(Exception e) {
            e.printStackTrace();
        }


        return null;

    }

    //Method to get the header names
    public String[] getColumns(MetadataUnit mu) {
        //System.out.println("getColumns()");

        ArrayList<String> als = new ArrayList<String>();
        if (mu.getUIDc()) als.add("uID");
        if (mu.getNamec()) als.add("Name");
        if (mu.getPIDc()) als.add("pID");
        if (mu.getCategoryc()) als.add("Category");
        if (mu.getTitlec()) als.add("Title");
        if (mu.getDesc1c()) als.add("Desc1");
        if (mu.getDesc2c()) als.add("Desc2");
        if (mu.getAwardc()) als.add("Award");
        if (mu.getYearc()) als.add("Year");

        String[] s = new String[als.size()];
        int i = 0;
        while(!als.isEmpty()) {
            s[i++] = als.remove(0);
        }

        /*for (int j = 0; j < s.length; j++) {
            System.out.println(s[j]);
        }*/

        return s;
    }

    //Method to get the data for the JTable
    //It is implicit that the number of true booleans for both getData and getColumns are the same. Both methods should be run consecutively.
    public String[][] getData(MetadataUnit mu) {
        //System.out.println("getData()");

        ArrayList<DataUnit> ald = getConnection(url, user, pass, query);

        //count for number of columns
        /*int i = 0;
        if (mu.getUIDc()) i++;
        if (mu.getNamec()) i++;
        if (mu.getPIDc()) i++;
        if (mu.getCategoryc()) i++;
        if (mu.getTitlec()) i++;
        if (mu.getDesc1c()) i++;
        if (mu.getDesc2c()) i++;
        if (mu.getAwardc()) i++;
        if (mu.getAwardc()) i++;*/

        String[][] s = new String[ald.size()][mu.countTrue()];
        //int r = ald.size();
        //int c = i;

        int i = 0;//count rows
        while(!ald.isEmpty()) {
            int j = 0;
            DataUnit du = ald.remove(0);
            if (mu.getUIDc()) s[i][j++] = du.getUID();
            if (mu.getNamec()) s[i][j++] = du.getName();
            if (mu.getPIDc()) s[i][j++] = du.getPID();
            if (mu.getCategoryc()) s[i][j++] = du.getCategory();
            if (mu.getTitlec()) s[i][j++] = du.getTitle();
            if (mu.getDesc1c()) s[i][j++] = du.getDesc1();
            if (mu.getDesc2c()) s[i][j++] = du.getDesc2();
            if (mu.getAwardc()) s[i][j++] = du.getAward();
            if (mu.getYearc()) s[i][j++] = Integer.toString(du.getYear());

            i++;
        }

        /*for (int j = 0; j < r; j++) {
            System.out.println("Printing Row "+j);
            for (int k = 0; k < c; k++) {
                System.out.println(s[j][k]);
            }

        }*/

        return s;
    }

    //STATE SETTERS
    /*public void setStateUID(boolean b) {
        uIDb = b;
    }
    public void setStateName(boolean b) {
        nameb = b;
    }
    public void setStatePID(boolean b) {
        pIDb = b;
    }
    public void setStateCategory(boolean b) {
        Categoryb = b;
    }
    public void setStateTitle(boolean b) {
        Titleb = b;
    }
    public void setStateDesc1(boolean b) {
        Desc1b = b;
    }
    public void setStateDesc2(boolean b) {
        Desc2b = b;
    }
    public void setStateAward(boolean b) {
        Awardb = b;
    }
    public void setStateYear(boolean b) {
        Yearb = b;
    }*/

}
