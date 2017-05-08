package krystian.drawacard.http;

import java.util.ArrayList;

import krystian.drawacard.http.data.Card;

public interface DeckOfCardListener {

    void onCreateShuffleAndDrawCards(String deckId, ArrayList<Card> cards, int remainingCards);
    void onDrawCards(String deckId, ArrayList<Card> cards, int remainingCards);
    void onReshuffleCards(String deckId, ArrayList<Card> cards, int remainingCards);
    void onError(String errorMessage);
    void onError(int errorCode);
}
