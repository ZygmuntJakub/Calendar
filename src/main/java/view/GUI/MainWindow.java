package view.GUI;

import model.Event;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -9057551984778158304L;
	JProgressBar pBar = new JProgressBar();
	Label color = new Label("Kolor: ");
	public static SwingCalendar calendar = new SwingCalendar();

	Timer timer;

	MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initTimer();

		setLayout(new FlowLayout(FlowLayout.LEFT, 60, 10));

		setUpBar();
		add(pBar);
		ActionListener pBarLoading = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (pBar.getValue() < 100) {
					calendar.changeVisible(false);
					pBar.setValue(pBar.getValue() + 10);
				} else {
					timer.stop();
					pBar.setVisible(false);
					calendar.changeVisible(true);
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
		add(calendar);
		add(color);
		add(comboBox);

	}

	private void changeBackground(Color color) {

		this.getContentPane().setBackground(color);
	}

	class RadioColorListener implements ActionListener { // klasa wewnętrzna

		public void actionPerformed(ActionEvent e) {
			String choose = e.getActionCommand();

			if (choose.equals("Dark"))
				changeBackground(Color.GRAY);
			if (choose.equals("White"))
				changeBackground(Color.WHITE);
		}
	}

	private void setUpBar() {
		pBar.setValue(0);
		pBar.setStringPainted(true);
	}

	public void initTimer(){
		Timer timer = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Event event  = EventEditorWindow.repoController.getAlertedEvents();
				if(event != null){
					JOptionPane.showMessageDialog(null, "Uwaga!\n" +
							"Zaplanowałeś wydarzenie: " + event.getTitle() + "\n" +
							"na dzień " + event.getDate().getTime() + "\n" +
							"zostało Ci " + event.getDuration() + "minut.");
					event.setDuration(EventEditorWindow.MINUTES + 1);
				}
			}
		});
		timer.start();
	}
}
