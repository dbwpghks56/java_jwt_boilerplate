# Todo_server

### Caution
공통 Response를 만들어 Swagger 에 나와있는 response와 실제 response가 다를 수 있습니다.  
공통 Response를 만든 이유는 API 를 사용하는 곳에서 response가 일관성있게 나오기 때문에 사용하기 쉽고  
규칙이 적용되어있기 때문에 재사용성과 향후수정 및 에러 처리에도 좋기때문이라고 생각합니다.
<hr>

### Technologies used:

• Java 11   
• Spring Boot 2.7.14    
• Swagger 3.0        
• JWT   
• QueryDsl  
• Spring Security (Security)    
• JPA   
• PostgreSQL    
• Sentry    
• Flyway     

## Installation

```bash
$ git clone https://github.com/HIGHERX-ASSIGNMENT/dbwpghks56.git
```

## Running the app
기본 포트는 8090 으로 되어있습니다. <br>
```
TodoApplication.java
```
실행전 실행할 yml 을 설정해서 실행해주어야 합니다.  
Intellij의 경우 [예시](https://velog.io/@dbwpghks56/Spring-Boot-%EC%9A%B4%EC%98%81%ED%99%98%EA%B2%BD%EB%B3%84-yml-%EC%84%A4%EC%A0%95) 를 보고 설정해주세요.   
접근한 뒤 main 을 실행해주시면 됩니다. <br>
clean gradle 및 build gradle 이 필요한 경우도 있습니다. <br>

## ERD
![image](https://github.com/HIGHERX-ASSIGNMENT/dbwpghks56/assets/43091440/1bbcdc53-e196-4661-99b7-2dc0037888bb)
모든 테이블은 base_entity를 상속받아 base_entity의 칼럼들을 가지고 있습니다.

# Projects Structure

 본 프로젝트는 `Package by feature` 형태이며, **모놀리스 아키텍처**로 구성되어 있습니다.

 하기에서 설명하는 프로젝트 구조는 간략한 설명이며, 이와 관련하여 구현된 메소드의 자세한 사항은 각 패키지의 Controller 에 표기된 OAS Annotation 및 주석을 참고하시면 됩니다.

## /src/main/java/com.test.api.todo

Default Package: com.test.api.todo

- ********************************auth:******************************** 인증 관련 로직을 모아둔 패키지입니다. 로그인, 회원가입, 토큰 재발급 및 검증 Filter 가 포함되어 있습니다.
- ************boot :************ 본 프로젝트에서 전역적이고, 공통으로 사용할 Spring Boot 에 대한 설정, 유틸 함수, Advisor, Custom Exceptions, 스케쥴러, Enum 등이 담긴 패키지입니다.
- ********************************user :******************************** 유저와 관련된 패키지입니다. 인증 로직과 밀접한 관련이 있으며, 유저 정보 수정 및 조회가 포함되어 있습니다.
  또한 회원가입에 대한 검증 로직이 포함되어있습니다.
- todo : todo 와 관련된 패키지입니다. user와 1 : n 연관관계 설정이 되어있으며, todo의 CRUD 기능이 포함되어있습니다.

## /src/main/resources

 해당 패키지에서 핵심이 되는 파일은 4가지 입니다.

- **********************************application.yml:********************************** 분리된 운영환경에서 공통으로 사용할 설정 파일
- **************************application-local.yml:************************** 로컬 환경에서 사용할 설정 파일
- **************************application-dev.yml:************************** 개발 환경에서 사용할 설정 파일
- **application-prod.yml:** 실운영 환경에서 사용할 설정 파일

 이때, 유의할 점은 로컬 환경에서 실행 시, Profile 은 `local` 로 지정하여 실행해야합니다. 정상적인 실행이 가능하도록 `application-local.yml` 을 참고하여 데이터베이스 등의 세팅을 수행해주세요.

 각 운영 파일은 해당 프로젝트가 Private Repository 인 관계로 Repository 내에 함께 포함시켜놓았습니다. **반드시 해당 Repository 이외의 외부에 노출되어서는 안됩니다.**

## Swagger
```
swagger (Api 명세) 접근 주소입니다.
http://localhost:8090/api/v1/swagger-ui/index.html
or
http://127.0.0.1:8090/api/v1/swagger-ui/index.html

```
접속하면 Api 명세를 볼 수 있습니다.

### JWT
JWT 를 이용해서 클라이언트단에서 token을 이용해 직접 정보를 관리하도록 설정했습니다.     
accessToken의 경우 4일, refreshToken의 경우 일주일을 만료기간으로 설정했습니다.    
refreshToken의 경우 cookie에 Httponly로 저장하여 보안을 높였습니다.

### Spring Security (Security)
Security 설정을 추가해 인가된 사용자만 특정 URL에 접근할 수 있도록 제한했습니다.   
Anonymous 가 접근할 수 있어야 하는 API는 permitAll()을 선언했습니다.  
또한 ROLE_USER, ROLE_ADMIN 등 권한에 대한 인가가 필요하지만 요구사항에 없기에 삭제했습니다.

### JPA & QueryDSL (ORM)
객체 중심 domain 설계 및 반복적인 CRUD 작업을 대체해 비즈니스 로직에 집중했습니다.  
• JPA : 반복적인 CRUD 작업을 대체해 간단히 DB에서 데이터를 조회합니다.   
• QueryDSL : JPA로 해결할 수 없는 SQL은 QueryDSL로 작성합니다.

### Swagger 3.0

Swagger 를 추가해 Api 에대한 명세를 쉽게 눈으로 확인할 수 있도록 만들었습다.     
@shema @Tag 등을 이용해서 swagger 에 대한 명세서를 더욱 구체적으로 작성했습니다.

### Sentry

커스텀 에러 및 스프링 에러를 추적, 기록하기 위해 sentry를 도입했습니다.    

### Flyway
[Flyway 도입 내용](https://velog.io/@dbwpghks56/Flyway-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EB%8F%84%EC%9E%85)




