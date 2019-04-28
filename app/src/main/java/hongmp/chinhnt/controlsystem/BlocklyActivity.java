package hongmp.chinhnt.controlsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.codegen.LanguageDefinition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hongmp.chinhnt.controlsystem.blockly_utils.MyGenerators;
import hongmp.chinhnt.controlsystem.net.Configuration;
import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.User;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BlocklyActivity extends AbstractBlocklyActivity {
    String generated_Code;
    static SystemElement  element=null;
    private static String languageApp;
    private User user;
    private View all_View;
    private View progress_View;
    private BlocklyActivity pointer;


    private static List<String> JS_NODE_BLOCK_DEFINITIONS=Arrays.asList(
            "myblock/colour_blocks.json",
            "myblock/list_blocks.json",
            "myblock/logic_blocks.json",
            "myblock/loop_blocks.json",
            "myblock/math_blocks.json",
            "myblock/procedures.json",
            "myblock/text_blocks.json",
            "myblock/variable_blocks.json",
            "myblock/arduino.json"
    );
    private static List<String> PYTHON_NODE_BLOCK_DEFINITIONS=Arrays.asList(
            "myblock/colour_blocks.json",
            "myblock/list_blocks.json",
            "myblock/logic_blocks.json",
            "myblock/loop_blocks.json",
            "myblock/math_blocks.json",
            "myblock/procedures.json",
            "myblock/text_blocks.json",
            "myblock/variable_blocks.json",
            "myblock/pi.json"
    );

    private static List<String> JS_NODE_BLOCK_DEFINITIONS_VIE=Arrays.asList(
            "myblock/colour_blocks.json",
            "myblock/list_blocks.json",
            "myblock/logic_blocks_vi.json",
            "myblock/loop_blocks_vi.json",
            "myblock/math_blocks_vi.json",
            "myblock/procedures_vi.json",
            "myblock/text_blocks_vi.json",
            "myblock/variable_blocks_vi.json",
            "myblock/arduino_vi.json"
    );
    private static List<String> PYTHON_NODE_BLOCK_DEFINITIONS_VIE=Arrays.asList(
            "myblock/colour_blocks.json",
            "myblock/list_blocks.json",
            "myblock/logic_blocks_vi.json",
            "myblock/loop_blocks_vi.json",
            "myblock/math_blocks_vi.json",
            "myblock/procedures_vi.json",
            "myblock/text_blocks_vi.json",
            "myblock/variable_blocks_vi.json",
            "myblock/pi_vi.json"
    );


    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            // Custom block generators go here. Default blocks are already included
            "generator/mygenerators.js"
    );
    private static final List<String> PYTHON_GENERATORS = Arrays.asList(
            // Custom block generators go here. Default blocks are already included
            "generator/pi_gen.js"
    );

    private static final LanguageDefinition PYTHON_LANGUAGE_DEF
            = new LanguageDefinition("generator/python_compressed.js", "Blockly.Python");

    @NonNull
    @Override
    protected LanguageDefinition getBlockGeneratorLanguage() {
        if (!element.getName().equalsIgnoreCase("Master"))
            return super.getBlockGeneratorLanguage();
        else
            return PYTHON_LANGUAGE_DEF;
    }

    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new MyGenerators(this, "BlocklyActivity");



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        if (!element.getName().equalsIgnoreCase("Master")) {
            if(languageApp.equalsIgnoreCase("vi")){
                return "myblock/toolbox_vi.xml";
            }else return "myblock/toolbox.xml";
        }
        else
        if(languageApp.equalsIgnoreCase("vi")) {
            return "myblock/toolboxpi_vi.xml";
        }else
            return "myblock/toolboxpi.xml";
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        List<String> assetPaths = new ArrayList<>(getAllBlockDefinitions());
        // Append your own block definitions here.
        return assetPaths;
    }
    public static List<String> getAllBlockDefinitions() {
        if (!element.getName().equalsIgnoreCase("Master"))
            if(languageApp.equalsIgnoreCase("vi")){
                return JS_NODE_BLOCK_DEFINITIONS_VIE;
            }else {
                return JS_NODE_BLOCK_DEFINITIONS;
            }
        else
        if(languageApp.equalsIgnoreCase("vi")) {
            return PYTHON_NODE_BLOCK_DEFINITIONS_VIE;
        }else{
            return PYTHON_NODE_BLOCK_DEFINITIONS;
        }
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        if (!element.getName().equalsIgnoreCase("Master"))
            return JAVASCRIPT_GENERATORS;
        else return PYTHON_GENERATORS;
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
        if (intent.getSerializableExtra("element")!=null) {
            element = (SystemElement) intent.getSerializableExtra("element");
        }
        if (intent.getSerializableExtra("user")!=null) {
            user = (User) intent.getSerializableExtra("user");
        }
        pointer=this;

        languageApp = getResources().getConfiguration().locale.getLanguage();
        System.out.println("ID :"+element.getName());
        System.out.println("Name :"+element.getName());
        return getLayoutInflater().inflate(R.layout.myblocklylayout, null);
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
        String xml="";
        try {
            String fileName ="/data/data/hongmp.chinhnt.controlsystem/files/"+getWorkspaceSavePath();
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                //process the line
                xml+="\n"+line;
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
   //     System.out.println("XML:"+xml);
        showProgress(true);
        SendTask mSendTask = new SendTask(element,user);
        mSendTask.execute(generated_Code,xml);

        generated_Code=null;
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
        System.out.println(element.getId()+"_"+element.getName()+".xml");
        return element.getId()+"_"+element.getName()+".xml";
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
    public void showProgress(final boolean show){
        all_View=(View)findViewById(R.id.allblockly_view);
        progress_View=(View)findViewById(R.id.blockly_progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            all_View.setVisibility(show ? View.GONE : View.VISIBLE);
            all_View.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    all_View.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress_View.setVisibility(show ? View.VISIBLE : View.GONE);
            progress_View.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress_View.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress_View.setVisibility(show ? View.VISIBLE : View.GONE);
            all_View.setVisibility(show ? View.GONE : View.VISIBLE);

            // mImageView.setVisibility(View.VISIBLE);
        }
    }
    class SendTask extends AsyncTask<String, Void, String> {
        SystemElement el;
        User user;
        int type;
        SendTask(SystemElement el,User user) {
            this.el=el;
            this.user=user;
            type=0;
        }

        @Override
        protected String doInBackground(String... data) {
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
                    params.put("q", code);
                    params.put("deviceId",el.getId());
                    params.put("xml",data[1]);
                    params.put("session_id",user.getSession_id());



                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    postDataBytes = postData.toString().getBytes("UTF-8");
                } else {
//                String jobId = data[0].substring(0, data[0].indexOf('|'));
                    type=1;
                    String code = data[0].substring(data[0].indexOf('|') + 1, data[0].length());
                    // prepare data
                    Map<String, Object> params = new LinkedHashMap<>();
                    params.put("request", "send_python_code");
                    params.put("q", data[0]);
                    params.put("xml",data[1]);
                    params.put("session_id",user.getSession_id());

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
                return returnData.toString();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ex: " + e);
                return "Server Error!";
            }
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            showProgress(false);
            Toast.makeText(pointer,string, Toast.LENGTH_LONG).show();
        }
    }


}


