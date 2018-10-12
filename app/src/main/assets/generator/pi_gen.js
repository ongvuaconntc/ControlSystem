var number=0;
var listCOM=[];
var py_init="\nimport thread\nfrom threading import Lock\nimport time\nimport serial\nserialList={}\nstatusList={}\nlock={}\ndef listenTask(serial_port):\n    if serial_port not in lock:\n        lock[serial_port]=Lock();\n    lock[serial_port].acquire()\n    if serial_port not in serialList:\n        baud_rate = 9600; \n        ser = serial.Serial(serial_port, baud_rate)\n        serialList[serial_port]=ser;\n    else:\n        ser=serialList[serial_port]\n    lock[serial_port].release()\n    while True:\n        if serial_port not in lock:\n             lock[serial_port]=Lock();\n        lock[serial_port].acquire()\n        in_buf=ser.inWaiting()\n        print(\"buf waiting \"+str(in_buf));";
var py_init_2="\n        if  in_buf>2 :\n            line = ser.readline();\n            i=0\n            while ( i<len(line) and line[i]!='|' ):\n                i+=1\n            portname=line[:i];\n            portvalue=line[i+1:];\n            statusList[serial_port+\"|\"+portname]=portvalue\n        lock[serial_port].release()\n        time.sleep(0.5)\ndef writeTask(serial_port,portname,value):\n    ser=0;\n    if serial_port not in lock:\n        lock[serial_port]=Lock();";
var py_init_3="\n    lock[serial_port].acquire()\n    if serial_port in serialList:\n        ser=serialList[serial_port]\n    else:\n        baud_rate = 9600; \n        ser = serial.Serial(serial_port, baud_rate)\n        serialList[serial_port]=ser;\n    data=str(portname)+\"|\"+str(value)\n    ser.write(data);\n    lock[serial_port].release()\ndef getPortValue(data):\n    if data in statusList:\n        return int(statusList[data])\n    else:\n        return None\n";
Blockly.Python['pi_get_node_port_value'] = function(block) {
  var text_nodename = block.getFieldValue('NodeName');
  var dropdown_portname = block.getFieldValue('portName');
  // TODO: Assemble Python into code variable.
  var code = 'get_port_value(\''+text_nodename+"|"+dropdown_portname+'\')';
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
  // TODO: Assemble Python into code variable.
  var code = 'write_task(\''+text_nodename+'\',\''+dropdown_portname+'|'+number_portvalue+'\')\n';
  return code;
};

Blockly.Python['task_pi_control'] = function(block) {
  var statements_do0 = Blockly.Python.statementToCode(block, 'DO0');
  // TODO: Assemble Python into code variable.
//  var code = `
//  def job${number}():
//${statements_do0}`;
  var code = `def job():\n${statements_do0}`;
  number++;
  return code;
};
Blockly.Python['main_pi_control'] = function(block) {
  var statements_do0 = Blockly.Python.statementToCode(block, 'DO0');
  // TODO: Assemble Python into code variable.
  var listTask="";
  for (i=0;i<listCOM.length;i++){
    listTask+="\nthread.start_new_thread( listenTask, (\""+listCOM[i]+"\",) )\n";
  }
  for (i=0;i<number;i++){
    listTask+="\nthread.start_new_thread( job"+i+",())\n";
  }
  var code =py_init+py_init_2+py_init_3+statements_do0+listTask;
  return code;
};
Blockly.Python['pi_delay'] = function(block) {
  var second = block.getFieldValue('SECOND');
    // TODO: Assemble JavaScript into code variable.
    var code = 'time.sleep('+second+');\n';
    return code;
};