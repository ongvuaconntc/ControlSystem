'use strict';
var number=0;
var listCOM=[];
var py_init="\nimport serial\nimport time\nimport datetime\nimport array\n\nport = \"COM5\"\nts = time.time()\nst = datetime.datetime.fromtimestamp(ts).strftime(\'%Y-%m-%d %H:%M:%S\')\nprint(st + \": Session started!\")\nCOM_port = serial.Serial(port)\nCOM_port.baudrate = 115200               # set Baud rate\n# COM_port.bytesize = 8                  # Number of data bits = 8\n# COM_port.parity   = \'N\'                # No parity\n# COM_port.stopbits = 1                  # Number of Stop bits = 1\nCOM_port.setRTS(0)  # read mode\nCOM_port.setDTR(0)  # read mode\nmachinedict={}\ndef isPing(data):\n    if len(data)!=7:\n        return False\n    if data[1]!=\'P\':\n        return False\n    if data[-5:] not in machinedict:\n        print(\"added new slave \",data[-5:])\n        machinedict[data[-5:]]=\"1\"\n    return True\ndef write(data):\n    COM_port.setRTS(1)\n    COM_port.setDTR(1)\n    rawcmd=\"\"\n    time.sleep(0.005)\n    while COM_port.in_waiting>0:\n        try:\n            rawcmd=COM_port.readline().decode(\"utf-8\").strip()\n            if isPing(rawcmd):\n                print(\"PING in write\",rawcmd)\n            time.sleep(0.005)\n        except UnicodeDecodeError:\n            pass\n\n    COM_port.setRTS(0)\n    COM_port.setDTR(0)\n    for t in list(data):\n        COM_port.write(bytes(t, \'utf-8\'))\n    time.sleep(0.011)\n    COM_port.setRTS(1)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n    COM_port.setDTR(1)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n\ndef read(data):\n    write(data)\n    #COM_port.setRTS(1)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n    #COM_port.setDTR(1)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n    time.sleep(0.005)\n    RxedData=\"\"\n\n    while COM_port.in_waiting>0:\n        try:\n            RxedData=COM_port.readline().decode(\"utf-8\").strip()\n            if isPing(RxedData):\n                print(\"PING in read\",RxedData)\n            else:\n                    break\n            time.sleep(0.005)\n        except UnicodeDecodeError:\n            pass\n\n   \n#    print(\"reading\", time.time())\n  #  time.sleep(0.00001)\n    # while RxedData is not None:\n    #     pass\n #   COM_port.setRTS(0)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n #   COM_port.setDTR(0)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n    if len(RxedData)<4:\n            return -1\n    else:\n            try:\n                    val = int(RxedData[-4:],10)\n                    return val\n            except ValueError:\n                    return -1\n\n\ndef clear_serial_buffer(time_read):\n    COM_port.setRTS(1)  # RTS=1,~RTS=0 so ~RE=0,Receive mode enabled for MAX485\n    COM_port.setDTR(1)  # DTR=1,~DTR=0 so  DE=0,(In FT232 RTS and DTR pins are inverted)\n    time.sleep(time_read)\n    COM_port.reset_input_buffer()\n    # COM_port.flush()\n    COM_port.setRTS(0)\n    COM_port.setDTR(0)\n    print(\"cleared buffer\")";
var py_close="\nexcept Exception as ex:\n    error = \"Exception is: \" + ex.__str__()\n    if not (COM_port is None):\n        COM_port.close()\n        COM_port = None\n        ts = time.time()\n        st = datetime.datetime.fromtimestamp(ts).strftime(\'%Y-%m-%d %H:%M:%S\')\n        print(st + \": \" + error)\n";
Blockly.Python['pi_get_node_port_value'] = function(block) {
  var text_nodename = block.getFieldValue('NodeName');
  var dropdown_portname = block.getFieldValue('portName');
  // TODO: Assemble Python into code variable.
  var code = 'read(\"*170000'+text_nodename+'0102R'+dropdown_portname+'\\0\")';
  var check=listCOM.indexOf(text_nodename);
  if (check==-1){
      listCOM.push(text_nodename);
  }
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_NONE];
};

Blockly.Python['pi_set_node_port_value'] = function(block) {
  var text_nodename = block.getFieldValue('NodeName');
  var dropdown_portname = block.getFieldValue('portName');
  var number_portvalue = block.getFieldValue('portValue');
  while (number_portvalue.length<4) number_portvalue='0'+number_portvalue;
  // TODO: Assemble Python into code variable.
  //"*2100001234010203W060001\0"
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
  code = py_init+"try:\n    "+goog.string.trimRight(code.replace(/(,\s*)?'block_id_[^']+'\)/g, ')'))
  code = code+py_close;
  return code;
};
