/**
 * @file Application.java
 *
 * @author Ayaan Khan
 *
 * {@summary} CPU Scheduling algorithm visualizer. Able to visualize
 *      - First come first serve algorithm
 *      - Non premptive Shortest Job First Algorithm
 *      - Preemptive shortest job first algorithm
 *      - Non premptive Priority Scheduling algorithm
 *      - Preemptive Priority Scheduling algorithm
 *
 *        Compiled with javac 16.0.2
 *        Tested on
 *        openjdk 16.0.2 2021-07-20
 *        OpenJDK Runtime Environment (build 16.0.2+7)
 *        OpenJDK 64-Bit Server VM (build 16.0.2+7, mixed mode)
 *
 */
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
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.Vector;
import java.util.Collections;

/**
 * {@summary} class denotes a single process with its attributes.
 */
class Process {

  /// stores the process ID of a process
  private int processId;

  /// stores name of process
  private String processName;

  /// stores the CPU brust time for a process
  private int processCPUBrust;

  /// stores the arrival time of process
  private int processArrivalTime;

  /// stores the priority of a process (utilized only in case of priority
  /// scheduling algorithm and will be ignored in rest of the algorithms)
  private int processPriority;

  /// stores the calculated waiting time for process
  private int processWaitingTime;

  /// stores the turn around time of process
  private int processTurnAroundTime;

  /// stores the completion time of a process
  private int processCompletionTime;

  public Process() {}

  /**
   * {@summary} one arg ctor
   *
   * @param processName name of process
   */
  public Process(String processName) {
    this.processName = processName;
    this.processWaitingTime = -1;
    this.processTurnAroundTime = -1;
    this.processCompletionTime = -1;
  }

  /**
   *
   * @param processName name of process
   * @param processCPUBrust brust time of process
   * @param processArrivalTime arrival time of process
   */
  public Process(String processName, int processCPUBrust,
      int processArrivalTime) {
    this.processName = processName;
    this.processCPUBrust = processCPUBrust;
    this.processArrivalTime = processArrivalTime;
    this.processWaitingTime = -1;
    this.processTurnAroundTime = -1;
    this.processCompletionTime = -1;
  }

  /**
   * 
   * @param processId ID of process
   * @param processName Name of process
   * @param processCPUBrust CPU brust time of process
   * @param processArrivalTime Arrival time of process
   * @param processPriority Priority vale of process (lower priority value
   * means higher the priority)
   */
  public Process(int processId, String processName, int processCPUBrust,
      int processArrivalTime, int processPriority) {
    this.processId = processId;
    this.processName = processName;
    this.processCPUBrust = processCPUBrust;
    this.processArrivalTime = processArrivalTime;
    this.processPriority = processPriority;
    this.processWaitingTime = -1;
    this.processTurnAroundTime = -1;
    this.processCompletionTime = -1;
  }

  /**
   * {@summary} copy ctor, copies one process into another
   * @param p process to be copied into another process
   */
  public Process(Process p) {
    this.processId = p.getProcessId();
    this.processName = p.getProcessName();
    this.processCPUBrust = p.getProcessCPUBrust();
    this.processArrivalTime = p.getProcessArrivalTime();
    this.processPriority =  p.getProcessPriority();
    this.processWaitingTime = p.getProcessWaitingTime();
    this.processTurnAroundTime = p.getProcessTurnAroundTime();
    this.processCompletionTime = p.getProcessCompletionTime();
  }

  /**
   *
   * @return ID of process on which it is called.
   */
  public int getProcessId() {
    return processId;
  }

  /**
   *
   * @param processId Process ID (integer value which is to be set as processID)
   */
  public void setProcessId(int processId) {
    this.processId = processId;
  }

  /**
   *
   * @return name of the process on which it is called
   */
  public String getProcessName() {
    return processName;
  }

  /**
   *
   * @param processName Name of process which is to be set
   */
  public void setProcessName(String processName) {
    this.processName = processName;
  }

  /**
   *
   * @return the CPU brust of process on which it is called
   */
  public int getProcessCPUBrust() {
    return processCPUBrust;
  }

  /**
   *
   * @param processCPUBrust value to be set as CPU brust time
   */
  public void setProcessCPUBrust(int processCPUBrust) {
    this.processCPUBrust = processCPUBrust;
  }

  /**
   *
   * @return the arrival time of process on which it is called
   */
  public int getProcessArrivalTime() {
    return processArrivalTime;
  }

  /**
   *
   * @param processArrivalTime arrival time value which you wish to set
   */
  public void setProcessArrivalTime(int processArrivalTime) {
    this.processArrivalTime = processArrivalTime;
  }

  /**
   *
   * @return priority value of process on which it is called
   */
  public int getProcessPriority() {
    return processPriority;
  }

  /**
   * @param processPriority sets this value as priority of process
   */
  public void setProcessPriority(int processPriority) {
    this.processPriority = processPriority;
  }

  /**
   *
   * @return waiting time of process
   */
  public int getProcessWaitingTime() {
    return processWaitingTime;
  }

  /**
   *
   * @param processWaitingTime sets this value as waiting time of process
   */
  public void setProcessWaitingTime(int processWaitingTime) {
    this.processWaitingTime = processWaitingTime;
  }

  /**
   *
   * @return turnaround time of process.
   */
  public int getProcessTurnAroundTime() {
    return processTurnAroundTime;
  }

  /**
   *
   * @param processTurnAroundTime sets this value as process turnaround time.
   */
  public void setProcessTurnAroundTime(int processTurnAroundTime) {
    this.processTurnAroundTime = processTurnAroundTime;
  }

  /**
   *
   * @return completion time of process
   */
  public int getProcessCompletionTime() {
    return processCompletionTime;
  }

  public void setProcessCompletionTime(int processCompletionTime) {
    this.processCompletionTime = processCompletionTime;
  }

  /**
   * retusn the information of a process in form of a string value
   */
  public String toString() {
    return processName + ", " + processCPUBrust + ", " + processArrivalTime +
      ", " + processPriority;
  }
}

/**
 * {@summary} sorts all processes in order of increasing arrival time
 */
class ArrivalTimeSorter implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
    return p1.getProcessArrivalTime() - p2.getProcessArrivalTime();
  }
}

/**
 * {@summary} sorts all processes in order of increasing CPU brust time.
 */
class CPUBrustSorter implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
    return p1.getProcessCPUBrust() - p2.getProcessCPUBrust();
  }
}

/**
 * {@summary} sorts all processes in decreasing order of priorities. (Lower the
 * priority value higher the priority of process)
 */
class PrioritySorter implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
    return p1.getProcessPriority() - p2.getProcessPriority();
  }
}

/**
 * {@summary} creates Frame for visualizer window
 */
class VisualizeAlgorithm extends JFrame {
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
  private final Color [] colors = {Color.RED, Color.MAGENTA, Color.CYAN,
    Color.GRAY, Color.GREEN, Color.PINK, Color.WHITE, Color.YELLOW};

  /**
   * 
   * @param processData contains raw data of all proceses which are supposed
   * to be visualized
   * @param selectedAlgorithm selected algorithm for visulisation
   */
  public VisualizeAlgorithm(Vector<Vector> processData, int selectedAlgorithm) {
    this.selectedAlgorithm = selectedAlgorithm;
    processes = new Vector<>();

    /// creating processes from raw processes data
    for(int i = 0; i < processData.size(); i++) {
      int processId = Integer.parseInt(processData.elementAt(i).elementAt(0)
          .toString());
      String processName = processData.elementAt(i).elementAt(1).toString();
      int processCPUBrust = Integer.parseInt(processData.elementAt(i)
          .elementAt(2).toString());
      int processArrivalTime = Integer.parseInt(processData.elementAt(i)
          .elementAt(3).toString());
      int processPriority = Integer.parseInt(processData.elementAt(i)
          .elementAt(4).toString());

      /// creating new processes form raw its raw data
      processes.add(new Process(processId, processName, processCPUBrust,
            processArrivalTime, processPriority));
    }

    /// initializing visulisation frame window title as per the selected
    /// algorithm
    switch(selectedAlgorithm) {
      case 0: this.setTitle("FCFS Visualizer");
              break;
      case 1: this.setTitle("Premptive SJF Visualizer");
              break;
      case 2: this.setTitle("Non Premptive SJF Visualizer");
              break;
      case 3: this.setTitle("Premptive Priority Scheduling Visualizer");
              break;
      case 4: this.setTitle("Non Premptive Priority Scheduling Visualizer");
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
    g2d.drawString("Process Name     CPU Brust    Arrival Time     " +
        "TurnAround Time     Waiting Time      " +
        "Completion Time", 20, 200);

    int yPos = 220;
    for(int i = 0; i < processes.size(); i++) {
      g2d.drawString("          "  +
          processes.elementAt(i).getProcessName() + "                         "
          + processes.elementAt(i).getProcessCPUBrust() + "                   "
          + processes.elementAt(i).getProcessArrivalTime() + "                "
          + "         "
          + processes.elementAt(i).getProcessTurnAroundTime() + "             "
          + "         "
          + processes.elementAt(i).getProcessWaitingTime() + "                "
          + "          "
          + processes.elementAt(i).getProcessCompletionTime(), 10, yPos);
      averageTurnAroundTime += processes.elementAt(i).getProcessTurnAroundTime();
      averageWaitingTime += processes.elementAt(i).getProcessWaitingTime();
      yPos = yPos + 20;
    }
    averageWaitingTime /= processes.size();
    averageWaitingTime = Math.floor((int) (averageWaitingTime * 1000)) / 1000;
    averageTurnAroundTime /= processes.size();
    averageTurnAroundTime = Math.floor((int)
        (averageTurnAroundTime * 1000)) / 1000;
    g2d.drawString("Average Turn Around Time = " + averageTurnAroundTime, 20,
        yPos);
    g2d.drawString("Average Waiting Time = " + averageWaitingTime, 20,
        yPos + 20);
  }

  /**
   * {@summary} implements first come first server (FCFS) policy for
   * proceses scheduling.
   *
   * @param g2d Graphics object for drawing object on screen.
   */
  private void FCFS(Graphics2D g2d) {
    Collections.sort(processes, new ArrivalTimeSorter());
    int xPos = 20;
    int currentTimeInstant = 0;
    for(int i = 0; i < processes.size(); i++) {
      g2d.setColor(colors[(int)Math.floor(Math.random() * 100) % 8]);
      processes.elementAt(i).setProcessWaitingTime(currentTimeInstant -
          processes.elementAt(i).getProcessArrivalTime());
      processes.elementAt(i).setProcessTurnAroundTime(processes.elementAt(i)
          .getProcessWaitingTime() + processes.elementAt(i)
          .getProcessCPUBrust());
      g2d.drawString(processes.elementAt(i).getProcessName(), xPos,
          processNameYPos);
      g2d.fillRect(xPos, processGanttChartYPos, (int)
          Math.floor(processes.elementAt(i)
            .getProcessCPUBrust() * ganttChartScalingFactor),
          ganttChartStripHeight);
      g2d.drawString(((Integer)currentTimeInstant).toString(), xPos - 2,
          processTimeStampYPos);
      xPos += ganttChartGapFactor + (int) Math.floor(processes.elementAt(i)
          .getProcessCPUBrust() * ganttChartScalingFactor);
      currentTimeInstant += processes.elementAt(i).getProcessCPUBrust();
      processes.elementAt(i).setProcessCompletionTime(currentTimeInstant);
    }
    g2d.drawString(((Integer)currentTimeInstant).toString(), xPos - 2,
        processTimeStampYPos);

    displayVisulizationSummary(g2d);
  }

  /**
   * {@summary} helping fuction which gives the next shortest job scheduling
   * processes for execution
   * @param currentTime time instant to ensure only those processes which are
   * arrived till this time instant.
   * @return index of processes which will be executed next
   */
  private int getSJFScheduleProcess(int currentTime) {
    int res = 0;
    int currentCPUBrust = 1000000;
    for(int i = 0; i < processes.size(); i++) {
      if (processes.elementAt(i).getProcessWaitingTime() == -1 &&
          currentTime >= processes.elementAt(i).getProcessArrivalTime()) {
        if (currentCPUBrust > processes.elementAt(i).getProcessCPUBrust()) {
          res = i;
          currentCPUBrust = processes.elementAt(i).getProcessCPUBrust();
        }
      }
    }
    return res;
  }

  /**
   * {@summary} function implements non premptive algorithms namely
   * non premptive SJF algorithm and non premptive Priority scheduling
   * algorithm. The selection of which algorithm to be executed is done
   * based on the value of selectedAlgorithm variable.
   *
   * @param g2d graphics object used for drawing stuff on visulisation screen.
   * @param selectedAlgorithm 0 for non premptive priority scheduling algorithm
   * and 1 for non premptive SJF algorithm.
   */
  private void nonPremptiveAlgorithms(Graphics2D g2d, int selectedAlgorithm) {
    Collections.sort(processes, new ArrivalTimeSorter());
    int xPos = 20;
    int currentTimeInstant = 0;
    for(int i = 0; i < processes.size(); i++) {
      g2d.setColor(colors[(int)Math.floor(Math.random() * 100) % 8]);
      int currentScheduledProcessIndex = -1;
      switch(selectedAlgorithm) {
        /// invoking the corrosponsing function to get the next processes
        /// based on the value of selectedAlgorithm
        case 0: currentScheduledProcessIndex = getPriorityScheduleProcess(
                    currentTimeInstant);
                break;
        case 1: currentScheduledProcessIndex = getSJFScheduleProcess(
                    currentTimeInstant);
                break;
      }
      processes.elementAt(currentScheduledProcessIndex).setProcessWaitingTime(
          currentTimeInstant - processes.elementAt(currentScheduledProcessIndex)
          .getProcessArrivalTime());
      processes.elementAt(currentScheduledProcessIndex).
        setProcessTurnAroundTime(processes.elementAt(
              currentScheduledProcessIndex).getProcessWaitingTime() +
            processes.elementAt(currentScheduledProcessIndex)
            .getProcessCPUBrust());
      g2d.drawString(processes.elementAt(currentScheduledProcessIndex)
          .getProcessName(), xPos, processNameYPos);
      g2d.fillRect(xPos, processGanttChartYPos, (int) Math.floor(processes.
            elementAt(currentScheduledProcessIndex).getProcessCPUBrust() * 
            ganttChartScalingFactor), ganttChartStripHeight);
      g2d.drawString(((Integer)currentTimeInstant).toString(), xPos - 2,
          processTimeStampYPos);
      xPos += ganttChartGapFactor + (int) Math.floor(processes.elementAt(
            currentScheduledProcessIndex).getProcessCPUBrust() * 
          ganttChartScalingFactor);
      currentTimeInstant += processes.elementAt(currentScheduledProcessIndex)
        .getProcessCPUBrust();
      processes.elementAt(currentScheduledProcessIndex)
        .setProcessCompletionTime(currentTimeInstant);
    }
    g2d.drawString(((Integer)currentTimeInstant).toString(), xPos - 2,
        processTimeStampYPos);

    /// displaying final results.
    displayVisulizationSummary(g2d);

  }

  /**
   * {@summary} helping fuction which gives the next priority scheduling
   * processes for execution
   * @param currentTime time instant to ensure only those processes which are
   * arrived till this time instant.
   * @return index of processes which will be executed next
   */
  private int getPriorityScheduleProcess(int currentTime) {
    int res = 0;
    int currentPriority = 1000000;
    for(int i = 0; i < processes.size(); i++) {
      if (processes.elementAt(i).getProcessWaitingTime() == -1 &&
          currentTime >= processes.elementAt(i).getProcessArrivalTime()) {
        if (currentPriority > processes.elementAt(i).getProcessPriority()) {
          res = i;
          currentPriority = processes.elementAt(i).getProcessPriority();
        }
      }
    }
    return res;
  }

 /**
   * {@summary} function implements premptive algorithms namely
   * premptive SJF algorithm and premptive Priority scheduling
   * algorithm. The selection of which algorithm to be executed is done
   * based on the value of selectedAlgorithm variable.
   *
   * @param g2d graphics object used for drawing stuff on visulisation screen.
   * @param selectedAlgorithm 0 for premptive priority scheduling algorithm
   * and 1 for premptive SJF algorithm.
   */
  private void premptiveSchedulingAlgorithms(Graphics2D g2d,
      int selectedAlgorithm) {
    Collections.sort(processes, new ArrivalTimeSorter());
    int xPos = 20;
    int currentTimeInstant = 0;
    int completedProcesses = 0;
    int oneTimeUnitScale = 20;
    int previousScheduleProcessIndex = -1;
    int [] processCompletionTracker = new int[processes.size()];
    while(completedProcesses != processes.size()) {
      int currentScheduledProcessIndex = -1;
      switch(selectedAlgorithm) {
        case 0: currentScheduledProcessIndex = getPriorityScheduleProcess(
          currentTimeInstant);
                break;
        case 1: currentScheduledProcessIndex = getSJFScheduleProcess(
                    currentTimeInstant);
                break;
      }
      if (previousScheduleProcessIndex != currentScheduledProcessIndex) {
        g2d.setColor(colors[(int)Math.floor(Math.random() * 100) % 8]);
        g2d.drawString(processes.elementAt(currentScheduledProcessIndex)
            .getProcessName(), xPos, processNameYPos);
        xPos += ganttChartGapFactor;
        g2d.drawString(((Integer)currentTimeInstant).toString(), xPos - 2,
            processTimeStampYPos);
      }
      g2d.fillRect(xPos, processGanttChartYPos, oneTimeUnitScale,
          ganttChartStripHeight);
      currentTimeInstant++;
      processes.elementAt(currentScheduledProcessIndex)
        .setProcessCompletionTime(currentTimeInstant);
      xPos += oneTimeUnitScale;
      processCompletionTracker[currentScheduledProcessIndex]++;
      if (processes.elementAt(currentScheduledProcessIndex)
          .getProcessCPUBrust() == processCompletionTracker
          [currentScheduledProcessIndex]) {
        processes.elementAt(currentScheduledProcessIndex).
          setProcessTurnAroundTime(processes.elementAt(
                currentScheduledProcessIndex).getProcessCompletionTime() -
              processes.elementAt(currentScheduledProcessIndex)
              .getProcessArrivalTime());
        processes.elementAt(currentScheduledProcessIndex)
          .setProcessWaitingTime(processes.elementAt(
                currentScheduledProcessIndex).getProcessTurnAroundTime() -
            processes.elementAt(currentScheduledProcessIndex)
            .getProcessCPUBrust());
        completedProcesses++;
      }
      previousScheduleProcessIndex = currentScheduledProcessIndex;
    }
    g2d.drawString(((Integer)currentTimeInstant).toString(), xPos - 2,
        processTimeStampYPos);

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
    switch(selectedAlgorithm) {
      case 0: FCFS(g2d);
              break;
              /// 1 for SJF
      case 1: premptiveSchedulingAlgorithms(g2d, 1);
              break;

              /// 1 for SJF
      case 2: nonPremptiveAlgorithms(g2d, 1);
              break;

              /// 0 for Priority Scheduling
      case 3: premptiveSchedulingAlgorithms(g2d, 0);
              break;

              /// 0 for Priority Scheduling
      case 4: nonPremptiveAlgorithms(g2d, 0);
              break;
      default:
              break;
    }
  }
}

/**
 * {@summary} class creates Main application frame, used for inputing ard
 * manipulating data of processes
 */
class AlgorithmVisulizer extends JFrame {

  /// lists all available algorithm
  private String [] listAlgorithmsData;

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
    Object [][] processTableModelData = {
      {1, "P1", 4, 0, 1},
      {2, "P2", 3, 0, 2},
      {3, "P3", 7, 6, 1},
      {4, "P4", 4, 11, 3},
      {5, "P5", 2, 12, 2}
    };

    /// creating table for showing processes data
    String [] processTableModelColumns = {"Process Id", "Process Name",
      "CPU Brust", "Arrival Time", "Priority"};
    processTableModel = new DefaultTableModel(processTableModelData,
        processTableModelColumns);

    this.setTitle("Algorithm Visulizer");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(800, 500));
    this.setResizable(false);
    this.setLayout(null);
    this.setLocationRelativeTo(null);

    JList<String> listAlgorithm = new JList<>(listAlgorithmsData);
    listAlgorithm.setVisibleRowCount(4);

    JScrollPane listAlgorithmScrollPane = new JScrollPane(listAlgorithm,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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

      if (processId.isEmpty() || processName.isEmpty() ||
          processCPUBrust.isEmpty() || processArrivalTime.isEmpty() ||
          processPriority.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Please Fill All Fields");
          return;
          }
      Object[] newRow = {
        processId,
        processName,
        processCPUBrust,
        processArrivalTime,
        processPriority
      };
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

    processTable.addMouseListener(new MouseInputAdapter(){
      @Override
      public void mouseClicked(MouseEvent e) {
        int rowIndex = processTable.getSelectedRow();
        String processId = processTableModel.getValueAt(rowIndex, 0).toString();
        String processName = processTableModel.getValueAt(rowIndex, 1)
          .toString();
        String processCPUBrust = processTableModel.getValueAt(rowIndex, 2)
          .toString();
        String processArrivalTime = processTableModel.getValueAt(rowIndex, 3)
          .toString();
        String processPriority = processTableModel.getValueAt(rowIndex, 4)
          .toString();

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
      VisualizeAlgorithm visualizeAlgorithm = new VisualizeAlgorithm(
          processTableModel.getDataVector(), selectedAlgorithm);
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

/**
 * {@summary} class implements main function
 */
public class Application {
  public static void main(String [] args) {
    new AlgorithmVisulizer();
  }
}
