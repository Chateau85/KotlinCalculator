# KotlinCalculator

간단한 Android 계산기 애플리케이션입니다. 사칙연산, 소수, 부호 변경, 퍼센트와 연속 계산을 지원합니다.

## 개발 환경

- JDK 17
- Android SDK 37
- Android Gradle Plugin 9.3.1
- Gradle 9.5
- AGP 내장 Kotlin

## 빌드와 테스트

Android SDK 경로를 `local.properties`의 `sdk.dir` 또는 `ANDROID_HOME`으로 설정한 뒤 실행합니다.

```powershell
.\gradlew.bat test lint assembleDebug
```

계산 로직은 Android UI와 분리되어 있으며 `app/src/test`의 로컬 단위 테스트로 검증합니다.
