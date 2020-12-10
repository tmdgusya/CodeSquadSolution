import java.util.ArrayList;

public class Step3 {

    public static ArrayList<String> recogInputValue(String input){
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
        cube.init();
        System.out.println();
        cube.LrRotate(0);
        cube.printcube();
    }
}