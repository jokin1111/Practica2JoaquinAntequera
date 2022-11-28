//import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.BorderLayout.*;
import static java.awt.Font.PLAIN;

public class Card extends JFrame {
    CardLayout tarjetas = new CardLayout();
    JPanel panel, panelInf , panelSup;
    JPanel tj[] = new JPanel[5];
    JTextField tj2textos[] = new JTextField[2];
    JLabel tj2etiquetas[] = new JLabel[3];
    String tj2etiquetasNom[] = {"Nombre Completo", "Email", "Contraseña"};
    JPasswordField pass;
    JButton cambiar, anterior;
    JComboBox lugres[] = new JComboBox[2];
    JLabel lugresEtiq[] = new JLabel[2];
    String lugresNom[]= {"País:", "Región:"};
    String rutaEstados = "/Users/joaquinantequera/Desktop/Insitituto/Interfaces/Practica2JoaquinAntequera/src/estados.txt";
    String rutaProvincias = "/Users/joaquinantequera/Desktop/Insitituto/Interfaces/Practica2JoaquinAntequera/src/provincias.txt";
    JLabel tj4etiquetas[] = new JLabel[5];
    String tj4etiquetasNom[] = {"resUsuario", "resEmail", "resPass", "resEstado", "resProvincia"};
    String tj4Resultados[] = new String[5];
    JTextArea tj4textos [] = new JTextArea[5];
    JFileChooser fileChooser;
    JCheckBox fileChooserCheck;
    JButton fileChooserButton;
    File fichero;
    BufferedWriter bf;
    boolean valido;
    public Card() throws IOException {
        initPantalla();
        initPanel();
        initBoton();
        initTarjetas();
        initPanelSup();
        configTj1();
        configTj2();
        configTj3();
        configTj4();
        configTj5();
        setVisible(true);
    }

    void leerFichero(String ruta, JComboBox comboBox) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(ruta));
        String linea;
        while ((linea = bufferedReader.readLine()) != null){
            comboBox.addItem(linea);
        }
    }

    private boolean validarPass() {
        String password = pass.getText();
        char clave;
        int  contNumero = 0, contLetraMay = 0, contLetraMin = 0, contNoAF = 0, contLen = 0;
        for (int i = 0; i < password.length(); i++) {
            clave = password.charAt(i);
            String passValue = String.valueOf(clave);
            if (passValue.matches("[A-Z]")) {
                contLetraMay++;
            } else if (passValue.matches("[a-z]")) {
                contLetraMin++;
            } else if (passValue.matches("[0-9]")) {
                contNumero++;
            } else {
                contNoAF++;
            }
        }
        if (password.length() >= 8 & password.length() <= 16){
            contLen = 1;
        }
        if (contLetraMay >= 1 & contLetraMin >=1 & contNumero >=1 & contNoAF >=1 & contLen == 1){
            return true;
        }
        else
            return false;
    }

    private boolean validarMail(String s){
        valido = false;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = s;

        Matcher mather = pattern.matcher(email);
        if (mather.find())
            valido = true;
        else
            valido = false;
        return valido;

    }

    private void configTj5() {

        JLabel registro = new JLabel("¡REGISTRO EXITOSO!");
        registro.setBounds(300,100, 300, 200);
        registro.setFont(new Font(Font.DIALOG, 1, 20));
        tj[4].add(registro);

    }

    private void configTj4() {
        int initialx = 40;
        fileChooserCheck = new JCheckBox();
        fileChooserCheck.setBounds(20,20,30,30);
        tj[3].add(fileChooserCheck);

        fileChooserButton = new JButton();
        fileChooserButton.setBounds(20, 50, 100, 30);
        fileChooserButton.setVisible(false);
        fileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                int seleccion = fileChooser.showOpenDialog(tj[3]);
                if (seleccion == JFileChooser.APPROVE_OPTION){
                    fichero = fileChooser.getSelectedFile();
                }
            }
        });
        tj[3].add(fileChooserButton);

        fileChooserCheck.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (fileChooserCheck.isSelected())
                    fileChooserButton.setVisible(true);
                else {
                    fileChooserButton.setVisible(false);
                }
            }
        });

        tj[3].add(fileChooserButton);
        for (int i = 0; i < tj4textos.length; i++) {
            tj4textos[i] = new JTextArea();
            tj4textos[i].setBounds(300, initialx, 200, 30);
            tj[3].add(tj4textos[i]);
            tj4etiquetas[i] = new JLabel(tj4etiquetasNom[i]);
            tj4etiquetas[i].setBounds(300, (initialx - 30), 200, 30);
            tj[3].add(tj4etiquetas[i]);
            initialx += 80;
        }

    }

    private void configTj3() {
        int initialx = 20;
        for (int i = 0; i < lugres.length; i++) {
            lugres[i] = new JComboBox();
            lugres[i].setBounds(initialx,70,200,30);
            tj[2].add(lugres[i]);
            lugresEtiq[i] = new JLabel(lugresNom[i]);
            lugresEtiq[i].setBounds(initialx, 40, 200, 20);
            tj[2].add(lugresEtiq[i]);
            initialx += 200;
        }


        lugres[0].addItem("Seleccione");
        lugres[0].addItem("España");
        lugres[0].addItem("Estados Unidos");
        lugres[1].setVisible(false);
        lugresEtiq[1].setVisible(false);

        lugres[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lugres[0].getSelectedIndex() == 1){
                    try {
                        lugres[1].removeAllItems();
                        leerFichero(rutaProvincias, lugres[1]);
                        lugres[1].setVisible(true);
                        lugresEtiq[1].setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (lugres[0].getSelectedIndex() == 2) {
                    try {
                        lugres[1].removeAllItems();
                        leerFichero(rutaEstados, lugres[1]);
                        lugres[1].setVisible(true);
                        lugresEtiq[1].setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (lugres[0].getSelectedIndex() == 0) {
                    lugres[1].setVisible(false);
                    lugresEtiq[1].setVisible(false);
                }
            }
        });
    }

    private void configTj2() {
        int initiali = 100;
        JTextPane condiciones = new JTextPane();
        String textoCondiciones = "Formato para asegurar datos validos: \n" + "\n" +
                                    "Para que el Email sea valido debe tener un formato parecido a:" +
                                    " string@gmail.com.\n" + "Para que la contraseña sea valida, debe contener entre " +
                                    "8 y 16 cáracteres, 1 número, 1 mayúscula, 1 minúscula y un caracter no-alfanúmerico.";
        for (int i = 0; i < tj2textos.length; i++) {
            tj2textos[i] = new JTextField();
            tj2textos[i].setBounds(20, initiali, 200, 50);
            tj[1].add(tj2textos[i]);
            initiali+=80;
        }
        initiali = 60;
        for (int i = 0; i < tj2etiquetas.length; i++) {
            tj2etiquetas[i] = new JLabel(tj2etiquetasNom[i]);
            tj2etiquetas[i].setBounds(20, (initiali), 200, 50);
            tj[1].add(tj2etiquetas[i]);
            initiali +=80;
        }

        pass = new JPasswordField();
        pass.setBounds(20, 260, 200, 50);
        tj[1].add(pass);

        condiciones.setBounds(320, 85, 410,300);
        condiciones.setBackground(Color.GRAY);
        condiciones.setOpaque(true);
        condiciones.setFont(new Font(Font.DIALOG, 1, 20));
        condiciones.setText(textoCondiciones);
        condiciones.setEditable(false);
        tj[1].add(condiciones);

    }

    private void configTj1() {
        //tarjeta número 1
        String texto = "¡Hola! Bienvenido a Joker's App.\n" +
                        "Para el correcto funcionamiento de la aplicación necesitaremos que introduzca alguno de sus dátos: \n" +
                        "\n" +
                        "*   Nombre completo.\n" + "*   Email.\n" + "*   Contraseña.\n" + "*   Pais.\n" +
                        "*   Provincia o estado.\n";
        JTextPane campoSaludo = new JTextPane();
        campoSaludo.setBounds(220,80,380,250);
        campoSaludo.setEditable(false);
        campoSaludo.setBackground(Color.GRAY);
        campoSaludo.setText(texto);
        campoSaludo.setFont(new Font(Font.DIALOG, 1, 20));
        tj[0].add(campoSaludo);
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(tarjetas);
        add(panel);

        panelInf = new JPanel();
        panelInf.setLayout(null);
        panelInf.setPreferredSize(new Dimension(800, 80));
        panelInf.setBackground(Color.RED);
        panelInf.setOpaque(true);
        add(panelInf, BorderLayout.SOUTH);
    }

    private void initPanelSup() {

        panelSup = new JPanel();
        panelSup.setPreferredSize(new Dimension(800,72));
        panelSup.setBackground(Color.BLACK);
        panelSup.setOpaque(true);
        panelSup.setLayout(tarjetas);
        add(panelSup, BorderLayout.NORTH);

    }

    public void initPantalla(){
        setTitle("Práctica2JoaquínAntequera");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void initTarjetas() {
        for (int i = 0; i < tj.length; i++) {
            tj[i] = new JPanel();
            tj[i].setBackground(Color.GRAY);
            tj[i].setLayout(null);
            panel.add(tj[i]);
        }
    }

    public void initBoton (){
        cambiar = new JButton("Siguiente");
        cambiar.setBounds(600, 20, 150, 50);
        cambiar.setBackground(Color.BLACK);
        cambiar.setOpaque(true);
        cambiar.setBorderPainted(false);
        cambiar.setForeground(Color.WHITE);
        cambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int contEmailPass = 0;
                if(tj[0].isShowing()){
                    tarjetas.next(panel);
                    anterior.setVisible(true);
                }
                else if(tj[1].isShowing()){
                    /*
                    * if (validarMail(tj2textos[1].getText())){
                        contEmailPass++;
                        tj2etiquetas[1].setForeground(Color.black);
                    }
                    else{
                        tj2etiquetas[1].setForeground(Color.red);

                    }
                    if (validarPass()){
                        contEmailPass++;
                        tj2etiquetas[2].setForeground(Color.black);
                    }
                    else{
                        tj2etiquetas[2].setForeground(Color.red);

                    }
                    if (contEmailPass == 2){
                        tarjetas.next(panel);
                    }
                    * */
                    tj4Resultados[0] = tj2textos[0].getText();
                    tj4Resultados[1] = tj2textos[1].getText();
                    tj4Resultados[2] = pass.getText();
                    tj4textos[0].setText(tj4Resultados[0]);
                    tj4textos[1].setText(tj4Resultados[1]);
                    tj4textos[2].setText(tj4Resultados[2]);
                    tarjetas.next(panel);
                }

                else if(tj[2].isShowing()){
                    tarjetas.next(panel);
                    tj4Resultados[3] = String.valueOf(lugres[0].getSelectedItem());
                    tj4Resultados[4] = String.valueOf(lugres[1].getSelectedItem());
                    tj4textos[3].setText(tj4Resultados[3]);
                    tj4textos[4].setText(tj4Resultados[4]);
                }
                else if(tj[3].isShowing()) {
                    tarjetas.next(panel);
                    cambiar.setVisible(false);
                    String datos[] = {"Usuario", "Email", "Contraseña", "País", "Provincia/Estado"};
                    try {
                        bf = new BufferedWriter(new FileWriter(fichero));
                        for (int i = 0; i < datos.length; i++) {
                            bf.write(datos[i]);
                            bf.newLine();
                            switch (i) {
                                case 0:
                                    bf.write(tj4Resultados[0]);
                                    break;
                                case 1:
                                    bf.write(tj4Resultados[1]);
                                    break;
                                case 2:
                                    bf.write(tj4Resultados[3]);
                                    break;
                                case 3:
                                    bf.write(tj4Resultados[4]);
                                    break;
                                case 4:
                                    bf.write(tj4Resultados[5]);
                                    break;
                            }
                            bf.newLine();
                            bf.newLine();
                        }
                        bf.close();
                    }catch(IOException ex){
                        System.out.println("Fichero no encontrado");
                    }
                }
            }
        });
        panelInf.add(cambiar);

        anterior = new JButton("Anterior");
        anterior.setBounds(40, 20, 150, 50);
        anterior.setBackground(Color.BLACK);
        anterior.setOpaque(true);
        anterior.setBorderPainted(false);
        anterior.setForeground(Color.white);
        anterior.setVisible(false);
        anterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tj[1].isShowing()){
                    tarjetas.previous(panel);
                    anterior.setVisible(false);
                }
                else if(tj[2].isShowing()){
                    tarjetas.previous(panel);
                }
                else if(tj[3].isShowing()){
                    tarjetas.previous(panel);
                }
                else if(tj[4].isShowing()){
                    tarjetas.previous(panel);
                    cambiar.setVisible(true);
                }
            }
        });
        panelInf.add(anterior);

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, 800, 70, Color.RED);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, 800, 115);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Font.DIALOG,1,30));
        g2d.drawString("Joker's App", 600, 80);

        Toolkit t = Toolkit.getDefaultToolkit();
        Image imagen = t.getImage("joker.png");
        g2d.drawImage(imagen,40,45,this);

    }

    public static void main(String[] args) throws IOException {
        new Card();
    }

}
