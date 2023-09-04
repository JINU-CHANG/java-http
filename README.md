# 톰캣 구현하기

- [x] GET /index.html 요청에 응답한다.
- [x] CSS 형식의 정적 파일 요청도 지원한다.
- [x] QueryString 파싱 기능을 추가한다.
- [x] 로그인 기능(/login)을 구현한다.
    - [x] 로그인의 성공하면 http status 302를 반환한다.
    - [x] 로그인에 성공하면 세션에 사용자를 저장한다.
    - [x] 로그인에 성공하면 JSESSIONID를 Set-Cookie 헤더에 담아 보낸다.
    - [x] 로그인에 성공한 상태에서 로그인 페이지로 접근하면 index.html로 리다이렉트한다.
    - [x] location header에 리다이렉트할 url을 추가한다.
    - [x] 로그인에 실패하는 경우 401.html로 리다이렉트한다.
- [x] 회원가입 기능(/register)을 구현한다.
    - [x] 회원가입 페이지의 경우 GET을 사용하여 보여준다.
    - [x] 회원가입의 경우 POST를 사용한다.
    - [x] 회원가입을 완료하는 경우 index.html로 리다이렉트한다.

