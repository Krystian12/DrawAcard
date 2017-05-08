package krystian.drawacard.cardslayout;

import android.content.Context;

import java.util.HashSet;

import krystian.drawacard.R;
import krystian.drawacard.http.data.Card;

class FiguresVerification implements CardsLayoutVerification {

    private static final HashSet<String> figures = new HashSet<>();
    static {
        figures.add("JACK");
        figures.add("QUEEN");
        figures.add("KING");
    }

    private int figuresCount = 0;
    private boolean isFigures = false;

    @Override
    public void addCard(Card card) {
        if(isFigures) return;

        if(figures.contains(card.getValue())){
            figuresCount ++;
        }

        if(figuresCount == 3 ){
            isFigures = true;
        }
    }

    @Override
    public boolean isCardsLayout() {
        return isFigures;
    }

    @Override
    public CardsLayout.Type getType() {
        return CardsLayout.Type.FIGURES;
    }

    @Override
    public String getName(Context context) {
        return context.getString(R.string.cards_layout_figures);
    }

}
