package krystian.drawacard.cardslayout;

import android.content.Context;

import java.util.HashMap;

import krystian.drawacard.R;
import krystian.drawacard.http.data.Card;

class FlushVerification implements CardsLayoutVerification {

    private static final String KEY_HEARTS = "HEARTS";
    private static final String KEY_DIAMONDS = "DIAMONDS";
    private static final String KEY_CLUBS = "CLUBS";
    private static final String KEY_SPADES = "SPADES";

    private HashMap<String, Integer> suitsCount;

    private boolean isFlush = false;

    FlushVerification() {
        suitsCount = new HashMap<>();
        suitsCount.put(KEY_HEARTS, 0);
        suitsCount.put(KEY_DIAMONDS, 0);
        suitsCount.put(KEY_CLUBS, 0);
        suitsCount.put(KEY_SPADES, 0);
    }

    @Override
    public void addCard(Card card) {
        if(isFlush) return;

        Integer suitCount = suitsCount.get(card.getSuit());
        if(suitCount != null){
            suitCount++;
            if(suitCount > 2){
                isFlush = true;
            }
            suitsCount.put(card.getSuit(), suitCount);
        }
    }

    @Override
    public boolean isCardsLayout() {
        return isFlush;
    }

    @Override
    public CardsLayout.Type getType() {
        return CardsLayout.Type.FLUSH;
    }

    @Override
    public String getName(Context context) {
        return context.getString(R.string.cards_layout_flush);
    }
}
