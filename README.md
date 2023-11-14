# Dokumentation

## Einleitung
Die Aufgabe war es den in den Vorlesungen zusammen erstellten App-Stand mit einer eigenen Erweiterung zu versehen.

### Stand nach Vorlesung:
Der User hat nach Start der App die Möglichkeit Fragen per Spracheingabe an die OpenAi zu senden. den für die Anfrage
benötigten API-Key kann der Nutzer in den root_preferences, über das Menü mit dem Tab Einstellungen, persistent speichern.
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

## Umsetzung

Als erstes begann ich damit die Anzeige des Chats scrollable zu machen. Damit man in der View des Fragments scrollen kann benötigt man das Element ScrollView,
dieses ließ sich in der fragment_main.xml an der Stelle, wo sich vorher die einfache Textview befand, einfügen und konfigurieren. Außerdem fügte ich innerhalb
der Scrollview noch eine TextView ein, damit ich dort wie im Vorhinein den Chat ausgeben kann. Dadurch das sich die TextView nun jedoch in einer ScrollView befindet,
hat der User nun die Möglichkeit nach oben und unten zu scrollen. Wie hier an der rechten Seite des Bildes zusehen:

<img src="https://github.com/TimoBehr/app_entwicklung/assets/147406630/bae23490-1582-449b-8811-5a643de4a246" width="250"/>

Als nächstes fügte ich vor jeder Ausgabe einer Frage oder Antwort in der View zunächst festcodierte Namen für den User und ChatGPT ein. Für eine eindeutige
Unterscheidung änderte ich dann die Farbe des Users auf ein dunkles Grün und die Farbe von ChatGPT auf ein dunkles Rot. Für diese Funktion schrieb ich eine eigene Methode
"colorChange", welche einen String (text) und einen String (Farbcode) übergeben bekommt. Mithilfe dieser wird ein sogenannter Spannable angelegt, welche die gesamte Spanne
des übergebenen textes umfasst und auf dieser Spanne dann die bestimmte Farbe mithilfe des übergebenen Farbcodes aufsetzt. Dieser Spannable wird dann returned und kann
beispielsweise in eine Textview eingefügt werden. Die Methode hab ich immer dann benutzt, wenn ich den entsprechenden Namen vor die Frage oder Antwort gesetzt habe.
Das ganze sieht dann so aus:

<img src="https://github.com/TimoBehr/app_entwicklung/assets/147406630/a46aa2a2-9b57-420d-9aca-bdc0c621fcfc" width="250"/>

## Probleme
Während der Zeit des programmierens traten drei "größere" Probleme auf, welche ich beheben durfte.

Das erste Problem trat auf nachdem ich die neue Scroll View und innerhalb dieser die Textview hinzugefügt hatte.
Genau genommen wurde der Fehler durch den Aufruf der TextView mithilfe der ID ausgelöst und zwar immer dann, wenn
ich die Methode welche mir die Textview zurücklieferte innerhalb des Background Threads benutzte um dort zum Beispiel die
Antwort von ChatGPT in die Textview einzufügen. Der Fehler besagte, dass nur der OriginalThread, welcher die View erzeugt hatte diese auch verändern kann.
Nachdem ich ein wenig zu dem Problem recherchiert hatte, konnte ich das Problem lösen, indem ich die Problemverusachenden Methodenaufrufe nun mithilfe von requireActivity
und dem Runnable runOnUiThread auf dem Originalen UiThread laufen lasse.

Das zweite Problem war, dass beim Rotieren des Bildschirmes zwar die View wieder mit den vorherigen Chat Messages wiederhergestellt wurde, nun jedoch die von mir hinzugefügten
Usernamen und ihre Farben verschwunden waren. Dies ließ sich lösen, indem ich in dem von Ihrem Branch stammenden updateTextView Methode entsprechend anpasste.
Da ich den Usernamen zusammen mit der ensprechenden Message im Message Objekt als Erweiterung gespeichert hatte, konnte ich vor jedem hinzufügen einer Message
in die Textview einfach den ensprechenden Usernamen vorweg anhängen. Wichtig war noch die Überprüfung, ob der Username ChatGPT lautete, dann wurde der Name dunkle Rot eingefärbt,
war das nicht der Fall wurde der Name dunkel Grün eingefärbt, da es sich dann um den spezifisch festgelegten Usernamen handelte.

Zuletzt hatte ich persönlich noch ein Problem damit, dass wenn der Nutzer einmal in das Texteingabefeld geklickt hatte dieses fokussiert blieb, was dazu führte, dass
sich die Samsung Bildschirm Tastatur auch dann wieder öffnete, wenn der User irgendwann danach die Spracheingabe nutzte und diese abschließ.
Beheben konnte ich dieses "Problem", indem ich jedesmal, wenn der onClickListener des Spracheingabe Buttons aufgerufen wurde (Der Nutzer also auf den Knopf für die
Spracheingabe drückte), mir das Eingabefeld mit der ID holte und bei diesem den Fokus zurücksetzte.

## Fazit

