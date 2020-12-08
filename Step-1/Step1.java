class Step1 {
        
        public static String pushWord(String word, int move_count, String input_direction){
            String result;
            try {
                validMoveCount(move_count);
                validInputDirection(input_direction);
            } catch (CustomError e) {
                System.out.println(e.getMessage());
            }
            char[] array = word.toCharArray();
            array = moveWord(array, input_direction, move_count);
            result = new String(array);
            return result;
        }
        
        //Validation
        public static void validMoveCount(int move_count) throws CustomError {
            if(!(move_count >= -100 && move_count < 100)){
                throw new CustomError("move_count 가 -100보다 작거나, 100 이상입니다.");
            }
        }

        public static void validInputDirection(String input_direction) throws CustomError {
            if(!(input_direction.equals("L") || input_direction.equals("R"))){
                throw new CustomError("input Direction 값을 잘못 입력하셨습니다. L 또는 R 을 입력하여 주십시오.");
            }
        }

        public static char[] moveWord(char[] array ,String input_direction, int move_count){
            char[] copy_array = new char[array.length];
            int length = array.length-1;
            if(move_count < 0){
                if(input_direction.equals("R")){
                    move_count *= -1;
                    input_direction = "L";
                }else{
                    move_count *= -1;
                    input_direction = "R";
                }
            }
            if(input_direction.equals("R")){
                for(int i = 0; i<array.length; i++){
                    int Index = i+(move_count%array.length);
                    if(Index > length){
                        copy_array[((length-(i+(move_count%array.length)))*-1)-1] = array[i];
                    }else{
                        copy_array[i+(move_count%array.length)] = array[i];
                    }
                }
            }else{
                for(int i = 0; i<array.length; i++){
                    int Index = i+(move_count%array.length);
                    if(Index > length){
                        copy_array[i] = array[((length-(i+(move_count%array.length)))*-1)-1];
                    }else{
                        copy_array[i] = array[i+(move_count%array.length)];
                    }
                }
            }
            return copy_array;
        }

    public static void main(String args[]){
        String answer = pushWord("banana", 3, "R");
        System.out.println(answer);

        String answer1 = pushWord("apple", 3, "L");
        System.out.println(answer1);

        String answer2 = pushWord("carrot", -1, "R");
        System.out.println(answer2);

        String answer3 = pushWord("cat", -4, "R");
        System.out.println(answer3);
    }

}

