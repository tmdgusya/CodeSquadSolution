class Step1 {
        
        public static String pushWord(String word, int move_count, String input_direction){
            String result;
            try {
                validMoveCount(move_count);
                validInputDirection(input_direction);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            char[] temp_array = word.toCharArray();
            temp_array = moveWord(temp_array, input_direction, move_count);
            result = new String(temp_array);
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
            char[] temp_array = array;
            char[] copy_array = new char[temp_array.length];
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
                for(int i = 0; i<temp_array.length; i++){
                    int Index = i+(move_count%temp_array.length);
                    if(Index > length){
                        System.out.println("Upper : " + (((length-(i+(move_count%temp_array.length)))*-1)-1));
                        copy_array[((length-(i+(move_count%temp_array.length)))*-1)-1] = temp_array[i];
                    }else{
                        System.out.println("Under : " + (i+(move_count%temp_array.length)));
                        copy_array[i+(move_count%temp_array.length)] = temp_array[i];
                    }
                }
            }else{
                for(int i = 0; i<temp_array.length; i++){
                    int Index = i+(move_count%temp_array.length);
                    if(Index > length){
                        copy_array[i] = temp_array[((length-(i+(move_count%temp_array.length)))*-1)-1];
                    }else{
                        copy_array[i] = temp_array[i+(move_count%temp_array.length)];
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

