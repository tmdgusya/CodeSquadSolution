import java.util.ArrayList;
import java.util.Scanner;
public class Step3 {

    private static ArrayList<String> recogInputValue(String input){
        String[] splitWord = input.split("");
        ArrayList<String> actionList = new ArrayList<>();
        for(int i = 0; i < splitWord.length; i++) {
            if (splitWord[i].equals("`")) {
                splitWord[i - 1] = splitWord[i - 1] + "`";
            }
        }
        for(int i = 0; i < splitWord.length; i++) {
            if (!splitWord[i].equals("`")) {
                actionList.add(splitWord[i]);
            }
        }
        return actionList;
    }

    public static void main(String[] args) {
        FinalCube cube = new FinalCube();
        Scanner scan = new Scanner(System.in);
        ArrayList<String> actionQueue = new ArrayList<>();
        String key;
        while(true){
            System.out.print("CUBE > ");
            key = scan.nextLine();
            actionQueue = recogInputValue(key);
            actionQueue.forEach((action) -> {
                cube.rotationCube(action);
            });  
        }
    }


}