# BilleProject
# Sklep internetowy w Spring Boot


## Opis projektu

Ten projekt jest sklepem internetowym, który został zaimplementowany w technologii Spring Boot. Sklep umożliwia użytkownikom przeglądanie, wyszukiwanie i zakup produktów online. Funkcjonalności sklepu obejmują:
- Przeglądanie kategorii produktów
- Wyszukiwanie produktów na podstawie nazwy, kategorii itp.
- Dodawanie produktów do koszyka
- Realizację zamówienia
- Zarządzanie kontem użytkownika

## Wymagania systemowe

Aby uruchomić sklep internetowy w Spring Boot, musisz mieć zainstalowane następujące narzędzia:
- Java Development Kit (JDK) w wersji 11 lub nowszej
- Maven - narzędzie do zarządzania zależnościami i budowy projektu

## Uruchamianie projektu

1. Sklonuj repozytorium sklepu internetowego na swój lokalny komputer:
```
git clone https://github.com/Xivoks/BilleProject
```
2. Przejdź do katalogu projektu:
```
cd demo
```
3. Uruchom aplikację przy użyciu Maven:
```
mvn spring-boot:run
```
4. Aplikacja sklepu internetowego zostanie uruchomiona na `http://localhost:8080`. Otwórz tę adres w przeglądarce, aby uzyskać dostęp do sklepu.

## Konfiguracja bazy danych

Sklep internetowy wykorzystuje bazę danych MySQL do przechowywania danych. Aby skonfigurować połączenie z bazą danych, należy dostosować ustawienia w pliku `application.properties`, znajdującym się w katalogu `src/main/resources`. 

Przykładowa konfiguracja dla bazy danych MySQL:
```
spring.datasource.url=jdbc:mysql://localhost:3306/nazwa_bazy_danych
spring.datasource.username=twoja_nazwa_uzytkownika
spring.datasource.password=twoje_haslo
```

## Dokumentacja API

Dokumentacja API sklepu internetowego jest dostępna pod adresem `http://localhost:8080/swagger-ui.html`. Po uruchomieniu aplikacji, możesz korzystać z interaktywnego interfejsu Swagger, aby poznać dostępne endpointy i parametry.


