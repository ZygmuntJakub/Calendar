package view;

import services.ButtonLanguageListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -9057551984778158304L;
	JProgressBar pBar = new JProgressBar();
	Label title = new Label("Witaj w Kalendarzu: Nieznajomy");
	Label loginLabel = new Label("Login: \n");
	Label color = new Label("Kolor: ");
	Button language_PL = new Button("Polski");
	Button language_EN = new Button("English");
	SwingCalendar calendar = new SwingCalendar();

	ButtonGroup colorGroup = new ButtonGroup();

	JCheckBox calendarVisible = new JCheckBox("Ukryj kalendarz");

	JTextField loginField = new JTextField(10);

	Timer timer;

	MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		class FieldLoginListener implements DocumentListener { // klasa lokalna

			public void insertUpdate(DocumentEvent e) {
				updateFieldState();
			}

			public void removeUpdate(DocumentEvent e) {
				updateFieldState();
			}

			public void changedUpdate(DocumentEvent e) {
				updateFieldState();
			}

			protected void updateFieldState() {
				title.setText("Witaj w Kalendarzu: " + loginField.getText());
			}
		}

		setLayout(new FlowLayout(FlowLayout.LEFT, 60, 10));

		setUpBar();
		add(pBar);
		ActionListener pBarLoading = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (pBar.getValue() < 100) {
					SwingCalendar.changeVisible(false);
					pBar.setValue(pBar.getValue() + 10);
				} else {
					timer.stop();
					pBar.setVisible(false);
					SwingCalendar.changeVisible(true);
				}
			}
		};
		timer = new Timer(100, pBarLoading);
		timer.setRepeats(true);
		timer.start();

		String[] colour = { "DARK", "WHITE", "GREEN", "PINK" };
		final JComboBox<String> comboBox = new JComboBox<String>(colour);
		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String choose = (String) comboBox.getSelectedItem();

				if (choose.equals("DARK"))
					changeBackground(Color.GRAY);
				if (choose.equals("WHITE"))
					changeBackground(Color.WHITE);
				if (choose.equals("PINK"))
					changeBackground(Color.PINK);
				if (choose.equals("GREEN"))
					changeBackground(Color.GREEN);
			}

		});

		language_PL.addActionListener(new ButtonLanguageListener());
		language_EN.addActionListener(new ButtonLanguageListener());

		loginField.getDocument().addDocumentListener(new FieldLoginListener());

		calendarVisible.addItemListener(new ItemListener() { // klasa anonimowa

			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.DESELECTED) {
					SwingCalendar.changeVisible(true);
					calendarVisible.setText("Ukryj kalendarz");
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					SwingCalendar.changeVisible(false);
					calendarVisible.setText("Poka¿ kalendarz");
				}

			}

		});

		add(title);
		add(language_PL);
		add(language_EN);
		add(calendar);
		add(calendarVisible);
		add(loginLabel);
		add(loginField);
		add(color);
		add(comboBox);

		addMouseListener(new MouseHandler());

	}

	private void changeBackground(Color color) {

		this.getContentPane().setBackground(color);
	}

	class RadioColorListener implements ActionListener { // klasa wewnÄ™trzna

		public void actionPerformed(ActionEvent e) {
			String choose = e.getActionCommand();

			if (choose.equals("Dark"))
				changeBackground(Color.GRAY);
			if (choose.equals("White"))
				changeBackground(Color.WHITE);
		}
	}

	class MouseHandler extends MouseAdapter // klasa adaptacyjna
	{
		public void mouseClicked(MouseEvent e) {
			System.out.println("Mysz klikniêta w punkcie " + e.getX() + ", " + e.getY());
		}
	}

	private void setUpBar() {
		pBar.setValue(0);
		pBar.setStringPainted(true);
	}
}
