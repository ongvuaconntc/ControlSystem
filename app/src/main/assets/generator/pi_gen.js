var number=0;
var listCOM=[];
var py_init="\nimport threading\nfrom threading import Lock\nimport time\nimport serial\nimport sys\nimport StringIO\nimport inspect\nimport ctypes\n\nserialList = {}\nstatusList = {}\nlock = {}\nthreadNodes = []\nthreadTasks = []\n# boolean object to check task is running or not\ntaskStatus = {}\nglobalCOM=\".//COM7\"\nclass Node(threading.Thread):\n    def __init__(self, serial_port):\n        threading.Thread.__init__(self)\n        self.serial_port = serial_port\n\n    def listen_task(self):\n        if self.serial_port not in lock:\n            lock[self.serial_port] = Lock()\n        lock[self.serial_port].acquire()\n        if self.serial_port not in serialList:\n            baud_rate = 9600\n            ser = serial.Serial(self.serial_port, baud_rate)\n            serialList[self.serial_port] = ser\n        else:\n            ser = serialList[self.serial_port]\n        lock[self.serial_port].release()\n        while True:\n            if self.serial_port not in lock:\n                lock[self.serial_port] = Lock()\n            lock[self.serial_port].acquire()\n            in_buf = ser.inWaiting()\n            # print(\"buf waiting \" + str(in_buf).strip())\n            if in_buf > 10:\n                line = ser.readline().strip()\n                i = 0\n                print(\"received:\"+line);\n                while i < len(line) and line[i] != '|':\n                    i += 1\n                port_name = line[:i]\n                port_value = line[i + 1:]\n                statusList[port_name] = port_value\n            lock[self.serial_port].release()\n\n    # def _get_my_tid(self):\n    #     \"\"\"determines this (self's) thread id\"\"\"\n    #     if not self.isAlive():\n    #         raise threading.ThreadError(\"the thread is not active\")\n    #\n    #     # do we have it cached?\n    #     if hasattr(self, \"_thread_id\"):\n    #         return self._thread_id\n    #\n    #     # no, look for it in the _active dict\n    #     for tid, t_obj in threading._active.items():\n    #         if t_obj is self:\n    #             self._thread_id = tid\n    #             return tid\n    #\n    #     raise AssertionError(\"could not determine the thread's id\")\n    #\n    # def raise_exc(self, exctype):\n    #     \"\"\"raises the given exception type in the context of this thread\"\"\"\n    #     _async_raise(self._get_my_tid(), exctype)\n    #\n    # def terminate(self):\n    #     \"\"\"raises SystemExit in the context of the given thread, which should\n    #     cause the thread to exit silently (unless caught)\"\"\"\n    #     self.raise_exc(SystemExit)\n\n    def run(self):\n        self.listen_task()\n\n\nclass Task(threading.Thread):\n    def __init__(self, task):\n        threading.Thread.__init__(self)\n        self.task = task\n\n    def _get_my_tid(self):\n        \"\"\"determines this (self's) thread id\"\"\"\n        if not self.isAlive():\n            raise threading.ThreadError(\"the thread is not active\")\n\n        # do we have it cached?\n        if hasattr(self, \"_thread_id\"):\n            return self._thread_id\n\n        # no, look for it in the _active dict\n        for tid, t_obj in threading._active.items():\n            if t_obj is self:\n                self._thread_id = tid\n                return tid\n\n        raise AssertionError(\"could not determine the thread's id\")\n\n    def raise_exc(self, exctype):\n        \"\"\"raises the given exception type in the context of this thread\"\"\"\n        _async_raise(self._get_my_tid(), exctype)\n\n    def terminate(self):\n        \"\"\"raises SystemExit in the context of the given thread, which should\n        cause the thread to exit silently (unless caught)\"\"\"\n        self.raise_exc(SystemExit)\n\n    def run(self):\n        self.task()\n\n\ndef _async_raise(tid, exctype):\n    \"\"\"raises the exception, performs cleanup if needed\"\"\"\n    if not inspect.isclass(exctype):\n        raise TypeError(\"Only types can be raised (not instances)\")\n    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, ctypes.py_object(exctype))\n    if res == 0:\n        raise ValueError(\"invalid thread id\")\n    elif res != 1:\n        # \"\"\"if it returns a number greater than one, you're in trouble,\n        # and you should call it again with exc=NULL to revert the effect\"\"\"\n        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, 0)\n        raise SystemError(\"PyThreadState_SetAsyncExc failed\")\n\n\ndef get_port_value(node_id,port):\n    listpattern=node_id+port\n    write_task(node_id+port+\"|?\")\n    while listpattern not in statusList:\n        time.sleep(0.2)\n    if listpattern in statusList:\n        return int(statusList[listpattern])\n    else:\n        return None\n\n\"\"\"format :read value = node_id+port|?\n           write value= node_id+port|value\n\"\"\"\ndef write_task(data):\n    print \"writing task\"\n    try:\n        if globalCOM not in lock:\n            lock[globalCOM] = Lock()\n        lock[globalCOM].acquire()\n        if globalCOM in serialList:\n            ser = serialList[globalCOM]\n        else:\n            baud_rate = 9600\n            ser = serial.Serial(globalCOM, baud_rate)\n            serialList[globalCOM] = ser\n        ser.write(data)\n        time.sleep(0.05)\n        print \"writing job...[\" + data + \"]\"\n        lock[globalCOM].release()\n    except:\n        print(\"An exception occurred at write t\")\n";
Blockly.Python['pi_get_node_port_value'] = function(block) {
  var text_nodename = block.getFieldValue('NodeName');
  var dropdown_portname = block.getFieldValue('portName');
  // TODO: Assemble Python into code variable.
  var code = 'get_port_value(\''+text_nodename+'\',\''+dropdown_portname+'\')';
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
  var code = 'write_task(\''+text_nodename+dropdown_portname+'|'+number_portvalue+'\', \'str\')\n';
  return code;
};

Blockly.Python['task_pi_control'] = function(block) {
  var statements_do0 = Blockly.Python.statementToCode(block, 'DO0');
  // TODO: Assemble Python into code variable.
//  var code = `
//  def job${number}():
//${statements_do0}`;
/*
  var code = `def job():\n${statements_do0}`;
  */
  number++;
  var endline="\n            time.sleep(0.5)\n            if not job"+number+".running:\n                break\n        except:\n            print(\"An exception occurred at job\")\n            break\n";

  //var code ='\ndef job'+number+'():\n    job'+number+'.running = True\n    while True:\n        try:\n'+statements_do0+'\n'+endline;
  var statements_do0_tmp = "";
  var res = statements_do0.split('\n');
  for (i = 0; i < res.length; i++) {
    statements_do0_tmp += '\t' + res[i] + '\n';
  }
  var code = '\n' + number + '|def job(self):\n\twhile self.running:\n'+statements_do0_tmp+'\n';
  return code;
};
Blockly.Python['main_pi_control'] = function(block) {
  var statements_do0 = Blockly.Python.statementToCode(block, 'DO0');
  // TODO: Assemble Python into code variable.
  var listTask="\nt = Node(globalCOM)\nt.start()\nprint \"Starting Nodes.....\"\ntime.sleep(2)\nprint \"---------------------\"\n";
  for (i=1;i<=number;i++){
    listTask+="\nt"+i+" = Task(job"+i+")\nt"+i+".start()\n";
  }
  for (i=1;i<=number;i++){
      listTask+="\nthreadTasks.append(t"+i+")\n";
  }

  var code=py_init+statements_do0+listTask+"\nthreadNodes.append(t)";
  return code;
};
Blockly.Python['pi_delay'] = function(block) {
  var second = block.getFieldValue('SECOND');
    // TODO: Assemble JavaScript into code variable.
    var code = 'time.sleep('+second+');\n';
    return code;
};