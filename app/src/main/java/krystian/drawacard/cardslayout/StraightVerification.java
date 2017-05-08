package krystian.drawacard.cardslayout;

import android.content.Context;

import java.util.HashMap;

import krystian.drawacard.R;
import krystian.drawacard.http.data.Card;

class StraightVerification implements CardsLayoutVerification{

    private static final HashMap<String, Integer> values = new HashMap<>();
    static {
        values.put("ACE", 1);
        values.put("2", 2);
        values.put("3", 3);
        values.put("4", 4);
        values.put("5", 5);
        values.put("6", 6);
        values.put("7", 7);
        values.put("8", 8);
        values.put("9", 9);
        values.put("10", 10);
        values.put("JACK", 11);
        values.put("QUEEN", 12);
        values.put("KING", 13);
    }


    private int lastValue = -10;
    private int increasingPoint = 0;
    private int decreasingPoint = 0;

    private boolean isStraight = false;

    @Override
    public void addCard(Card card) {
        if(isStraight) return;

        String valueString = card.getValue();
        if(valueString != null){
            int valueInt = values.get(valueString);

            if(valueInt == lastValue + 1){
                increasingPoint ++;
            } else {
                increasingPoint = 0;
            }

            if(increasingPoint == 2){
                isStraight = true;
                return;
            }

            if(valueInt == lastValue - 1){
                decreasingPoint ++;
            } else {
                decreasingPoint = 0;
            }

            if(decreasingPoint == 2){
                isStraight = true;
            }

            lastValue = valueInt;
        }
    }

    @Override
    public boolean isCardsLayout() {
        return isStraight;
    }

    @Override
    public CardsLayout.Type getType() {
        return CardsLayout.Type.STRAIGHT;
    }

    @Override
    public String getName(Context context) {
        return context.getString(R.string.cards_layout_straight);
    }
}
