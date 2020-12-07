class Step2 {

    public static void validateKey(String key) throws CustomError{
        if(!(key.equals("U") || key.equals("U`") || key.equals("R") || key.equals("R`") || key.equals("L") || key.equals("L`") || key.equals("B") || key.equals("B`") || key.equals("Q"))){
            throw new CustomError("올바른 키를 입력하지 않았습니다. 프로그램이 종료됩니다.");
        }
    }

    public static String[][] rotationCube(String[][] cube, String key) throws CustomError{
        String[][] tempCube = deepClone(cube);
        try{
            validateKey(key);
        }catch(Exception er){
            System.out.println(er.getMessage());
        }
        switch (key){
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
            case "U":
                for(int i = 0; i<cube[0].length; i++){
                    int index = i+1;
                    if(index == cube[0].length-1){
                        tempCube[0][cube[0].length-2] = cube[0][cube[0].length-1];
                    }else{
                        tempCube[0][i] = cube[0][index];
                    }
                }
                break;
            case "R":

                break;
            case "R`":

                break;
            case "L":
                for(int i = 0; i<cube.length; i++){
                    int index = i+1;
                    if(index == cube.length-1){
                        tempCube[0][0] = cube[cube.length-1][0];
                    }else{
                        tempCube[index][0] = cube[i][0];
                    }
                }
                break;
            case "L`":
                for(int i = 0; i<cube.length; i++){
                    int index = i+1;
                    if(index == cube.length-1){
                        tempCube[0][0] = cube[cube.length-1][0];
                    }else{
                        tempCube[i][0] = cube[index][0];
                    }
                }
                break;
            case "B":
                for(int i = 0; i<cube[0].length; i++){
                    int index = i+1;
                    if(index == cube[0].length-1){
                        tempCube[cube.length-1][0] = cube[cube.length-1][i];
                    }else{
                        tempCube[cube.length-1][index] = cube[cube.length-1][i];
                    }
                }
                break;
            case "B`":
                for(int i = 0; i<cube[0].length; i++){
                    int index = i+1;
                    if(index == cube[0].length-1){
                        tempCube[cube.length-1][0] = cube[cube.length-1][i];
                    }else{
                        tempCube[cube.length-1][i] = cube[cube.length-1][index];
                    }
                }
                break;
            case "Q":
                throw new CustomError("프로그램을 종료합니다.");
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
    public static void main(String[] args) {

        String[][] Example = {{"R","R","W"},{"G","C","W"},{"G","B","B"}};
        try {
            Example = rotationCube(Example, "U");
        }catch (Exception e){
            e.getMessage();
        }
        for(int i = 0; i<Example[0].length; i++){
            System.out.println(Example[0][i] + " ");
        }





    }
}