# AndroidSampleApp - Dragon Ball

Applicazione Android realizzata come esercitazione, basata sulle API di Dragon Ball.

## Funzionalità principali

- Visualizzazione della lista dei personaggi
- Visualizzazione del dettaglio di un personaggio
- Caricamento dati tramite API Dragon Ball
- Ordinamento alfabetico dei personaggi

## Modifica effettuata

In questa versione è stata aggiunta una miglioria alla schermata della lista personaggi.

Sono stati inseriti due nuovi pulsanti:

- **A-Z**: ordina i personaggi in ordine alfabetico crescente
- **Z-A**: ordina i personaggi in ordine alfabetico decrescente

## File modificati

Per implementare questa funzionalità sono stati modificati i seguenti file:

- `app/src/main/res/layout/fragment_character_list.xml`
- `app/src/main/java/it/zakantonio/androidsampleapp/CharacterListFragment.kt`
- `app/src/main/java/it/zakantonio/androidsampleapp/MainViewModel.kt`

## Descrizione tecnica

- Nel file `fragment_character_list.xml` sono stati aggiunti i due pulsanti per l'ordinamento.
- Nel file `CharacterListFragment.kt` sono stati gestiti i click dei pulsanti.
- Nel file `MainViewModel.kt` è stata aggiunta la logica di ordinamento alfabetico della lista dei personaggi.

## Branch di lavoro

La modifica è stata sviluppata nel branch:

`esercizi/dragonball/Gianfranco-Cito`
