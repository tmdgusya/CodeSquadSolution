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

public static void validateKey(String key) throws CustomError{
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

