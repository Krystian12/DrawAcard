package krystian.drawacard;

import org.junit.Test;

import java.util.ArrayList;

import krystian.drawacard.cardslayout.CardsLayout;
import krystian.drawacard.http.data.Card;

import static org.junit.Assert.assertEquals;


public class CardsLayoutUnitTest {

    private static final String SUIT_HEARTS = "HEARTS";
    private static final String SUIT_DIAMONDS = "DIAMONDS";
    private static final String SUIT_CLUBS = "CLUBS";
    private static final String SUIT_SPADES = "SPADES";


    private static final String VALUE_ACE = "ACE";
    private static final String VALUE_2 = "2";
    private static final String VALUE_3 = "3";
    private static final String VALUE_4 = "4";
    private static final String VALUE_5 = "5";
    private static final String VALUE_6 = "6";
    private static final String VALUE_7 = "7";
    private static final String VALUE_8 = "8";
    private static final String VALUE_9 = "9";
    private static final String VALUE_10 = "10";
    private static final String VALUE_JACK = "JACK";
    private static final String VALUE_QUEEN = "QUEEN";
    private static final String VALUE_KING = "KING";



    @Test
    public void checkFlushHearts()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_HEARTS, VALUE_ACE));
        cards.add(newCard(SUIT_HEARTS, VALUE_2));
        cards.add(newCard(SUIT_HEARTS, VALUE_5));
        cards.add(newCard(SUIT_HEARTS, VALUE_6));
        cards.add(newCard(SUIT_HEARTS, VALUE_9));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FLUSH), true);
    }

    @Test
    public void checkFlushClubs()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_CLUBS, VALUE_KING));
        cards.add(newCard(SUIT_DIAMONDS, VALUE_10));
        cards.add(newCard(SUIT_HEARTS, VALUE_3));
        cards.add(newCard(SUIT_CLUBS, VALUE_2));
        cards.add(newCard(SUIT_CLUBS, VALUE_5));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FLUSH), true);
    }

    @Test
    public void checkFlushSpades()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_SPADES, VALUE_ACE));
        cards.add(newCard(SUIT_DIAMONDS, VALUE_10));
        cards.add(newCard(SUIT_SPADES, VALUE_3));
        cards.add(newCard(SUIT_SPADES, VALUE_6));
        cards.add(newCard(SUIT_SPADES, VALUE_5));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FLUSH), true);
    }

    @Test
    public void checkFlushDiamonds()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_ACE));
        cards.add(newCard(SUIT_DIAMONDS, VALUE_2));
        cards.add(newCard(SUIT_HEARTS, VALUE_3));
        cards.add(newCard(SUIT_DIAMONDS, VALUE_4));
        cards.add(newCard(SUIT_HEARTS, VALUE_5));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FLUSH), true);
    }

    @Test
    public void checkUnknown()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_ACE));
        cards.add(newCard(SUIT_DIAMONDS, VALUE_2));
        cards.add(newCard(SUIT_HEARTS, VALUE_6));
        cards.add(newCard(SUIT_HEARTS, VALUE_4));
        cards.add(newCard(SUIT_CLUBS, VALUE_5));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FIGURES), false);
        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.STRAIGHT), false);
        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.THREE), false);
        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FLUSH), false);
    }

    @Test
    public void checkIncreasingStraightWithAce()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_KING));
        cards.add(newCard(SUIT_SPADES, VALUE_ACE));
        cards.add(newCard(SUIT_HEARTS, VALUE_2));
        cards.add(newCard(SUIT_HEARTS, VALUE_3));
        cards.add(newCard(SUIT_CLUBS, VALUE_5));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.STRAIGHT), true);
    }

    @Test
    public void checkIncreasingStraight()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_KING));
        cards.add(newCard(SUIT_SPADES, VALUE_ACE));
        cards.add(newCard(SUIT_HEARTS, VALUE_JACK));
        cards.add(newCard(SUIT_HEARTS, VALUE_QUEEN));
        cards.add(newCard(SUIT_CLUBS, VALUE_KING));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.STRAIGHT), true);
    }

    @Test
    public void checkDecreasingStraightWithAce()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_ACE));
        cards.add(newCard(SUIT_SPADES, VALUE_3));
        cards.add(newCard(SUIT_HEARTS, VALUE_2));
        cards.add(newCard(SUIT_HEARTS, VALUE_ACE));
        cards.add(newCard(SUIT_CLUBS, VALUE_JACK));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.STRAIGHT), true);
    }

    @Test
    public void checkDecreasingStraight()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_KING));
        cards.add(newCard(SUIT_SPADES, VALUE_QUEEN));
        cards.add(newCard(SUIT_HEARTS, VALUE_6));
        cards.add(newCard(SUIT_HEARTS, VALUE_5));
        cards.add(newCard(SUIT_CLUBS, VALUE_4));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.STRAIGHT), true);
    }

    @Test
    public void checkFigures()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_KING));
        cards.add(newCard(SUIT_SPADES, VALUE_KING));
        cards.add(newCard(SUIT_HEARTS, VALUE_JACK));
        cards.add(newCard(SUIT_HEARTS, VALUE_KING));
        cards.add(newCard(SUIT_CLUBS, VALUE_2));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FIGURES), true);
    }

    @Test
    public void checkFiguresWithAce()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_ACE));
        cards.add(newCard(SUIT_SPADES, VALUE_3));
        cards.add(newCard(SUIT_HEARTS, VALUE_JACK));
        cards.add(newCard(SUIT_HEARTS, VALUE_KING));
        cards.add(newCard(SUIT_CLUBS, VALUE_QUEEN));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.FIGURES), true);
    }

    @Test
    public void checkThree()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_2));
        cards.add(newCard(SUIT_SPADES, VALUE_3));
        cards.add(newCard(SUIT_HEARTS, VALUE_2));
        cards.add(newCard(SUIT_HEARTS, VALUE_KING));
        cards.add(newCard(SUIT_CLUBS, VALUE_2));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.THREE), true);
    }

    @Test
    public void checkThreeWithAce()  {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(newCard(SUIT_DIAMONDS, VALUE_ACE));
        cards.add(newCard(SUIT_SPADES, VALUE_3));
        cards.add(newCard(SUIT_HEARTS, VALUE_5));
        cards.add(newCard(SUIT_HEARTS, VALUE_5));
        cards.add(newCard(SUIT_CLUBS, VALUE_5));

        CardsLayout cardsLayout = new CardsLayout(cards);

        assertEquals(cardsLayout.checkMatchingLayouts().containsKey(CardsLayout.Type.THREE), true);
    }

    private Card newCard(String suit, String value){
        Card card = new Card();
        card.setSuit(suit);
        card.setValue(value);
        if(VALUE_10.equals(value)) {
            card.setCode("0" + suit.substring(0, 1));
        } else {
            card.setCode(value.substring(0, 1) + suit.substring(0, 1));
        }
        return card;
    }
}
