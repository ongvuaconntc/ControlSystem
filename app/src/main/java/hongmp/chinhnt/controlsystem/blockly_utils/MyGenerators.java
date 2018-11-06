package hongmp.chinhnt.controlsystem.blockly_utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import hongmp.chinhnt.controlsystem.BlocklyActivity;
import hongmp.chinhnt.controlsystem.net.Configuration;
import hongmp.chinhnt.controlsystem.net.MyTCPClient;

public class MyGenerators implements CodeGenerationRequest.CodeGeneratorCallback {
    protected final String mTag;
    protected final Context mContext;
    private BlocklyActivity blocklyActivity;

    public MyGenerators(Context context, String loggingTag) {
        this.mTag = loggingTag;
        this.mContext = context;
        blocklyActivity=(BlocklyActivity)mContext;
    }
    @Override
    public void onFinishCodeGeneration(String s) {
        if (s.isEmpty()) {
            Toast.makeText(this.mContext, "Something went wrong with code generation.", Toast.LENGTH_LONG).show();
        } else {
            Log.d(this.mTag, "code: \n" + s);
            SendTask mSendTask = new SendTask();
            mSendTask.execute(s);
            Toast.makeText(this.mContext, s, Toast.LENGTH_LONG).show();
            blocklyActivity.addNewCode(s);
        }
    }
}

class SendTask extends AsyncTask<String, Void, Boolean> {

    SendTask() {
    }

    @Override
    protected Boolean doInBackground(String... data) {
        for (int i = 0; i < data.length; i++) {
            System.out.println("data: [" + i + "] " + data[i]);
        }
        // TODO: attempt authentication against a network service.
        HttpURLConnection conn = null;
        byte[] postDataBytes = null;
        String intentData = "";
        try {
            if (data[0].startsWith("Ar:")) {
                String code = data[0].substring(2, data[0].length());
                // prepare data
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("request", "send_arduino_code");
                params.put("port", "/dev/ttyACM0");
                params.put("q", code);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                postDataBytes = postData.toString().getBytes("UTF-8");
            } else {
                String jobId = data[0].substring(0, data[0].indexOf('|'));
                String code = data[0].substring(data[0].indexOf('|') + 1, data[0].length());
                // prepare data
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("request", "create_job");
                params.put("job_id", jobId);
                params.put("job_content", code);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                postDataBytes = postData.toString().getBytes("UTF-8");
            }
            // Simulate network access.
            URL url = new URL(Configuration.SERVER_IP + ":" + Configuration.PORT + "/");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder returnData = new StringBuilder();
            for (int c; (c = in.read()) >= 0; ) {
                returnData.append((char) c);
            }

            //////////////////this is all serial ports
            System.out.println(returnData.toString());

            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ex: " + e);
            return true;
        }

        return true;
    }

}