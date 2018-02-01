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
public class Fragment3 extends AbstractFragment {

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance(String[][] theData, int theCount, String thePast) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", theData);
        bundle.putInt("counter", theCount);
        bundle.putString("past", thePast);
        System.out.println("--------------------------- From F3  " + theCount);
        Fragment3 fragment = new Fragment3();
        fragment.setArguments(bundle);

        return fragment;
    }
}
