import java.util.ArrayList;
import java.util.Scanner;

class Step2{

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

    public static void main(String[] args) throws CustomError {
        Cube cubeInstance = new Cube();
        Scanner scan = new Scanner(System.in);
        String action;
        ArrayList<String> action_list;
        while(true){
            System.out.println("움직일 방향을 입력해 주십시오 : ");
            action = scan.nextLine();
            action_list = recogInputValue(action);
            for(String key : action_list){
                cubeInstance.rotationCube(key);
                cubeInstance.showCube();
            }
            if(action.equals("Q") || action.equals("q")){
                System.out.println("Bye~");
                scan.close();
                break;
            }
        }


    }
}