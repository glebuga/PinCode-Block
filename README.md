# Android PIN & Biometric Auth Demo

Этот проект представляет собой демонстрационное Android-приложение, реализованное с использованием современного стека технологий. Основной функционал — установка и ввод PIN-кода с возможностью последующей аутентификации через биометрию (отпечаток пальца или распознавание лица). Проект построен по принципам чистой архитектуры (Clean Architecture) и использует MVI-подход.

## 🧹 Функциональность

* Экран установки PIN-кода при первом запуске
* Экран ввода PIN-кода при последующих входах
* Поддержка биометрической аутентификации
* HelloWorld-экран после успешной авторизации
* Сохранение состояния между сессиями с использованием DataStore
* Имитация работы с сервером через Fake Repository с захардкоженными данными

## 🛠️ Технологии и инструменты

* **Язык программирования**: Kotlin
* **Архитектура**: Clean Architecture + MVI (Model-View-Intent)
* **UI**: Jetpack Compose + Material 3
* **Навигация**: Compose Navigation
* **DI**: Koin
* **Асинхронность**: Kotlin Coroutines, Flows
* **Хранение данных**: DataStore
* **Дата и время**: LocalDateTime
* **Среда разработки**: Android Studio

## 🔧 Структура проекта

* `presentation` — экраны, ViewModel и состояние UI
* `domain` — use cases и бизнес-логика
* `data` — репозитории и фейковые источники данных
* `di` — конфигурация зависимостей с помощью Koin

## 🔄 Поток работы

1. Пользователь впервые запускает приложение — отображается экран установки PIN-кода.
2. После успешной установки PIN-кода — предлагается включить биометрическую аутентификацию.
3. При следующих запусках — показывается экран ввода PIN-кода или, если включена биометрия, предлагается вход с её помощью.
4. После успешного входа — отображается основной экран (HelloWorld).

## 🖼 Демонстрация работы
![Демонстрация работы](https://github.com/user-attachments/assets/e4daea49-76fb-464e-a16b-f6651cf5c838)



