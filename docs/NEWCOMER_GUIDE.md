# Onboarding: как быстро понять кодовую базу

## 1) Что это за проект
`Coworker.ru` — backend на Spring Boot для управления коворкингами:
- регистрация/логин + JWT;
- просмотр коворкингов и доступности;
- бронирование;
- админское добавление коворкингов;
- сценарий «дверь + QR + WebSocket».

Точка входа — `Application` (`@SpringBootApplication`).

## 2) Как устроены пакеты

### `authenticationService`
Отдельный блок для auth/security:
- `controller/SecurityController` — REST для register/login/refresh/verify;
- `service/UserService` — регистрация, email-верификация, логин, refresh-токены;
- `jwt/*` — генерация/проверка access/refresh JWT, фильтр и auth-модель;
- `entity/User` + `repository/UserRepo`.

### `mainService`
Бизнес-логика коворкингов:
- `common/entity` — JPA-сущности (`Coworking`, `Booking`, `Review`) и request DTO;
- `common/repository` — JPA-репозитории + агрегирующий query по занятости;
- `userService` — публичная «главная» часть (список коворкингов, поиск доступных);
- `bookingService` — создание бронирований;
- `adminService` — админские операции;
- `doorService` — логика сканера/двери, проверка ближайшей брони.

### Корневые конфиги
- `SecurityConfig` — общая security-конфигурация Spring Security;
- `WebConfig` — CORS;
- `WebSocketConfig` — STOMP endpoint `/door-websocket`;
- `OpenApiConfig` — метаданные OpenAPI.

## 3) Базовый запросный поток (очень кратко)
1. Клиент вызывает endpoint контроллера.
2. Контроллер делегирует в сервис.
3. Сервис работает с JPA-репозиториями и сущностями.
4. Результат возвращается как `ResponseEntity`.

Примеры:
- `POST /api/auth/login` -> `UserService.login()` -> JWT;
- `POST /api/main/bookings/create-booking` -> username извлекается из JWT -> `BookingService.CreateNewBooking()`;
- `POST /api/main/availability` -> `MainPageService.findAvailableCoworkings()` с расчетом свободной вместимости.

## 4) Что важно знать новичку в первую очередь

### Безопасность и доступ
- Используется role/authority-подход (`student`, `admin`) и `@PreAuthorize` на методах.
- В коде одновременно есть stateful элементы (`formLogin`, `httpBasic`) и JWT-логика — перед доработкой auth лучше выровнять стратегию (stateless JWT vs смешанная схема).

### Доменные сущности и связи
- `User` <-> `Booking`, `Review` (one-to-many);
- `Coworking` <-> `Booking`, `Review` (one-to-many);
- ключи в основном UUID.

### Потенциальные технические риски
- В `application.properties` лежат чувствительные данные (пароли/секреты) — вынести в env/secret manager.
- В `Booking` есть поле `capacity`, но в создании брони оно сейчас не заполняется — проверяйте логику доступности и фактическую емкость.
- `UserRepo` типизируется как `JpaRepository<User, Long>`, хотя `User.id` — `UUID`.

## 5) С чего читать код (рекомендуемый маршрут)
1. `README.md` + `pom.xml` (что за проект и стек).
2. `application.properties` (порт, БД, jwt/mail настройки).
3. `SecurityController` -> `UserService` -> `JwtProvider`.
4. `MainController` -> `MainPageService` -> `BookingRepo` query.
5. `BookingController` -> `BookingService`.
6. `DoorController` -> `DoorService` + `WebSocketConfig`.
7. Сущности и репозитории (`common/entity`, `common/repository`).

## 6) Что изучить дальше (практические подсказки)
- Прогнать OpenAPI (`/swagger-ui/index.html`) и сверить фактические контракты endpoint-ов.
- Добавить интеграционные тесты на auth-flow и booking-flow (сейчас есть только `contextLoads`).
- Проверить и унифицировать CORS/безопасность.
- Вынести секреты в переменные окружения.
- Уточнить бизнес-правила по вместимости и истечению бронирований.
