package io.github.ayaankhan98.algorithmVisualizer;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;

/**
 * {@summary} class creates Main application frame, used for inputing ard
 * manipulating data of processes
 */
class AlgorithmVisulizer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/// lists all available algorithm
	private String[] listAlgorithmsData;

	/// data model for all processes data
	private DefaultTableModel processTableModel;

	AlgorithmVisulizer() {
		listAlgorithmsData = new String[5];
		listAlgorithmsData[0] = "First Come First Serve FCFS";
		listAlgorithmsData[1] = "Preemptive SJF";
		listAlgorithmsData[2] = "Non Preemptive SJF";
		listAlgorithmsData[3] = "Premptive Priority Scheduling";
		listAlgorithmsData[4] = "Non Premptive Priority Scheduling";

		/// some pre-default processea available for testing
		Object[][] processTableModelData = { { 1, "P1", 4, 0, 1 }, { 2, "P2", 3, 0, 2 }, { 3, "P3", 7, 6, 1 },
				{ 4, "P4", 4, 11, 3 }, { 5, "P5", 2, 12, 2 } };

		/// creating table for showing processes data
		String[] processTableModelColumns = { "Process Id", "Process Name", "CPU Brust", "Arrival Time", "Priority" };
		processTableModel = new DefaultTableModel(processTableModelData, processTableModelColumns);

		this.setTitle("Algorithm Visulizer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800, 500));
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);

		JList<String> listAlgorithm = new JList<>(listAlgorithmsData);
		listAlgorithm.setVisibleRowCount(4);

		JScrollPane listAlgorithmScrollPane = new JScrollPane(listAlgorithm, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listAlgorithmScrollPane.setPreferredSize(new Dimension(280, 180));
		JLabel algorithmListLabel = new JLabel("Select Algorithm");

		JPanel algorithmListPanel = new JPanel();
		algorithmListPanel.add(algorithmListLabel);
		algorithmListPanel.add(listAlgorithmScrollPane);
		algorithmListPanel.setBounds(10, 10, 300, 230);

		JPanel processListPanel = new JPanel();
		processListPanel.setBounds(320, 10, 480, 500);

		JTable processTable = new JTable(processTableModel);
		processTable.setAutoCreateRowSorter(true);
		processListPanel.add(new JScrollPane(processTable));

		JPanel processManipulationPanel = new JPanel(new GridLayout(8, 2));
		processManipulationPanel.setBounds(10, 250, 300, 270);

		JLabel processIdLabel = new JLabel("Process Id");
		JTextField processIdTextField = new JTextField();

		JLabel processNameLabel = new JLabel("Process Name");
		JTextField processNameTextField = new JTextField();

		JLabel processCPUBrustLabel = new JLabel("CPU Brust");
		JTextField processCPUBrustTextField = new JTextField();

		JLabel processArrivalTimeLabel = new JLabel("Arrival Time");
		JTextField processArrivalTimeTextField = new JTextField();

		JLabel processPriorityLabel = new JLabel("Priority");
		JTextField processPriorityTextField = new JTextField();

		JButton addProcessButton = new JButton("Add");
		JButton updateProcessButton = new JButton("Update");
		JButton deleteProcessButton = new JButton("Delete");
		JButton clearProcessButton = new JButton("Clear");
		JButton visualizeButton = new JButton("Visualize");

		addProcessButton.addActionListener(action -> {
			String processId = processIdTextField.getText().toString();
			String processName = processNameTextField.getText().toString();
			String processCPUBrust = processCPUBrustTextField.getText();
			String processArrivalTime = processArrivalTimeTextField.getText();
			String processPriority = processPriorityTextField.getText();

			if (processId.isEmpty() || processName.isEmpty() || processCPUBrust.isEmpty()
					|| processArrivalTime.isEmpty() || processPriority.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please Fill All Fields");
				return;
			}
			Object[] newRow = { processId, processName, processCPUBrust, processArrivalTime, processPriority };
			processTableModel.addRow(newRow);
			processIdTextField.setText("");
			processNameTextField.setText("");
			processCPUBrustTextField.setText("");
			processArrivalTimeTextField.setText("");
			processPriorityTextField.setText("");
		});

		deleteProcessButton.addActionListener(action -> {
			processTableModel.removeRow(processTable.getSelectedRow());
		});

		processTable.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowIndex = processTable.getSelectedRow();
				String processId = processTableModel.getValueAt(rowIndex, 0).toString();
				String processName = processTableModel.getValueAt(rowIndex, 1).toString();
				String processCPUBrust = processTableModel.getValueAt(rowIndex, 2).toString();
				String processArrivalTime = processTableModel.getValueAt(rowIndex, 3).toString();
				String processPriority = processTableModel.getValueAt(rowIndex, 4).toString();

				processIdTextField.setText(processId);
				processNameTextField.setText(processName);
				processCPUBrustTextField.setText(processCPUBrust);
				processArrivalTimeTextField.setText(processArrivalTime);
				processPriorityTextField.setText(processPriority);
			}
		});

		updateProcessButton.addActionListener(action -> {
			String processId = processIdTextField.getText().toString();
			String processName = processNameTextField.getText().toString();
			String processCPUBrust = processCPUBrustTextField.getText();
			String processArrivalTime = processArrivalTimeTextField.getText();
			String processPriority = processPriorityTextField.getText();

			int rowIndex = processTable.getSelectedRow();
			processTableModel.setValueAt(processId, rowIndex, 0);
			processTableModel.setValueAt(processName, rowIndex, 1);
			processTableModel.setValueAt(processCPUBrust, rowIndex, 2);
			processTableModel.setValueAt(processArrivalTime, rowIndex, 3);
			processTableModel.setValueAt(processPriority, rowIndex, 4);

			processIdTextField.setText("");
			processNameTextField.setText("");
			processCPUBrustTextField.setText("");
			processArrivalTimeTextField.setText("");
			processPriorityTextField.setText("");

		});

		clearProcessButton.addActionListener(action -> {
			processIdTextField.setText("");
			processNameTextField.setText("");
			processCPUBrustTextField.setText("");
			processArrivalTimeTextField.setText("");
			processPriorityTextField.setText("");
		});

		visualizeButton.addActionListener(action -> {
			int selectedAlgorithm = listAlgorithm.getSelectedIndex();
			if (selectedAlgorithm == -1) {
				JOptionPane.showMessageDialog(this, "Please select an Algorithm");
				return;
			}
			new VisualizeAlgorithm(processTableModel.getDataVector(),
					selectedAlgorithm);
		});

		processManipulationPanel.add(processIdLabel);
		processManipulationPanel.add(processIdTextField);

		processManipulationPanel.add(processNameLabel);
		processManipulationPanel.add(processNameTextField);

		processManipulationPanel.add(processCPUBrustLabel);
		processManipulationPanel.add(processCPUBrustTextField);

		processManipulationPanel.add(processArrivalTimeLabel);
		processManipulationPanel.add(processArrivalTimeTextField);

		processManipulationPanel.add(processPriorityLabel);
		processManipulationPanel.add(processPriorityTextField);
		processManipulationPanel.add(addProcessButton);
		processManipulationPanel.add(updateProcessButton);
		processManipulationPanel.add(deleteProcessButton);
		processManipulationPanel.add(clearProcessButton);

		processListPanel.add(visualizeButton);

		this.add(algorithmListPanel);
		this.add(processManipulationPanel);
		this.add(processListPanel);
		this.pack();
		this.setVisible(true);
	}
}
