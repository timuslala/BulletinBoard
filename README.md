#Projekt na laboratoria z kursu: Projekt i implementacja systemów webowych

Specyfikacja:
Tablica ogłoszeń drobnych
System umożliwia publikację ogłoszeń wraz z informacjami kontaktowymi.

Sprzedający posiadają w systemie odpowiednie konta. Po zalogowaniu mają oni możliwość dodawania, edytowania i usuwania własnych ogłoszeń drobnych.

Ogłoszenie składa się z tytułu, opisu, galerii zdjęć oraz tagów.

Sprzedający posiada pewną ilość danych kontaktowych (telefon, email), które może udostępniać wraz z ogłoszeniem. Dla każdego ogłoszenia można dane kontaktowe udostępniać indywidualnie.

Dostępna jest też poczta wewnętrzna, która pozwala na komunikację ze sprzedającym bez użycia zewnętrznego adresu email i telefonu. Poczta wewnętrzna jest zawsze dostępna (może być jedynym środkiem komunikacji).

Sprzedający mogą swobodnie dodawać własne tagi lub podpinać się do tagów już istniejących.

Ogłoszenie może być w trzech stanach: wersja robocza, opublikowane, archiwum. Ogłoszenie w stanach: wersja robocza i archiwum jest widoczne jedynie dla ogłoszeniodawcy.

Ogłoszeniodawca powinien mieć możliwość poglądu wersji roboczej aby zobaczyć, jak ogłoszenie będzie finalnie wyglądać. Tę funkcjonalność można zrealizować np z użyciem losowo wygenerowanego tajnego linku.

Kupujący mogą przeszukiwać bazę opublikowanych ogłoszeń z wykorzystaniem wyszukiwania tekstowego (po nazwie i opisie) oraz po tagach.

System powinien w wyszukiwarce podpowiadać tagi sortując je wg częstości użycia, na podstawie wprowadzonych znaków.

System powinien udostepniać prosty mechanizm poczty wewnętrznej. Poczta powinna umożliwiać wysłanie wiadomości tekstowej od jednego do drugiego użytkownika systemu. Poczta powinna zawierać zakładkę z zawartością skrzynki z wiadomościami. Powinna być dostępna funkcja: odpowiedz.

System powinien obsługiwać dwie klasy użytkowników:

Kupujący
Funkcja wymaga zalogowania się do systemu. Kupujący może przeglądać ogłoszenia. Kupujący, po zalogowaniu, otrzymuje dodatkowo możliwość korzystania z poczty wewnętrznej.

Ogłoszeniodawcy
Funkcja wymaga zalogowania się do systemu z użyciem loginu i hasła.
