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
            Toast.makeText(this.mContext, s, Toast.LENGTH_LONG).show();
            blocklyActivity.addNewCode(s);
        }
    }
}

