package view.GUI;

import model.Event;
import view.ApplicationStarter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Główne okno aplickacji
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = -9057551984778158304L;
	private JProgressBar pBar = new JProgressBar();
	private JButton deleteOlderEvents = new JButton("Usuń wydarzenia, które już się odbyły");
	private Timer timer;
	/**
	 * Komponent odpoiwedzialny za wygląd kalendarza
	 */
	public static SwingCalendar calendar = new SwingCalendar();



	/**
	 * Dodaje komponenty do okna aplikacji
	 */
	MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loadingBarOperations();
		deleteOlderEvents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationStarter.repoController.deleteEventsOlderThan(Calendar.getInstance());
				calendar.upDateEventsOnCalendar();
			}
		});
		add(deleteOlderEvents);
		add(calendar);

	}

	public void changeBackground(Color color) {

		this.getContentPane().setBackground(color);
	}

	private void setUpBar() {
		pBar.setValue(0);
		pBar.setStringPainted(true);
	}

	private void timerForAlert(){
		Timer timer = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Event event  = ApplicationStarter.repoController.getAlertedEvents();
				if(event != null){
					Toolkit.getDefaultToolkit().beep();
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
	private void loadingBarOperations(){
		timerForAlert();
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
	}
}
