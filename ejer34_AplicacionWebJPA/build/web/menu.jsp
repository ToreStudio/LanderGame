<%-- 
    Document   : menu
    Created on : 26-nov-2016, 1:02:47
    Author     : PEPE
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Lander Menu</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
        <script type="text/javascript">
            function showonlyone(theform) {

                $('.hideform').each(function (index) {
                    if ($(this).attr("id") == theform) {
                        $(this).show(200);
                    } else {
                        $(this).hide(600);
                    }
                });
            }
            function clckl1() {

                document.getElementById('li1').style.color = 'white';
                document.getElementById("li2").style.color = 'green';
            }

            function clckl2() {

                document.getElementById('li1').style.color = 'green';
                document.getElementById("li2").style.color = 'white';
            }



            function checkCookie() {

                var user = getCookie("name");
                var pwd = getCookie("pass");
                if (user != "") {
                    document.getElementById("loginuser").value = user;
                    document.getElementById("loginpass").value = pwd;
                }
            }

            function getCookie(cname) {

                var name = cname + "=";
                var ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(name) == 0) {
                        return c.substring(name.length, c.length);
                    }
                }
                return "";
            }

            function validateForm() {
                var imgWrong = document.getElementById("imgWrong");
                var imgWrongpwd = document.getElementById("imgWrongpwd");
                var errorMjs = document.getElementById("errorMjs");
                var userInput = document.getElementById("userps");
                var pwdInput = document.getElementById("ps1");
                var confpwdInput = document.getElementById("ps2");

                var user = document.forms["formsign"]["usersign"].value;
                var pass = document.forms["formsign"]["passsign"].value;
                var cpass = document.forms["formsign"]["cpasssign"].value;
                if (pass != cpass) {
                    pwdInput.style.border = "1px solid red";
                    confpwdInput.style.border = "1px solid red";
                    imgWrong.style.display = 'inline';
                    errorMjs.style.backgroundColor = '#e05c67';
                    errorMjs.innerText = "Password do not match";
                    errorMjs.style.border = "solid";
                    //alert("passwords don't macth!");

                    userInput.style.border = "none";
                    imgWrongpwd.style.display = 'none';
                    return false;
                }
                if (pass == user) {
                    errorMjs.style.border = "solid";
                    errorMjs.style.backgroundColor = '#e05c67';
                    errorMjs.innerText = "Password must be different from Username!";
                    userInput.style.border = "1px solid red";
                    pwdInput.style.border = "1px solid red";
                    imgWrongpwd.style.display = 'inline';
                    confpwdInput.style.border = "1px solid red";

                    imgWrong.style.display = 'none';
                    //alert("Error: Password must be different from Username!");
                    return false;
                }
                if (pwdInput.value.length < 6) {
                    errorMjs.style.border = "solid";
                    errorMjs.style.backgroundColor = '#e05c67';
                    errorMjs.innerText = "Password must be at least 6 characters long.";
                    pwdInput.style.border = "1px solid red";
                    imgWrongpwd.style.display = 'inline';

                    imgWrong.style.display = 'none';
                    confpwdInput.style.border = "none";

                    userInput.style.border = "none";
                    return false;
                }
                userInput.style.border = "none";
                imgWrong.style.display = 'none';
                confpwdInput.style.border = "none";
                errorMjs.style.backgroundColor = 'green';
                errorMjs.innerText = "Correct!";
                errorMjs.style.border = "solid";
                pwdInput.style.border = "none";
                imgWrongpwd.style.display = 'none';
            }

            function info() {
                document.getElementById("errorMjs").style.border = "solid";
                document.getElementById("errorMjs").style.backgroundColor = 'green';
                document.getElementById("errorMjs").innerText = "Please only use letters (A-Z, any case) or numbers(0-9) when entering your username or password.";
            }
            function loading(){
                var user = document.forms["formlogin"]["userlog"].value;
                var pass = document.forms["formlogin"]["passlog"].value;
                if(user!="" && pass!=""){
                document.getElementById("1errorMjs").style.border = "solid";
                document.getElementById("1errorMjs").style.backgroundColor = 'green';
                document.getElementById("1errorMjs").innerText = "Loading...";
            }
            }
        </script>
    </head>
    <body onload="checkCookie()">
        <div id="loginmenu">
            <div class="containermenu">
                <ul>
                    <a  href="javascript:showonlyone('formsign');" >
                        <li id="li1" onclick="clckl1()">Sign up</li>
                    </a>
                    <li> &nbsp; &nbsp;</li>
                    <a  href="javascript:showonlyone('formlog');" >
                        <li id="li2" onclick="clckl2()">Log in</li>
                    </a>
                </ul>
                <br/>
                <br/>
                <div class="hideform" id="formsign">
                    <h2>Sign up</h2>
                    <br />
                    <form method="POST" onsubmit="return validateForm()" action="${pageContext.request.contextPath}/DBServlet" name="formsign">
                        <!-- onsubmit="validatePass(this);"-->
                        <center>
                            <table>
                                <tr>
                                    <td><label>User: *</label></td>
                                    <td><input type="text" title="Please only use letters (A-Z, any case) or numbers(0-9) when entering your username" placeholder="User" name="usersign" id="userps"  pattern="[a-zA-Z0-9]+" value="" required="required" /></td>
                                    <td><a onclick="info()"  href="#" >(?)</a></td>
                                </tr>
                                <tr>
                                    <td><label>Password: *</label></td>
                                    <td><input type="password" title="Please only use letters (A-Z, any case) or numbers(0-9) when entering your password" placeholder="Password"  id="ps1" name="passsign" pattern="[a-zA-Z0-9]+" value="" required="required" /></td>
                                    <td><a onclick="info()"  href="#" >(?)</a></td>
                                    <td>&nbsp;<img class="invi wrongicon" id="imgWrongpwd" src="img/wrong.png"/></td>
                                </tr>
                                <tr>
                                    <td><label id="pslabel">Confirm Password: *</label></td>
                                    <td><input type="password" title="Please only use letters (A-Z, any case) or numbers(0-9) when entering your password" placeholder="Confirm Password" id="ps2" name="cpasssign"  pattern="[a-zA-Z0-9]+" value="" required="required" /></td>
                                    <td><a onclick="info()"  href="#" >(?)</a></td>
                                    <td>&nbsp;<img class="invi wrongicon" id="imgWrong" src="img/wrong.png"/></td>
                                </tr>
                            </table>
                        </center>
                        <input type="submit"  class="Btn" value="Sign Up"/>
                        <p id="errorMjs" class="errorMjs"></p>
                    </form>
                </div>
                <div class="hideform" id="formlog"  style="display:none;">
                    <h2>Log in</h2>
                    <br/>
                    <form method="GET" name="formlogin" action="${pageContext.request.contextPath}/DBServlet"   >
                        <center>
                            <table>
                                <tr>
                                    <td><label>User: </label></td>
                                    <td><input type="text" name="userlog" placeholder="User"  id="loginuser" required="required"/></td>
                                </tr>
                                <tr>
                                    <td><label>Password: </label></td>
                                    <td><input type="password" name="passlog" placeholder="Password" id="loginpass" required="required" /></td>
                                </tr>
                            </table>
                        </center>
                        <input type="submit" onclick="loading();" class="Btn"  value="Log In"/>
                        <br/>
                        <p id="1errorMjs" class="errorMjs"></p>
                    </form >
                </div>
            </div>
        </div>
    </body>
</html>