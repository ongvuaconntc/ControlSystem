package hongmp.chinhnt.controlsystem.net;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ServerConnection extends AsyncTask<byte[], Void, String> {

    private Activity contextParent;
    private boolean logined = false;

    public ServerConnection() {
    }

    public ServerConnection(Activity contextParent) {
        this.contextParent = contextParent;
    }

    //send a http post request and return string data
    @Override
    protected String doInBackground(byte[]... postDataBytes) {
        HttpURLConnection conn = null;
        String intentData = "";
        try {
            // Simulate network access.
//            URL url = new URL(Configuration.SERVER_IP + ":" + Configuration.PORT + "/");
//            conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//            conn.setDoOutput(true);
//            conn.getOutputStream().write(postDataBytes);
//            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            StringBuilder returnData = new StringBuilder();
//            for (int c; (c = in.read()) >= 0;) {
//                returnData.append((char)c);
//            }
//            intentData = returnData.toString();
//            if (intentData.equals("login successfully")) {
//                return true;
//            } else if (intentData.equals("login failed")) {
//                return false;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ex: " + e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return intentData;

    }

    @Override
    protected void onPostExecute(String result) {
        if (logined) {
            Toast.makeText(contextParent, "Okie, Finished Login! " + result, Toast.LENGTH_LONG).show();
        }
    }

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }
}


