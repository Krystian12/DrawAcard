package krystian.drawacard.http.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Deck {

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("cards")
    @Expose
    private ArrayList<Card> cards = null;

    @SerializedName("deck_id")
    @Expose
    private String deckId;

    @SerializedName("remaining")
    @Expose
    private Integer remaining;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean isSuccessful() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "error='" + error + '\'' +
                ", success=" + success +
                ", cards=" + cards +
                ", deckId='" + deckId + '\'' +
                ", remaining=" + remaining +
                '}';
    }
}
