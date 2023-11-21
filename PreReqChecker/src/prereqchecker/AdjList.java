package prereqchecker;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    private HashMap<String, ArrayList<String>> adjList;
    public AdjList() {
        adjList = new HashMap<String, ArrayList<String>>();
        buildGraph();
    }
    public void buildGraph() {
        int numOfCourses = StdIn.readInt();
        StdIn.readLine();
        String course = null;

        for (int i = 0; i < numOfCourses; i++) {
            course = StdIn.readLine();
            adjList.put(course, new ArrayList<String>());
            adjList.get(course).add(course);
        }

        int numOfConnections = StdIn.readInt();
        StdIn.readLine();
        String prereq = null;

        for (int i = 0; i < numOfConnections; i++) {
            prereq = StdIn.readLine();

            course = prereq.substring(0, prereq.indexOf(" "));

            prereq = prereq.substring(prereq.indexOf(" ") + 1);
            adjList.get(course).add(prereq);
        }
    }
    public void addEdge(String course, String prereq) {
        adjList.get(course).add(prereq);
    }
    public HashMap<String, ArrayList<String>> getGraph() {
        return adjList;
    }
    public boolean hasCycle(String course1, String course2) {
        boolean hasCycle = search(course1, course2);
        return hasCycle;
    }
    public boolean search(String course1, String course2) {
        if (course2.equals(course1)) {
            return true;
        }
        if (adjList.get(course2).size() == 1) {
            return false;
        }

        for (int i = 1; i < adjList.get(course2).size(); i++) {
            if (search(course1, adjList.get(course2).get(i))) {
                return true;
            }
        }

        return false;
    }
    public ArrayList<String> coursesCompleted(ArrayList<String> courses) {
        ArrayList<String> prereqList = new ArrayList<String>();
        for (int i = 0; i < courses.size(); i++) {
            prereqList.add(courses.get(i));
            prereq(prereqList, courses.get(i));
        }
        return prereqList;
    }
    public void prereq(ArrayList<String> prereqList, String course) {
        for (int i = 1; i < adjList.get(course).size(); i++) {
            prereqList.add(adjList.get(course).get(i));
            prereq(prereqList, adjList.get(course).get(i));
        }
    }
    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        // WRITE YOUR CODE HERE

        StdIn.setFile(args[0]);

        AdjList graph = new AdjList();

        HashMap<String, ArrayList<String>> adjList = graph.getGraph();

        StdOut.setFile(args[1]);

        for (String key : adjList.keySet()) {
            for (int i = 0; i < adjList.get(key).size(); i++) {
                StdOut.print(adjList.get(key).get(i) + " ");
            }
            StdOut.println();
        }
    }
}