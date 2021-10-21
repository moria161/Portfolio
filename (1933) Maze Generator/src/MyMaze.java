import java.util.Random;

public class MyMaze {
    Cell[][] maze;
    int startRow;
    int endRow;
    int cols;
    int rows;

    public MyMaze(int cols, int rows, int startRow, int endRow) { // note: y is rows, x is columns
        // set class attributes
        this.maze = new Cell[cols][rows];
        this.startRow = startRow;
        this.endRow = endRow;
        this.rows = rows;
        this.cols = cols;

        // create a new cell object at each location on the 2D cell array
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                maze[i][j] = new Cell();
            }
        }
    } // MyMaze constructor

    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {
        MyMaze maze = new MyMaze(rows, cols, startRow, endRow);
        Stack1Gen<int[]> stack = new Stack1Gen<>();
        Random rnd = new Random();

        // initialize stack
        stack.push(new int[]{startRow, 0});
        maze.maze[startRow][0].setVisited(true);
        while (!stack.isEmpty()) { // some Array errors still but works with squares

            // generate list of reachable elements AND check if it is a dead end
            int[][] listOfReachable = new int[4][2];
            int[] top = stack.top();
            int x = top[1];
            int y = top[0];
            boolean deadEnd = true;
            int ptr = 0;
            if ((y == rows - 1 && x != cols - 1) && !maze.maze[y][x + 1].getVisited()) { // check for an edge cell
                listOfReachable[0] = new int[]{y, x + 1};
                ptr++;
            } else if ((y != rows - 1 && x == cols - 1) && !maze.maze[y + 1][x].getVisited()) {
                listOfReachable[0] = new int[]{y + 1, x};
                ptr++;
            } else if ((y == rows - 1 && x != cols - 1) && maze.maze[y][x + 1].getVisited()) { // pop if edge cell is deadEnd
                stack.pop();
                continue;
            } else if ((y != rows - 1 && x == cols - 1) && maze.maze[y + 1][x].getVisited()) {
                stack.pop();
                continue;
            } else if (y == rows - 1 && x == cols - 1) {
                stack.pop();
                continue;
            } else { // grab accessible cells which are neighbors
                if (y + 1 != rows ) { // FIXME: these rectangles still give array exceptions
                    if (!maze.maze[y + 1][x].getVisited()) {
                        listOfReachable[ptr] = new int[]{y + 1, x};
                        deadEnd = false;
                        ptr++;
                    }

                }
                if (y - 1 >= 0){
                    if (!maze.maze[y-1][x].getVisited()){
                        listOfReachable[ptr] = new int[] {y - 1, x};
                        deadEnd = false;
                        ptr++;
                    }
                }
                if (x + 1 != cols) {
                    if (!maze.maze[y][x + 1].getVisited()) {
                        listOfReachable[ptr] = new int[]{y, x + 1};
                        deadEnd = false;
                        ptr++;
                    }
                }
                if (x - 1 >= 0){
                    if (!maze.maze[y][x - 1].getVisited()){
                        listOfReachable[ptr] = new int[] {y, x - 1};
                        deadEnd = false;
                        ptr++;
                    }
                }
                if (deadEnd) {
                    stack.pop();
                    continue;
                }
            }
            // pick a random neighbor and act on it
            int[] neighbor;
            if (ptr > 0) {
                int r = rnd.nextInt(ptr);
                neighbor = listOfReachable[r];
            } else {
                neighbor = listOfReachable[0];
            }
            stack.push(neighbor);
            maze.maze[neighbor[0]][neighbor[1]].setVisited(true);
            if (neighbor[0] > top[0]) { // check the orientation of the neighboring cell
                maze.maze[top[0]][top[1]].setBottom(false);
            } else if (neighbor[1] > top[1]) {
                maze.maze[top[0]][top[1]].setRight(false);
            } else if (neighbor[0] < top[0]){
                maze.maze[neighbor[0]][neighbor[1]].setBottom(false);
            } else {
                maze.maze[neighbor[0]][neighbor[1]].setRight(false);
            }
        }
        // set all visited attributes to false
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                maze.maze[j][i].setVisited(false);
            }
        }
        return maze;
    } // makeMaze

    public void printMaze() {
        for  ( int i = 0; i <maze[0].length; i ++){
            System.out.print("|---");
        }
        System.out.print("| \n"); // this does the top row
        for (int i = 0; i < maze.length; i ++){
            if (i != startRow){
                System.out.print("|");
            } else {
                System.out.print(" ");
            }
            for (int j = 0; j < maze[0].length; j++){ // prints dots and vertical walls
                if(i==endRow && j == maze.length-1){
                    if (maze[i][j].getVisited()){
                        System.out.print(" *  ");
                    } else {
                        System.out.print("   |");
                    }
                } else{
                    if (maze[i][j].getVisited()){
                        if (maze[i][j].getRight()){
                            if (i == maze[0].length-1 && j == endRow-1){
                                System.out.print(" *  ");
                            } else {
                                System.out.print(" * |");
                            }
                        } else {
                            System.out.print(" *  ");

                        }
                    } else {
                        if (maze[i][j].getRight() ){
                            if (i == maze[0].length-1 && j == endRow-1){
                                System.out.print("    ");
                            } else {
                                System.out.print("   |");
                            }
                        } else {
                            System.out.print("    ");
                        }
                    }
                }
            }
            System.out.println();
            for (int j = 0; j <maze[0].length; j++){ // prints horizontal walls under the dots and vertical walls
                if (maze[i][j].getBottom()){
                    System.out.print("|---");
                } else {
                    System.out.print("|   ");
                }
            }

            System.out.println("|");

        }
    } // printMaze

    public String debugger(){ // This is just a way to see the status of each cell in a way that's more visual than printMaze
        String message = "RIGHT_TEST\n";
        for (int i = 0 ; i < maze.length; i ++){
            for (int j = 0; j <maze[0].length; j++){
                if (maze[i][j].getRight()){
                    message+= "|Right| True  ";
                } else {
                    message+= "|Right| False ";
                }
            }
            message += "\n";
        }
        message+="BOTTOM_TEST\n";
        for (int i = 0 ; i < maze.length; i ++){
            for (int j = 0; j <maze[0].length; j++){
                if (maze[i][j].getBottom()){
                    message+= "|BOTTOM| True  ";
                } else {
                    message+= "|BOTTOM| False ";
                }
            }
            message += "\n";
        }
        message+="VISITED_TEST\n";
        for (int i = 0 ; i < maze.length; i ++){
            for (int j = 0; j <maze[0].length; j++){
                if (maze[i][j].getVisited()){
                    message+= "|Visited| True  ";
                } else {
                    message+= "|Visited| False ";
                }
            }
            message += "\n";
        }

        return message;
    }

    public void solveMaze() {
        // initialize queue
        Q2Gen<int[]> queue = new Q2Gen<>();
        queue.add(new int[]{0,startRow});
        int r = maze.length;
        int c = maze[0].length;
        while (queue.length() != 0) {
            // get active and check if it is the end
            int[] active = queue.remove();
            maze[active[1]][active[0]].setVisited(true);
            if (active[0]==rows-1 && active[1] == endRow-1) {
                maze[endRow-1][rows-1].setRight(false);
                break;
            } else { // generate list of unvisited, reachable neighbors and enqueue each
                int[][] listOfReachable = new int[4][2];
                int ptr = 0;
                int x = active[0];
                int y = active[1];
                if (y + 1 <= cols-1) {
                    if (!maze[y + 1][x].getVisited()) {
                        if (!maze[y][x].getBottom()){
                            listOfReachable[ptr] = new int[]{x,y+1};
                            queue.add(listOfReachable[ptr]);
                            ptr++;
                        }
                    }
                }
                if (y - 1 >= 0) {
                    if (!maze[y-1][x].getVisited()) {
                        if (!maze[y-1][x].getBottom()){
                            listOfReachable[ptr] = new int[]{x,y-1};
                            queue.add(listOfReachable[ptr]);
                            ptr++;
                        }
                    }
                }
                if (x + 1 <= rows-1) {
                    if (!maze[y][x+1].getVisited()) {
                        if (!maze[y][x].getRight()){
                            listOfReachable[ptr] = new int[]{x+1,y};
                            queue.add(listOfReachable[ptr]);
                            ptr++;
                        }
                    }
                }
                if (x - 1 >= 0) {
                    if (!maze[y][x-1].getVisited()) {
                        if (!maze[y][x-1].getRight()){
                            listOfReachable[ptr] = new int[]{x-1,y};
                            queue.add(listOfReachable[ptr]);
                            ptr++;
                        }
                    }
                }
            }
        }
    } //solveMaze
    public static void main(String[] args) {
        /* Any testing can be put in this main function */
        MyMaze maze = makeMaze(4, 20, 0, 3);
        MyMaze maze2 = makeMaze(16,16,3,8);
        MyMaze maze3 =  makeMaze(20,5,0,19);
        maze.solveMaze();
        maze.printMaze();
        maze2.solveMaze();
        maze2.printMaze();
        maze3.solveMaze();
        maze3.printMaze();

    }
}
