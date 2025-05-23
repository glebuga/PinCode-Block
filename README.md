# Проект MVI-приложения на Android

## Описание

В рамках этого проекта реализована часть Android-приложения с использованием современных практик и архитектурных подходов. Были созданы экраны, ViewModel, UseCase и репозитории. Вместо обращения к реальному серверу используется набор "Fake" репозиториев, которые возвращают заранее определённые данные.

## Стек технологий

* **Android Studio**
* **Kotlin**

  * Coroutines
  * Flows
  * LocalDateTime
* **Jetpack Compose**

  * Material3
  * Compose Navigation
* **MVI (Model–View–Intent)**
* **Koin** (Dependency Injection)
* **Clean Architecture**
* **DataStore** (для хранения данных между сессиями)

## Архитектурные слои

1. **Presentation**

   * Экраны на Compose
   * ViewModel, обрабатывающие Intent и обновляющие UI State
2. **Domain**

   * UseCase: бизнес-логика приложения
3. **Data**

   * Репозитории: интерфейсы и их Fake-реализации
   * DataStore для локального хранения

## Структура репозитория

```
├── app/                     # Модуль приложения
│   ├── src/main/java/...
│   │   ├── presentation/    # UI, Compose, ViewModel
│   │   ├── domain/          # UseCase, модели домена
│   │   └── data/            # Fake-репозитории, DataStore
│   └── src/main/res/        # Ресурсы (строки, темы)
├── build.gradle
└── settings.gradle
```

## Представление


## Лицензия

Проект предоставляется "как есть" без каких-либо гарантий. Использование кода — на ваш страх и риск.
