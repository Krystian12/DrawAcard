package krystian.drawacard.cardslayout;

import android.content.Context;

import java.util.HashMap;

import krystian.drawacard.R;
import krystian.drawacard.http.data.Card;

class ThreeVerification implements CardsLayoutVerification {

    private HashMap<String, Integer> valuesCount = new HashMap<>();

    private boolean isThree = false;

    @Override
    public void addCard(Card card) {
        if(isThree) return;

        Integer valueCount = valuesCount.get(card.getValue());
        if(valueCount == null){
            valuesCount.put(card.getValue(), 1);
        } else {
            valueCount ++;
            valuesCount.put(card.getValue(), valueCount);
            if(valueCount == 3){
                isThree = true;
            }
        }
    }

    @Override
    public boolean isCardsLayout() {
        return isThree;
    }

    @Override
    public CardsLayout.Type getType() {
        return CardsLayout.Type.THREE;
    }

    @Override
    public String getName(Context context) {
        return context.getString(R.string.cards_layout_three);
    }
}
