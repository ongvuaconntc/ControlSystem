package hongmp.chinhnt.controlsystem.blockly_utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.blockly.android.codegen.CodeGenerationRequest;

import hongmp.chinhnt.controlsystem.BlocklyActivity;

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

