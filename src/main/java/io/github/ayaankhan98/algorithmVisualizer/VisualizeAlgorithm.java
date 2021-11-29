package io.github.ayaankhan98.algorithmVisualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JFrame;

/**
 * {@summary} creates Frame for visualizer window
 */
class VisualizeAlgorithm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/// keep track of all processes in memory
	private Vector<Process> processes;

	/// stores the algorithm which is selected for visualization
	private int selectedAlgorithm;

	/// y-coordinate for process Name label in visualization screen
	private final int processNameYPos = 70;

	/// gantt chart y-coordinate in visualization screen
	private final int processGanttChartYPos = 80;

	/// y-coordinate for Time stamp of processes in visualization screen
	private final int processTimeStampYPos = 135;

	/// scalling factor for ganttchart element width
	private final double ganttChartScalingFactor = 15.0;

	/// gap between two ganttchart strips
	private final int ganttChartGapFactor = 5;

	/// height of a gantt chart strip
	private final int ganttChartStripHeight = 40;

	/// for multiple color of gantt chart strips
	private final Color[] colors = { Color.RED, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.GREEN, Color.PINK,
			Color.WHITE, Color.YELLOW };

	/**
	 * 
	 * @param processData       contains raw data of all proceses which are supposed
	 *                          to be visualized
	 * @param selectedAlgorithm selected algorithm for visulisation
	 */
	public VisualizeAlgorithm(Vector<Vector> processData, int selectedAlgorithm) {
		this.selectedAlgorithm = selectedAlgorithm;
		processes = new Vector<>();

		/// creating processes from raw processes data
		for (int i = 0; i < processData.size(); i++) {
			int processId = Integer.parseInt(processData.elementAt(i).elementAt(0).toString());
			String processName = processData.elementAt(i).elementAt(1).toString();
			int processCPUBrust = Integer.parseInt(processData.elementAt(i).elementAt(2).toString());
			int processArrivalTime = Integer.parseInt(processData.elementAt(i).elementAt(3).toString());
			int processPriority = Integer.parseInt(processData.elementAt(i).elementAt(4).toString());

			/// creating new processes form raw its raw data
			processes.add(new Process(processId, processName, processCPUBrust, processArrivalTime, processPriority));
		}

		/// initializing visulisation frame window title as per the selected
		/// algorithm
		switch (selectedAlgorithm) {
		case 0:
			this.setTitle("FCFS Visualizer");
			break;
		case 1:
			this.setTitle("Premptive SJF Visualizer");
			break;
		case 2:
			this.setTitle("Non Premptive SJF Visualizer");
			break;
		case 3:
			this.setTitle("Premptive Priority Scheduling Visualizer");
			break;
		case 4:
			this.setTitle("Non Premptive Priority Scheduling Visualizer");
			break;
		default:
			break;
		}

		this.setBackground(Color.BLACK);
		this.setSize(1024, 900);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * {@summary} shows the end results of algorithm in tabular format of all
	 * processes
	 * 
	 * @param g2d Graphics object for drawing objects on screen for visulisation
	 */
	private void displayVisulizationSummary(Graphics2D g2d) {
		/// setting text color to white
		g2d.setColor(Color.WHITE);

		/// calculating average waiting time and average turn around time for
		/// all processes in memory
		double averageWaitingTime = 0.0;
		double averageTurnAroundTime = 0.0;

		/// displaying table of all processes final result.
		g2d.drawString("Process Name     CPU Brust    Arrival Time     " + "TurnAround Time     Waiting Time      "
				+ "Completion Time", 20, 200);

		int yPos = 220;
		for (int i = 0; i < processes.size(); i++) {
			g2d.drawString("          " + processes.elementAt(i).getProcessName() + "                         "
					+ processes.elementAt(i).getProcessCPUBrust() + "                   "
					+ processes.elementAt(i).getProcessArrivalTime() + "                " + "         "
					+ processes.elementAt(i).getProcessTurnAroundTime() + "             " + "         "
					+ processes.elementAt(i).getProcessWaitingTime() + "                " + "          "
					+ processes.elementAt(i).getProcessCompletionTime(), 10, yPos);
			averageTurnAroundTime += processes.elementAt(i).getProcessTurnAroundTime();
			averageWaitingTime += processes.elementAt(i).getProcessWaitingTime();
			yPos = yPos + 20;
		}
		averageWaitingTime /= processes.size();
		averageWaitingTime = Math.floor((int) (averageWaitingTime * 1000)) / 1000;
		averageTurnAroundTime /= processes.size();
		averageTurnAroundTime = Math.floor((int) (averageTurnAroundTime * 1000)) / 1000;
		g2d.drawString("Average Turn Around Time = " + averageTurnAroundTime, 20, yPos);
		g2d.drawString("Average Waiting Time = " + averageWaitingTime, 20, yPos + 20);
	}

	/**
	 * {@summary} implements first come first server (FCFS) policy for proceses
	 * scheduling.
	 *
	 * @param g2d Graphics object for drawing object on screen.
	 */
	private void FCFS(Graphics2D g2d) {
		Collections.sort(processes, new ArrivalTimeSorter());
		int xPos = 20;
		int currentTimeInstant = 0;
		for (int i = 0; i < processes.size(); i++) {
			g2d.setColor(colors[(int) Math.floor(Math.random() * 100) % 8]);
			processes.elementAt(i)
					.setProcessWaitingTime(currentTimeInstant - processes.elementAt(i).getProcessArrivalTime());
			processes.elementAt(i).setProcessTurnAroundTime(
					processes.elementAt(i).getProcessWaitingTime() + processes.elementAt(i).getProcessCPUBrust());
			g2d.drawString(processes.elementAt(i).getProcessName(), xPos, processNameYPos);
			g2d.fillRect(xPos, processGanttChartYPos,
					(int) Math.floor(processes.elementAt(i).getProcessCPUBrust() * ganttChartScalingFactor),
					ganttChartStripHeight);
			g2d.drawString(((Integer) currentTimeInstant).toString(), xPos - 2, processTimeStampYPos);
			xPos += ganttChartGapFactor
					+ (int) Math.floor(processes.elementAt(i).getProcessCPUBrust() * ganttChartScalingFactor);
			currentTimeInstant += processes.elementAt(i).getProcessCPUBrust();
			processes.elementAt(i).setProcessCompletionTime(currentTimeInstant);
		}
		g2d.drawString(((Integer) currentTimeInstant).toString(), xPos - 2, processTimeStampYPos);

		displayVisulizationSummary(g2d);
	}

	/**
	 * {@summary} helping fuction which gives the next shortest job scheduling
	 * processes for execution
	 * 
	 * @param currentTime time instant to ensure only those processes which are
	 *                    arrived till this time instant.
	 * @return index of processes which will be executed next
	 */
	private int getSJFScheduleProcess(int currentTime) {
		int res = 0;
		int currentCPUBrust = 1000000;
		for (int i = 0; i < processes.size(); i++) {
			if (processes.elementAt(i).getProcessWaitingTime() == -1
					&& currentTime >= processes.elementAt(i).getProcessArrivalTime()) {
				if (currentCPUBrust > processes.elementAt(i).getProcessCPUBrust()) {
					res = i;
					currentCPUBrust = processes.elementAt(i).getProcessCPUBrust();
				}
			}
		}
		return res;
	}

	/**
	 * {@summary} function implements non premptive algorithms namely non premptive
	 * SJF algorithm and non premptive Priority scheduling algorithm. The selection
	 * of which algorithm to be executed is done based on the value of
	 * selectedAlgorithm variable.
	 *
	 * @param g2d               graphics object used for drawing stuff on
	 *                          visulisation screen.
	 * @param selectedAlgorithm 0 for non premptive priority scheduling algorithm
	 *                          and 1 for non premptive SJF algorithm.
	 */
	private void nonPremptiveAlgorithms(Graphics2D g2d, int selectedAlgorithm) {
		Collections.sort(processes, new ArrivalTimeSorter());
		int xPos = 20;
		int currentTimeInstant = 0;
		for (int i = 0; i < processes.size(); i++) {
			g2d.setColor(colors[(int) Math.floor(Math.random() * 100) % 8]);
			int currentScheduledProcessIndex = -1;
			switch (selectedAlgorithm) {
			/// invoking the corrosponsing function to get the next processes
			/// based on the value of selectedAlgorithm
			case 0:
				currentScheduledProcessIndex = getPriorityScheduleProcess(currentTimeInstant);
				break;
			case 1:
				currentScheduledProcessIndex = getSJFScheduleProcess(currentTimeInstant);
				break;
			}
			processes.elementAt(currentScheduledProcessIndex).setProcessWaitingTime(
					currentTimeInstant - processes.elementAt(currentScheduledProcessIndex).getProcessArrivalTime());
			processes.elementAt(currentScheduledProcessIndex)
					.setProcessTurnAroundTime(processes.elementAt(currentScheduledProcessIndex).getProcessWaitingTime()
							+ processes.elementAt(currentScheduledProcessIndex).getProcessCPUBrust());
			g2d.drawString(processes.elementAt(currentScheduledProcessIndex).getProcessName(), xPos, processNameYPos);
			g2d.fillRect(xPos, processGanttChartYPos, (int) Math.floor(
					processes.elementAt(currentScheduledProcessIndex).getProcessCPUBrust() * ganttChartScalingFactor),
					ganttChartStripHeight);
			g2d.drawString(((Integer) currentTimeInstant).toString(), xPos - 2, processTimeStampYPos);
			xPos += ganttChartGapFactor + (int) Math.floor(
					processes.elementAt(currentScheduledProcessIndex).getProcessCPUBrust() * ganttChartScalingFactor);
			currentTimeInstant += processes.elementAt(currentScheduledProcessIndex).getProcessCPUBrust();
			processes.elementAt(currentScheduledProcessIndex).setProcessCompletionTime(currentTimeInstant);
		}
		g2d.drawString(((Integer) currentTimeInstant).toString(), xPos - 2, processTimeStampYPos);

		/// displaying final results.
		displayVisulizationSummary(g2d);

	}

	/**
	 * {@summary} helping fuction which gives the next priority scheduling processes
	 * for execution
	 * 
	 * @param currentTime time instant to ensure only those processes which are
	 *                    arrived till this time instant.
	 * @return index of processes which will be executed next
	 */
	private int getPriorityScheduleProcess(int currentTime) {
		int res = 0;
		int currentPriority = 1000000;
		for (int i = 0; i < processes.size(); i++) {
			if (processes.elementAt(i).getProcessWaitingTime() == -1
					&& currentTime >= processes.elementAt(i).getProcessArrivalTime()) {
				if (currentPriority > processes.elementAt(i).getProcessPriority()) {
					res = i;
					currentPriority = processes.elementAt(i).getProcessPriority();
				}
			}
		}
		return res;
	}

	/**
	 * {@summary} function implements premptive algorithms namely premptive SJF
	 * algorithm and premptive Priority scheduling algorithm. The selection of which
	 * algorithm to be executed is done based on the value of selectedAlgorithm
	 * variable.
	 *
	 * @param g2d               graphics object used for drawing stuff on
	 *                          visulisation screen.
	 * @param selectedAlgorithm 0 for premptive priority scheduling algorithm and 1
	 *                          for premptive SJF algorithm.
	 */
	private void premptiveSchedulingAlgorithms(Graphics2D g2d, int selectedAlgorithm) {
		Collections.sort(processes, new ArrivalTimeSorter());
		int xPos = 20;
		int currentTimeInstant = 0;
		int completedProcesses = 0;
		int oneTimeUnitScale = 20;
		int previousScheduleProcessIndex = -1;
		int[] processCompletionTracker = new int[processes.size()];
		while (completedProcesses != processes.size()) {
			int currentScheduledProcessIndex = -1;
			switch (selectedAlgorithm) {
			case 0:
				currentScheduledProcessIndex = getPriorityScheduleProcess(currentTimeInstant);
				break;
			case 1:
				currentScheduledProcessIndex = getSJFScheduleProcess(currentTimeInstant);
				break;
			}
			if (previousScheduleProcessIndex != currentScheduledProcessIndex) {
				g2d.setColor(colors[(int) Math.floor(Math.random() * 100) % 8]);
				g2d.drawString(processes.elementAt(currentScheduledProcessIndex).getProcessName(), xPos,
						processNameYPos);
				xPos += ganttChartGapFactor;
				g2d.drawString(((Integer) currentTimeInstant).toString(), xPos - 2, processTimeStampYPos);
			}
			g2d.fillRect(xPos, processGanttChartYPos, oneTimeUnitScale, ganttChartStripHeight);
			currentTimeInstant++;
			processes.elementAt(currentScheduledProcessIndex).setProcessCompletionTime(currentTimeInstant);
			xPos += oneTimeUnitScale;
			processCompletionTracker[currentScheduledProcessIndex]++;
			if (processes.elementAt(currentScheduledProcessIndex)
					.getProcessCPUBrust() == processCompletionTracker[currentScheduledProcessIndex]) {
				processes.elementAt(currentScheduledProcessIndex).setProcessTurnAroundTime(
						processes.elementAt(currentScheduledProcessIndex).getProcessCompletionTime()
								- processes.elementAt(currentScheduledProcessIndex).getProcessArrivalTime());
				processes.elementAt(currentScheduledProcessIndex).setProcessWaitingTime(
						processes.elementAt(currentScheduledProcessIndex).getProcessTurnAroundTime()
								- processes.elementAt(currentScheduledProcessIndex).getProcessCPUBrust());
				completedProcesses++;
			}
			previousScheduleProcessIndex = currentScheduledProcessIndex;
		}
		g2d.drawString(((Integer) currentTimeInstant).toString(), xPos - 2, processTimeStampYPos);

		/// displaying final results.
		displayVisulizationSummary(g2d);
	}

	/**
	 * {@summary} function invokes the visulisation for corrosponsing algorithm
	 * based on value of selectedAlgorithm
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		switch (selectedAlgorithm) {
		case 0:
			FCFS(g2d);
			break;
		/// 1 for SJF
		case 1:
			premptiveSchedulingAlgorithms(g2d, 1);
			break;

		/// 1 for SJF
		case 2:
			nonPremptiveAlgorithms(g2d, 1);
			break;

		/// 0 for Priority Scheduling
		case 3:
			premptiveSchedulingAlgorithms(g2d, 0);
			break;

		/// 0 for Priority Scheduling
		case 4:
			nonPremptiveAlgorithms(g2d, 0);
			break;
		default:
			break;
		}
	}
}
