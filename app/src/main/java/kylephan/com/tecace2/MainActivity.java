package kylephan.com.tecace2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * This main activity drives the TecAce sample project.
 *
 * @author Kyle Phan 2/1/18
 */
public class MainActivity extends AppCompatActivity {

    /** 2D array of Strings, contains content read from JSON files */
    private String[][] jsonData = new String[3][6];
    /** Int value, allows fragments to keep track of order */
    private int counter = 0;

    /**
     * Method called when this Activity is created.
     *
     * Creates new fragments using reflect API.
     *
     * @param savedInstanceState Stores data that was sent from the caller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findJSON(getApplicationContext());

        /** This try/catch block creates new fragments */
        Class<?> c = null;
        try {

            /**
             *  Getting the entry and exit animator resources for the current and next fragments.
             * */
            int entryAnim = getResources().getIdentifier(jsonData[0][3],"animator", getPackageName());
            int exitAnim = getResources().getIdentifier(jsonData[1][4],"animator", getPackageName());
            int entryAnim2 = getResources().getIdentifier(jsonData[1][3],"animator", getPackageName());
            int exitAnim2 = getResources().getIdentifier(jsonData[1][4],"animator", getPackageName());

            /**
             * Creating new fragments with Java Reflect.
             */
            c = Class.forName(jsonData[0][1]);             /** Set c object to a Fragment1 object. */
            Class[] argTypes = new Class[] { String[][].class, int.class, String.class}; /** Class array containing parameter types of Fragment1 newInstance method. */
            Method main = c.getDeclaredMethod("newInstance", argTypes); /** Get the method newInstance from Fragment1 with specified parameters types. */
            Object[] mainArgs = {jsonData, counter, ""}; /** Object array containing parameters to be passed into newInstance method call. */
            Fragment frag = (Fragment) main.invoke(c, (Object[])mainArgs); /** Invoke Fragment1 newInstance method using Java reflect */

            /**
             * Setting the newly created fragment as the active view.
             */
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(entryAnim2, exitAnim, entryAnim, exitAnim2); /** Passing in animator resources for transitions */
            fragmentTransaction.add(R.id.fragment_container, frag); /** Adding new fragment to the designated fragment container */
            fragmentTransaction.commit();
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

    /**
     * Locates the JSON files in assets directory then
     * iterates through each file calling readJSON on each file
     * to store the data.
     *
     * @param context is the current state of the application/object.
     */
    private void findJSON(Context context) {

        String json = null;
        String [] list;
        int fragCounter = 0;
        try {
            list = getAssets().list("");
            if (list.length > 0) {
                /** Iterate through all files in assets directory. */
                for (String file : list) {
                    /** Read file if ".json" is in the name. */
                    if (file.contains(".json")) {
                        try {
                            /**
                             * Take contents of file, format it to a String
                             * and pass it into readJSON for parsing.
                             */
                            InputStream is = context.getAssets().open(file);
                            int size = is.available();
                            byte[] buffer = new byte[size];
                            is.read(buffer);
                            is.close();
                            json = new String(buffer, "UTF-8");
                            readJSON(json, fragCounter);

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

    /**
     * Takes string contents of a JSON file and gets the data from the string
     * and stores it in the appropriate location within the 2D jsonData array.
     * @param data is the string contents from the JSON file
     * @param count is an int representing which file's data is being read.
     */
    private void readJSON(String data, int count) {
        try {

            /** Putting JSON data in the corresponding location for each fragment */
            JSONObject jObject = new JSONObject(data);
            jsonData[count][0] = jObject.getString("name");
            jsonData[count][1] = jObject.getString("path");
            jsonData[count][2] = jObject.getString("layout");
            jsonData[count][3] = jObject.getString("entry");
            jsonData[count][4] = jObject.getString("exit");
            jsonData[count][5] = jObject.getString("next");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Override onBackPressed to pop fragments off the stack when back button is pressed.
     * When backstack is empty (app is on first fragment) the app closes on back button press.
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
