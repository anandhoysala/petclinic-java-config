// (c) Jessop Industries.  Could It Be Any Longer? (tm)
function choose_lang(field){
	switch(field.value.replace(/.+\.(.+)/, "$1")) {
		case 'pl':
			update_select("perl");
			break
		case 'rb':
			update_select("ruby");
			break
		case 'php':
			update_select("php");
			break;
		case 'py':
			update_select("python");
			break;
		default:
			//nothing
	}
}

function update_select(lang) {
	select = document.getElementById('lang_select');
	for ( var i=0, len=select.options.length; i<len; ++i ) {
		if(select.options[i].value == lang) {
			select.options[i].selected = true;
		} else {
			select.options[i].selected = false;
		}
	}
}