- **[README.md](http://README.md) 파일 필수**
    - 팀원 역할분담
    - 서비스 구성 및 실행방법
        - **각각의 endpoint 기재 필수**
    - 프로젝트 목적/상세
    - ERD
    - 기술 스택
    - **트러블슈팅**
    - (선택)API docs



# 프로젝트명-삼~쉽조-30
---
## 📌 프로젝트 소개
**온·오프라인 주문 관리 플랫폼**으로, 사용자와 매장이 원활하게 주문을 처리하고 관리할 수 있도록 돕는 **백엔드 API**입니다.
### 🎯 주요 기능
- 🛒 **주문 관리**: 고객이 웹/모바일 또는 매장에서 상품을 주문할 수 있음 (온라인/오프라인 통합)
- 📦 **상품 관리**: 가게 주인이 상품을 등록, 수정, 삭제하고 재고를 관리할 수 있음
- 🍽 **음식점 관리**: 메뉴 관리, 가게 정보 수정 기능 제공
- 🚚 **배송 및 픽업 관리**: 배달/픽업 주문 관리 기능 제공
- ⭐ **리뷰 및 피드백**: 고객이 주문한 상품에 대한 리뷰 작성 가능
- 🤖 **AI 상품 상세설명 시스템**: Google Gemini API를 활용한 상품 상세 설명 기능
### ⏰프로젝트 기간
---
📅**2025-02-12 ~ 2025-02-25**
### 참여인원
|                                                                                          |                                                                                          |                                                                                          |                                                                                          |                                                                                                |
|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/197976648?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/189347549?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/158035502?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/142812547?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/123526228?s=400&v=4" width="120px;" alt=""/> |
|                            [김 훈](https://github.com/Hooni-i)                             |                         [손세라](https://https://github.com/srrrn)                          |                         [주소연](https://https://github.com/jcowwk)                         |                            [차상준](https://github.com/SSan0613)                            |                              [최용석](https://github.com/challduck)                               |
|                                       **리뷰 기능 구현**                                       |                                    **카테고리 및 음식점 관리 기능 구현**                                  |                                  **상품 관리 및 장바구니 기능 구현**                                   |                             **주문 및 결제 기능 구현 / 배송지 관리 기능 구현**                             |                               **회원 관리 및 Gemini AI API 연동 기능 구현**                               |
---
## 📋 목차
1. [기술 스택](#기술-스택)
2. [주요 라이브러리](#주요-라이브러리)
3. [아키텍처](#아키텍처)
4. [API Docs](#API-Docs)
5. [ERD](#ERD)
### ⚒️ 기술 스택
- **Language:** Java 17
- **Framework:** Spring Boot 3.4.2
- **Build Tool:** Gradle 8.2.1
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Test:** JUnit 5 , Mockito
- **CI/CD:** GitHub Actions, Docker
- **Infra:** AWS EC2
- **Security:** Spring Security, JWT
