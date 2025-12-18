# Entwicklung automatisierter Metriken und Methoden zur Verbesserung von Texten in Leichter Sprache
T2000 Arbeit Jorden Malecha

## Compilieren
Für das Paket _latexmk_ und die Erzeugung eines Glossars muss ein Perl-Interpreter installiert sein. Linux- und Mac-User haben normalerweise diesen schon im System installiert. Windows-Nutzern ist ActivePerl zu empfehlen. Die Vorlage nutzt außerdem _biblatex_ mit dem Backend _biber_ für die Bibliographie.

### Über latexmk:
#### Bauen:
* mittels Skript:
```
.\compile.bat # Windows
./compile.sh  # Linux, Mac
```
* direkt (inside ./src/):
```
latexmk -pdf -file-line-error -interaction=nonstopmode -synctex=1 -output-format=pdf -output-directory=./out -aux-directory=./auxil -lualatex dokumentation.tex
```
* Aufräumen: `latexmk -c`


## Ordnerstruktur
* **ads/** - enthält die notwendigen Seiten, z.B. Abstract, Deckblatt etc., sowie einige Interna
	* **ads/glossary.tex** - Glossareinträge
	* **ads/acronyms.tex** - Einträge des Abkürzungsverzeichnisses
* **content/** - enthält pro Kapitel eine Datei (Schema: `<nn>kapitel.tex`)
* **images/** - enthält Bilder und Logos
	* **images/logo.png** - Logo der Firma auf Deckblatt.
* **einstellungen.tex** - hier werden z.B. die Pflichtangaben auf dem Deckblatt geändert
* **dokumentation.tex** - die Hauptdatei, die alles andere einbindet
* **bibliographie.bib** - Einträge der Bibliographie
* **latexmkrc** - die Regeln, nach denen latexmk das Dokument baut

