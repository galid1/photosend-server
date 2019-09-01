### PhotoSend Server

<br>
<br>
<br>

1. 설계
하위 도메인은 기능별로 나눔



2. 테스트
- 테스트도 일종의 문서로써 코드로 표현이 어려운 부분을 대신 문서로써 나타낼 수 있음
- 테스트 코드로인해 리팩토링시 자신감 상승
- 테스트시간 절약


3. etc
- 하루하루 코딩했던것을 리팩토링을 통해 매일 유지보수 (깔끔한 코드가 유지되어 점차 리팩토링이 어려워지는것을 조금이나마 줄임)
- 클린코드를 읽고 메소드 단위를 다시 나누어야 함을 깨달음
- 변수명 메소드명 역시 부족함
- 테스트 코드 이름역시 whenDoSomethingThenResult 형식으로 변경

4. 값 검증
- 값에 올바른값이 들어왔는지(ex. 생일필드에 문자열이 들어온다든지)는 `Presentation(Controller)계층`에서 검증
- 값이 존재하는지 (ex. findById 결과, null 검사 등)은 `Service계층`에서 검증 (Optional을 이용하면 더욱 코드가 깔끔해지는듯)
- 도메인 규칙을 올바르게 준수했는지는 `Domain계층`에서 검증