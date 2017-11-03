import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Routing {

    /*
    findPaths is the start of the routing algorithm
    It has output arraylist as a list of all the final paths for wires
    to get the outputs we call runBFS which starts the actual algorithms calling helpers

    */
    static ArrayList<ArrayList<Coord>>
    findPaths(Board board, ArrayList<Coord[]> points) {
        ArrayList<ArrayList<Coord>> output;
        Map<Coord, Integer> original = new HashMap<>();

        original.putAll(board.getGrid());
        Board bboard = new Board(original, board.getHeight(), board.getWidth());

        output = runBFS(board, points);

        // In-case output.size() returns null
        try {
            if (output.size() != points.size()) {
                output = runBFS(bboard, points);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return output;
    }

    /*
     runBFS is a null checker for markPaths
     It checks to see if there is an output at all.
     Instead of returning an empty array in markPaths, null will be returned
   */

    static ArrayList<ArrayList<Coord>> runBFS(Board board, ArrayList<Coord[]> points) {
        ArrayList<ArrayList<Coord>> output;

        output = markPaths(board, points);
        if (output.size() == 0) {
            return null;
        }
        return output;
    }

    /*
        markPaths method is what calls BFS for each individual pair of points
        It will take a Board as a parameter and a list of Coord Pairs
        For each Coordinate pair it will run the BFS method with the first(start) and other(finish) Coord
        BFS produces a list of paths for each pair
        For each path given:
            Find the path with the ending as the finish Node
            For each Coord in that path:
                If the coord is just a 0 on the board, change its value to be the same as the start value
            Finally add that path to the output and return it
     */

    static ArrayList<ArrayList<Coord>> markPaths(Board board, ArrayList<Coord[]> points) {
        ArrayList<ArrayList<Coord>> output = new ArrayList<>();
        for (Coord[] i : points) {
            Coord start = i[0];
            Coord finish = i[1];
            ArrayList<ArrayList<Coord>> paths = bfs(board, start, finish);
            for (ArrayList<Coord> j : paths) {
                if (j.get(j.size() - 1).equals(finish)) {
                    for (Coord k : j) {
                        if (!(k.equals(start) || k.equals(finish))) {
                            board.putValue(k, board.getValue(start));
                        }
                    }
                    output.add(j);
                }
            }
        }
        return output;
    }

    /*
          Basic BFS search method
          This method runs the basic breadth first search on the board given
          There will be a start Coord where the search begins from
          There will also be a finish Coord where the search should end on
          The method returns an ArrayList of ArrayLists of Coords
          Each ArrayList within the returned ArrayList represents additions to the BFS
          BFS will spread around the board as it preforms its algorithm with one of the
          lists actually being the past from start to finish
          This Method is the base BFS to be called later in class
       */

    static ArrayList<ArrayList<Coord>> bfs(Board board, Coord start, Coord finish) {
        Queue queue = new LinkedList();
        ArrayList<Coord> alist = new ArrayList<>();
        ArrayList<ArrayList<Coord>> paths = new ArrayList<>();
        HashMap<Coord, Boolean> visited = new HashMap<>();

        queue.add(start);
        alist.add(start);
        paths.add(alist);
        visited.put(start, true);

        while (!queue.isEmpty()) {
            Coord n = (Coord) queue.remove();
            for (Coord e : board.adj(n)) {
                if (visited.get(e) == null) {
                    if (board.getValue(e) == 0) {
                        ArrayList<Coord> temp = new ArrayList<>(path(paths, n));
                        temp.add(e);
                        paths.add(temp);
                        queue.add(e);
                    } else if (e.equals(finish)) {
                        ArrayList<Coord> temp = new ArrayList<>(path(paths, n));
                        temp.add(e);
                        paths.add(temp);
                        queue.add(e);
                        return paths;
                    }
                    visited.put(e, true);
                }
            }
        }
        return paths;
    }

    /*
        getPath method will take a list of list of coordinates
        The main list cotains other lists which are paths from BFS
        BFS worked by adding paths to a main list as it spreads around the grid
        getPath needs to get THE path which is from start to finish.
        This is obtained by getting the path that has the finish-node as its last entry
     */

    static ArrayList<Coord> path(ArrayList<ArrayList<Coord>> paths, Coord node) {
        for (ArrayList<Coord> e : paths) {
            if (node.equals(e.get(e.size() - 1))) {
                return e;
            }
        }
        return null;
    }
}