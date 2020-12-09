import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Step3 {
    static char[][][] cube = new char[6][3][3];

    public static void init() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cube[0][i][j] = 'W';
                cube[1][i][j] = 'R';
                cube[2][i][j] = 'Y';
                cube[3][i][j] = 'O';
                cube[4][i][j] = 'G';
                cube[5][i][j] = 'B';
            }
        }
    }

    public static void printcube(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for(int z = 0; z< 3; z++){
                    System.out.print(cube[i][j][z] + " ");
                    System.out.print("["+i+"]"+"["+j+"]"+"["+z+"]"+ " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        init();
        printcube();
    }
}