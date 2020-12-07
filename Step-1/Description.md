# 코드 설계 과정

**에러 처리 클래스는 Step 전역에서 사용한다.**

1. 필드명은 '_' 포함 lowerCase 형태로 작성한다. 함수명은 CamelCase로 작성한다.
2. 최대한 함수 별로 분리하도록 한다. => Test 를 한번 진행해 보았을때, 함수 기능별로 구분이 잘 되어 있어야 Test 하기가 편했음.
3. 각 입력값에 대한 Validate 처리 함수를 설계한다. 함수명 "validateFieldName"

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

* move_word 와 push_word 는 퇴근 시간으로 인해 집에서 작성예정