package hongmp.chinhnt.controlsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hongmp.chinhnt.controlsystem.blockly_utils.MyGenerators;
import hongmp.chinhnt.controlsystem.list_utils.CustomAdapterSystemFunction;
import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.SystemFunction;

public class BlocklyActivity extends AbstractBlocklyActivity {
    String generated_Code;
    private static List<String> MY_BLOCK_DEFINITIONS;
    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            // Custom block generators go here. Default blocks are already included.
    );
    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new MyGenerators(this, "BlocklyActivity");
    SystemFunction function=null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState, persistentState);


        Intent intent=getIntent();


        if (intent.getSerializableExtra("function")!=null) {
           function = (SystemFunction) intent.getSerializableExtra("function");
        }
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "myblock/toolbox.xml";
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        List<String> assetPaths = new ArrayList<>(getAllBlockDefinitions());
        // Append your own block definitions here.
        return assetPaths;
    }
    public static List<String> getAllBlockDefinitions() {
        if (MY_BLOCK_DEFINITIONS == null) {
            MY_BLOCK_DEFINITIONS = Collections.unmodifiableList(Arrays.asList("myblock/colour_blocks.json", "myblock/list_blocks.json", "myblock/logic_blocks.json", "myblock/loop_blocks.json", "myblock/math_blocks.json", "myblock/procedures.json", "myblock/text_blocks.json", "myblock/variable_blocks.json"));
        }
        return MY_BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return JAVASCRIPT_GENERATORS;
    }

    @Override
    protected void onInitBlankWorkspace() {
        getController().addVariable("item");
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return mCodeGeneratorCallback;
    }

    @Override
    protected View onCreateContentView(int containerId) {
        System.out.println("onCreateContentView");
        Intent intent=getIntent();
        if (intent.getSerializableExtra("function")!=null) {
            function = (SystemFunction) intent.getSerializableExtra("function");
        }
        return getLayoutInflater().inflate(R.layout.blockly_unified_workspace, null);
    }


    @Override
    public void onLoadWorkspace() {
        System.out.println("OnLoadWorkspace");
        super.onLoadWorkspace();
    }

    @Override
    protected void onLoadInitialWorkspace() {
        System.out.println("OnLoadInitial WorkSpace");
        super.onLoadInitialWorkspace();
    }

    @Override
    public void onSaveWorkspace() {
        super.onSaveWorkspace();
        if (generated_Code==null) return;
        byte[] data=new byte[generated_Code.length()];
        for (int i=0;i<generated_Code.length();i++) data[i]=(byte)generated_Code.charAt(i);
        try {
            ViewElementActivity.client.writeData(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        generated_Code=null;
        Toast.makeText(this,"Saved to "+function.getNode()+"_"+function.getName()+".xml", 1).show();

    }

    @Override
    protected void onRunCode() {
        super.onRunCode();
    }

    @Override
    public void onClearWorkspace() {
        super.onClearWorkspace();
    }

    public void addNewCode(String s){
        generated_Code=s;
    }

    @NonNull
    @Override
    protected String getWorkspaceSavePath() {
        System.out.println(function.getNode()+"_"+function.getName()+".xml");
        return function.getNode()+"_"+function.getName()+".xml";
    }

    @Override
    protected int getActionBarMenuResId() {
        return R.menu.split_actionbar;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onClearWorkspace();
        try {
            onLoadWorkspace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            onSaveWorkspace();
        }

    }
}
