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

public class MainActivity extends AppCompatActivity {

    private String[][] jsonData = new String[3][6];
    private int fragCounter = 0;
    private int counter = 0;

    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findJSON(getApplicationContext());
        Class<?> c = null;
        try {
            int entryAnim = getResources().getIdentifier(jsonData[0][3],"anim", getPackageName());
            int exitAnim = getResources().getIdentifier(jsonData[0][4],"anim", getPackageName());
            c = Class.forName(jsonData[0][1]);
            Class[] argTypes = new Class[] { String[][].class, int.class, String.class};
            Method main = c.getDeclaredMethod("newInstance", argTypes);
            Object[] mainArgs = {jsonData, counter, ""};
            Fragment frag = (Fragment) main.invoke(c, (Object[])mainArgs);

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(entryAnim, exitAnim, exitAnim, entryAnim);
            fragmentTransaction.add(R.id.fragment_container, frag);
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

    private void findJSON(Context context) {

        String json = null;
        String [] list;
        try {
            list = getAssets().list("");
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    if (file.contains(".json")) {
                        try {
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

    private void readJSON(String data, int counter) {
        String fragmentName = new String();
        try {
            JSONObject jObject = new JSONObject(data);
            jsonData[counter][0] = jObject.getString("name");
            jsonData[counter][1] = jObject.getString("path");
            jsonData[counter][2] = jObject.getString("layout");
            jsonData[counter][3] = jObject.getString("entry");
            jsonData[counter][4] = jObject.getString("exit");
            jsonData[counter][5] = jObject.getString("next");

            for (int i = 0; i < jsonData.length; i++) {
                for (int j = 0; j < jsonData[i].length; j++) {
                    System.out.println("[" + i + "]" + "[" + j + "]" + "==" + jsonData[i][j]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
