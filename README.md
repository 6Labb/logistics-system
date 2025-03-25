# 대규모 AI 시스템 프로젝트 - 6Lab



### 👥 팀원

|                                                                                          |                                                                                          |                                                                                          |                                                                                          |                                                                                                |
|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/197976648?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/76428635?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/163955181?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/125863849?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/85439982?s=400&v=4" width="120px;" alt=""/> |
|                            [김 훈](https://github.com/Hooni-i)                             |                         [김민주](https://github.com/mjjmjmjmj)                          |                         [정은선](https://https://github.com/jeongeunsun)                         |                            [박성주](https://github.com/goodperiodt)                            |                              [엄은진](https://github.com/mummumm)                               |
|                                       **공통 모듈, 추천 알고리즘 연동 및 AI 응답 처리, 슬랙 알림 기능 및 Webhook 연동**                                       |                                    **Gateway, Config Server, Eureka, 인증/인가(JWT+Security), 사용자, 허브 도메인 개발**                                  |                                  **협력 업체 관리를 위한 업체 도메인의 기획 및 개발, 입출고 관리의 기반이 되는 상품 도메인 개발**                                   |                             **물류 시스템 내에서 주문의 생성부터 상태 전이까지 전반적인 흐름을 관리하는 주문 도메인의 설계 및 구현**                             |                               **상품의 출고 및 배송 프로세스를 담당하는 배송 도메인을 설계하고 구현**                               |

---

### ⏰ 프로젝트 기간
📅 **2025-03-11 ~ 2025-03-25**

---

## 📌 프로젝트 목적

본 프로젝트는 B2B 기반의 물류관리 및 배송 시스템을 구축하여,  
**공급업체와 수령업체 간의 상품 주문, 재고 관리, 배송 과정을 디지털화하고 자동화**하는 것을 목표로 합니다.  
허브(물류센터)를 중심으로 한 효율적인 재고 관리, 허브 간 배송 추적,  
예측 기반 출고 시점 안내, 실시간 알림 기능 등을 통해  
**현실적인 물류 환경에 적용 가능한 시스템을 설계**하였습니다.

## 📦 프로젝트 상세

본 시스템은 공급업체와 수령업체가 사용하는 B2B 기반의 물류 플랫폼으로,  
다음과 같은 흐름을 중심으로 구성되어 있습니다:

1. **업체 등록 및 허브 지정**  
 공급업체와 수령업체는 시스템에 등록되며, 각 업체는 해당 지역의 물류 허브(센터)와 연결됩니다.  
 허브는 재고 보관, 출고, 배송의 출발지 또는 경유지로 활용됩니다.

2. **상품 재고 관리**
 공급업체는 허브에 상품 재고를 사전 입고하여 관리합니다.  
 상품은 재고 수량의 정보가 포함됩니다.

3. **주문 생성 및 처리**  
 수령업체가 공급업체에 상품을 주문하면 주문이 생성되고,  
 주문 정보는 허브 단위로 연결되어 출고 준비가 시작됩니다.  
 이 과정에서 허브 재고가 차감됩니다.

4. **배송 프로세스**  
 출고가 확정되면 배송이 시작되며, 허브 간 사전에 정의된 이동 경로를 따라 배송이 진행됩니다.  
 배송 상태(출발, 허브 도착, 이동 중, 완료 등)는 단계별로 업데이트되며,  
 배송 이력 또한 시스템에 기록됩니다.

5. **출고 시점 예측 및 알림**  
 AI 시스템은 요청된 도착 시간에 맞춰 언제 출고해야 할지를 계산하여,  
 발송 허브 담당자에게 배송 예상 시간 정보를 제공합니다.  
 또한, Slack을 통해 주문이 발생한 시점에  
 **주문 번호, 주문자 정보, 요청사항, 발송지, 경유지, 도착지, 배송 담당자** 등의 정보를  
 자동으로 발송 허브 담당자에게 전달함으로써 신속한 대응을 가능하게 합니다.

본 시스템은 MSA(Microservice Architecture) 기반으로,  
도메인별로 독립적인 서비스를 구성하였으며  
Eureka, Config Server, Gateway, JWT 인증 시스템을 통해 전체 서비스를 유기적으로 연결하였습니다.

---
<details>
  <summary><strong>🛠 서비스 구성 및 실행 방법</strong></summary><br>

### 1. 프로젝트 클론

```bash
git clone https://github.com/6Labb/logistics-system.git
cd logistics-system
```

---

### 2. Docker 인프라 실행

본 프로젝트는 `docker-compose`를 통해 공통 인프라 환경을 구성합니다.

**구성 요소:**
- **PostgreSQL**
- **Redis**
- **Zipkin**

```bash
docker-compose up -d
```

---

### 3. 필수 인프라 서비스 실행

> 실행 순서에는 상관 없지만, 두 서버 모두 반드시 먼저 실행되어 있어야 합니다.

```bash
# Config Server
cd infra/config-server
./gradlew bootRun
```

```bash
# Eureka Server
cd infra/eureka-server
./gradlew bootRun
```

---

### 4. 마이크로서비스 실행

Config Server와 Eureka Server가 실행된 상태에서,  
각 도메인 서비스들을 실행합니다.

```bash
# User Service
cd services/user-service
./gradlew bootRun
```

```bash
# Hub Service
cd services/hub-service
./gradlew bootRun
```

```bash
# Order Service
cd services/order-service
./gradlew bootRun
```

```bash
# Product Service
cd services/product-service
./gradlew bootRun
```

```bash
# Company Service
cd services/company-service
./gradlew bootRun
```

```bash
# Delivery Service
cd services/delivery-service
./gradlew bootRun
```

```bash
# Slack&AI Service
cd services/slack-ai-service
./gradlew bootRun
```

---

### 5. Gateway 실행

```bash
cd infra/gateway-server
./gradlew bootRun
```

---

### 6. 접속 경로 안내

| 기능 | 주소 |
|------|------|
| **Gateway 통합 진입점** | `http://localhost:19091` |
| **Swagger API 문서 (통합)** | `http://localhost:19091/swagger-ui` |
| **Zipkin 트레이싱** | `http://localhost:9411` |
| **Eureka 대시보드** | `http://localhost:19090` |

</details>

---
### ERD
![logistics_system (4)](https://github.com/user-attachments/assets/cbdfc867-4b40-45c2-992e-5cd0b42dca13)

---
### 트러블 슈팅
- [트러블슈팅: FeignClient 응답 DTO와 내부 도메인 객체의 분리 필요성](https://github.com/6Labb/logistics-system/wiki/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85%5D-FeignClient-%EC%9D%91%EB%8B%B5-DTO%EC%99%80-%EB%82%B4%EB%B6%80-%EB%8F%84%EB%A9%94%EC%9D%B8-%EA%B0%9D%EC%B2%B4%EC%9D%98-%EB%B6%84%EB%A6%AC-%ED%95%84%EC%9A%94%EC%84%B1)

- [트러블슈팅: Zipkin Docker 이미지 실행 실패 (Apple M4 칩 + Java 21 이슈)](https://github.com/6Labb/logistics-system/wiki/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85%5D-Zipkin-Docker-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%8B%A4%ED%96%89-%EC%8B%A4%ED%8C%A8-(Apple-M4-%EC%B9%A9---Java-21-%EC%9D%B4%EC%8A%88))

### 잘한 점
- [잘한 점: common 공통모듈 개발](https://github.com/6Labb/logistics-system/wiki/%5B%EC%9E%98%ED%95%9C%EC%A0%90%5D-common-%EA%B3%B5%ED%86%B5%EB%AA%A8%EB%93%88-%EA%B0%9C%EB%B0%9C)


--- 

### ⚒️ 기술 스택
- **Language:** Java 17
- **Framework:** Spring Boot 3.4.3
- **Build Tool:** Gradle 8.10
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Test:** JUnit 5 , Mockito
- **Security:** Spring Security, JWT


## 📝 Technologies & Tools (BE) 📝
![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=jsonwebtokens&logoColor=white)
![Spring Cloud Gateway](https://img.shields.io/badge/Spring%20Cloud%20Gateway-6DB33F?style=flat)
![Eureka](https://img.shields.io/badge/Eureka-6DB33F?style=flat)
![Spring Cloud Config](https://img.shields.io/badge/Config%20Server-6DB33F?style=flat&logo=spring&logoColor=white)
![Feign Client](https://img.shields.io/badge/FeignClient-000000?style=flat)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=flat&logo=rabbitmq&logoColor=white)
![Resilience4j](https://img.shields.io/badge/Resilience4j-4B8BBE?style=flat)
![Slack API](https://img.shields.io/badge/Slack%20API-4A154B?style=flat&logo=slack&logoColor=white)
![Gemini API](https://img.shields.io/badge/Gemini%20API-ffca28?style=flat&logo=google&logoColor=black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![Zipkin](https://img.shields.io/badge/Zipkin-000000?style=flat&logo=apache&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000?style=flat&logo=intellijidea&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=flat&logo=slack&logoColor=white)


---

