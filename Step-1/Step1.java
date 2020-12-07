import java.util.Scanner;

class Step1 {
        
        public static String pushWord(String word, int move_count, String input_direction){
            String result;
            try {
                validWord(word);
                validMoveCount(move_count);
                validInputDirection(input_direction);
            } catch (Exception e) {
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
            if(!(input_direction.equals("L") || input_direction.equals("R") || input_direction.equals("l") || input_direction.equals("r"))){
                throw new CustomError("input Direction 값을 잘못 입력하셨습니다. L 또는 R 을 입력하여 주십시오.");
            }
        }

        public static void validWord(String word) throws CustomError{
            if(word.length() == 0){
                throw new CustomError("문자를 입력해주세요. 문자가 입력되지 않았습니다.");
            }
        }

        public static char[] moveWord(char[] array ,String input_direction, int move_count){
            char[] copy_array = new char[array.length];
            int length = array.length-1;
            if(move_count < 0){
                if(input_direction.equals("R") || input_direction.equals("r")){
                    move_count *= -1;
                    input_direction = "L";
                }else{
                    move_count *= -1;
                    input_direction = "R";
                }
            }
            if(input_direction.equals("R")|| input_direction.equals("r")){
                for(int i = 0; i<array.length; i++){
                    int Index = i+(move_count%array.length);
                    if(Index > length){
                        System.out.println("Upper : " + (((length-(i+(move_count%array.length)))*-1)-1));
                        copy_array[((length-(i+(move_count%array.length)))*-1)-1] = array[i];
                    }else{
                        System.out.println("Under : " + (i+(move_count%array.length)));
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

        Scanner scanner = new Scanner(System.in);
        System.out.println("단어를 입력해주세요 : ");
        String word = scanner.next();

        System.out.println("움직일 횟수를 적어주세요 : ");
        int move_count = scanner.nextInt();

        System.out.println("움직일 방향을 골라주세요 : ");
        String input_direction = scanner.next();

        String answer = pushWord("banana", 3, "R");
        System.out.println(answer);

        String answer1 = pushWord("apple", 3, "L");
        System.out.println(answer1);

        String answer2 = pushWord("carrot", -1, "R");
        System.out.println(answer2);

        String answer3 = pushWord("cat", -4, "R");
        System.out.println(answer3);

        String answer4 = pushWord(word, move_count, input_direction);
        System.out.println(answer4);
    }

}

