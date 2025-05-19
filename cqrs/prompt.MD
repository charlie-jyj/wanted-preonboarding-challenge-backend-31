## 역할 부여 및 페르소나 설정

### 개발 환경
```yaml
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.4.5'
	id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.wanted'
version = '0.0.1-SNAPSHOT'

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'org.postgresql:postgresql'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
	useJUnitPlatform()
}

```

당신은 능숙한 시니어 Java 개발자로서 제가 앞으로 제공할 API Spec 문서와 DB DDL 문서를 기반으로 서비스를 개발할 것입니다.
제가 제공한 문서를 기반으로 주니어 개발자인 저에게 조언해주세요.
샘플 코드를 바로 주지 마시고 제가 요청할 때에 주세요.
샘플 코드를 주실 때에는 축약하지 말고 완전한 코드로 주세요.
그리고 이해를 돕기 위해 왜 그렇게 구현했는지 주석을 써주세요.
문서의 파악이 끝났다면 답변 준비가 되었다고 말해주세요.

### 첨부 자료
(API_SPEC.md, ddl.sql 첨부)


## 답변 
주요 구성 요소:
1. 상품 관리 API - 상품 등록, 조회, 수정, 삭제 및 옵션/이미지 관리
2. 카테고리 API - 카테고리 조회 및 해당 카테고리 상품 조회
3. 메인 페이지 API - 신규/인기 상품 및 주요 카테고리 조회
4. 리뷰 API - 상품 리뷰 조회, 작성, 수정, 삭제

데이터베이스는 PostgreSQL을 사용하며, 상품과 관련된 다양한 정보(가격, 상세정보, 옵션, 이미지 등)를 여러 테이블로 나누어 관리하고 있습니다.
이 프로젝트를 진행할 때 다음과 같은 조언을 드립니다:

1. 계층형 아키텍처 구성: 코드를 Controller, Service, Repository 계층으로 구분하여 관심사 분리를 명확히 하세요.
2. 엔티티 설계: DB 스키마를 기반으로 JPA 엔티티를 설계하세요. 관계 설정(OneToMany, ManyToOne 등)에 주의하세요.
3. DTO 패턴 사용: API 요청/응답을 위한 DTO(Data Transfer Object)를 만들어 사용하세요. 이는 API 명세서의 요청/응답 모델을 기반으로 합니다.
4. 예외 처리: 문서에 명시된 에러 응답 모델에 맞게 글로벌 예외 처리기를 구현하세요.
5. 검증(Validation): 요청 데이터에 대한 유효성 검증을 구현하세요.
6. 페이지네이션: JPA의 Pageable을 활용하여 페이지네이션을 구현하세요.
7. CQRS 패턴 고려: 문서 제목에 언급된 CQRS(Command Query Responsibility Segregation) 패턴을 고려하여 조회와 명령 로직을 분리하세요.
8. 트랜잭션 관리: 데이터 일관성을 보장하기 위해 @Transactional을 적절히 사용하세요.

## 질문1
1번 조언에 따라 패키지를 아래와 같이 분리하였습니다.
- com.wanted.cqrs.controller
- com.wanted.cqrs.model (하위 패키지: dto, entity 로 분리)
- com.wanted.cqrs.repository
- com.wanted.cqrs.service

아래와 같은 순서로 구현하고자 합니다.
1. 전역 공유되는 공통 코드 구현
2. 상품 관리 관련 API 구현
3. 카테고리 관련 API 구현
4. 메인 페이지 관련 API 구현
5. 리뷰 관련 API 구현

먼저, 전역으로 공유될 공통 코드를 구현하겠습니다.
아래와 같은 순서로 진행하겠습니다.
1-1. 공통 응답 포맷
1-2. 에러 응답 모델 
1-3. 공통 에러 코드 

1-1, 1-2, 1-3 에 해당하는 모델을 자바 코드로 알려주세요.
그리고 제 질문에 대해서도 답변해주세요.

- 응답 모델은 dto 로 구현되어야 하나요?
- dao와 dto의 차이점은 무엇이며 dto가 채택되는 이유는 무엇인가요?
- 공통 응답 포맷을 보면 페이지네이션이 포함된 응답은 구조가 조금 다른데 그렇다면 페이지네이션이 있는 성공 응답과 페이지네이션이 없는 성공 응답을 아예 다른 클래스로 구분해서 사용해야 하나요?
- 에러 응답 모델을 별개로 만들어야하나요? 그렇다면 응답을 반환할 때, 성공/실패에 따라 분기를 해서 다른 클래스를 사용해야하나요? 
- 글로벌 예외 처리기를 구현하라고 하셨는데 그것은 무엇인가요?
- 에러 코드를 사용할 때 enum 같은 것으로 선언하나요?

## 답변1
전역 공유되는 공통 코드를 구현하기 위한 모델들을 만들어 보겠습니다. 먼저 질문에 답변드리고, 코드를 제공하겠습니다.
질문 답변

1. 응답 모델은 dto로 구현되어야 하나요?
네, 응답 모델은 DTO(Data Transfer Object)로 구현하는 것이 좋습니다. DTO는 계층 간 데이터 전송을 위한 객체로, API 응답 형태를 명확히 정의하고 엔티티와 분리하여 유지보수성을 높입니다.

2. dao와 dto의 차이점은 무엇이며 dto가 채택되는 이유는 무엇인가요?
DAO(Data Access Object): 데이터베이스에 접근하기 위한 객체로, 실제 DB 연동을 담당합니다. Spring에서는 Repository가 이 역할을 대신합니다.
DTO(Data Transfer Object): 계층 간 데이터 전송을 위한 객체로, 주로 API 요청/응답 데이터 구조를 정의합니다.
DTO가 채택되는 이유:
엔티티 노출 방지(보안)
필요한 데이터만 선택적 전송 가능
API 버전 관리 용이
엔티티와 표현 계층의 관심사 분리

3. 페이지네이션이 있는 응답과 없는 응답을 다른 클래스로 구분해야 하나요?
공통 응답 포맷을 유지하면서 제네릭을 활용하여 하나의 클래스로 두 경우를 모두 처리할 수 있습니다. ApiResponse<T>와 PagedApiResponse<T> 두 클래스를 만들거나, 또는 ApiResponse<T>에서 페이지네이션 정보를 선택적으로 포함할 수 있도록 구현할 수 있습니다.

4. 에러 응답 모델을 별개로 만들어야하나요?
에러 응답 모델은 별도로 만드는 것이 좋습니다. 하지만 공통 응답 포맷을 유지하기 위해 ApiResponse 클래스를 확장하거나 활용할 수 있습니다. 실제 구현에서는 글로벌 예외 처리기를 통해 일관된 방식으로 에러 응답을 생성할 수 있습니다.

5. 글로벌 예외 처리기는 무엇인가요?
Spring의 @ControllerAdvice나 @RestControllerAdvice를 사용하여 애플리케이션 전체에서 발생하는 예외를 일관되게 처리하는 중앙화된 컴포넌트입니다. 이를 통해 모든 예외를 동일한 응답 형식으로 변환할 수 있습니다.

6. 에러 코드를 enum으로 선언하나요?
네, 에러 코드는 enum으로 선언하는 것이 좋습니다. 이렇게 하면 코드의 일관성을 유지하고, 오타나 중복을 방지할 수 있으며, IDE의 자동 완성 기능을 활용할 수 있습니다.

## 1-1. 질문
* 프로퍼티에 final을 선언한 이유가 궁금합니다.
* APiResponse에 명시적으로 builder  함수를 선언하지 않았는데도 사용할 수 있는 이유가 궁금합니다. 
* 만약 성공 응답일 경우에 error 는 null일텐데 그래도 괜찮은 것인가요?
* 페이지네이션 지원 클래스를 별도로 분리하셨는데 그 이유가 궁금합니다. ApiResponse 안에서 data의 형식만 달라지면 되는 것이 아닌가요?
* 페이지네이션 지원 클래스에 대한 부가 질문을 하고 싶습니다.
- Spring Data의 Page 객체를 사용하였는데 이 객체는 통상 어떻게 사용되는 것이고 어떨 때에 사용되는지 궁금합니다.

## 1-1. 답변
1. 프로퍼티에 final을 선언한 이유
   final 키워드를 프로퍼티에 선언한 이유는 다음과 같습니다:

1) 불변성(Immutability) 보장:
final로 선언된 필드는 초기화 후 값이 변경될 수 없습니다.
응답 객체는 생성 후 변경되어서는 안 되는 불변 객체로 설계하는 것이 좋습니다.

2) 스레드 안전성(Thread Safety):
불변 객체는 본질적으로 스레드 안전합니다.
여러 스레드가 동일한 객체에 접근해도 값이 변경될 수 없으므로 동시성 문제가 발생하지 않습니다.

3) 예측 가능한 동작:
불변 객체는 상태가 변경되지 않으므로 동작을 예측하기 쉽습니다.
디버깅이 용이하고 사이드 이펙트가 발생할 가능성이 줄어듭니다.

2. Builder 사용 및 null 필드 처리
Lombok의 @Builder 어노테이션은 클래스에 붙이면 자동으로 빌더 패턴을 위한 메서드들을 생성합니다. 
명시적으로 builder 함수를 선언하지 않아도 컴파일 시점에 Lombok이 생성해줍니다.

3. 성공 응답일 경우 error 필드가 null이어도 괜찮은 이유:
@JsonInclude(JsonInclude.Include.NON_NULL) 어노테이션이 클래스에 붙어 있어서 null 값인 필드는 JSON 응답에서 아예 제외됩니다. 
즉, 성공 응답에서는 error 필드가 아예 포함되지 않습니다.
필드가 final 이더라도 값이 null이 될 수 있습니다. final은 참조가 변경되지 않음을 보장할 뿐, 참조가 null이 될 수 없다는 의미는 아닙니다.

4. 페이지네이션 지원 클래스를 별도로 분리한 이유
   PagedResponse 클래스를 별도로 분리한 주요 이유는 다음과 같습니다:

1) 역할 분리와 단일 책임 원칙(SRP):
ApiResponse는 기본 응답 구조를 담당합니다.
PagedResponse는 페이지네이션된 데이터 구조를 담당합니다.
각 클래스가 한 가지 책임만 갖도록 분리했습니다.

2) 유연성 증가:
페이지네이션 로직에 변경이 필요한 경우 ApiResponse에 영향을 주지 않고 PagedResponse만 수정할 수 있습니다.
두 기능을 분리함으로써 각각 독립적으로 발전시킬 수 있습니다.

3) 사용 방식:
이렇게 분리해도 실제 사용 시에는 ApiResponse.success(PagedResponse.from(page))와 같이 조합하여 사용합니다.
즉, API 응답의 data 부분에 PagedResponse 객체가 들어가는 형태입니다.

4) 명확한 의도 표현:
명시적으로 두 클래스를 분리함으로써 페이지네이션이 필요한 응답과 그렇지 않은 응답을 코드 수준에서 명확히 구분할 수 있습니다.

5. Spring Data의 Page 객체
Page<T> 인터페이스는 Spring Data에서 페이지네이션된 결과를 표현하기 위한 핵심 컴포넌트입니다. 아래에서 Page 객체의 주요 특징과 사용법에 대해 설명해 드리겠습니다.

1) Page 객체의 특징과 주요 메서드
Page<T> 인터페이스는 다음과 같은 중요한 정보를 제공합니다:
- getContent(): 현재 페이지의 실제 데이터(엔티티 또는 DTO 목록)를 반환
- getTotalElements(): 전체 데이터의 개수
- getTotalPages(): 총 페이지 수
- getNumber(): 현재 페이지 번호 (0부터 시작)
- getSize(): 페이지 크기 (한 페이지에 포함된 항목 수)
- getNumberOfElements(): 현재 페이지에 포함된 실제 항목 수
- hasNext(): 다음 페이지가 있는지 여부
- hasPrevious(): 이전 페이지가 있는지 여부
- isFirst(): 현재 페이지가 첫 페이지인지 여부
- isLast(): 현재 페이지가 마지막 페이지인지 여부

2) Page 객체 사용 시나리오
Page 객체는 다음과 같은 상황에서 주로 사용됩니다:

- 대용량 데이터 조회:
대량의 데이터를 한 번에 가져오지 않고 필요한 만큼만 조회
사용자에게 페이지 단위로 데이터를 보여줄 때 유용

- API 응답에서 페이지네이션 정보 제공:
REST API에서 클라이언트에게 페이지네이션 관련 메타데이터 제공
클라이언트가 "다음 페이지", "이전 페이지" 등의 기능을 구현할 수 있도록 함

- 무한 스크롤(Infinite Scroll) 구현:
모바일 앱이나 현대적인 웹 UI에서 많이 사용
사용자가 스크롤을 내릴 때마다 다음 페이지 데이터를 로드