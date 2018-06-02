package GUI;

import javax.swing.* ;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;

public class GUI  {


    public GUI() {

        createGui();
        //Window
    }

    private static JFrame MainWindow;
    private static JPanel contentPanel;
    private static JLabel Title;
    private static JTable contentTable;
    private static JLabel FooterLeft;
    private static JLabel FooterCenter;
    private static JLabel FooterRight;

    static void createGui(){
        // TODO Auto-generated constructor stub
        //Window
        MainWindow = new JFrame("OBD2 Scanner");
        //MainWindow.setSize(816, 518);
        //MainWindow.setVisible(true);
        MainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        MainWindow.setUndecorated(true);
        MainWindow.setVisible(true);

        //WindowManager
        contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.setLayout(null);

        //Title
        Title = new JLabel("OBDoc", JLabel.CENTER);
        Title.setSize(800, 70);
        Title.setLocation(0, 0);
        Title.setFont(new Font("Serif", Font.PLAIN, 28));
        Title.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        //Tabelle initiieren
        initializeTable();



        // FooterLinks
        FooterLeft = new JLabel("Version 0.0.1", JLabel.LEFT);
        FooterLeft.setSize(300, 20);
        FooterLeft.setFont(new Font("Serif", Font.PLAIN, 10));
        FooterLeft.setLocation(0, 460);
        FooterLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //FooterMitte
        FooterCenter = new JLabel("Studienarbeit an der DHBW Mannheim", JLabel.CENTER);
        FooterCenter.setSize(200, 20);
        FooterCenter.setFont(new Font("Serif", Font.PLAIN, 10));
        FooterCenter.setLocation(300, 460);
        FooterCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //FooterRight
        FooterRight = new JLabel("© Maximilian Olbort, Jonathan Seibel, Marvin Meinhard", JLabel.RIGHT);
        FooterRight.setSize(300, 20);
        FooterRight.setFont(new Font("Serif", Font.PLAIN, 10));
        FooterRight.setLocation(500, 460);
        FooterRight.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //weise dem Frame das Panel zu
        contentPanel.setPreferredSize(new Dimension(800,480));
        contentPanel.add(Title);
        contentPanel.add(FooterLeft);
        contentPanel.add(FooterCenter);
        contentPanel.add(FooterRight);
        contentPanel.add(contentTable);
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPanel.setLayout(null);
        //MainWindow.setContentPane(contentPanel);
        MainWindow.getContentPane().add(contentPanel);
        MainWindow.revalidate();
        MainWindow.repaint();

        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void initializeTable(){

        Object[][] data = new Object[][]{ {"Adapter ist nicht erreichbar", "Auto ist nicht erreichbar"}, {"Datenbank ist nicht erreichbar", "MQTT Server ist nicht erreichbar"}, {"Kein DTC gefunden", "Ausschalten"}};
        String[] header = new String [] {"1","2"};
        contentTable = new JTable(new DefaultTableModel(data, header));

        contentTable.setBackground(Color.WHITE);
        contentTable.setSize(800, 390);
        contentTable.setLocation(0, 70);
        contentTable.setRowHeight(130);
        contentTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentTable.setTableHeader(null);
        contentTable.setRowSelectionAllowed(false);
        contentTable.setDefaultEditor(Object.class, null);


        contentTable.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                int row = contentTable.rowAtPoint(e.getPoint());
                int col = contentTable.columnAtPoint(e.getPoint());
                //Debug only:
                //System.out.println("Zeile: " + row + " Spalte :" + col);
                if (row == 2 && col == 1){
                    System.exit(0);
                }
            }
        });

    }

    public static void updateMQTTChecker(boolean state) {

        if (state) {
            contentTable.setValueAt("MQTT Server ist verbunden", 1, 1);
        } else {
            contentTable.setValueAt("MQTT Server ist nicht erreichbar", 1, 1);
        }
    }

    public static void updateDBChecker (boolean state) {

        if (state) {
            contentTable.setValueAt("Datenbank ist verbunden", 1, 0);
        } else {
            contentTable.setValueAt("Datenbank ist  nicht erreichbar", 1, 0);
        }
    }

    public static void updateCarChecker (boolean state) {

        if (state) {
            contentTable.setValueAt("Auto ist verbunden", 0, 1);
        } else {
            contentTable.setValueAt("Auto ist  nicht erreichbar", 0, 1);
        }
    }

    public static void updateAdapterChecker (boolean state) {

        if (state) {
            contentTable.setValueAt("Adapter ist verbunden", 0, 0);
        } else {
            contentTable.setValueAt("Adapter ist nicht erreichbar", 0, 0);
        }
    }

    public static void updateDTCChecker (int count) {

        if (count == 0) {
            contentTable.setValueAt("Keine DTCs gefunden", 2, 0);
        }
        if(count > 0) {
            contentTable.setValueAt(count + " Fehlercodes ausgelesen", 2, 0);
        }
        if(count < 0) {
            contentTable.setValueAt("Fehler", 2, 0);
        }
    }
     }


