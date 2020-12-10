# Git 이용과정

1. 각 브랜치 마다 Step 별로 작업한다. 브랜치 NAME 은 Step-1 ... Step-n 이런식으로

2. 브랜치별로 Issue 는 "#Step-worknum-Cause" 로 작성한다.

3. 브랜치에서 작업 완료 후 각 브랜치로 Push 한뒤 Master 가 Merge Request를 검토한뒤 Main Branch 로 Merge 된다.

4. Main Branch 에서 remote branch 와 시점을 맞춘뒤 rebase를 진행한다. 

5. 각 Branch 는 Master로 부터 git pull rebase main 으로 최신 코드를 주입받는다.



# 각 단계별 풀이과정

# 전체 스텝별 코드 작성 및 공용 Class 설명

**에러 처리 클래스는 Step 전역에서 사용한다.**

1. 최대한 함수 별로 분리하도록 한다. => Test 를 한번 진행해 보았을때, 함수 기능별로 구분이 잘 되어 있어야 Test 하기가 편했음.
2. 각 입력값에 대한 Validate 처리 함수를 설계한다. 함수명 "validateFieldName"

## 오류 로그에 대한 고민
* 과연 이단계에서 오류 로그가 필요할까? 사실 Invalid Argument 를 처리하는 오류 클래스는 많음, 
근데 최대한 프로그램 처럼 설계 하고싶었음. 그래서 Error 관련된 부분도 확장성을 생각해 보자라는 생각이 들었고,
별고의 예외 클래스를 생성하였음.

* 오류 클래스를 따로 설계했을때 가져올 수 있는 장점?
    - 예외 발생시 처리 로직을 설계할 수 있다.
    - 후에 로그에 쓰이게 할 수도 있다.
    - 발생할 수 있는 오류에 대해 코드를 보고 파악이 가능하다.
    - 오류에 대해 정리가 되어 있다면, 테스트 케이스를 작성하는데도 도움이 될 것이라고 판단.

## 오류 코드 설계

- Enum 클래스로 설계한다.

# Step -1 코드 설계 과정

## 로직 설계 과정

1. [] 배열의 총 칸수가 n 이라고 계산했을때, count 만큼 이동한 뒤의 자리수는 n%4 의 자리에 위치하게 됨. 

2. '-'(음수 부호의 경우) 반대로 위치하는 것이므로, 디렉션을 반대걸로 지정해주면됨.


## 에러 처리과정

1. 오류처리 는 따로 Class 를 생성하여, 후에 예외 처리간 다른 log 기능을 추가할 수 있도록 클래스를 생성한다.
2. 사용자가 값을 입력 하는 형태이므로, CheckedException 형식으로 설계하여, 명시적으로 try..catch 문을 작성해야 한다.
3. 입력값이 틀릴시 Exception 을 발생시켜 함수를 종료 시킨다. (ErrorMessage 를 출력하여 사용자가 인지 할 수 있도록 한다.)

## 함수 설명

1. 에러 코드

```java

public class CustomError extends Exception{
    
    private String message;

    public CustomError(String message){
        super(message);
        this.message = message;
    }
}


```

* ErrorCode 는 간단하게 message 만 저장하도록 1차적으로 설계되었음, ErrorCode 를 사용한다면, Enum Class 를 설계한뒤 사용할 수 있도록 할 예정(아직 1차 설계에서는 고려하지 않았음)

2. 유효성 검사 코드

```java

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

```

* 요구사항에 주어진 조건대로 유효성 검사를 진행하고, 해당 유효성 검사에 부적합할시 Error 를 Throw 하도록 설계됨. 사용로직에서는 이를 Try..Catch 를 이용해 Message 를 확인할 수 있음.

3. 핵심 로직 moveWord 부분 설명

```java
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

```

### 음수 이동시 처리

1. 일단 유효성 검사를 거치기 때문에, **"L"과"R"** 만 들어올 수 있다. 
2. 그러므로 음수 부분은 서로 바꿔서 출력되기만 하면 된다고 판단했음. 
3. 그래서 음수로 들어올경우 -를 곱해주는 걸로 판단!

### 이동과정 로직

일단 위에서 설명했듯이, **move_count%array.length** 만큼 이동하는게 원칙 <br>
copy_array 를 Temporary Array 처럼 이용하여, 기존 Array 에서 값을 이동하는건 복잡하다고 생각하여 copy_array의 위치에 복사하도록 설정
**Index = i+(move_count%array.length)** 은 위에서 말했듯이 **(move_count%array.length)** 만큼 이동해야 하는데, 
i 번의 Index 의 Value 가 해당 값만큼 이동해야 하므로, i 의 위의 값을 더해준다, 근데 i 값이 증가 할 수록 배열의 최대길이를 벗어나게 되므로, i+(move_count%array.length) 가
최대 이보다 커질때, 위의 조건문을 타고 **Index = ((length-(i+(move_count%array.length)))\*-1)-1** 위치에 저장하게 된다.
**\*-1** 을 해준이유는, 해당값이 length의 값 + 위치해야될 Index 의 값이므로, 위치해야될 위치의 Index의 
음수 형태로 나오게 되므로, *-1 을 해서 양수형태로 변경하여 위치해야될 Index 값에 정상적으로 저장한다.

**'L'** 의 이동과정은 R의 이동과정 반대이므로, 위치해야될 Index 의 위치와, i 의 위치만 변경해주면 된다.


---

# Step-2 코드 설계 과정

## 로직 설계 과정

1. Step-1과 유사하다. 다만 큐브는 무조건 한칸씩 움직인다는 점.

2. 큐브를 가장 위쪽 / 가장 아래 / 가장 좌측 / 가장 오른쪽 을 움직여야 하는 요구사항이다.

3. Key 입력은 switch 문을 이용해, 설계한다. (각 Case 별로 설계

4. 아래의 인덱스로 설계한다.

5. 처음에 큐브를 클래스화 처리하여 사용하지 않았으나, 만약 여러 사용자가 사용한다면 큐브가 여러개 필요할 것이라 판단<br>
따라서 큐브를 객체화 시킨뒤, 유효성 검증 이나 내부 동작 함수는 private 처리하여 외부에서 어떻게 검증하는지 알수 없도록함. 
```
[n][0] 의 인덱스를 가짐 / 가장 우측 [0][array[0].length-1] / 상단 [0][n] / 하단 [array.length-1][n]
```

## 코드 설계

```java

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

```
### 큐브회전 코드 설명
0. 위치는 아래 로직 설계과정에서 설명했으니 반복하지 않았음

0. 확장성을 위해 3*3 이 아닌 다른 큐브 칸에서도 작동할 수 있도록 설계해보자!

1. 'Q' 혹은 'q' 를 입력하여 종료할 수 있도록 설계한다.

1. **U** 는 가장 윗줄을 한칸씩 왼쪽(낮은 인덱스), 최대 인덱스 부터 시작한다. **높은 인덱스 => 낮은 인덱스**

2. **U`** 는 가장 윗줄을 한칸씩 오른쪽(높은 인덱스), 낮은 인덱스 부터 시작한다. **낮은 인덱스 => 높은 인덱스**

3. **R** 은 가장 오른쪽 줄을 위로 한칸 밀기, 낮은 인덱스 부터 시작한다. **낮은 인덱스 => 높은 인덱스** 

4. **R`** 은 가장 오른쪽 줄로 아래로 한칸 밀기, 높 인덱스 부터 시작한다. **높은 인덱스 => 낮은 인덱스**

5. 나머지는 반대 사이드로 이동하기 때문에, 따로 설명하지 않겠음.

### 유효성 검사 로직 설명

```java

private void validateKey(String key) throws CustomError{
        if(!(key.equals("U") || key.equals("U`") || key.equals("R") || key.equals("R`") || key.equals("L") || key.equals("L`") || key.equals("B") || key.equals("B`"))){
            throw new CustomError("올바른 키를 입력하지 않았습니다. 프로그램이 종료됩니다.");
        }
    }

```

* if 문에 지정해 둔 키들은 모두 요구사항에 필요한 키들로,

### 입력값 분석 로직

* 입력값을 분석하는데 가장 생각해야될점은, 
첫번째로 Q 나 q 로 종료하기 직전까지 반복되어야 한다는 점<br>
두번째는 여러가지 입력값을 한줄로 받을경우 char 배열로 변경하게 되면, '`' 를 처리하는데 애를 먹는다.
=>왜냐면 둘이 합치려해도 char 배열이라 합쳐지지가 않음..<br> 그래서 해당 문자를 다른 문자로 Mapping 하자니 확장성이 너무
낮아질것 같다는 생각이들었음.용 (**String[] 배열로 받아낼 방법을 구상해야됨**)

* 그래서 input 값을 .split("") 을 통해 String[] 배열로 받아내면 합칠 수 있겠다고 판단. 해당 함수를 이용하였음

```java

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

```

**split 부분 설명**
```java
String[] splitWord = input.split("");
```
해당 부분에 User 가 UU\`RR 을 입력한다면, [U, U, \`, R, R] 로 입력된다. 그래서 해당 \` 의 Index 를 구한뒤, [Index-1] 열과 합쳐서 저장해주면된다.<br>
이렇게 될경우 [U, U, \`, R, R] => [U, U\`, \`, R, R] 가 된다. 따라서 \` 는 무시해 줘야되므로 **if (!splitWord[i].equals("`"))** 를 이용해 무시해주면 된다.
해당 actionList 를 리턴하여 메인 함수에서 작업해준다.

# Step-3 로직 설계 과정

## 로직 설계 과정

### 큐브를 구현하는 방법 생각

1. 큐브가 3*3 면이 총 6개이므로 [6][3][3] 배열을 이용해야 한다.
2. 해당 키가 들어오면 교환변에 해당하는 배열을 서로 교환하는 식으로 이용한다. 중간에 1*3의 Temp 배열을 이용한뒤 값 Swap

### 움직임 동작

U - 가장위를 왼쪽으로 한면 돌리기<br>
U\` - 가장위를 오른쪽으로 한면 돌리기<br>
D - 가장 아래쪽을 오른쪽으로 한면 돌리기<br>
D\` - 가장 아래쪽을 왼쪽으로 한면돌리기<br>
L - 가장 왼변을 아래쪽으로 한면돌리기<br>
L\` - 가장 왼변을 윗쪽으로 한면 돌리기<br>
R - 가장 오른쪽 변을 위쪽으로 한번 돌리기<br>
R\` - 가장 오른쪽 변을 아랫쪽으로 한번 돌리기<br>
F - 앞면을 시계방향으로 90도 회전<br>
F\` - 앞면을 반시계 방향으로 90도 회전<br>
B - 가장 뒷변을 반시계 방향으로 90도 회전<br>
B` - 가장 뒷변을 시계 방향으로 90도 회전<br>

### 큐브 모양 출력 [3차원 배열]
```
W [0][0][0] W [0][0][1] W [0][0][2] 
W [0][1][0] W [0][1][1] W [0][1][2] 
W [0][2][0] W [0][2][1] W [0][2][2] 
R [1][0][0] R [1][0][1] R [1][0][2] 
R [1][1][0] R [1][1][1] R [1][1][2] 
R [1][2][0] R [1][2][1] R [1][2][2] 
Y [2][0][0] Y [2][0][1] Y [2][0][2] 
Y [2][1][0] Y [2][1][1] Y [2][1][2] 
Y [2][2][0] Y [2][2][1] Y [2][2][2] 
O [3][0][0] O [3][0][1] O [3][0][2] 
O [3][1][0] O [3][1][1] O [3][1][2] 
O [3][2][0] O [3][2][1] O [3][2][2] 
G [4][0][0] G [4][0][1] G [4][0][2] 
G [4][1][0] G [4][1][1] G [4][1][2] 
G [4][2][0] G [4][2][1] G [4][2][2] 
B [5][0][0] B [5][0][1] B [5][0][2] 
B [5][1][0] B [5][1][1] B [5][1][2] 
B [5][2][0] B [5][2][1] B [5][2][2]
```

### 큐브 평면도 (아래 처럼 구상할 예)

```
        W[0]
        |
G[4] - Y[2] - B[5] - O[3]
        |
        R[1]
```

### 움직임시 인덱싱 변경 

* **U 과 B 의 경우는 가운 자리만 0 이냐 2이냐 차이므로, 같은 함수로 쓰고, L일때는 0 대입 R 일때는 2 대입식으로 구현**
---
* U의 경우 [4]-[2]-[5]-[3] 왼쪽 으로 회전, W는 시계 방향으로 회전 뒤의 **(뒷 두자리 : [0][n])**
```
W => F()

temp1 = cube[4][0][0] 
temp2 = cube[4][0][1] 
temp3 = cube[4][0][2] 

cube[4][0][0] = cube[2][0][0]
cube[4][0][1] = cube[2][0][1]
cube[4][0][2] = cube[2][0][2]

cube[2][0][0] = cube[5][0][0]
cube[2][0][1] = cube[5][0][1]
cube[2][0][2] = cube[5][0][2]

cube[5][0][0] = cube[3][0][0]
cube[5][0][1] = cube[3][0][1]
cube[5][0][2] = cube[3][0][2]

cube[3][0][0] = temp1
cube[3][0][1] = temp2
cube[3][0][2] = temp3

```
* 근데 계속 Save 치면 [4]값이 덮어지므로 4 본래의 값은 시작전에 temp = [4] 저장해두고 시작 
---
* U` 의 경우 [4]-[2]-[5]-[3] 오른쪽 으로 회전, W는 시계 방향으로 회전 **(뒷 두자리 : [0][n])**
```
W = F`()

temp1 = cube[5][0][0] 
temp2 = cube[5][0][1] 
temp3 = cube[5][0][2] 

cube[5][0][0] = cube[2][0][0]
cube[5][0][1] = cube[2][0][1]
cube[5][0][2] = cube[2][0][2]

cube[2][0][0] = cube[4][0][0]
cube[2][0][1] = cube[4][0][1]
cube[2][0][2] = cube[4][0][2]

cube[4][0][0] = cube[3][0][0]
cube[4][0][1] = cube[3][0][1]
cube[4][0][2] = cube[3][0][2]

cube[3][0][0] = temp1
cube[3][0][1] = temp2
cube[3][0][2] = temp3
```
---
* B 의 경우 
    * U` 로직 **(뒷 두자리 : [2][n])**
    * R => F\`()
---
* B\` 의 경우
    * U 로직 **(뒷 두자리 : [2][n])**
    * R => F()
---
* **L 과 R 의 경우는 마지막 자리만 0 이냐 2이냐 차이므로, 같은 함수로 쓰고, L일때는 0 대입 R 일때는 2 대입식으로 구현**
---
* L 의 경우 
```
[4] => F()

tmp1 = cube[1][0][0]
tmp2 = cube[1][1][0]
tmp3 = cube[1][2][0]

cube[1][0][0] = cube[2][0][0]
cube[1][1][0] = cube[2][1][0]
cube[1][2][0] = cube[2][2][0]

cube[2][0][0] = cube[0][0][0]
cube[2][1][0] = cube[0][1][0]
cube[2][2][0] = cube[0][2][0]

cube[0][0][0] = cube[3][0][0]
cube[0][1][0] = cube[3][1][0]
cube[0][2][0] = cube[3][2][0]

cube[3][0][0] = tmp
cube[3][1][0] = tmp2
cube[3][2][0] = tmp3
```
---
* L`의 경우
```
[4] => 반시계

tmp1 = cube[0][0][0]
tmp2 = cube[0][1][0]
tmp3 = cube[0][2][0]

cube[0][0][0] = cube[2][0][0]
cube[0][1][0] = cube[2][1][0]
cube[0][2][0] = cube[2][2][0]

cube[2][0][0] = cube[1][0][0]
cube[2][1][0] = cube[1][1][0]
cube[2][2][0] = cube[1][2][0]

cube[1][0][0] = cube[3][0][0]
cube[1][1][0] = cube[3][1][0]
cube[1][2][0] = cube[3][2][0]

cube[3][0][0] = tmp1
cube[3][1][0] = tmp2
cube[3][2][0] = tmp3
```
---
* R의 경우 
    * **(끝자리가 : [2])**
    * L`
---
* R` 의 경우
    * **(끝자리가 : [2])**
    * L
---
* **F 과 B 의 경우는 첫자리만 0 이냐 3이냐 차이므로, 같은 함수로 쓰고, L일때는 F 대입 B 일때는 3 대입식으로 구현**
---
* F 의 경우
```
tmp1 = cube[0][0][2]
tmp2 = cube[0][1][2]
tmp3 = cube[0][2][2]

cube[0][0][2] = [0][0][0]
cube[0][1][2] = [0][0][1]
cube[0][2][2] = [0][0][2]

cube[0][0][0] = [0][0][0]
cube[0][0][1] = [0][1][0]
cube[0][0][2] = [0][2][0]

cube[0][0][0] = [0][2][0]
cube[0][1][0] = [0][2][1]
cube[0][2][0] = [0][2][2]

cube[0][2][0] = tmp1
cube[0][2][1] = tmp2
cube[0][2][2] = tmp3

```
---
* F\` 의 경우

```
tmp1 = cube[0][0][0]
tmp2 = cube[0][0][1]
tmp3 = cube[0][0][2]

cube[0][0][0] = cube[0][0][0]
cube[0][0][1] = cube[0][1][0]
cube[0][0][2] = cube[0][2][0]

cube[0][0][0] = cube[0][2][0]
cube[0][1][1] = cube[0][2][1]
cube[0][2][2] = cube[0][2][2]

cube[0][2][0] = cube[0][0][2]
cube[0][2][1] = cube[0][1][2]
cube[0][2][2] = cube[0][2][2]

cube[0][0][0] = tmp1
cube[0][1][1] = tmp2
cube[0][2][2] = tmp3

```
---
* B 의 경우
    * **(앞자리가 : [3])**
    * F\`()

* B\` 의 경우
    * **(앞자리가 : [3])**
    * F()
---

### 시간 설계

```java

//시작 시간

long start = System.currentTimeMillis();



//코

.

.

.



//끝 시간

long end = System.currentTimeMillis();



//시작 시간과 끝 시간의 차이

long time = (long) ((end - start) / 1000.0);



//결과 출력(000초)

System.out.println(time + "초");


```

### 무작위 섞기 기능

* 함수이름은 **random()** 

    * 각 색깔이 6개 들어있는 배열을 람다로 돌려서 인덱스는 랜덤하게 들어가도될듯?
    
### 맞추면 종료 기능

* 회전한 횟수를 제야함 일단 왜냐면 회전횟수가 1일때는 적용되면 안되니깐

* 맞추면은.. 으음 count > 1 부터 **spec()** 함수로 한번 돌릴때마다 전체면의 내용이 같은지 확인해보면 될듯

* 맞출경우 축하메세지 출력 "모든 면을 다맞추셨습니다 당신은 천재!"

### 함수설명

```java

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

```

* 위에서 설명했듯이 구현과정 자체에서는 생각하지 못했던 것들이 코드를 적다보니 생겨났음, 예를 들면 F 를 실행할경우
앞면과 맞닿아있는 양옆위아래면들이 Rrotate 가 발생해야 되어야 한다. 그런 부분들을 적느라 생각보다, 기존 설계 사고에서 추가된 함수들이 많다. 그리고 Frotate 같은 경우는 설계 자체가 실수로 잘못해서, 코드를 작성하면서 수정을 하였다.

* L 과 R 은 index 로 구분가능하므로, R의 발생시킬경우 마지막 index 값이 2로 L` 를 발생시키면 되므로, main index 혹은 별도의 키를 인지하는 과정을 통해 해당 부분은 함수에 인덱스를 넘겨줄 것이다.

* sideLRotate() 관련 함수는 위에서 말한 F 혹은 B 가 발생했을시 발동하는 함수이다. 양옆 사이드에서 돈다고 생각되어 
side 를 붙여주었다.

* rotate 즉 회전 함수와 관련된 함수를 모두 private 으로 캡슐화 처리하였음, 내부 로직을 클라이언트가 알수없고, 클라이언트는 클라이언트의 역할인 키 입력만 할수있도록 함


### 시간함수

```java

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

```

* 위에서 설명한 내용을 그대로 구현하였다.

### Random 함수 설명

```java

 public void random(){
        String[] actionArray = {"U", "U`","B","B`","L","L`","F","F`","D","D`"};
        int selectActionKey;
        Random random = new Random();
        for(int i = 0; i<10; i++){
            selectActionKey = random.nextInt(10);
            this.rotationCube(actionArray[selectActionKey]);
        } 
    }

```

* 랜덤으로 10번은 방향키를 입력하여 섞어서 나온다.

### 유효성 검사 코드

```java
  private void validateKey(String key) throws CustomError{
        if(!(key.equals("U") || key.equals("U`") || key.equals("R") || key.equals("R`") || key.equals("F`") || key.equals("F") || key.equals("B`") || key.equals("B") || key.equals("L") || key.equals("L`") || key.equals("B") || key.equals("B`") || key.equals("Q") || key.equals("q"))){
            throw new CustomError("올바른 키를 입력하지 않았습니다.");
        }
    }
```

* 입력할 수 있는 키에 대한 유효성 검사를 진행한다.

### SPEC(큐브를 맞췄는지) 검증 함수

```java
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
```

* 각 면의 첫 배열을 수집한뒤 해당 면의 단어들이, 수집한 단어와 동일한지 확인한뒤 모두 동일하다면 true 를 뱉는다.

### 메인 동작 함수

```java
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
                    this.endTime = this.endTime();
                    this.duratinGameTime = this.getDurationGameTime(this.startTime, this.endTime);
                    System.out.println("게임 플레이타임 : "+ this.duratinGameTime +"초");
                    System.out.println("큐브 회전횟수 : " + (this.rotateCount-1) + "회");
                    System.out.println("게임을 종료합니다....");
                    System.exit(0);
                    break;
            }
            this.printcube();
            if(spec()){
                System.out.println("큐브를 맞추셨습니다! 당신은 천재!");
                this.endTime = this.endTime();
                this.duratinGameTime = this.getDurationGameTime(this.startTime, this.endTime);
                System.out.println("게임 플레이타임 : "+ this.duratinGameTime +"초");
                System.out.println("큐브 회전횟수 : " + (this.rotateCount-1) + "회");
                System.out.println("게임을 종료합니다....");
                System.exit(0);
            }
        }
 
    }

```
* 일단 큐브를 init() 시키는 과정에서 처음 시작시간을 측정하고, 큐브 게임을 종료하거나 맞췄을때를 종료 시점으로 하여 시간을 계산하였다.
* rotationCount 는 회전횟수 및 첫 회전전에 검증 함수가 돌아가지 않도록 하기 위해 사용했다.


### 코드 스케치 과정
* ~~0차 스케치 : 코드 설계~~
* ~~1차 스케치 : 코드 설계 / 주요기능 코드 작성~~
* ~~2차 스케치 : 주요 기능 코드 작성~~
* ~~3차 스케치 : 가동시간 및 랜덤 / SPEC 함수 작성~~
* 리팩토링!
### 고려사항

* ~~큐브를 객체화 시켜 싱글톤 클래스로 반환해줄까 고민중~~ 

* ~~전역변수를 쓰지않고, 객체를 리턴받는다면 큐브 같은경우는 회전을 다수 해야되기때문에 객체를 싱글톤으로 하나로 반환하는것도 좋을것 같다. 객체를 생성하지 않고 한 객체만 쓰므로!~~

* Singleton Pattern 으로 설계하는것을 고민하였으나, 큐브를 여러 사용자가 쓸경우 하나의 큐브 객체를 주면 안되는 상황이므로 잘못 고민했다고 판단, 사용자 하나마다 큐브 객체를 주어주는 것으로 설정함.

### 설계하며 느낀점 && 배운점

* 최대한 Step3.java 는 클라이언트 측이라고 생각해서 Class 에서 캡슐화 하려고 노력했다.

* 그리고 큐브의 역할을 최대한 큐브 Class 내에서 처리하려고 노력했다. 처음에는 Main 에서 모든 걸 진행하려 했지만 객체 지향 적으로 코드를 적기 위해서는 큐브를 클래스화 처리 한뒤, 인스턴스로 이용할 수 있도록 하고 싶었다. 그리고 캡슐화를 하여 사용자가 내부 로직을 몰라도 키 입력만으로 동작할 수 있도록 처리했다.

* 아직 리팩토링 과정이 남아있다. 코드를 더욱더 개선하거나 확장성을 늘려주고 싶다.