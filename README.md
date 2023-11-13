# Dokumentation

## Einleitung
Die Aufgabe war es den in den Vorlesungen zusammen erstellten App-Stand mit einer eigenen Erweiterung zu versehen.

Stand nach Vorlesung:
Der User hat nach Start der App die Möglichkeit Fragen per Spracheingabe an die OpenAi zu senden. den für die Anfrage
benötigten API-Key kann der Nutzer in den root_preferences, über das Menü mit dem Tab Einstellungen, speichern.
Nach absenden der Frage wird sowohl die Frage als auch die Antwort von ChatGPT ohne weitere Kennzeichnung mit Abstand in der Textview ausgegeben.
Außerdem wir die Antwort von ChatGpt noch laut vorgelesen. Der Chat wird samt Messages persistent gespeichert.

Erweiterung:
Als Erweiterung habe ich mich dazu entschieden, zum einen eine Scrollable View in das Fragment einzubauen, damit 
der User bei längeren Chatverläufen nach oben und unten scrollen kann um sich diesen komplett anschauen zu können.
Außerdem wollte ich noch vor die jeweilige Chatnachrich den Namen des entsprechenden Users setzen, damit man im Nachhinein
noch erkennen kann wer welche Nachricht geschrieben hat. Um das ganze noch mehr zu verdeutlichen sollte die Namen (User, und ChatGPT)
in verschiedenen Farben gekennzeichnet werden.
Der User sollte außerdem noch die Möglichkeit bekommen einen spezifischen Usernamen für die Anzeige und Speicherung einzugeben.
Zuletzt wollte ich dem User noch die Eingabe seiner Frage in Textform ermöglichen, sodass sowohl die schon vorhandene Spracheingabe
als auch die Texteingabe für Fragen an die OpenAi genutzt werden können. 
