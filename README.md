# Albumirekisteri

Albumirekisteri on Java-sovellus, jonka tein opinnoissa. Sen avulla käyttäjä voi hallita musiikkialbumeita ja ylläpitää omaa albumirekisteriä.

## Kuvaus
- Sovelluksessa voi **luoda käyttäjätilin** ja kirjautua sisään.  
- Käyttäjä voi **lisätä albumeita**, tarkastella niitä ja ylläpitää omaa **muistilistaa / rekisteriä**.
- Sovellus on toteutettu Javalla ja käyttäen JavaFX-kirjastoa.

## Vaatimukset
- Java JDK (versio 11 tai uudempi)  
- JavaFX-kirjasto 

## Asennus ja ajaminen
Suoritettava `.jar`-tiedoston ajaminen onnistuu komennolla (JavaFX-kirjastopolut määritelty):
> ```bash
> java --module-path /polku/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar Albumirekisteri.jar
> ```
