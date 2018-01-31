package kylephan.com.tecace2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String frag1Name = new String();
    private String frag2Name = new String();
    private String frag3Name = new String();

    private String[][] jsonData = new String[3][4];
    private int fragCounter = 0;

    private List<Fragment> fragList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findJSON(getApplicationContext());
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            Bundle args = new Bundle();
            args.putSerializable("data", jsonData);
            args.putInt("counter", 0);

            Fragment1 firstFragment = new Fragment1();
            firstFragment.setArguments(args);

//            Fragment1 firstFragment = Fragment1.newInstance(jsonData, 0);
//            Fragment2 secFragment = Fragment2.newInstance(frag2Name, "");
//            Fragment3 thirdFragment = Fragment3.newInstance(frag3Name, "");

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

    }

    private void findJSON(Context context) {

        String json = null;
        String [] list;
        try {
            list = getAssets().list("");
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
//                    ------------------------------------------------------------------------------
//                    -------------------------WORKS TO CREATE ONE FRAGMENT-------------------------
//                    ------------------------------------------------------------------------------
                    if (file.contains(".json")) {
                        try {
                            InputStream is = context.getAssets().open(file);
                            int size = is.available();
                            byte[] buffer = new byte[size];
                            is.read(buffer);
                            is.close();
                            json = new String(buffer, "UTF-8");
                            if (file.contains("1")) {
                                frag1Name = readJSON(json, fragCounter);
                            } else if (file.contains("2")) {
                                frag2Name = readJSON(json, fragCounter);
                            } else if (file.contains("3")) {
                                frag3Name = readJSON(json, fragCounter);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    fragCounter++;
                }
            }
        } catch (IOException e) {
        }
    }

    private String readJSON(String data, int counter) {
        String fragmentName = new String();
        try {
            JSONObject jObject = new JSONObject(data);
            fragmentName = jObject.getString("name");
            jsonData[counter][0] = jObject.getString("name");
            jsonData[counter][1] = jObject.getString("path");
            jsonData[counter][2] = jObject.getString("layout");
            jsonData[counter][3] = jObject.getString("transition");

            for (int i = 0; i < jsonData.length; i++) {
                for (int j = 0; j < jsonData[i].length; j++) {
                    System.out.println("[" + i + "]" + "[" + j + "]" + "==" + jsonData[i][j]);

                }
            }

            System.out.println("--------------------" + fragmentName + "--------------------");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragmentName;
    }
}
