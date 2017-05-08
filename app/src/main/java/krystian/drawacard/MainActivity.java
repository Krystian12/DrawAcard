package krystian.drawacard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import krystian.drawacard.cardslayout.CardsLayout;
import krystian.drawacard.cardslayout.CardsLayoutVerification;
import krystian.drawacard.http.DeckOfCardListener;
import krystian.drawacard.http.DeckOfCardProvider;
import krystian.drawacard.http.data.Card;


public class MainActivity extends Activity implements DeckCountFragment.OnChooseDeckCountListener , ReshuffleCardsFragment.OnReshuffleCardsListener{

    private final static String KEY_DECK_ID = "KEY_DECK_ID";
    private final static String KEY_LAST_CARD_ARRAY_LIST = "KEY_LAST_CARD_ARRAY_LIST";
    private final static String KEY_REMAINING_CARDS = "KEY_REMAINING_CARDS";

    private String deckId = "";
    private ArrayList<Card> lastCardArrayList;
    private int remainingCards = 0 ;

    private ProgressDialog progressDialog;

    private DeckOfCardProvider deckOfCardProvider;
    private ReshuffleCardsFragment reshuffleCardsFragment;
    private CardsLayoutAsyncTask cardsLayoutAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        deckOfCardProvider = new DeckOfCardProvider();

        if(savedInstanceState != null) {
            deckId = savedInstanceState.getString(KEY_DECK_ID);
            lastCardArrayList = savedInstanceState.getParcelableArrayList(KEY_LAST_CARD_ARRAY_LIST);
            remainingCards = savedInstanceState.getInt(KEY_LAST_CARD_ARRAY_LIST, 0);
        }

        if(TextUtils.isEmpty(deckId) || lastCardArrayList == null){
            switchToDeckCountFragment();
        } else {
            switchToReshuffleCardsFragment(lastCardArrayList);
        }
    }

    private void switchToReshuffleCardsFragment(ArrayList<Card> cardArrayList){
        reshuffleCardsFragment = ReshuffleCardsFragment.newInstance(cardArrayList);
        switchFragment(reshuffleCardsFragment);
    }

    private void switchToDeckCountFragment(){
        switchFragment(DeckCountFragment.newInstance());
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void OnChooseDeckCount(int deckCount) {
        showProgressDialog();
        deckOfCardProvider.createShuffleAndDrawCards(Parameters.COUNT_CARDS, deckCount);
    }

    @Override
    protected void onStart() {
        deckOfCardProvider.registerDeckOfCardListener(getDeckOfCardListener());
        super.onStart();
    }

    @Override
    protected void onStop() {
        deckOfCardProvider.unregisterDeckOfCardListener();
        super.onStop();
    }

    private DeckOfCardListener getDeckOfCardListener(){
        return new DeckOfCardListener() {

            @Override
            public void onCreateShuffleAndDrawCards(String deckId, ArrayList<Card> cards, int remainingCards) {
                MainActivity.this.deckId = deckId;
                lastCardArrayList = cards;
                switchToReshuffleCardsFragment(cards);
                dismissProgressDialog();
            }

            @Override
            public void onDrawCards(String deckId, ArrayList<Card> cards, int remainingCards) {
                lastCardArrayList = cards;
                MainActivity.this.remainingCards = remainingCards;
                if(reshuffleCardsFragment != null){
                    if(reshuffleCardsFragment.isAdded()) reshuffleCardsFragment.setCardArrayListAndCheckCardsLayout(cards);
                }
                dismissProgressDialog();
            }

            @Override
            public void onReshuffleCards(String deckId, ArrayList<Card> cards, int remainingCards) {
                MainActivity.this.remainingCards = remainingCards;
                deckOfCardProvider.drawCard(deckId, Parameters.COUNT_CARDS);
            }

            @Override
            public void onError(String errorMessage) {
                dismissProgressDialog();
                showAlertDialog(errorMessage);
            }

            @Override
            public void onError(int errorCode) {
                dismissProgressDialog();
                showAlertDialog(getString(R.string.connection_error));
            }
        };
    }

    private void showProgressDialog(){
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.progress_dialog_waiting));
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void dismissProgressDialog(){
        if(progressDialog != null){
            if(progressDialog.isShowing()) progressDialog.dismiss();
        }
    }

    private void showAlertDialog(String messages){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setMessage(messages);
        alertDialogBuilder.setPositiveButton(R.string.dialog_error_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.create().show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_DECK_ID, deckId);
        outState.putParcelableArrayList(KEY_LAST_CARD_ARRAY_LIST, lastCardArrayList);
        outState.putInt(KEY_REMAINING_CARDS, remainingCards);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDraw() {
        showProgressDialog();
        if(remainingCards > Parameters.COUNT_CARDS) {
            deckOfCardProvider.drawCard(deckId, Parameters.COUNT_CARDS);
        } else {
            deckOfCardProvider.reshuffleCard(deckId);
        }
    }

    @Override
    public void onNew() {
        deckId = "";
        lastCardArrayList = null;
        remainingCards = 0 ;
        switchToDeckCountFragment();
    }

    @Override
    public void checkCardsLayout(ArrayList<Card> cardArrayList) {
        cardsLayoutAsyncTask = new CardsLayoutAsyncTask(cardArrayList);
        cardsLayoutAsyncTask.execute();
    }

    private class CardsLayoutAsyncTask extends AsyncTask<Void, Void, Iterator<CardsLayoutVerification>>{

        private ArrayList<Card> cardArrayList;

        public CardsLayoutAsyncTask(ArrayList<Card> cardArrayList){
            this.cardArrayList = cardArrayList;
        }

        @Override
        protected Iterator<CardsLayoutVerification> doInBackground(Void... params) {
            CardsLayout cardsLayout = new CardsLayout(cardArrayList);
            return cardsLayout.checkMatchingLayouts().values().iterator();
        }

        @Override
        protected void onPostExecute(Iterator<CardsLayoutVerification> matchingLayoutsIterator) {
            super.onPostExecute(matchingLayoutsIterator);

            if(reshuffleCardsFragment != null){
                if(reshuffleCardsFragment.isAdded()){
                    reshuffleCardsFragment.setTextCardLayout(matchingLayoutsIterator);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(cardsLayoutAsyncTask != null) cardsLayoutAsyncTask.cancel(true);
        super.onDestroy();
    }
}
