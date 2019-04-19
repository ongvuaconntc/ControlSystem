'use strict';
var py_init="\nimport serial\nimport time\nimport datetime\nimport array\nimport sys,os\nport = \"COM7\"\nts = time.time()\nst = datetime.datetime.fromtimestamp(ts).strftime(\'%Y-%m-%d %H:%M:%S\')\nprint(st + \": Session started!\")\nCOM_port = serial.Serial(port)\nCOM_port.baudrate = 115200               # set Baud rate\n# COM_port.bytesize = 8                  # Number of data bits = 8\n# COM_port.parity   = \'N\'                # No parity\n# COM_port.stopbits = 1                  # Number of Stop bits = 1\nCOM_port.setRTS(0)  # read mode\nCOM_port.setDTR(0)  # read mode\nmachinedict={}\ndef getNormalChar(c):\n   # data=str(data)\n    if (c>=b\'0\' and c<=b\'9\')or (c==b\'\\n\') or (c==b\'*\') or (c>=b\'A\' and c<=b\'Z\'):\n        return c\n    return b\'\'\ndef isPing(data):\n    if len(data)!=6:\n        return False\n    if data[1]!=\'P\':\n        return False\n    if data[-5:] not in machinedict:\n        print(\"added new slave \",data[-5:])\n        machinedict[data[-5:]]=\"1\"\n    return True\ndef write(data):\n    COM_port.setRTS(1)\n    COM_port.setDTR(1)\n    rawcmd=b\'\'\n    time.sleep(0.007)\n    while COM_port.in_waiting>0:\n        incomingByte=getNormalChar(COM_port.read())\n        rawcmd=rawcmd+incomingByte#.decode(\"utf-8\").strip()\n       # print(incomingByte)\n        if (incomingByte==b\'\\n\'):\n            rawcmd=rawcmd.decode(\"utf-8\").strip()\n        #    print(time.time(),\"received in write \"+rawcmd)\n            if isPing(rawcmd):\n                print(\"PING in write\",rawcmd)\n            rawcmd=b\'\'\n            time.sleep(0.007)\n       \n\n    COM_port.setRTS(0)\n    COM_port.setDTR(0)\n    for t in list(data):\n        COM_port.write(bytes(t, \'utf-8\'))\n    time.sleep(0.0125)\n    COM_port.setRTS(1)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n    COM_port.setDTR(1)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n\ndef read(data):\n    write(data)\n    #COM_port.setRTS(1)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n    #COM_port.setDTR(1)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n    time.sleep(0.007)\n    rawcmd=b\'\'\n    while COM_port.in_waiting>0:\n        incomingByte=getNormalChar(COM_port.read())\n        rawcmd=rawcmd+incomingByte#.decode(\"utf-8\").strip()\n      #  print(str(incomingByte))\n        if incomingByte==b\'\\n\':\n            rawcmd=rawcmd.decode(\"utf-8\").strip() \n         #   print(time.time(),\"received in read \"+rawcmd)\n            if isPing(rawcmd):\n                print(\"PING in write\",rawcmd)\n                rawcmd=b\'\'\n                time.sleep(0.007)\n            else:\n                if (len(rawcmd)>=len(data)):\n                    break\n                else:\n                    rawcmd=b\'\'\n   \n#    print(\"reading\", time.time())\n  #  time.sleep(0.00001)\n    # while RxedData is not None:\n    #     pass\n #   COM_port.setRTS(0)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n #   COM_port.setDTR(0)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n    if len(rawcmd)<4:\n            return \"\"\n    else:\n            return rawcmd[-1:]\n\n\ndef clear_serial_buffer(time_read):\n    COM_port.setRTS(1)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n    COM_port.setDTR(1)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n    time.sleep(time_read)\n    COM_port.reset_input_buffer()\n    # COM_port.flush()\n    COM_port.setRTS(0)\n    COM_port.setDTR(0)\n    print(\"cleared buffer\")\n\nclear_serial_buffer(2)\n";
var py_close="\nexcept Exception as e:\n    exc_type, exc_obj, exc_tb = sys.exc_info()\n    fname = os.path.split(exc_tb.tb_frame.f_code.co_filename)[1]\n    print(exc_type, fname, exc_tb.tb_lineno)\n    if not (COM_port is None):\n        COM_port.close()\n        COM_port = None\n        ts = time.time()\n        st = datetime.datetime.fromtimestamp(ts).strftime(\'%Y-%m-%d %H:%M:%S\')\n        print(st + \": \" + error)\n";
Blockly.Python['pi_get_node_port_value'] = function(block) {
  var text_nodename = block.getFieldValue('NodeName');
  var dropdown_portname = block.getFieldValue('portName');
  // TODO: Assemble Python into code variable.
  var code = 'read(\"*170000'+text_nodename+'0102R'+dropdown_portname+'\\0\")';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_NONE];
};

Blockly.Python['pi_set_node_port_value'] = function(block) {
  var text_nodename = block.getFieldValue('NodeName');
  var dropdown_portname = block.getFieldValue('portName');
  var number_portvalue = block.getFieldValue('portValue');
  while (number_portvalue.length<4) number_portvalue='0'+number_portvalue;
  // TODO: Assemble Python into code variable.
  var code = 'write(\"*210000'+text_nodename+"010203W"+dropdown_portname+number_portvalue+'\\0\")\n';
  return code;
};


Blockly.Python['pi_delay'] = function(block) {
  var second = block.getFieldValue('SECOND');
    // TODO: Assemble JavaScript into code variable.
    var code = 'time.sleep('+second+');\n';
    return code;
};

Blockly.Python.workspaceToCodeWithId = Blockly.Python.workspaceToCode;

Blockly.Python.workspaceToCode = function(workspace) {
  var code = this.workspaceToCodeWithId(workspace);
  // Strip out block IDs for readability.
  code = goog.string.trimRight(code.replace(/(,\s*)?'block_id_[^']+'\)/g, ')'))
  code = py_init+"try:\n    "+code.replace("\n","\n    ")+py_close;
  return code;
};
