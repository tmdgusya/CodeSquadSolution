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

    public void random(){
        //TODO : Random 으로 큐브 섞기
    }

    public void URotate(String key){
        // W => F()
        //U 과 B 의 경우는 가운 자리만 0 이냐 2이냐 차이므로, 같은 함수로 쓰고, L일때는 0 대입 R 일때는 2 대입식으로 구현
        char temp1 = cube[4][0][0];
        char temp2 = cube[4][0][1];
        char temp3 = cube[4][0][2];

        cube[4][0][0] = cube[2][0][0];
        cube[4][0][1] = cube[2][0][1];
        cube[4][0][2] = cube[2][0][2];

        cube[2][0][0] = cube[5][0][0];
        cube[2][0][1] = cube[5][0][1];
        cube[2][0][2] = cube[5][0][2];

        cube[5][0][0] = cube[3][0][0];
        cube[5][0][1] = cube[3][0][1];
        cube[5][0][2] = cube[3][0][2];

        cube[3][0][0] = temp1;
        cube[3][0][1] = temp2;
        cube[3][0][2] = temp3;
    }

    public void UrRotate(){
        // W = F`()
        char temp1 = cube[5][0][0]; 
        char temp2 = cube[5][0][1]; 
        char temp3 = cube[5][0][2]; 

        cube[5][0][0] = cube[2][0][0];
        cube[5][0][1] = cube[2][0][1];
        cube[5][0][2] = cube[2][0][2];

        cube[2][0][0] = cube[4][0][0];
        cube[2][0][1] = cube[4][0][1];
        cube[2][0][2] = cube[4][0][2];

        cube[4][0][0] = cube[3][0][0];
        cube[4][0][1] = cube[3][0][1];
        cube[4][0][2] = cube[3][0][2];

        cube[3][0][0] = temp1;
        cube[3][0][1] = temp2;
        cube[3][0][2] = temp3;
    }

    //L 과 R 의 경우는 마지막 자리만 0 이냐 2이냐 차이므로, 같은 함수로 쓰고, L일때는 0 대입 R 일때는 2 대입식으로 구현

    public void Lrotate(){
        // [4] => F()

        char tmp1 = cube[1][0][0];
        char tmp2 = cube[1][1][0];
        char tmp3 = cube[1][2][0];

        cube[1][0][0] = cube[2][0][0];
        cube[1][1][0] = cube[2][1][0];
        cube[1][2][0] = cube[2][2][0];

        cube[2][0][0] = cube[0][0][0];
        cube[2][1][0] = cube[0][1][0];
        cube[2][2][0] = cube[0][2][0];

        cube[0][0][0] = cube[3][0][0];
        cube[0][1][0] = cube[3][1][0];
        cube[0][2][0] = cube[3][2][0];

        cube[3][0][0] = tmp1;
        cube[3][1][0] = tmp2;
        cube[3][2][0] = tmp3;
    }

    public void LrRotate(){
        // [4] => F`()

        char tmp1 = cube[0][0][0];
        char tmp2 = cube[0][1][0];
        char tmp3 = cube[0][2][0];

        cube[0][0][0] = cube[2][0][0];
        cube[0][1][0] = cube[2][1][0];
        cube[0][2][0] = cube[2][2][0];

        cube[2][0][0] = cube[1][0][0];
        cube[2][1][0] = cube[1][1][0];
        cube[2][2][0] = cube[1][2][0];

        cube[1][0][0] = cube[3][0][0];
        cube[1][1][0] = cube[3][1][0];
        cube[1][2][0] = cube[3][2][0];

        cube[3][0][0] = tmp1;
        cube[3][1][0] = tmp2;
        cube[3][2][0] = tmp3;
    }

    //F 과 B 의 경우는 첫자리만 0 이냐 3이냐 차이므로, 같은 함수로 쓰고, L일때는 F 대입 B 일때는 3 대입식으로 구현

    public void FRotate(){
        char tmp1 = cube[0][0][2];
        char tmp2 = cube[0][1][2];
        char tmp3 = cube[0][2][2];
        
        cube[0][0][2] = cube[0][0][0];
        cube[0][1][2] = cube[0][0][1];
        cube[0][2][2] = cube[0][0][2];
        
        cube[0][0][0] = cube[0][0][0];
        cube[0][0][1] = cube[0][1][0];
        cube[0][0][2] = cube[0][2][0];
        
        cube[0][0][0] = cube[0][2][0];
        cube[0][1][0] = cube[0][2][1];
        cube[0][2][0] = cube[0][2][2];
        
        cube[0][2][0] = tmp1;
        cube[0][2][1] = tmp2;
        cube[0][2][2] = tmp3;
    }

    public void FrRotate(){
        char tmp1 = cube[0][0][0];
        char tmp2 = cube[0][0][1];
        char tmp3 = cube[0][0][2];

        cube[0][0][0] = cube[0][0][0];
        cube[0][0][1] = cube[0][1][0];
        cube[0][0][2] = cube[0][2][0];

        cube[0][0][0] = cube[0][2][0];
        cube[0][1][1] = cube[0][2][1];
        cube[0][2][2] = cube[0][2][2];

        cube[0][2][0] = cube[0][0][2];
        cube[0][2][0] = cube[0][1][2];
        cube[0][2][0] = cube[0][2][2];

        cube[0][0][0] = tmp1;
        cube[0][1][1] = tmp2;
        cube[0][2][2] = tmp3;
    }
}
