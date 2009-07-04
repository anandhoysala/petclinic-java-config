<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<hr>
    <p id="breadcrumbs">
        You are here: <a href="http://codegolf.com/" title="Return to the front page">Home</a> ? <strong>Signup</strong>
    </p>
<form action="/user/signup" method="post">
	<script type="text/javascript">
		addEvent(window, 'load', function() {
		 document.getElementById('user_login').focus()
		});

		function addEvent(obj, evType, fn){
		 if (obj.addEventListener){
		    obj.addEventListener(evType, fn, true);
		    return true;
		 } else if (obj.attachEvent){
		    var r = obj.attachEvent("on"+evType, fn);
		    return r;
		 } else {
		    return false;
		 }
		}
	</script>
	<h1>?????????</h1>
	<table class="form-table" cellspacing="0">
        <tbody>
            <tr>
                <th><label for="user_login">?????????????????</label></th>
                    <td>
                        <input class="input-text" id="user_login" name="user[login]" size="30" value="" type="text">
                        <p class="note">What do you want to be known as? Choose carefully - this <strong>can't be changed</strong>. Shorter is generally better, but it must be <strong>at least 3 characters</strong> long.</p>
                    </td>
            </tr>
            <tr class="altRow">
                <th><label for="user_email_address">??????</label></th>
                    <td>
                        <input class="input-text" id="user_email_address" name="user[email_address]" size="30" type="text">
                        <p class="note">We'll use this to send your account details to and, optionally, for things like <strong>newly added competition alerts</strong>. We verify that the email address is valid before you can use codegolf, so make sure you <strong>use an address you have access to</strong>.</p>
                    </td>
            </tr>
            <tr>
                <th><label for="user_password">????????(password)</label></th>
                <td>
                    <input class="input-text" id="user_password" name="user[password]" size="30" value="" type="password">
                    <p class="note">Choose a password for your account. Has to be a <strong>minimum of 5 characters</strong>.</p>
                </td>
            </tr>
            <tr class="altRow">
                <th><label for="user_password_confirmation">?????? ????????(confirm password)</label></th>
                <td>
                    <input class="input-text" id="user_password_confirmation" name="user[password_confirmation]" size="30" type="password">
                    <p class="note">Better safe than sorry ...</p>
                </td>
            </tr>
        </tbody>
    </table>
	<p class="formAction">
        <input src="images/signup.png" name="login" title="Signup with these details now" type="image">
	</p>
</form>
<hr>
<div class="clear">&nbsp;</div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
