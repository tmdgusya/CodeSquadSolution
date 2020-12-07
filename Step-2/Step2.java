class Step2 {

    public static void validateKey(String key) throws CustomError{
        if(!(key.equals("U") || key.equals("U`") || key.equals("R") || key.equals("R`") || key.equals("L") || key.equals("L`") || key.equals("B") || key.equals("B`") || key.equals("Q"))){
            throw new CustomError("올바른 키를 입력하지 않았습니다. 프로그램이 종료됩니다.");
        }
    }

    public static String[][] rotationCube(String[][] cube, String key) throws CustomError{
        try{
            validateKey(key);
        }catch(Exception er){
            System.out.println(er.getMessage());
        }
        switch (key){
            case "U" :

                break;
            case "U`":

                break;
            case "R":

                break;
            case "R`":

                break;
            case "L":

                break;
            case "L`":

                break;
            case "B":

                break;
            case "B`":

                break;
            case "Q":
                throw new CustomError("프로그램을 종료합니다.");
        }

        return cube;
    }

    public static void main(String[] args) {

        String[][] Example = {{"R","R","W"},{"G","C","W"},{"G","B","B"}};




    }
}