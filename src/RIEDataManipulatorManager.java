import javax.swing.*;
import javax.swing.table.TableModel;
import javax.xml.transform.Result;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by ongzexuan on 27/4/14.
 */
public class RIEDataManipulatorManager {

    private String url = "jdbc:mysql://lorongpisang.dyndns.org:3306/rie_transcript";
    private String user = "maki";
    private String pass = "bestgirl";
    private String query = "SELECT * FROM Students s, Records r, Student_Records sr WHERE s.user_ID = sr.uID AND r.project_ID = sr.pID ORDER BY s.user_ID, Year;";

    ArrayList<DataUnit> currentData = null;//as an intermediate data storage unit

    public ArrayList<DataUnit> getConnection(String url, String user, String pass, String query) {

        //should try validating the input first


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

                dataArray.add(du);
            }


            connection.close();

            currentData = dataArray;
            return dataArray;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Method to get the header names
    public String[] getColumns(MetadataUnit mu, boolean gradeEnabled) {


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
        if (gradeEnabled) als.add("Grade");

        String[] s = new String[als.size()];
        int i = 0;
        while(!als.isEmpty()) {
            s[i++] = als.remove(0);
        }



        return s;
    }

    //Method to get the data for the JTable
    //It is implicit that the number of true booleans for both getData and getColumns are the same. Both methods should be run consecutively.
    public String[][] getData(MetadataUnit mu, boolean gradeEnabled, boolean fetchNew) {

        ArrayList<DataUnit> ald;
        if (!fetchNew) {
            if (!hasData()) {
                ald = getConnection(url, user, pass, query);
            } else {
                ald = currentData;
            }
        } else {
            ald = getConnection(url, user, pass, query);
        }

        int n = 0;
        if (gradeEnabled) n = 1;


        String[][] s = new String[ald.size()][mu.countTrue()+n];

        int i = 0;//count rows
        for (int k = 0; k < ald.size(); k++) {
            int j = 0;
            DataUnit du = ald.get(k);
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

        return s;
    }

    public boolean hasData() {
        if (currentData.equals(null)) return false;
        else return true;
    }

    public void exportAsCSV(JTable table, String path) {
        try {

            TableModel model = table.getModel();
            PrintWriter pw = new PrintWriter(new FileOutputStream(path));



            int n = model.getColumnCount();
            if (n < 1) return;
            int i = 0;
            for (; i < n-1; i++) {
                pw.print(model.getColumnName(i) + ",");
            }
            pw.println(model.getColumnName(i));


            int m = model.getRowCount();
            for (int j = 0; j < m; j++) {
                int k = 0;
                for (; k < n-1; k++) {
                    try {
                        pw.print("\""+model.getValueAt(j, k).toString() + "\",");
                    } catch(NullPointerException ne) {
                        pw.print(",");
                    }
                }
                try {
                    pw.println("\""+model.getValueAt(j, k).toString()+"\"");
                } catch(NullPointerException ne) {
                    pw.print("\n");
                }
            }


            pw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> findMissing(int x, int y, int z) {

        MetadataUnit mu = new MetadataUnit(true,true,true,true,true,true,true,true,true);
        String[][] s = getData(mu,true,false);
        ArrayList<String> rtnArray = new ArrayList<String>();

        for (int i = 1; i <= x; i++) {
            String append = "";
            if (i < 100) append = "0";
            if (i < 10) append = "00";

            boolean found = false;
            for (int j = 0; j < s.length; j++) {

                if (s[j][0].equals("h0910"+append+i)) {
                    found = true;
                    j = s.length;
                }
            }
            if (!found) {
                rtnArray.add("h0910"+append+i);
            }
        }

        for (int i = 1; i <= y; i++) {
            String append = "";
            if (i < 100) append = "0";
            if (i < 10) append = "00";

            boolean found = false;
            for (int j = 0; j < s.length; j++) {

                if (s[j][0].equals("h1020"+append+i)) {
                    found = true;
                    j = s.length;
                }
            }
            if (!found) {
                rtnArray.add("h1020"+append+i);
            }
        }

        for (int i = 1; i <= z; i++) {
            String append = "";
            if (i < 100) append = "0";
            if (i < 10) append = "00";

            boolean found = false;
            for (int j = 0; j < s.length; j++) {

                if (s[j][0].equals("h1130"+append+i)) {
                    found = true;
                    j = s.length;
                }
            }
            if (!found) {
                rtnArray.add("h1130"+append+i);
            }
        }

        for (int i = 0; i < rtnArray.size(); i++) {
            System.out.println(rtnArray.get(i));
        }

        return rtnArray;
    }

    public ArrayList<String> checkPublications(JTable table) {

        ArrayList<String> rtnArray = new ArrayList<String>();
        TableModel model = table.getModel();
        int r = model.getRowCount();

        for (int i = 0; i < r; i++) {
            if (((String)model.getValueAt(i,3)).equals("Publications")) {//if it is a publication
                boolean correct = checkFormat((String)model.getValueAt(i,5));
                if (!correct) {
                    rtnArray.add((String)model.getValueAt(i,2));
                }
            }
        }
        return rtnArray;
    }

    private boolean checkFormat(String input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter(",");

        try {
            ArrayList<String> tokens = new ArrayList<String>();
            while(sc.hasNext()) {
                tokens.add(sc.next());
            }
            boolean found = false;
            while (!tokens.isEmpty()) {
                char[] s = (tokens.remove(tokens.size() - 1)).toCharArray();//starting from back is faster
                if (s[0] == '(' && s[s.length-1] == ')') {
                    found = true;
                }
            }
            return found;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
