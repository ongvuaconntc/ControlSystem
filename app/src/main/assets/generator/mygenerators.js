'use strict';
Blockly.BlockSvg.START_HAT = true;
Blockly.JavaScript['set_port_value'] = function(block) {
  var dropdown_port_name = block.getFieldValue('port_name');
  var value_portvalue = Blockly.JavaScript.valueToCode(block, 'PORTVALUE', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
  var code;
  if (dropdown_port_name[0]=='A')
  code='analogWrite('+dropdown_port_name+','+value_portvalue+');';
  else
  code = 'digitalWrite('+dropdown_port_name+','+value_portvalue+');';
  return code;
};

Blockly.JavaScript['init_arduino'] = function(block) {
  var statements_name = Blockly.JavaScript.statementToCode(block, 'DO');
  // TODO: Assemble JavaScript into code variable.
  var code = statements_name+'\n';
  return code;
};

Blockly.JavaScript['set port mode'] = function(block) {
  var dropdown_portname = block.getFieldValue('PortName');
  var dropdown_portmode = block.getFieldValue('PortMode');
  // TODO: Assemble JavaScript into code variable.
  var code = 'pinMode('+dropdown_portname+','+dropdown_portmode+');\n';
  return code;
};

Blockly.JavaScript['get_port_value'] = function(block) {
  var dropdown_name = block.getFieldValue('NAME');
  // TODO: Assemble JavaScript into code variable.
  var code="";
  if (dropdown_name[0]=='A')
   code = 'analogRead('+dropdown_name+')';
   else
   code = 'digitalRead('+dropdown_name+')';

  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};
Blockly.JavaScript['delay_arduino'] = function(block) {
  var number_name = block.getFieldValue('NAME');
  // TODO: Assemble JavaScript into code variable.
  var code = 'delay('+number_name+');\n';
  return code;
};

Blockly.JavaScript['core_arduino'] = function(block) {
  var statements_do0 = Blockly.JavaScript.statementToCode(block, 'DO0');
  var statements_do = Blockly.JavaScript.statementToCode(block, 'DO');
  // TODO: Assemble JavaScript into code variable.
  var i;
  var serialcode="";
  var tmp=statements_do;
  for (i=0;i<statements_do.length-10;i++){

      	if (statements_do.substring(i,i+10)=='analogRead'){
            var j=i+11;
            while (j>0&&statements_do[j]!=')')j++;
            var port=statements_do.substring(i+11,j)

            j=i-1;
            while (j>0&&statements_do[j]==' '||statements_do[j]=='=')j--;
            var k=j;
            while (j>0&&statements_do[j]!=' '&&statements_do[j]!='\n'&&statements_do[j]!=';'&&statements_do[j]!='('&&statements_do[j]!='{') j--;
            var variable="";
            if (j+1<k+1) variable=statements_do.substring(j+1,k+1);
            else variable="analogRead("+port+")";

            var newSerial='Serial.print('+port+');Serial.println('+variable+');\n';
            serialcode+=newSerial;
        }
        else
        if (i+10<statements_do.length && statements_do.substring(i,i+11)=='digitalRead'){
            var j=i+12;
            while (j>0&&statements_do[j]!=')')j++;
            var port=statements_do.substring(i+12,j)

            j=i-1;
            while (j>0&&statements_do[j]==' '||statements_do[j]=='=')j--;
            var k=j;
            while (j>0&&statements_do[j]!=' '&&statements_do[j]!='\n'&&statements_do[j]!=';'&&statements_do[j]!='('&&statements_do[j]!='{') j--;
            var variable="";
            if (j+1<k+1) variable=statements_do.substring(j+1,k+1);
            else variable="digitalRead("+port+")";

            var newSerial='Serial.print('+port+');Serial.println('+variable+');\n';
            serialcode+=newSerial;
        }
        else
        if (i+10<statements_do.length && statements_do.substring(i,i+11)=='analogWrite'){
            var j=i+12;
            while (j>0&&statements_do[j]!=',')j++;
            var port=statements_do.substring(i+12,j)

            var k=j;
            while (j>0&&statements_do[j]!=')')j++;
            var value=statements_do.substring(k+1,j);

            var newSerial='Serial.print('+port+');Serial.println('+value+');\n';
            tmp=tmp.substring(0,j+2)+newSerial+tmp.substring(j+2);
        }
        else
        if (i+11<statements_do.length && statements_do.substring(i,i+12)=='digitalWrite'){
            var j=i+13;
            while (j>0&&statements_do[j]!=',')j++;
            var port=statements_do.substring(i+13,j)

            var k=j;
            while (j>0&&statements_do[j]!=')')j++;
            var value=statements_do.substring(k+1,j);

            var newSerial='Serial.print('+port+');Serial.println('+value+');\n';
            tmp=tmp.substring(0,j+2)+newSerial+tmp.substring(j+2);
        }


    }
  var code = 'function setup(){\nSerial.begin(9600);\n'+statements_do0+'};\n'+'function loop(){\n'+tmp+serialcode+'}\n';

  return code;
};

Blockly.JavaScript.workspaceToCodeWithId = Blockly.JavaScript.workspaceToCode;

Blockly.JavaScript.workspaceToCode = function(workspace) {
  var code = this.workspaceToCodeWithId(workspace);
  // Strip out block IDs for readability.
  code = goog.string.trimRight(code.replace(/(,\s*)?'block_id_[^']+'\)/g, ')'))
  return code;
};
