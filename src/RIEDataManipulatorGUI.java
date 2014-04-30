import javax.management.remote.rmi._RMIConnection_Stub;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ongzexuan on 27/4/14.
 */
public class RIEDataManipulatorGUI extends JFrame implements ActionListener{

    private RIEDataManipulatorManager rdmm;
    private JButton btnFetch;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane jsp;
    private JMenuBar menubar;
    private JMenu menuFile;
    private JMenu menuData;
    private JMenu menuCheck;
    private JPanel pnlMain;
    private JFileChooser fc;


    private JMenuItem miExport;

    private JCheckBox uIDc;
    private JCheckBox namec;
    private JCheckBox pIDc;
    private JCheckBox Categoryc;
    private JCheckBox Titlec;
    private JCheckBox Desc1c;
    private JCheckBox Desc2c;
    private JCheckBox Awardc;
    private JCheckBox Yearc;
    private JCheckBox Gradec;

    private JMenuItem miCheckARP;
    private JMenuItem miCheckPub;



    public static void main(String[] args) {
        RIEDataManipulatorGUI rdmg = new RIEDataManipulatorGUI();
        rdmg.setTitle("RIE Data Manipulator Version 0.1");
        rdmg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rdmg.setSize(new Dimension(650,650));
        rdmg.setResizable(true);
        rdmg.setVisible(true);
        //rdmg.pack();

    }

    public RIEDataManipulatorGUI() {
        rdmm = new RIEDataManipulatorManager();
        iniGUI();

    }

    private void iniGUI() {


        pnlMain = new JPanel();
        pnlMain.setSize(new Dimension(600,600));
        pnlMain.setBackground(Color.DARK_GRAY);
        pnlMain.setLayout(new BorderLayout());
        this.add(pnlMain);

        btnFetch = new JButton("Fetch Data");
        pnlMain.add(btnFetch,BorderLayout.SOUTH);
        btnFetch.addActionListener(this);

        //Menubar and items
        menubar = new JMenuBar();
        pnlMain.add(menubar,BorderLayout.NORTH);

        menuFile= new JMenu("File");
        menubar.add(menuFile);

        miExport = new JMenuItem("Export");
        miExport.addActionListener(this);
        menuFile.add(miExport);

        menuData = new JMenu("Data");
        menubar.add(menuData);

        uIDc = new JCheckBox("uID");
        namec = new JCheckBox("Name");
        pIDc = new JCheckBox("pID");
        Categoryc = new JCheckBox("Category");
        Titlec = new JCheckBox("Title");
        Desc1c = new JCheckBox("Desc1");
        Desc2c = new JCheckBox("Desc2");
        Awardc = new JCheckBox("Award");
        Yearc = new JCheckBox("Year");
        Gradec = new JCheckBox("Grade");
        uIDc.addActionListener(this);
        namec.addActionListener(this);
        pIDc.addActionListener(this);
        Categoryc.addActionListener(this);
        Titlec.addActionListener(this);
        Desc1c.addActionListener(this);
        Desc2c.addActionListener(this);
        Awardc.addActionListener(this);
        Yearc.addActionListener(this);
        Gradec.addActionListener(this);

        menuData.add(uIDc);
        menuData.add(namec);
        menuData.add(pIDc);
        menuData.add(Categoryc);
        menuData.add(Titlec);
        menuData.add(Desc1c);
        menuData.add(Desc2c);
        menuData.add(Awardc);
        menuData.add(Yearc);
        menuData.add(Gradec);
        uIDc.setSelected(true);
        namec.setSelected(true);
        pIDc.setSelected(true);
        Categoryc.setSelected(true);
        Titlec.setSelected(true);
        Desc1c.setSelected(true);
        Desc2c.setSelected(true);
        Awardc.setSelected(true);
        Yearc.setSelected(true);
        Gradec.setSelected(true);

        menuCheck = new JMenu("Check");
        menubar.add(menuCheck);
        miCheckARP = new JMenuItem("Check all Students for ARP");
        menuCheck.add(miCheckARP);
        miCheckARP.addActionListener(this);

        miCheckPub = new JMenuItem("Check all publication formats");
        menuCheck.add(miCheckPub);
        miCheckPub.addActionListener(this);


        //Table
        MetadataUnit mu = new MetadataUnit(uIDc.isSelected(),namec.isSelected(),pIDc.isSelected(),Categoryc.isSelected(),Titlec.isSelected(),Desc1c.isSelected(),Desc2c.isSelected(),Awardc.isSelected(),Yearc.isSelected());
        model = new DefaultTableModel(rdmm.getData(mu,Gradec.isSelected(),true),rdmm.getColumns(mu,Gradec.isSelected()));
        //table = new JTable(rdmm.getData(),rdmm.getColumns());
        table = new JTable();
        table.setModel(model);
        jsp = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        pnlMain.add(jsp,BorderLayout.CENTER);


    }
    public MetadataUnit makeMetadata() {

        MetadataUnit mu = new MetadataUnit(uIDc.isSelected(),namec.isSelected(),pIDc.isSelected(),Categoryc.isSelected(),Titlec.isSelected(),Desc1c.isSelected(),Desc2c.isSelected(),Awardc.isSelected(),Yearc.isSelected());
        return mu;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(miExport)) {
            try {

                fc = new JFileChooser();
                int rtnVal = fc.showSaveDialog(RIEDataManipulatorGUI.this);
                if (rtnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();

                    rdmm.exportAsCSV(table,file.getPath());
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(ae.getSource().equals(miCheckPub)) {
            try {
                ArrayList<String> rtnList = rdmm.checkPublications(table);
                while (!rtnList.isEmpty()) {
                    JOptionPane.showMessageDialog(RIEDataManipulatorGUI.this,rtnList.remove(0),"Formatting Error",JOptionPane.PLAIN_MESSAGE);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(ae.getSource().equals(miCheckARP)) {
            try {
                String s = (String)JOptionPane.showInputDialog(RIEDataManipulatorGUI.this,"Enter number of students who joined us in year 1, 2 and 3 in the following format: x,y,z","Check if all students have ARP",JOptionPane.PLAIN_MESSAGE);
                Scanner sc = new Scanner(s);
                sc.useDelimiter(",");
                int x = Integer.parseInt(sc.next());
                int y = Integer.parseInt(sc.next());
                int z = Integer.parseInt(sc.next());

                //MetadataUnit mu = new MetadataUnit(uIDc.isSelected(),namec.isSelected(),pIDc.isSelected(),Categoryc.isSelected(),Titlec.isSelected(),Desc1c.isSelected(),Desc2c.isSelected(),Awardc.isSelected(),Yearc.isSelected());
                ArrayList<String> rtnList = rdmm.findMissing(x,y,z);
                while (!rtnList.isEmpty()) {
                    //works for small numbers. You shouldn't have too many missing students in the first place. Keying in wrong numbers teaches you a lesson :P
                    JOptionPane.showMessageDialog(RIEDataManipulatorGUI.this,rtnList.remove(0),"Missing Student",JOptionPane.PLAIN_MESSAGE);
                }
            } catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(RIEDataManipulatorGUI.this,"Invalid Input!","Error",JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (ae.getSource().equals(btnFetch)) {
            MetadataUnit mu = new MetadataUnit(uIDc.isSelected(),namec.isSelected(),pIDc.isSelected(),Categoryc.isSelected(),Titlec.isSelected(),Desc1c.isSelected(),Desc2c.isSelected(),Awardc.isSelected(),Yearc.isSelected());
            model = new DefaultTableModel(rdmm.getData(mu,Gradec.isSelected(),true),rdmm.getColumns(mu,Gradec.isSelected()));
            table.setModel(model);
        } else {
            MetadataUnit mu = new MetadataUnit(uIDc.isSelected(),namec.isSelected(),pIDc.isSelected(),Categoryc.isSelected(),Titlec.isSelected(),Desc1c.isSelected(),Desc2c.isSelected(),Awardc.isSelected(),Yearc.isSelected());
            model = new DefaultTableModel(rdmm.getData(mu,Gradec.isSelected(),false),rdmm.getColumns(mu,Gradec.isSelected()));
            table.setModel(model);
        }
    }

}


