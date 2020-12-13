import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
public class FinalCube {
    
    private char[][][] cube = new char[6][3][3];

    private Long startTime;
    private Long endTime;
    private Long duratinGameTime;
    private int rotateCount = 1;
    private boolean isGameStart = true;
    private String[] actionArray = {"U", "U`","B","B`","L","L`","F","F`","D","D`","Q","Q`"};

    public FinalCube(){init();}

    public char[][][] getCube(){
        return cube;
    }

    public void init() {
        this.startTime = start();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cube[0][i][j] = 'W';
                cube[1][i][j] = 'R';
                cube[2][i][j] = 'Y';
                cube[3][i][j] = 'O';
                cube[4][i][j] = 'G';
                cube[5][i][j] = 'B';
            }
        }
    }

    private Long start(){
        long start = System.currentTimeMillis();
        return start;
    }

    private Long endTime(){
        long end = System.currentTimeMillis();
        return end;
    }

    private Long getDurationGameTime(Long start, Long end){
        long duration = (long) ((end - start) / 1000.0);
        return duration;
    }

    private void validateKey(String key) throws CustomError{
        System.out.println("key = " + key);
        if (!Arrays.asList(actionArray).contains(key)) {
            throw new CustomError("올바른 키를 입력하지 않았습니다.");
        }
    }

    public void rotationCube(String key){
        try{
            this.validateKey(key);
            this.rotateCount++;
            isGameStart = true;
        }catch(CustomError er){
            System.out.println(er.getMessage());
            this.rotateCount--;
            isGameStart = false;
        }
        if(this.isGameStart){
            switch (key){
                case "U":
                    this.URotate(0);
                break;
                case "U`" :
                    this.UrRotate(0);
                    break;
                case "D":
                    this.UrRotate(2);
                    break;
                case "D`":
                    this.URotate(2);
                    break;
                case "R":
                    this.LrRotate(0);
                    break;
                case "R`":
                    this.LRotate(0);
                    break;
                case "L": 
                    this.LRotate(0);
                    break;
                case "L`":
                    this.LrRotate(0);
                    break;
                case "F":
                    this.FRotate(0);
                    break;
                case "F`":
                    this.FrRotate(0);
                    break;
                case "B":
                    this.FrRotate(3);
                    break;
                case "B`":
                    this.FRotate(3);
                    break;
                case "q":
                case "Q":
                    getGameTime();
                    break;
            }
            this.printcube();
            if(spec()){
                System.out.println("큐브를 맞추셨습니다! 당신은 천재!");
                getGameTime();
            }
        }
 
    }

    private void getGameTime() {
        this.endTime = this.endTime();
        this.duratinGameTime = this.getDurationGameTime(this.startTime, this.endTime);
        System.out.println("게임 플레이타임 : " + this.duratinGameTime + "초");
        System.out.println("큐브 회전횟수 : " + (this.rotateCount - 1) + "회");
        System.out.println("게임을 종료합니다....");
        System.exit(0);
    }


    private void printcube(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for(int z = 0; z< 3; z++){
                    System.out.print(cube[i][j][z] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private void deepClone(char[][][] copy, char[][][] original){
        for(int i=0; i<6; i++) {
            for(int j=0; j<3; j++) {
                for(int z = 0; z< 3; z++){
                    copy[i][j][z] = original[i][j][z];
                }
            }
        }
    }

    public void random(){
        String[] actionArray = {"U", "U`","B","B`","L","L`","F","F`","D","D`"};
        int selectActionKey;
        Random random = new Random();
        for(int i = 0; i<10; i++){
            selectActionKey = random.nextInt(10);
            this.rotationCube(actionArray[selectActionKey]);
        } 
    }


    // 이 함수들이 호출되기전 키값들로 0 이 들어올지 2가 들어올지 걸러야됨 그건 main method 에서 진행
    //U 과 B 의 경우는 가운 자리만 0 이냐 2이냐 차이므로, 같은 함수로 쓰고, L일때는 0 대입 R 일때는 2 대입식으로 구현
    
    private void URotate(int index){
        if(index == 0){
            FrRotate(0);
        }else{
            FrRotate(1);
        }
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[4][index][i];}
        for(int i = 0; i<3; i++){cube[4][index][i] = cube[2][index][i];}
        for(int i = 0; i<3; i++){cube[2][index][i] = cube[5][index][i];}
        for(int i = 0; i<3; i++){cube[5][index][i] = cube[3][index][i];}
        for(int i = 0; i<3; i++){cube[3][index][i] = temp[i];}
    }

    private void UrRotate(int index){
        if(index == 0){
            FRotate(0);
        }else{
            FRotate(1);
        }
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[5][index][i];}
        for(int i = 0; i<3; i++){cube[5][index][i] = cube[2][index][i];}
        for(int i = 0; i<3; i++){cube[2][index][i] = cube[4][index][i];}
        for(int i = 0; i<3; i++){cube[4][index][i] = cube[3][index][i];}
        for(int i = 0; i<3; i++){cube[3][index][i] = temp[i];}
    }

    //L 과 R 의 경우는 마지막 자리만 0 이냐 2이냐 차이므로, 같은 함수로 쓰고, L일때는 0 대입 R 일때는 2 대입식으로 구현
    //phase 는 각 면을 나타냄! phase 를 사용하는 이유는 0일때는 2차 스케치 로직 대로지만, 다른 면만 회전될 경우가 필요함
    private void LRotate(int index){
        FRotate(4);
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[1][i][index];}
        for(int i = 0; i<3; i++){cube[1][i][index] = cube[2][i][index];}
        for(int i = 0; i<3; i++){cube[2][i][index] = cube[0][i][index];}
        for(int i = 0; i<3; i++){cube[0][i][index] = cube[3][i][index];}
        for(int i = 0; i<3; i++){cube[3][i][index] = temp[i];}
    }

    private void LrRotate(int index){
        FrRotate(4);
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[0][i][index];}
        for(int i = 0; i<3; i++){cube[0][i][index] = cube[2][i][index];}
        for(int i = 0; i<3; i++){cube[2][i][index] = cube[1][i][index];}
        for(int i = 0; i<3; i++){cube[1][i][index] = cube[3][i][index];}
        for(int i = 0; i<3; i++){cube[3][i][index] = temp[i];}
    }

    //F 나 F` 이 발동될때는 기존 [1]-[2]-[0]-[3] 이아닌 [4]-[0]-[5]-[1] 로 도는 로직도 필요함
    //마지막 index 0 2 l-> 아래 L` 위
    private void sideLRotate(int index){
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[1][i][index];}
        for(int i = 0; i<3; i++){cube[1][i][index] = cube[4][i][index];}
        for(int i = 0; i<3; i++){cube[4][i][index] = cube[0][i][index];}
        for(int i = 0; i<3; i++){cube[0][i][index] = cube[5][i][index];}
        for(int i = 0; i<3; i++){cube[5][i][index] = temp[i];}
    }

    private void sideLrRotate(int index){
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[5][i][index];}
        for(int i = 0; i<3; i++){cube[5][i][index] = cube[0][i][index];}
        for(int i = 0; i<3; i++){cube[0][i][index] = cube[4][i][index];}
        for(int i = 0; i<3; i++){cube[4][i][index] = cube[1][i][index];}
        for(int i = 0; i<3; i++){cube[1][i][index] = temp[i];}
    }

    //F 과 B 의 경우는 첫자리만 0 이냐 3이냐 차이므로, 같은 함수로 쓰고, L일때는 F 대입 B 일때는 3 대입식으로 구현
    //어떤 면이 시계 방향으로 돌지 입력받아야됨
    //앞면일 경우 0 뒷면일 경우 3 근데 phase 를 받는이유는 U 나 다른 rotate 메소드 호출시 시계 혹은 반시계로 도는 phase 가 있기 때문
    //문제가 발생함.. 한면에서만 돌리는건 돌리고나서 끝 배열이 도는데 영향을 받음 따라서 따로돌아야됨
    private void FRotate(int phase){
        char[][][] tempArray = new char[6][3][3];
        deepClone(tempArray, cube);
        if(phase == 2){
            sideLrRotate(2);
        }else if(phase == 3){
            sideLrRotate(0);
        }
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[phase][i][2];}
        for(int i = 0; i<3; i++){tempArray[phase][i][2] = cube[phase][0][i];}
        for(int i = 0; i<3; i++){tempArray[phase][0][i] = cube[phase][i][0];}       
        for(int i = 0; i<2; i++){tempArray[phase][i][0] = cube[phase][2][i];} 
        for(int i = 0; i<3; i++){tempArray[phase][2][2-i] = temp[i];}
        deepClone(cube, tempArray);
    }

    private void FrRotate(int phase){
        if(phase == 2){
            sideLRotate(2);
        }else if(phase == 3){
            sideLRotate(0);
        }
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[phase][i][0];}
        for(int i = 0; i<3; i++){cube[phase][i][0] = cube[phase][0][i];}
        for(int i = 0; i<3; i++){cube[phase][0][i] = cube[phase][i][2];}
        for(int i = 0; i<3; i++){cube[phase][i][2] = cube[phase][2][i];}
        for(int i = 0; i<3; i++){cube[phase][i][2-i] = temp[i];}
    }

    private boolean spec(){
        boolean clear = false;
        int clearPhaseCount = 0;
        int clearCount = 0;
        char word; 
        for (int i = 0; i < 6; i++) {
            word = cube[i][0][0];
            clearPhaseCount = 0;
            for (int j = 0; j < 3; j++) {
                for(int z = 0; z< 3; z++){
                    if(word == cube[i][j][z]){
                        clearPhaseCount++;
                    }
                }
            }
            if(clearPhaseCount == 9){
                clearCount++;
            }
        }
        if(clearCount == 6 && this.rotateCount > 1){
            clear = true;
        }
        return clear;
    }
}
