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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends AbstractFragment {

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance(String[][] theData, int theCount, String thePast) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", theData);
        bundle.putInt("counter", theCount);
        bundle.putString("past", thePast);
        System.out.println("--------------------------- From F1  newInstance  " + theCount);
        Fragment1 fragment = new Fragment1();
        fragment.setArguments(bundle);

        return fragment;
    }
}
