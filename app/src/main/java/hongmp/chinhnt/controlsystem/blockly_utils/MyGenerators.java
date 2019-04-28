package hongmp.chinhnt.controlsystem.blockly_utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onFinishCodeGeneration(String s) {
        if (s.isEmpty()) {
            Toast.makeText(this.mContext, "Something went wrong with code generation.", Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this.mContext);
            dialog.setTitle("Generated code");
            dialog.setMessage(s);
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                }
            });
            dialog.show();
            blocklyActivity.addNewCode(s);
        }
    }
}

