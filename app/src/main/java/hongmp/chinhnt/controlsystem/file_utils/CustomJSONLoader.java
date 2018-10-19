package hongmp.chinhnt.controlsystem.file_utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class CustomJSONLoader {

    public static void getNewPIJson(ArrayList<String> listPort,Context context){
        StringBuilder tmp=new StringBuilder();
        for (String com:listPort){
            tmp.append("[\""+com+"\",\""+com+"\"],\n");
        }

        String data="[{\n" +
                "  \"type\": \"pi_get_node_port_value\",\n" +
                "  \"message0\": \"Node %1 %2\",\n" +
                "  \"args0\": [\n" +
                "    {\n" +
                "      \"type\": \"field_dropdown\",\n" +
                "      \"name\": \"NodeName\",\n" +
                "      \"options\": [\n" +
                "        "+tmp.toString() +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"field_dropdown\",\n" +
                "      \"name\": \"portName\",\n" +
                "      \"options\": [\n" +
                "        [\n" +
                "          \"A0\",\n" +
                "          \"A0\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"A1\",\n" +
                "          \"A1\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"A2\",\n" +
                "          \"A2\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"A3\",\n" +
                "          \"A3\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"A4\",\n" +
                "          \"A4\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"A5\",\n" +
                "          \"A5\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D0\",\n" +
                "          \"0\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D1\",\n" +
                "          \"1\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D2\",\n" +
                "          \"2\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D3\",\n" +
                "          \"3\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D4\",\n" +
                "          \"4\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D5\",\n" +
                "          \"5\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D6\",\n" +
                "          \"6\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D7\",\n" +
                "          \"7\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D8\",\n" +
                "          \"8\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D9\",\n" +
                "          \"9\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D10\",\n" +
                "          \"10\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D11\",\n" +
                "          \"11\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D12\",\n" +
                "          \"12\"\n" +
                "        ],\n" +
                "        [\n" +
                "          \"D13\",\n" +
                "          \"13\"\n" +
                "        ]\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"output\": null,\n" +
                "  \"colour\": 180,\n" +
                "  \"tooltip\": \"\",\n" +
                "  \"helpUrl\": \"\"\n" +
                "},\n" +
                "  {\n" +
                "    \"type\": \"pi_set_node_port_value\",\n" +
                "    \"message0\": \"Set Node %1 Port %2 Value %3\",\n" +
                "    \"args0\": [\n" +
                "      {\n" +
                "        \"type\": \"field_dropdown\",\n" +
                "        \"name\": \"NodeName\",\n" +
                "       \"options\": [\n" +
                "        "+tmp.toString() +
                "       ]\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"field_dropdown\",\n" +
                "        \"name\": \"portName\",\n" +
                "        \"options\": [\n" +
                "          [\n" +
                "            \"A0\",\n" +
                "            \"A0\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"A1\",\n" +
                "            \"A1\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"A2\",\n" +
                "            \"A2\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"A3\",\n" +
                "            \"A3\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"A4\",\n" +
                "            \"A4\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"A5\",\n" +
                "            \"A5\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D0\",\n" +
                "            \"0\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D1\",\n" +
                "            \"1\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D2\",\n" +
                "            \"2\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D3\",\n" +
                "            \"3\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D4\",\n" +
                "            \"4\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D5\",\n" +
                "            \"5\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D6\",\n" +
                "            \"6\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D7\",\n" +
                "            \"7\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D8\",\n" +
                "            \"8\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D9\",\n" +
                "            \"9\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D10\",\n" +
                "            \"10\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D11\",\n" +
                "            \"11\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D12\",\n" +
                "            \"12\"\n" +
                "          ],\n" +
                "          [\n" +
                "            \"D13\",\n" +
                "            \"13\"\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"field_number\",\n" +
                "        \"name\": \"portValue\",\n" +
                "        \"value\": 0\n" +
                "      }\n" +
                "    ],\n" +
                "    \"previousStatement\": null,\n" +
                "    \"nextStatement\": null,\n" +
                "    \"colour\": 315,\n" +
                "    \"tooltip\": \"\",\n" +
                "    \"helpUrl\": \"\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"task_pi_control\",\n" +
                "    \"message0\": \"task %1 %2\",\n" +
                "    \"args0\": [\n" +
                "      {\n" +
                "        \"type\": \"input_dummy\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"input_statement\",\n" +
                "        \"name\": \"DO0\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"previousStatement\": null,\n" +
                "    \"nextStatement\": null,\n" +
                "    \"colour\": 230,\n" +
                "    \"tooltip\": \"\",\n" +
                "    \"helpUrl\": \"\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"main_pi_control\",\n" +
                "    \"message0\": \"task_control %1 %2\",\n" +
                "    \"args0\": [\n" +
                "      {\n" +
                "        \"type\": \"input_dummy\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"input_statement\",\n" +
                "        \"name\": \"DO0\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"previousStatement\": null,\n" +
                "    \"colour\": 90,\n" +
                "    \"tooltip\": \"\",\n" +
                "    \"helpUrl\": \"\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"pi_delay\",\n" +
                "    \"message0\": \"Delay %1\",\n" +
                "    \"args0\": [\n" +
                "      {\n" +
                "        \"type\": \"field_number\",\n" +
                "        \"name\": \"SECOND\",\n" +
                "        \"value\": 0\n" +
                "      }\n" +
                "    ],\n" +
                "    \"previousStatement\": null,\n" +
                "    \"nextStatement\": null,\n" +
                "    \"colour\": 230,\n" +
                "    \"tooltip\": \"\",\n" +
                "    \"helpUrl\": \"\"\n" +
                "  }\n" +
                "]";
        try{
            FileWriter fileWriter=new FileWriter(context.getExternalCacheDir()+"/newpi.json");
            fileWriter.write(data);
            fileWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
