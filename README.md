# Dokumentation

## Einleitung
Die Aufgabe war es den in den Vorlesungen zusammen erstellten App-Stand mit einer eigenen Erweiterung zu versehen.

### Stand nach Vorlesung:
Der User hat nach Start der App die Möglichkeit Fragen per Spracheingabe an die OpenAi zu senden. den für die Anfrage
benötigten API-Key kann der Nutzer in den root_preferences, über das Menü mit dem Tab Einstellungen, speichern.
Nach absenden der Frage wird sowohl die Frage als auch die Antwort von ChatGPT ohne weitere Kennzeichnung mit Abstand in der Textview ausgegeben.
Außerdem wir die Antwort von ChatGpt noch laut vorgelesen. Der Chat wird mit allen Messages persistent gespeichert.

### Erweiterung:
Als Erweiterung habe ich mich dazu entschieden, zum einen eine Scrollable View in das Fragment einzubauen, damit 
der User bei längeren Chatverläufen nach oben und unten scrollen kann um sich diesen komplett anschauen zu können.
Bisher gab es nämlich bei längeren Antworten auch das Problem, dass man die Antwort nur bis zum Ende der Textview lesen konnte,
das Antwortende jedoch noch nicht errreicht war und somit auch nicht lesbar war, die eventuell darauf folgende Frage und Antwort waren dann sogar gar nicht mehr zu sehen.
Außerdem wollte ich noch vor die jeweilige Chatnachricht den Namen des entsprechenden Users setzen, damit man im Nachhinein
noch erkennen kann wer welche Nachricht geschrieben hat. Es war bisher vor allem bei Antworten mit Absätzen oft nicht
erkentlich wo die Frage und wo die Antwort anfingen oder aufhörten. Um das Ganze noch mehr zu verdeutlichen sollten die Namen (User, und ChatGPT)
in verschiedenen Farben gekennzeichnet werden.
Der User sollte außerdem noch die Möglichkeit bekommen einen spezifischen Usernamen für die Anzeige in der Textview und der Speicherung in der Message einzugeben.
Zuletzt wollte ich dem User noch die Eingabe seiner Frage in Textform ermöglichen, sodass sowohl die schon vorhandene Spracheingabe
als auch die Texteingabe für Fragen an die OpenAi genutzt werden können.

## Anforderungen
Die Scrollable View soll das fehlerfreie scrollen innerhalb der Chat Anzeige ermöglichen.
Der User kann seinen Namen jederzeit ändern und der Name bleibt auch über einen Neustart der App hinweg erhalten.
Der Username soll in dem Einstellungs-Tab festgelegt werden können.
Der Username soll in einem dunklen grün und der Name ChatGPT in einem dunklen Rot ausgegeben werden.
Der entsprechende Name wird vor jede einzelne Chatnachricht zur Kennzeichnung geschrieben.
Der Username soll außerdem zusammen mit den bereits vorhanden Message Komponenten innerhalb des jeweiligen Message Objektes gespeichert werden.
Die Texteingabe soll mit Enter oder Bestätigen die Frage an die API senden.
Wenn sich Text innerhalb des Eingabefeldes befindet soll sich der Spracheingabe-Button wie ein Bestätigen für die Texteingabe verhalten.
Die Erweiterungen sollen bei vorhergesehener Benutzung keine Fehler produzieren können.


