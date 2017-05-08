package krystian.drawacard;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import krystian.drawacard.cardslayout.CardsLayoutVerification;
import krystian.drawacard.http.data.Card;

/**
 * A fragment that displays draw a card.
 * Shows card layout
 * Yuo can draw again or start over
 */

public class ReshuffleCardsFragment extends Fragment {

    private static final String KEY_CARDS_LIST = "KEY_CARDS_LIST";

    public static ReshuffleCardsFragment newInstance(ArrayList<Card> cardArrayList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_CARDS_LIST, cardArrayList);

        ReshuffleCardsFragment fragment = new ReshuffleCardsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GridView cardListView;
    private OnReshuffleCardsListener onReshuffleCardsListener;

    private TextView textCardLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reshuffle_cards, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardListView = (GridView)view.findViewById(R.id.cards_list);
        textCardLayout = (TextView) view.findViewById(R.id.text_card_layout);

        ArrayList<Card> cardArrayList = getArguments().getParcelableArrayList(KEY_CARDS_LIST);
        if(cardArrayList == null){
            throw new IllegalArgumentException("cardList is null");
        }

        if(cardArrayList.isEmpty()){
            throw new IllegalArgumentException("cardList is empty");
        }

        setCardArrayListAndCheckCardsLayout(cardArrayList);

        view.findViewById(R.id.button_draw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReshuffleCardsListener.onDraw();
            }
        });

        view.findViewById(R.id.button_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReshuffleCardsListener.onNew();
            }
        });
    }

    public void setCardArrayListAndCheckCardsLayout(ArrayList<Card> cardArrayList){
        cardListView.setAdapter(new CardAdapter(getActivity(), cardArrayList));
        onReshuffleCardsListener.checkCardsLayout(cardArrayList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onReshuffleCardsListener = (OnReshuffleCardsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnChooseDrawCountListener");
        }
    }

    public interface OnReshuffleCardsListener {
        void onDraw();
        void onNew();
        void checkCardsLayout(ArrayList<Card> cardArrayList);
    }

    public void setTextCardLayout(Iterator<CardsLayoutVerification> matchingLayoutsIterator){
        if(matchingLayoutsIterator.hasNext()){
            String text = matchingLayoutsIterator.next().getName(getActivity());
            while (matchingLayoutsIterator.hasNext()){
                text = text + ", " + matchingLayoutsIterator.next().getName(getActivity());
            }
            textCardLayout.setText(text);
        } else {
            textCardLayout.setText(" ");
        }
    }

}
