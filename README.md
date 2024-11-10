# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> 깔끔한방
![90](https://github.com/user-attachments/assets/40e41c5d-319a-4d85-9d2f-1a629d9d92c7)


# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> ‘깔끔한 방’ 서비스 소개
- '깔끔한 방' 은 사용자 친화적인 청소 중개 플랫폼입니다.
- 이 플랫폼은 소비자가 청소 의뢰를 비대면으로 신청할 수 있습니다.
- 청소업체는 소비자가 보낸 의뢰를 확인하고 견적을 작성, 발송해 줄 수 있습니다.
- 소비자는 작성된 견적목록을 보고 비교분석 하여 원하는 청소업체를 선택할 수 있습니다.


# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> 프로젝트 설치 및 실행 가이드
이 가이드는 프로젝트의 백엔드와 프론트엔드 설치 및 실행 방법을 안내합니다.  

>### **사전 요구사항**
>- Java: Zulu 17
>- Spring boot 3.3.2
>- Node.js: 22.9.0
>- MySQL

### 백엔드 설치 및 실행 (clean_back)
**1. 저장소 클론** (<u>clean_back: 고객 서버, clean_adm_back: 청소업체 서버</u>)
```
git clone https://github.com/projectcleanroom/clean_back
cd clean_back
```

```
git clone https://github.com/projectcleanroom/clean_adm_back
cd clean_adm_back
```    
  
**2. 프로젝트 빌드**  
- IDE: 프로젝트를 열고 빌드 옵션을 실행합니다.  
- 커맨드 라인을 사용하는 경우 아래의 명령어를 실행합니다.
```
./gradlew build
```

**3. 애플리케이션 실행**
- IDE: 'Run' 버튼을 클릭하여 애플리케이션을 실행합니다.
- 커맨드 라인을 사용하는 경우 아래의 명령어를 실행합니다.
```
./gradlew bootRun
```

---
### 프론트엔드 설치 및 실행 (clean_front)
**1. 저장소 클론**
```
git clone https://github.com/projectcleanroom/clean_front
cd clean_front
```

**2. 의존성 설치**
```
yarn install
```

**3. 개발 서버 실행**
```
yarn dev
```

# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> 환경설정
**백엔드**: `application.properties` 또는 `application.yml` 파일에서 데이터베이스 연결 정보를 설정합니다.  
**프론트엔드**: `.env` 파일을 생성하고 필요한 환경 변수를 설정합니다.

# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> API 키 및 비밀 정보
API 키와 같은 민감한 정보는 GitHub Secrets에 저장되어 있습니다.  
로컬 개발 환경에서는 이 정보를 별도의 파일(예: `.env`)에 저장하고 `.gitignore`에 추가하여 관리하세요.

개발 팀원들과 안전하게 공유하려면  
1.암호화된 채널(예: 암호화된 메신저, 보안 파일 공유 서비스)을 사용하세요.  
2.각 개발자가 자신의 로컬 환경에 직접 설정하도록 안내하세요.

# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> 주의사항
- 데이터베이스 스키마 및 초기 데이터 설정이 필요할 수 있습니다.  
- 백엔드와 프론트엔드의 개발 서버 주소가 일치하는지 확인하세요.

문제가 발생하거나 추가 도움이 필요한 경우 프로젝트 관리자에게 문의하세요.



# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> License
**MIT 라이센스**   

**저작권 (c) 2024 Cleanroom**

이 소프트웨어와 관련된 문서 파일(이하 "소프트웨어")의 사본을 취득하는 모든 사람에게,  
소프트웨어를 제한 없이 사용할 수 있는 권한을 무료로 부여합니다.  
여기에는 소프트웨어를 사용하는 권한, 복사, 수정, 병합, 게시, 배포, 서브라이센스,  
그리고 소프트웨어의 사본을 판매하는 권한이 포함되며,  
소프트웨어가 제공된 사람에게 이러한 권한을 허용하는 것도 포함됩니다.
  
다음 조건을 충족해야 합니다:

위의 저작권 표시와 이 허가 문구는 소프트웨어의 모든 복사본 또는 주요 부분에 포함되어야 합니다.  
해당 소프트웨어는 "있는 그대로" 제공되며, 명시적이거나 묵시적인 어떠한 종류의 보증도 포함되지 않습니다.  
여기에는 상업성, 특정 목적에 대한 적합성 및 비침해성에 대한 묵시적인 보증이 포함되지만 이에 국한되지 않습니다.  
어떠한 경우에도 저작권자 또는 권리자는 소프트웨어나 이 소프트웨어의 사용 또는 기타 거래와 관련하여 발생하는  
청구, 손해 또는 기타 책임에 대해 책임을 지지 않습니다.  
이는 계약, 불법행위 또는 기타 행위로 인한 것일 수 있습니다.

# <img src="https://www.clean-room.co.kr/assets/icon-Utf2YN7S.png" width="25" height="25"> 바로가기 링크
- [와이어 프레임](https://www.canva.com/design/DAGLWqWorMw/jQiYR7C1BtGn37JS92WGcA/edit)
- [유저 플로우](https://app.diagrams.net/#G1C7F-7YTrrj5GN6io-2lenHBzUS2et9ZH#%7B%22pageId%22%3A%22ReBwz5dknbD1WBu8VFdt%22%7D)
- [백엔드 WIKI 바로가기](https://github.com/projectcleanroom/clean_back/wiki)


