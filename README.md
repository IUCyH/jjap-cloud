# jjap-cloud
음악 스트리밍 백엔드 포트폴리오
<br>
[웹 사이트](https://jjapcloud.website/)

~~~
프레임워크:
  - Java17 + Spring MVC
  - JPA + SpringDataJPA + QueryDSL
  - JUnit
인프라:
  - AWS EC2
~~~
## 기능
- 세션 기반 로그인
- 회원가입
- 리소스 변경 요청에는 CSRF 토큰 검증 추가로 보안 강화
- 일관된 DTO 정의 및 ControllerAdvice 활용해 에러 핸들링
- 음악 스트리밍
  - 현재는 인스턴스 내부 폴더에 저장하도록 구현
  - HTTP 범위 기반 요청 활용
  - 커스텀 InputStream + InputStreamResource 활용해 구현

## ERD
![JJAPCloud](https://github.com/user-attachments/assets/e6f9308a-05ac-481c-92ff-504210e37589)

## 폴더 구조
~~~
src
├── main
│   ├── java
│   │   └── com
│   │       └── iucyh
│   │           └── jjapcloud
│   │               ├── common
│   │               │   ├── annotation
│   │               │   │   └── loginuser
│   │               │   ├── argumentresolver
│   │               │   ├── constant
│   │               │   ├── exception
│   │               │   │   └── errorcode
│   │               │   ├── interceptor
│   │               │   ├── util
│   │               │   └── wrapper
│   │               ├── controller
│   │               ├── domain
│   │               │   ├── music
│   │               │   └── user
│   │               ├── dto
│   │               │   ├── auth
│   │               │   ├── music
│   │               │   └── user
│   │               │       └── query
│   │               ├── repository
│   │               │   ├── mapper
│   │               │   │   └── user
│   │               │   ├── music
│   │               │   └── user
│   │               └── service
│   └── resources
│       ├── com
│       │   └── iucyh
│       │       └── jjapcloud
│       │           └── repository
│       │               └── mapper
│       │                   └── user
│       ├── static
│       └── templates
└── test
    ├── java
    │   └── com
    │       └── iucyh
    │           └── jjapcloud
    │               └── repository
    │                   └── music
    └── resources
~~~
