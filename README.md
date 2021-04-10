## Kontekst aplikacji
Aplikacja jest prostym room chatem z mozliwością pisania prywatnych wiadomości. Istnieje system logowania / rejestracji na podstawie tokenu JWT oraz istnieją role takie jak użytkownik standardowy oraz moderator. Moderator może usuwać wiadomości z chat roomu.

## Wdrożenie aplikacji
Repozytorium zawiera gotowe pipeliny CI/CD które automatycznie wdrażają infrastrukturę na platformę Azure. Należy zastąpić sekrety własnymi.
Pipeliny można znaleźć tutaj: https://github.com/metelskiwiktor/chat/tree/main/.github/workflows

## Testy obciążeniowe
Testy zostały wykonane za pomocą Azure DevOps pipelines, kod źródłowy znajduje się tutaj: https://github.com/metelskiwiktor/chat/blob/main/azure-pipelines.yml

## 1) 10 000 requestów (20-220 requestów/sek, ~97 transakcji/sek)
![10000summary](https://user-images.githubusercontent.com/26926804/115932475-521e6600-a48d-11eb-8820-f956e064268e.png)
<sub><sup>Podsumowanie</sup></sub>

* Apdex*: 0.852 
* Średni czas przetworzenia requestu: 0.42s
* Ilość timeoutów: 0%

Wszystkie parametry są zadowalające.

![rtvr10000](https://user-images.githubusercontent.com/26926804/115933154-96f6cc80-a48e-11eb-953c-7011a33aa143.png)
<sub><sup>Response Time vs Request</sup></sub>

Wykres wskazuje na tendencję spadkową czasu reakcji pod większym obciążeniem. Myślę że jest to spowodane gorszym App Service Plan.

## 2) 15 000 requestów (20-220 requestów/sek, ~97 transakcji/sek)
![image](https://user-images.githubusercontent.com/26926804/115933261-ce657900-a48e-11eb-9940-7cc55fa2acc7.png)
<sub><sup>Podsumowanie</sup></sub>

* Apdex*: 0.350 
* Średni czas przetworzenia requestu: 1.87s
* Ilość timeoutów: 0%

Średni czas przetworzenia requestu znacznie wzrosł, jednakże nie znaleziono ani jednego nieprzetworzonego żądania. 

![image](https://user-images.githubusercontent.com/26926804/115933628-901c8980-a48f-11eb-8c4a-d83c68ec8b8d.png)
<sub><sup>Response Time vs Request</sup></sub>

W tym przypadku widać stałą liczbę czasu reakcji bez wpływu na obciążenie serwera.

## 3) 23 000 requestów (50-350 requestów/sek, ~112 transakcji/sek)
![image](https://user-images.githubusercontent.com/26926804/115933872-1b961a80-a490-11eb-9a10-7880836d2c1e.png)
<sub><sup>Podsumowanie</sup></sub>

* Apdex*: 0.583 
* Średni czas przetworzenia requestu: 2.31s
* Ilość timeoutów: 1.15%

Pomimo dłuższego czasu przetwarzania requestów statystyka apdex pozytywnie wzrosła w porównianiu do poprzedniego testu, prawdopodobnie wynika to ze zwiększonego obciążenia ilością requestów. Przy takim obciążeniu pojawiają się pierwsze requesty, które nie zostają pomyślnie obsłużone. 

![image](https://user-images.githubusercontent.com/26926804/115934453-551b5580-a491-11eb-9b80-d8cfe5a3e3a7.png)
<sub><sup>Response Time vs Request</sup></sub>

Ze względu na pojawienie się nieobsługiwanych requestów wykres został przybliżony, po prawej widnieje bardziej ogólny obraz wykresu. Przy około ~170 requestów/sek można zauważyć spore zmniejszenie czasu reakcji. Wynika to ze skalowania aplikacji, a dokładniej przez zwiększenie instancji. Od tego czasu czestotliwość występowania timeout requestów się lekko zmniejszyła.

-----------
<sub><sup>*Apdex: https://en.wikipedia.org/wiki/Apdex</sup></sub>
