import java.util.*;

import javax.naming.LinkLoopException;
public class internship{
    public static void main(String[] args) {

        //Setting up the blank slate of the world
        Square[][] world = new Square[10][10];
        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                world[i][j] = new Square(i, j);
            }
        }

        //Set up for different phases
        boolean loop =true;
        Scanner myObj = new Scanner(System.in);
        System.out.println("Which phase do you wish to run?");
        while (loop){
            int temp = myObj.nextInt();
            if (temp == 1 || temp == 2){
                world[9][7].block();
                world[8][7].block();
                world[6][7].block();
                world[6][8].block();
                loop = false;
            }if (temp == 2){
                Random rand = new Random();
                int temp_x;
                int temp_y; 
                for (int i = 0; i<20; i++){
                    // Ensures the obstacles don't overlay or block the start or end
                    do{
                        temp_x = rand.nextInt(10);
                        temp_y = rand.nextInt(10);
                    } while ((temp_x ==0 && temp_y ==0) || (temp_x ==9 && temp_y ==9) || !(world[temp_x][temp_y].isFree()));
                    world[temp_x][temp_y].block();
                }
                loop = false;
            }

        }
        //Displays the grid 
        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                if (i == 0 && j==0){
                    System.out.print("|S");
                }
                else if (i == 9 && j==9){
                    System.out.print("|F");
                }else if (world[j][i].isFree()){
                    System.out.print("|O");
                }else{
                    System.out.print("|X");
                }
            }
            System.out.println("|");
        }

        //
        LinkedList<Square> queue = new LinkedList<Square>();
        world[0][0].setDistance(0);
        queue.add(world[0][0]);
        while(!queue.isEmpty()){
            Square temp = queue.remove(0);
            for(int i = -1 ; i<2; i++){
                for(int j = -1 ; j<2; j++){
                    int temp_x = temp.x +i;
                    int temp_y = temp.y +j;

                    if ((temp_x <10 && temp_x >-1 && temp_y <10 && temp_y >-1)){
                        if (world[temp_x][temp_y].getDistance()> temp.getDistance() +1 && world[temp_x][temp_y].isFree()){
                            world[temp_x][temp_y].setDistance(temp.getDistance() +1);
                            world[temp_x][temp_y].setParent(temp);
                            queue.add(world[temp_x][temp_y]);
                        }
                    }
                }
            }
            
        }
        if(world[9][9].getDistance() == 101 ){
            System.out.println("Path cannot be constructed");
        }else{
            path(world[9][9]);
            System.out.println("");
            for (int i=0; i<10; i++){
                for (int j=0; j<10; j++){
                    if (i == 0 && j==0){
                        System.out.print("|S");
                    }else if (i == 9 && j==9){
                        System.out.print("|F");
                    }else if (world[j][i].inPath){
                        System.out.print("|P");
                    }else if (world[j][i].isFree()){
                        System.out.print("|O");
                    }else{
                        System.out.print("|X");
                    }
                }
                System.out.println("|");
            }
        }
        
    }

    public static void path(Square input){
        if(input.x == 0 && input.y == 0){
            System.out.print("The path is defined by: (0,0)");
        }else{
            path(input.getParent());
            input.inPath =true;
            System.out.print(",("+input.x+","+input.y+")");
        }
    }

    
        
}

class Square{
    private boolean free;
    boolean inPath;
    int x;
    int y;
    private int distance;
    private Square parent;

    public Square(int x, int y){
        this.x= x;
        this.y= y;
        distance = 101;
        inPath= false;
        free = true;
    }

    public int getDistance(){
        return distance;
    }

    public void setDistance(int input){
        distance = input;
    }

    public void setParent(Square input){
        parent = input;
    }

    public Square getParent(){
        return parent;
    }

    public boolean isFree(){
        return free;
    }

    public void block(){
        free = false;
    }

}