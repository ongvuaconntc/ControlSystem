'use strict';

Blockly.JavaScript['set_port_value'] = function(block) {
  var dropdown_port_name = block.getFieldValue('port_name');
  var value_portvalue = Blockly.JavaScript.valueToCode(block, 'PORTVALUE', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
   var code = 'digitalWrite('+dropdown_port_name+','+value_portvalue+');\n';
  return code;
};

Blockly.JavaScript['init_arduino'] = function(block) {
  var statements_name = Blockly.JavaScript.statementToCode(block, 'DO');
  // TODO: Assemble JavaScript into code variable.
  var code = statements_name+'\n';
  return code;
};

Blockly.JavaScript['setup_arduino'] = function(block) {
  var statements_name = Blockly.JavaScript.statementToCode(block, 'DO');
  // TODO: Assemble JavaScript into code variable.
  var code = 'function setup(){'+statements_name+'}\n';
  return code;
};

Blockly.JavaScript['set port mode'] = function(block) {
  var dropdown_portname = block.getFieldValue('PortName');
  var dropdown_portmode = block.getFieldValue('PortMode');
  // TODO: Assemble JavaScript into code variable.
  var code = 'pinMode('+dropdown_portname+','+dropdown_portmode+');\n';
  return code;
};

Blockly.JavaScript['loop_arduino'] = function(block) {
  var statements_name = Blockly.JavaScript.statementToCode(block, 'DO');
  // TODO: Assemble JavaScript into code variable.
  var code = 'function loop(){'+statements_name+'};\n';
  return code;
};

Blockly.JavaScript['get_port_value'] = function(block) {
  var dropdown_name = block.getFieldValue('NAME');
  var value_name = Blockly.JavaScript.valueToCode(block, 'NAME', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
   var code = 'digitalRead('+dropdown_name+')';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};
Blockly.JavaScript['delay_arduino'] = function(block) {
  var number_name = block.getFieldValue('NAME');
  // TODO: Assemble JavaScript into code variable.
  var code = 'delay('+number_name+');\n';
  return code;
};

Blockly.JavaScript.workspaceToCodeWithId = Blockly.JavaScript.workspaceToCode;

Blockly.JavaScript.workspaceToCode = function(workspace) {
  var code = this.workspaceToCodeWithId(workspace);
  // Strip out block IDs for readability.
  code = goog.string.trimRight(code.replace(/(,\s*)?'block_id_[^']+'\)/g, ')'))
  return code;
};
