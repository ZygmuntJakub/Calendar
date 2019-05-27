package view;

import view.services.ButtonLanguageListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {


    JProgressBar pBar = new JProgressBar();
    Label title = new Label("Witaj w Kalendarzu: Nieznajomy");
    Label loginLabel = new Label("Login: \n");
    Label color = new Label("Kolor: ");
    Button language_PL = new Button("Polski");
    Button language_EN = new Button("English");
    SwingCalendar calendar = new SwingCalendar();

    JRadioButton dark = new JRadioButton("Dark");
    JRadioButton white = new JRadioButton("White");
    ButtonGroup colorGroup = new ButtonGroup();

    JCheckBox calendarVisible = new JCheckBox("Ukryj kalendarz");

    JTextField loginField = new JTextField(10);

    Timer timer;

    MainWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        class FieldLoginListener implements DocumentListener { //klasa lokalna

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldState();
            }
            protected void updateFieldState() {
                title.setText("Witaj w Kalendarzu: " + loginField.getText());
            }
        }

        setLayout(new FlowLayout());

        setUpBar();
        add(pBar);
        ActionListener pBarLoading = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                if(pBar.getValue() < 100){
                    SwingCalendar.changeVisible(false);
                    pBar.setValue(pBar.getValue() + 10);
                } else{
                    timer.stop();
                    pBar.setVisible(false);
                    SwingCalendar.changeVisible(true);
                }
            }
        };
        timer = new Timer(200, pBarLoading);
        timer.setRepeats(true);
        timer.start();


        colorGroup.add(dark);
        colorGroup.add(white);

        dark.addActionListener(new RadioColorListener());
        white.addActionListener(new RadioColorListener());

        language_PL.addActionListener(new ButtonLanguageListener());
        language_EN.addActionListener(new ButtonLanguageListener());

        loginField.getDocument().addDocumentListener(new FieldLoginListener());

        calendarVisible.addItemListener(new ItemListener() { //klasa anonimowa
            @Override
            public void itemStateChanged(ItemEvent e) {
                Object source = e.getItemSelectable();

                if (e.getStateChange() == ItemEvent.DESELECTED){
                    SwingCalendar.changeVisible(true);
                    calendarVisible.setText("Ukryj kalendarz");
                }
                if( e.getStateChange() == ItemEvent.SELECTED){
                    SwingCalendar.changeVisible(false);
                    calendarVisible.setText("Pokaż kalendarz");
                }

            }
        });

        add(title);
        add(language_PL);
        add(language_EN);
        add(calendar);
        add(color);
        add(dark);
        add(white);
        add(calendarVisible);
        add(loginLabel);
        add(loginField);

        addMouseListener(new MouseHandler());


    }
    private void changeBackground(Color color){

            this.getContentPane().setBackground(color);
    }

    class RadioColorListener implements ActionListener{ //klasa wewnętrzna

        @Override
        public void actionPerformed(ActionEvent e) {
            String choose = e.getActionCommand();

            if(choose.equals("Dark"))changeBackground(Color.GRAY);
            if(choose.equals("White"))changeBackground(Color.WHITE);
        }
    }
    class MouseHandler extends MouseAdapter  // klasa adaptacyjna
    {
        public void mouseClicked(MouseEvent e)
        {
            System.out.println("Mysz kliknięta w punkcie " + e.getX() + ", " + e.getY());
        }
    }
    private void setUpBar(){
        pBar.setValue(0);
        pBar.setStringPainted(true);
    }
}
