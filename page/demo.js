var protocol = 'ws'; // ws 或 wss
var ip = '127.0.0.1'
var port = 9326

var heartbeatTimeout = 5000; // 心跳超时时间，单位：毫秒
var reconnInterval = 1000; // 重连间隔时间，单位：毫秒

var binaryType = 'blob'; // 'blob' or 'arraybuffer';//arraybuffer是字节
var handler = new DemoHandler()

var tiows

function initWs () {
  var queryString = 'name=科比&name=科比&name=库里'
  var param = null
  tiows = new tio.ws(protocol, ip, port, queryString, param, handler, heartbeatTimeout, reconnInterval, binaryType)
  tiows.connect()
}



function send () {
  var msg = document.getElementById('textId')
  tiows.send(msg.value)
}

function clearMsg () {
  document.getElementById('contentId').innerHTML = ''
}

initWs()