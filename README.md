# Daily-WOD
크로스핏 박스(체육관) 와드(운동 스케쥴)가 맘에 안들어서 내가 만들 자동 WOD 생성기

## 화면 및 기능
1. 사용자 (로그인)
   1. 와드 조회 화면
      1. 와드 조회 (getDailyWod) ✅
      2. 와드 결과 기록 (createWodHistory) ✅
   2. 커스텀 와드 생성 화면
      1. 커스텀 와드 생성 기능
         1. 마음에 들때까지 생성 가능 (하루에 최대 n회 생성 가능)
      2. 커스텀 와드 기록
   3. 와드 달력 화면
      1. 와드 결과 조회 (getWodHistoriesByDateBetween) ✅
      2. 커스텀 와드 결과 조회
   4. 로그인 페이지
      1. 구글 로그인
2. 사용자 (비로그인)
   1. 와드 조회 화면 (조회만 가능)
3. 관리자
   1. 와드 달력 화면
      1. 와드 생성
         1. 직접 생성: 텍스트로 입력해서 생성 ✅
         2. LLM 기반 생성: (GPTs?)
            1. openai API 사용해서 WOD를 생성함 (WOD는 2가지로 하나는 메트콘, 하나는 유산소 베이스)
               1. WOD는 특정 템플릿에 맞게 생성되어야함 (예: 영어로, Rx'd/A/B/C 같은 레벨을 포함, 정돈된 텍스트 혹은 이미지로 출력할 수 있어야 함)
            2. WOD 생성을 시도하면 매번 다른 WOD가 생성되어야함
            3. 생성했던 WOD 목록 학습하여 이전에 생성한 WOD와 다른 WOD를 생성해야함
      2. 와드 조회 (getWodsByDateBetween) ✅
   2. 사용자 관리 화면
      1. 사용자 조회
   3. 화면 없는 기능
      1. 와드가 없을 때 만들라고 알림
   4. 로그인 페이지
      1. ID/PW 로그인

## 화면 및 기능 - 로그인
- 사용자는 SNS 로그인(구글)을 통해 로그인한다.
- 관리자는 ID/PW 로그인을 통해 로그인한다.
- 사용자와 관리자는 각각 Role 정보를 가진다. (추후 확장을 위해)
- 사용자와 관리자는 로그인 후 JWT 토큰을 기반으로 인가 처리를 한다.
- 사용자와 관리자는API의 URL을 기준으로 API를 구분한다.

--- 

- 사용자의 구글 로그인 시도 (웹 클라이언트)
- 구글 로그인 성공 후 명시한 콜백 URL로 code를 전달받는다. (code는 리소스 서버로부터 access token을 받기 위한 인증 코드)
- 해당 code를 서버로 전달하여 서버는 구글에게 access token을 요청한다.
- access token으로 사용자 정보를 요청하여 사용자 정보를 받아온다.
   
## 예외 처리 전략
1. 목적
   1. 예상되는 예외를 대응하여 프로그램의 지속적 흐름을 유지하고 예외가 왜/어떻게 발생했는지 클라이언트/개발자가 이해하기 쉽게 메시지를 전달한다. 
   2. 예상하지 못한 예외가 발생하는 경우 개발자가 빠르게 조치할 수 있게 알림 등을 통해 알린다.
2. 예외 종류(개발자가 대처할 필요가 없는 경우와 있는 경우로 크게 2가지로 나눴음) 및 구현
   1. 클라이언트(직/간접적으로 API를 사용하는 사용자/프론트/클라이언트 등)의 잘못된 요청으로 인해 생기는 예외 (ClientBadRequestException)
      1. 예: 없는 ID의 데이터를 요청하는 경우(findById), 없는 리소스를 요청하는 경우, 잘못된 파라미터로 API를 호출하는 경우 등
      2. API 차원에서 추가로 대응할 필요가 없다, 클라이언트가 잘못된 요청을 보낸 것이므로 클라이언트에게 다시 정상적으로 요청하라고 알림만 전달하면 된다.
      3. 기본적으로 400 Bad Request로 응답한다. (클라이언트가 잘못된 요청을 보낸 것이므로)
   2. 시스템에서 발생하는 예외, 사용자는 정상적인 흐름과 데이터로 요청했으나 시스템 문제로 발생하는 예외 (SystemException)
      1. 예: 연동된 외부 API 호출 시 연결이 끊긴 경우, 서버 내부 로직에서 예외가 발생한 경우 등
      2. API 차원에서 추가로 대응할 필요가 있다. 개발자의 빠른 대응이 필요하므로 개발자에게 즉시 알림을 보내야 한다.
         1. 사용자에게는 '죄송합니다. 일시적인 문제로 잠시 후 다시 시도해 주세요.'와 같은 메시지와 함께 재시도를 요청한다.
      3. 기본적으로 200 Ok로 응답한다. (클라이언트는 정상적으로 요청을 보낸 것이므로)
3. 기타
   1. 기본적인 예외 메시지가 정의돼 있어야 하고 필요하다면 별도의 예외 메시지를 정의할 수 있어야 한다.
   2. 둘 다 RuntimeException을 상속받아 구현한다.
      1. 트랜잭션 롤백이 필요하기 때문이다.

## 와드 형식 예시
```
For time of (Time CAP: 10min)

21 - 15 - 9
Thruster (95lb/65lb)
Pull-up

A
85lb/55lb

B
75lb/45lb

C
-/-
```