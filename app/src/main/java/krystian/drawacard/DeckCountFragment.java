package krystian.drawacard;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A fragment that displays choice deck.
 */

public class DeckCountFragment extends Fragment {

    private Spinner deckCountSpinner;
    private TextView buttonDraw;

    private OnChooseDeckCountListener onChooseDeckCountListener;

    public static DeckCountFragment newInstance() {
        return new DeckCountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deck_count_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deckCountSpinner = (Spinner) view.findViewById(R.id.deck_count_spinner);
        buttonDraw = (TextView) view.findViewById(R.id.button_draw);

        buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseDeckCountListener.OnChooseDeckCount(deckCountSpinner.getSelectedItemPosition() + 1);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onChooseDeckCountListener = (OnChooseDeckCountListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnChooseDrawCountListener");
        }
    }

    public interface OnChooseDeckCountListener {
        void OnChooseDeckCount(int drawCount);
    }
}
