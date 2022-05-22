function submitRegData(){
    var username = document.getElementById("usernameInput").value;
    var password = document.getElementById("passwordInput").value;
    var usernameReg = /^.{5,10}$/;
    var passwordReg = /^[A-Za-z0-9]{6,10}$/;
    if (usernameReg.test(username) && passwordReg.test(password)){
        window.location.href="/user/reg?username="+username+"&password="+md5(password);
    }else{
        alert("输入的用户名或密码格式不正确！请重新输入")
    }
    // window.location.href="/user/reg?username="+username+"&password="+password;
    //异步请求$.post不会刷新页面，因此无法使用model传值


}