import java.util.Scanner;
class Step2 {

    public static void validateKey(String key) throws CustomError{
        if(!(key.equals("U") || key.equals("U`") || key.equals("R") || key.equals("R`") || key.equals("L") || key.equals("L`") || key.equals("B") || key.equals("B`"))){
            throw new CustomError("올바른 키를 입력하지 않았습니다. 프로그램이 종료됩니다.");
        }
    }

    public static String[][] rotationCube(String[][] cube, String key){
        String[][] tempCube = deepClone(cube);
        try{
            validateKey(key);
        }catch(Exception er){
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
                int right_index = cube[0].length;
                for(int i = cube.length-1; i>0; i--){
                    int index = i-1;
                    if(index <= cube.length){
                        tempCube[index][right_index] = cube[i][right_index];
                    }
                }
                tempCube[cube.length-1][right_index] = cube[0][right_index];
                break;
            case "R`":
                right_index = cube[0].length;
                for(int i = 0 ; i<cube.length-1; i++){
                    int index = i+1;
                    if(index <= cube.length-1){
                        tempCube[index][right_index] = cube[i][right_index];
                    }
                }
                tempCube[0][right_index] = cube[cube.length-1][right_index];
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
                int bottom_index = cube.length-1;
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
                bottom_index = cube.length-1;
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

    public static void main(String[] args) {
        
        String[][] Example = {{"R","R","W"},{"G","C","W"},{"G","B","B"}};
        Scanner scan = new Scanner(System.in);
        String action = "";
        while(true){
            System.out.println("움직일 방향을 입력해 주십시오 : ");
            action += scan.nextLine();
            if(action.equals("Q") || action.equals("q")){
                System.out.println("프로그램을 종료합니다.");
                scan.close();
                break;
            }else{
                Example = rotationCube(Example, action);
                System.out.println();
                showCube(Example);
            }
        }    
    }
}