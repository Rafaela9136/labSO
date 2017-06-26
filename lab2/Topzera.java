import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;
import java.util.ArrayList;

public class Topzera {

	public static List<String> getAllPIDs() {
		List<String> PIDs = new ArrayList<String>();

		File folder = new File("/proc");
		for(File pid : folder.listFiles()) {
			if(pid.getName().matches("^[0-9]+$")) {
				PIDs.add(pid.getName());
			}
		}

		return PIDs;
	}

	public static String getProcessUid(String pid) {
		String processUser = null;

		try {
			File file = new File("/proc/" + pid + "/status");
			BufferedReader buffer = new BufferedReader(new FileReader(file));

			String line = "";
			while((line = buffer.readLine()) != null) {
				if(line.split("	")[0].equals("Uid:")) {
					processUser = line.split("	")[1];
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		return processUser; 
	}

	public static String getUserName(String uid) {
		String userName = null;

		try {
			String command = "grep " + uid + " /etc/passwd";
			Process process = Runtime.getRuntime().exec(command);

			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = input.readLine();
			userName = line.split(":")[0];
		} catch(IOException e) {
			e.printStackTrace();
		}

		return userName;
	}

	public static String getProcessName(String pid) {
		String processName = null;

		try {
			File file = new File("/proc/" + pid + "/stat");
			BufferedReader buffer = new BufferedReader(new FileReader(file));

			String line = buffer.readLine();
			processName = line.split(" ")[1];
		} catch(IOException e) {
			e.printStackTrace();
		}

		return processName;
	}

	public static String getProcessState(String pid) {
		String processState = null;

		try {
			File file = new File("/proc/" + pid + "/stat");
			BufferedReader buffer = new BufferedReader(new FileReader(file));

			String line = buffer.readLine();
			processState = line.split(" ")[2];
		} catch(IOException e) {
			e.printStackTrace();
		}

		return processState;	
	}

	public static void printTable() {
		List<String> PIDs = getAllPIDs();

		System.out.println("PID    | User    |    PROCNAME    | Estado |");
		System.out.println("-------|---------|----------------|--------|");
		for(int i = 0; i < 20; i++) {
			String pid = PIDs.get(i);
			String uid = getProcessUid(pid);
			String userName = getUserName(uid);
			String processName = getProcessName(pid);
			String processState = getProcessState(pid);
			System.out.printf("%-7s|%-9s|%-16s|%-8s|\n", pid, userName, processName, processState);
		}
	}

	public static void main(String[] args) {
		printTable();
	}
}