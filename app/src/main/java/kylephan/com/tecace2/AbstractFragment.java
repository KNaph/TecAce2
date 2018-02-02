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
 *
 * An abstract class to help drive the TecAce sample program.
 */
public class AbstractFragment extends Fragment {

    /** int representing the fragment order index */
    private int count;
    /** int representing the index of the next fragment */
    private int nextCount;

    /** int fields for storing animator resource IDs */
    private int entryAnim;
    private int exitAnim;
    private int entryAnim2;
    private int exitAnim2;

    /** String field storing name of fragment. e.g. Fragment 1, Fragment 2, Fragment 3 */
    private String fragName;
    /** String field storing the path for the fragment class. */
    private String fragPath;
    /** String field storing the name of the layout XML file. */
    private String fragLayout;
    /** String field storing the name of the entry animator XML file. */
    private String fragEntry;
    /** String field storing the name of the exit animator XML file. */
    private String fragExit;
    /** String field storing the name of the next fragments entry animator XML file. */
    private String nextFragEntry;
    /** String field storing the name of the next fragments exit animator XML file. */
    private String nextFragExit;
    /** String field storing the names of past fragments visited. */
    private String pastFrags;
    /** 2D String array storing the data JSON data. */
    private String[][] data = new String[3][6];



    public AbstractFragment() {
        // Required empty public constructor
    }

    /**
     * Overridden onCreate method that sets fields on fragment creation.
     * @param savedInstanceState Stores data that was sent from the caller.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Getting values from the bundle passed into child classes newInstance method */
        data = (String[][]) getArguments().getSerializable("data");
        count = getArguments().getInt("counter");
        pastFrags = getArguments().getString("past");
        /** Setting fields */
        fragName = data[count][0];
        fragPath = data[count][1];
        fragLayout = data[count][2];
        /** If count reaches 2, reset to 0 to avoid out of bounds error.
         *  e.g. count of 2 means we're on Fragment 3, set to 0 to return to Fragment 1.
         */
        if (count >= 2) {
            nextCount = 0;
        }  else {
            nextCount = count + 1;
        }
        fragEntry = data[count][3];
        fragExit = data[count][4];
        nextFragEntry = data[nextCount][3];
        nextFragExit = data[nextCount][4];
    }

    /**
     * Overriden onCreateView method continues to drive the program.
     *
     * On creation of view it sets the TextViews to relevant info. e.g. set name to Fragment 3,
     * and past fragments are Fragment 1 Fragment 2.
     *
     * Sets onClick of Next button to create the new fragment and continue the cycle.
     *
     * @param inflater A LayoutInflater object, that is used to get a View.
     * @param container A ViewGroup object that is also used to get a View.
     * @param savedInstanceState Stores data that was sent from the caller.
     * @return Returns the View object that was generated.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /** Get the layout resource XML ID to set change the layout. */
        int layoutId = getResources().getIdentifier(fragLayout, "layout", getActivity().getPackageName());

        View inflatedView = inflater.inflate(layoutId, container, false);

        /** Setting the TextViews to have the correct information in them.
         * t contains the fragment name.
         * t2 contains the past fragments visited.
         */
        TextView t = (TextView) inflatedView.findViewById(R.id.fragment_name);
        t.setText(fragName);
        TextView t2 = (TextView)  inflatedView.findViewById(R.id.fragment_list);
        t2.setText(pastFrags);

        /** Setting the Next button to create a new fragment once clicked. */
        Button next = (Button) inflatedView.findViewById(R.id.fragment_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** This try/catch block creates new fragments */
                Class<?> c = null;
                try {
                    /**
                     *  Getting the entry and exit animator resources for the current and next fragments.
                     * */
                    entryAnim = getResources().getIdentifier(fragEntry,"animator", getActivity().getPackageName());
                    exitAnim = getResources().getIdentifier(fragExit,"animator", getActivity().getPackageName());
                    entryAnim2 = getResources().getIdentifier(nextFragEntry,"animator", getActivity().getPackageName());
                    exitAnim2 = getResources().getIdentifier(nextFragExit,"animator", getActivity().getPackageName());

                    /**
                     * Creating new fragments with Java Reflect.
                     */
                    c = Class.forName(data[nextCount][1]);              /** Set c object to a Fragment1 object. */
                    Class[] argTypes = new Class[] { String[][].class, int.class, String.class}; /** Class array containing parameter types of Fragment1 newInstance method. */
                    Method main = c.getDeclaredMethod("newInstance", argTypes); /** Get the method newInstance from child fragments with specified parameters types. */
                    Object[] mainArgs = {data, nextCount, (pastFrags + " " + fragName)}; /** Object array containing parameters to be passed into newInstance method call. */
                    Fragment frag = (Fragment) main.invoke(c, (Object[])mainArgs); /** Invoke Fragment1 newInstance method using Java reflect */

                    /**
                     * Setting the newly created fragment as the active view.
                     */
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(entryAnim2, exitAnim, entryAnim, exitAnim2);
                    fragmentTransaction.replace(R.id.fragment_container, frag, fragName);
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
