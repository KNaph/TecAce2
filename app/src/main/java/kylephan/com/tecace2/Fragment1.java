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
public class Fragment1 extends Fragment {

    private int count;

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
        System.out.println("--------------------------- From F1  newInstance  " + theCount);
        Fragment1 fragment = new Fragment1();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = (String[][]) getArguments().getSerializable("data");
        count = getArguments().getInt("counter");
        System.out.println("--------------------------- From F1 onCreate  " + getArguments().getInt("counter"));
        System.out.println("--------------------------- From F1  " + count);
        fragName = data[count][0];
        fragPath = data[count][1];
        fragLayout = data[count][2];
        fragTransition = data[count][3];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        TextView t = (TextView) inflatedView.findViewById(R.id.fragment_name);
        t.setText(fragName);
        TextView t2 = (TextView)  inflatedView.findViewById(R.id.fragment_list);
        t2.setText(getBackStack());
        Log.w("Fragment 1 name:", fragName);
        Button next = (Button) inflatedView.findViewById(R.id.fragment_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Fragment 1:", "Clicked!");
                Class<?> c = null;
                try {
                    int nextCount = count + 1;
                    c = Class.forName(data[nextCount][1]);
                    Class[] argTypes = new Class[] { String[][].class, int.class };
                    Method main = c.getDeclaredMethod("newInstance", argTypes);
                    Object[] mainArgs = {data, nextCount};
                    Fragment frag = (Fragment) main.invoke(c, (Object[])mainArgs);

                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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

    private String getBackStack() {
        StringBuilder sb = new StringBuilder();
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        List<Fragment> backStack = fm.getFragments();
        for (Fragment frag :  backStack){
            sb.append(getFragName());
            System.out.println("---------------------" + getFragName());
        }
        return sb.toString();
    }

    public String getFragName() {
        return fragName;
    }
}
