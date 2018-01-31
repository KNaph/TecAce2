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

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private int count = 0;

    private String fragName = new String();
    private String fragPath = new String();
    private String fragLayout = new String();
    private String fragTransition = new String();

    private String[][] data = new String[3][4];

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance(String[][] theData, int theCount) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", theData);
        bundle.putInt("counter", theCount);

        Fragment1 fragment = new Fragment1();
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
        TextView t = (TextView) inflatedView.findViewById(R.id.fragment_name);
        t.setText(fragName);
        Button next = (Button) inflatedView.findViewById(R.id.fragment_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Next button", "It's clicked");
                Log.w("The Count", "Count:" + count);
                Log.w("Frag Name", fragName);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putSerializable("data", data);
                args.putInt("counter", count++);
                Fragment2 secFragment = new Fragment2();
                secFragment.setArguments(args);

                ft.add(R.id.fragment_container, secFragment).commit();
            }
        });
        return inflatedView;
    }

}
