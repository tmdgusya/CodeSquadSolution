public class FinalCube {
    
    private char[][][] cube = new char[6][3][3];

    public FinalCube(){}

    public char[][][] getCube(){
        return cube;
    }

    public void init() {
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
        cube[4][0][2]='S';
    }

    public void printcube(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for(int z = 0; z< 3; z++){
                    System.out.print(cube[i][j][z] + " ");
                }
                System.out.println();
            }
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
        //TODO : Random 으로 큐브 섞기
    }

    // 이 함수들이 호출되기전 키값들로 0 이 들어올지 2가 들어올지 걸러야됨 그건 main method 에서 진행
    //U 과 B 의 경우는 가운 자리만 0 이냐 2이냐 차이므로, 같은 함수로 쓰고, L일때는 0 대입 R 일때는 2 대입식으로 구현
    
    public void URotate(int index){
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

    public void UrRotate(int index){
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
    public void LRotate(int index){
        FRotate(4);
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[1][i][index];}
        for(int i = 0; i<3; i++){cube[1][i][index] = cube[2][i][index];}
        for(int i = 0; i<3; i++){cube[2][i][index] = cube[0][i][index];}
        for(int i = 0; i<3; i++){cube[0][i][index] = cube[3][i][index];}
        for(int i = 0; i<3; i++){cube[3][i][index] = temp[i];}
    }

    public void LrRotate(int index){
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
    public void sideLRotate(int index){
        char[] temp = new char[3];
        for(int i = 0; i<3; i++){temp[i] = cube[1][i][index];}
        for(int i = 0; i<3; i++){cube[1][i][index] = cube[4][i][index];}
        for(int i = 0; i<3; i++){cube[4][i][index] = cube[0][i][index];}
        for(int i = 0; i<3; i++){cube[0][i][index] = cube[5][i][index];}
        for(int i = 0; i<3; i++){cube[5][i][index] = temp[i];}
    }

    public void sideLrRotate(int index){
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
    public void FRotate(int phase){
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

    public void FrRotate(int phase){
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
}
