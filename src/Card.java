//import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

public class Card extends JFrame {
    BorderLayout jflay = new BorderLayout();
    JPanel panel, panelInf;

    public Card(){
        initPantalla();
        initPanel();
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setBackground(Color.WHITE);
        add(panel);

        panelInf = new JPanel();
        panelInf.setPreferredSize(new Dimension(800, 100));
        panelInf.setBackground(Color.BLACK);
        add(panelInf, SOUTH);
    }

    public void initPantalla(){
        setTitle("Práctica2JoaquínAntequera");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(jflay);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        Toolkit t = Toolkit.getDefaultToolkit();
        Image imagen = t.getImage("C:\\dam\\Interfaces\\Práctica1JoaquínAntequera\\joker.png");
        g.drawImage(imagen,30,520,this);
    }

    public void initAAA(){
        //Commit
    }

    public static void main(String[] args){
        new Card();
    }

}
