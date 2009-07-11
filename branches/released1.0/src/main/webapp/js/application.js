window.onload = colorRows;
function colorRows() {
	if (!document.getElementsByTagName) return;
		var myTR = document.getElementsByTagName('tr');
	   	for (var i=0;i<myTR.length;i++) {
			if (i%2) {
				myTR[i].className += ' altRow';
			}
	}
}

PeriodicalExecuter.prototype.registerCallback = function() { 
	this.intervalID = setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
}

PeriodicalExecuter.prototype.stop = function() { 
	clearInterval(this.intervalID);
}