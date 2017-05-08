package krystian.drawacard.http;

import krystian.drawacard.http.data.Deck;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface DeckOfCardsAction {

    @GET("/api/deck/new/draw")
    Call<Deck> shuffleAndDrawCards(@Query("count") int cardCount, @Query("deck_count") int deckCount);

    @GET("/api/deck/{deck_id}/draw/")
    Call<Deck> drawCard(@Path("deck_id") String deckId, @Query("count") int cardCount);

    @GET("/api/deck/{deck_id}/shuffle/")
    Call<Deck> reshuffleCard(@Path("deck_id") String deckId);

}
