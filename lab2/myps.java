import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class myps
{
	final static File folder = new File("/proc");
	static Map<String,List<String>> pids = new HashMap<String,List<String>>();

	public static void main(String[] args) {
		listFiles(folder);
		printPids("0", 0); 
	}

	public static void listFiles(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.getName().matches("^[0-9]+$")) {
				readFile("/proc/" + fileEntry.getName() + "/status");
			}
		}
	}

	private static void readFile(String filename) {
		try {
			String pid, ppid;

			pid = Files.readAllLines(Paths.get(filename)).get(4).split("	")[1];
			ppid = Files.readAllLines(Paths.get(filename)).get(5).split("	")[1];
	
			if(pids.get(ppid) == null) {
				List<String> pf = new ArrayList<String>();
				pf.add(pid);

				pids.put(ppid, pf);
			} else {
				pids.get(ppid).add(pid);
			}
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", filename);
	    	e.printStackTrace();
		}
	}

	public static void printPids(String parent, int deep) {
		List<String> childs = pids.get(parent);
		String space = deep(deep);
		deep++;	

		if(childs != null) { 
			for(String child : childs) {
				System.out.println(space + child);
				printPids(child, deep);
			}	
		}
	}

	private static String deep(int value) {
		String string = "";
		for(int i = 0; i < value; i++) {
			string = " " + string;
		}
		return string;
	}
}
