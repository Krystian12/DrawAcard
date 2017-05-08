package krystian.drawacard.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.Primitives;

import krystian.drawacard.Parameters;
import krystian.drawacard.http.data.Deck;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeckOfCardProvider {

    private static final String TAG  = "DeckOfCardProvider";

    public static final int UNKNOWN_ERROR = -1;

    private Retrofit retrofit;
    private DeckOfCardListener deckOfCardListener;

    public DeckOfCardProvider(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Parameters.URL_APPI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public void registerDeckOfCardListener(DeckOfCardListener deckOfCardListener){
        this.deckOfCardListener = deckOfCardListener;
    }

    public void unregisterDeckOfCardListener(){
        this.deckOfCardListener = null;
    }

    public void createShuffleAndDrawCards(int deckCount, int cardCount){
        DeckOfCardsAction deckOfCardsAction = retrofit.create(DeckOfCardsAction.class);
        Call<Deck> call = deckOfCardsAction.shuffleAndDrawCards(deckCount, cardCount);
        call.enqueue(new Callback<Deck>() {
            @Override
            public void onResponse(Call<Deck> call, Response<Deck> response) {
                if (response.isSuccessful()) {
                    Deck deck = response.body();
                    Log.d(TAG, "response: " + deck.toString());
                    if(deck.isSuccessful()){
                        if(deckOfCardListener != null)deckOfCardListener.onCreateShuffleAndDrawCards(deck.getDeckId(), deck.getCards(), deck.getRemaining());
                    } else {
                        if(deckOfCardListener != null)deckOfCardListener.onError(deck.getError());
                    }
                } else {
                    Log.d(TAG, "response: " + response.errorBody());
                    if(deckOfCardListener != null)deckOfCardListener.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Deck> call, Throwable t) {
                t.printStackTrace();
                if(deckOfCardListener != null)deckOfCardListener.onError(UNKNOWN_ERROR);
            }
        });
    }

    public void drawCard(String deckId, int cardCount){
        DeckOfCardsAction deckOfCardsAction = retrofit.create(DeckOfCardsAction.class);
        Call<Deck> call = deckOfCardsAction.drawCard(deckId, cardCount);
        call.enqueue(new Callback<Deck>() {
            @Override
            public void onResponse(Call<Deck> call, Response<Deck> response) {
                if (response.isSuccessful()) {
                    Deck deck = response.body();
                    Log.d(TAG, "response: " + deck.toString());
                    if(deck.isSuccessful()){
                        if(deckOfCardListener != null)deckOfCardListener.onDrawCards(deck.getDeckId(), deck.getCards(), deck.getRemaining());
                    } else {
                        if(deckOfCardListener != null)deckOfCardListener.onError(deck.getError());
                    }
                } else {
                    Log.d(TAG, "response: " + response.errorBody());
                    if(deckOfCardListener != null)deckOfCardListener.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Deck> call, Throwable t) {
                t.printStackTrace();
                if(deckOfCardListener != null)deckOfCardListener.onError(UNKNOWN_ERROR);
            }
        });
    }

    public void reshuffleCard(String deckId){
        DeckOfCardsAction deckOfCardsAction = retrofit.create(DeckOfCardsAction.class);
        Call<Deck> call = deckOfCardsAction.reshuffleCard(deckId);
        call.enqueue(new Callback<Deck>() {
            @Override
            public void onResponse(Call<Deck> call, Response<Deck> response) {
                if (response.isSuccessful()) {
                    Deck deck = response.body();
                    Log.d(TAG, "response: " + deck.toString());
                    if(deck.isSuccessful()){
                        if(deckOfCardListener != null)deckOfCardListener.onReshuffleCards(deck.getDeckId(), deck.getCards(), deck.getRemaining());
                    } else {
                        if(deckOfCardListener != null)deckOfCardListener.onError(deck.getError());
                    }
                } else {
                    Log.d(TAG, "response: " + response.errorBody());
                    if(deckOfCardListener != null)deckOfCardListener.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Deck> call, Throwable t) {
                t.printStackTrace();
                if(deckOfCardListener != null)deckOfCardListener.onError(UNKNOWN_ERROR);
            }
        });
    }

}
