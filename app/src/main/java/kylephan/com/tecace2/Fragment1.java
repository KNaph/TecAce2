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
 *
 * Unique child class with newInstance method to be called via Java reflect.
 */
public class Fragment1 extends AbstractFragment {

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * This method is necessary to dynamically create new fragments using Java Reflect.
     *
     * Takes parameters and bundles them to set the fragments arguments.
     *
     * @param theData 2D String array that contains all the JSON data.
     * @param theCount int representing which fragment this is.
     * @param thePast String containing all the past visited fragments.
     * @return returns a Fragment1 object.
     */
    public static Fragment1 newInstance(String[][] theData, int theCount, String thePast) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", theData);
        bundle.putInt("counter", theCount);
        bundle.putString("past", thePast);
        Fragment1 fragment = new Fragment1();
        fragment.setArguments(bundle);

        return fragment;
    }
}
