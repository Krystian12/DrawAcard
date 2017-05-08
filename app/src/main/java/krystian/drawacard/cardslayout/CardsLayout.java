package krystian.drawacard.cardslayout;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import krystian.drawacard.http.data.Card;

/**
 * Class responsible for check cards layout. You can verify that you got flush, straight, figures, three
 */

public class CardsLayout {

    public enum Type { FLUSH, STRAIGHT, FIGURES, THREE};

    private ArrayList<Card> cards;
    private ArrayList<CardsLayoutVerification> cardsLayoutVerifications;
    private HashMap<Type, CardsLayoutVerification> matchingLayouts = null;

    public CardsLayout(@NonNull ArrayList<Card> cards){
        this.cards = cards;
        cardsLayoutVerifications = new ArrayList<>();
        cardsLayoutVerifications.add(new FlushVerification());
        cardsLayoutVerifications.add(new StraightVerification());
        cardsLayoutVerifications.add(new FiguresVerification());
        cardsLayoutVerifications.add(new ThreeVerification());
    }

    /**
     * Method can work long . Do not call in main thread.
     */
    public HashMap<Type, CardsLayoutVerification> checkMatchingLayouts() {
        if(matchingLayouts != null){
            return matchingLayouts;
        }

        matchingLayouts = new HashMap<>();

        Iterator<CardsLayoutVerification> verificationIterator = cardsLayoutVerifications.iterator();
        while (verificationIterator.hasNext()) {
            CardsLayoutVerification cardsLayoutVerification = verificationIterator.next();
            Iterator<Card> cardsIterator = cards.iterator();
            while (cardsIterator.hasNext()) {
                Card card = cardsIterator.next();
                cardsLayoutVerification.addCard(card);
            }
            if(cardsLayoutVerification.isCardsLayout()) {
                matchingLayouts.put(cardsLayoutVerification.getType(), cardsLayoutVerification);
            }
        }

        return matchingLayouts;
    }
}
