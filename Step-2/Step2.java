import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
class Step2 {

    public static void validateKey(String key) throws CustomError{
        if(!(key.equals("U") || key.equals("U`") || key.equals("R") || key.equals("R`") || key.equals("L") || key.equals("L`") || key.equals("B") || key.equals("B`"))){
            throw new CustomError("올바른 키를 입력하지 않았습니다.");
        }
    }

    public static String[][] rotationCube(String[][] cube, String key){
        String[][] tempCube = deepClone(cube);
        int bottom_index = cube.length-1;
        try{
            validateKey(key);
        }catch(CustomError er){
            System.out.println(er.getMessage());
        }
        switch (key){
            case "U":
            for(int i = cube[0].length-1; i>=0; i--){
                int index = i-1;
                if(index >= 0){
                    tempCube[0][index] = cube[0][i];
                }else{
                    tempCube[0][cube[0].length-1] = cube[0][0];
                } 
            }
            break;
            case "U`" :
                for(int i = 0; i<cube[0].length; i++){
                    int index = i+1;
                    if(index == cube[0].length){
                        tempCube[0][0] = cube[0][(cube[0].length-1)];
                    }else{
                        tempCube[0][index] = cube[0][i];
                    }
                }
                break;
            case "R":
                for(int i = cube.length-1; i>0; i--){
                    int index = i-1;
                    if(index <= cube.length){
                        tempCube[index][cube[0].length-1] = cube[i][cube[0].length-1];
                    }
                }
                tempCube[cube.length-1][cube[0].length-1] = cube[0][cube[0].length-1];
                break;
            case "R`":
                for(int i = 0 ; i<cube.length-1; i++){
                    int index = i+1;
                    if(index <= cube.length-1){
                        tempCube[index][cube[0].length-1] = cube[i][cube[0].length-1];
                    }
                }
                tempCube[0][cube[0].length-1] = cube[cube.length-1][cube[0].length-1];
                break;
            case "L": 
                for(int i = 0 ; i<cube.length-1; i++){
                    int index = i+1;
                    if(index <= cube.length-1){
                        tempCube[index][0] = cube[i][0];
                    }
                }
                tempCube[0][0] = cube[cube.length-1][0];
                break;
            case "L`":
                for(int i = cube.length-1; i>0; i--){
                    int index = i-1;
                    if(index <= cube.length){
                        tempCube[index][0] = cube[i][0];
                    }
                }
                tempCube[cube.length-1][0] = cube[0][0];
                break;
            case "B":
                for(int i = 0; i<cube[bottom_index].length; i++){
                    int index = i+1;
                    if(index == cube[bottom_index].length){
                        tempCube[bottom_index][0] = cube[bottom_index][(cube[0].length-1)];
                    }else{
                        tempCube[bottom_index][index] = cube[bottom_index][i];
                    }
                }
                break;
            case "B`":
                for(int i = cube[0].length-1; i>=0; i--){
                    int index = i-1;
                    if(index >= 0){
                        tempCube[bottom_index][index] = cube[bottom_index][i];
                    }else{
                        tempCube[bottom_index][cube[0].length-1] = cube[bottom_index][0];
                    } 
                }
                break;
        }
        return tempCube;
    }

    private static String[][] deepClone(String[][] array){
        String[][] cloneArray = new String[array.length][array[0].length];
        for(int i=0; i<array.length; i++) {
            for(int j=0; j<array[i].length; j++) {
                cloneArray[i][j] = array[i][j];
            }
        }
        return cloneArray;
    }

    public static void showCube(String[][] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j <array[0].length; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println("");
        }
    }

    static public ArrayList<String> recogInputValue(String input){
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
        String[][] Example = {{"R","R","W"},{"G","C","W"},{"G","B","B"}};
        Scanner scan = new Scanner(System.in);
        String action;
        ArrayList<String> action_list;
        while(true){
            System.out.println("움직일 방향을 입력해 주십시오 : ");
            action = scan.nextLine();
            action_list = recogInputValue(action);
            for(String key : action_list){
                Example = rotationCube(Example, key);
                System.out.println(key);
                showCube(Example);
            }
            if(action.equals("Q") || action.equals("q")){
                System.out.println("Bye~");
                scan.close();
                break;
            }
        }


    }
}