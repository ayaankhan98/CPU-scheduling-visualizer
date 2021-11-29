package io.github.ayaankhan98.algorithmVisualizer;

import java.util.Comparator;

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

	public Process() {
	}

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
	 * @param processName        name of process
	 * @param processCPUBrust    burst time of process
	 * @param processArrivalTime arrival time of process
	 */
	public Process(String processName, int processCPUBrust, int processArrivalTime) {
		this.processName = processName;
		this.processCPUBrust = processCPUBrust;
		this.processArrivalTime = processArrivalTime;
		this.processWaitingTime = -1;
		this.processTurnAroundTime = -1;
		this.processCompletionTime = -1;
	}

	/**
	 * 
	 * @param processId          ID of process
	 * @param processName        Name of process
	 * @param processCPUBrust    CPU burst time of process
	 * @param processArrivalTime Arrival time of process
	 * @param processPriority    Priority vale of process (lower priority value
	 *                           means higher the priority)
	 */
	public Process(int processId, String processName, int processCPUBrust, int processArrivalTime,
			int processPriority) {
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
	 * {@summary} copy constructor, copies one process into another
	 * 
	 * @param p process to be copied into another process
	 */
	public Process(Process p) {
		this.processId = p.getProcessId();
		this.processName = p.getProcessName();
		this.processCPUBrust = p.getProcessCPUBrust();
		this.processArrivalTime = p.getProcessArrivalTime();
		this.processPriority = p.getProcessPriority();
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
	 * @return turn around time of process.
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
		return processName + ", " + processCPUBrust + ", " + processArrivalTime + ", " + processPriority;
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
