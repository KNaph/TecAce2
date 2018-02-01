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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AbstractFragment extends Fragment {

    private int count;

    private int entryAnim;
    private int exitAnim;

    private String fragName;
    private String fragPath;
    private String fragLayout;
    private String fragEntry;
    private String fragExit;
    private String pastFrags;

    private String[][] data = new String[3][6];



    public AbstractFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = (String[][]) getArguments().getSerializable("data");
        count = getArguments().getInt("counter");
        pastFrags = getArguments().getString("past");
        fragName = data[count][0];
        fragPath = data[count][1];
        fragLayout = data[count][2];
        fragEntry = data[count][3];
        fragExit = data[count][4];

//        pastFrags = data[count][5];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int layoutId = getResources().getIdentifier(fragLayout, "layout", getActivity().getPackageName());
        View inflatedView = inflater.inflate(layoutId, container, false);
        TextView t = (TextView) inflatedView.findViewById(R.id.fragment_name);
        t.setText(fragName);
        TextView t2 = (TextView)  inflatedView.findViewById(R.id.fragment_list);
        t2.setText(pastFrags);
        Button next = (Button) inflatedView.findViewById(R.id.fragment_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> c = null;
                try {
                    entryAnim = getResources().getIdentifier(fragEntry,"anim", getActivity().getPackageName());
                    exitAnim = getResources().getIdentifier(fragExit,"anim", getActivity().getPackageName());
                    int nextCount;
                    if (count >= 2) {
                        nextCount = 0;
                    }  else {
                        nextCount = count + 1;
                    }
                    c = Class.forName(data[nextCount][1]);
                    Class[] argTypes = new Class[] { String[][].class, int.class, String.class};
                    Method main = c.getDeclaredMethod("newInstance", argTypes);

                    Object[] mainArgs = {data, nextCount, (pastFrags + " " + fragName)};
                    Fragment frag = (Fragment) main.invoke(c, (Object[])mainArgs);

                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(entryAnim, exitAnim, entryAnim, exitAnim);
                    fragmentTransaction.replace(R.id.fragment_container, frag);
                    fragmentTransaction.addToBackStack(data[nextCount][1]);
                    fragmentTransaction.commit();
                    // production code should handle these exceptions more gracefully
                } catch (ClassNotFoundException x) {
                    x.printStackTrace();
                } catch (NoSuchMethodException x) {
                    x.printStackTrace();
                } catch (IllegalAccessException x) {
                    x.printStackTrace();
                } catch (InvocationTargetException x) {
                    x.printStackTrace();
                }
            }
        });
        return inflatedView;
    }
}
