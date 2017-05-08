package krystian.drawacard.cardslayout;

import android.content.Context;

import krystian.drawacard.http.data.Card;

public interface CardsLayoutVerification {

    void addCard(Card card);
    boolean isCardsLayout();
    CardsLayout.Type getType();
    String getName(Context context);
}
