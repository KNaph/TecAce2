package kylephan.com.tecace2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {

    private int count;

    private String fragName = new String();
    private String fragPath = new String();
    private String fragLayout = new String();
    private String fragTransition = new String();

    private String[][] data = new String[3][4];

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance(String[][] theData, int theCount) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", theData);
        bundle.putInt("counter", theCount);

        Fragment3 fragment = new Fragment3();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (String[][]) getArguments().getSerializable("data");
        if (getArguments() == null) {
            Log.w("getArgs", "true");
        } else { Log.v("getArgs", "False"); }
        count = getArguments().getInt("counter");
        fragName = data[count][0];
        fragPath = data[count][1];
        fragLayout = data[count][2];
        fragTransition = data[count][3];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflatedView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        TextView t = (TextView) inflatedView.findViewById(R.id.fragment_name3);
        t.setText(fragName);
        Button next = (Button) inflatedView.findViewById(R.id.fragment_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Next button", "It's clicked");
                Log.w("The Count", "Count:" + count);
                Log.w("Frag Name", fragName);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment1 secFragment = Fragment1.newInstance(data, count++);
                ft.replace(R.id.fragment_container, secFragment, "Fragment 1");
                ft.commit();

            }
        });
        return inflatedView;
    }

}
