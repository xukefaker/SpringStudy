//请求后台传文章数据过来
function displayArticles(){
  var username = $("#username-box").text();
  var sourceBox = document.getElementById("source-container");
  var articleBox = document.getElementById("article-container");
  var informationBox = document.getElementById("information-container");
  var changeInfoBox = document.getElementById("popOut-info");
  document.getElementById("popOut-source").style.display = "none";
  document.getElementById("popOut-wechat").style.display = "none";
  sourceBox.style.display = "none";
  changeInfoBox.style.display = "none";
  articleBox.style.display = "block";
  informationBox.style.display = "none";
  $.post("/user/fetchArticles", {"username":username}, function (data){
    addArticleToPage(data)
  });
}

function addArticleToPage(datas){
  $("#article-list").html("<h2>文章展示</h2>");
  for (let i=0;i<datas.length;i++){
    let data=datas[i];
    $("#article-list").append("            <div class=\"blogs\">\n" +
          "              <h3><a href=\"/\">"+data.title+"</a></h3>\n" +
          "              <ul>\n" +
          " "+data.description+"\n" +
          "                <a href='"+data.link+"' target=\"_blank\" class=\"readmore\">阅读全文&gt;&gt;</a>\n" +
          "              </ul>\n" +
          "              <p class=\"autor\"><span>来源：【<a href=\"/\">"+data.platform+"</a>】</span><span>【<a href=\"/\">取消订阅</a>】</span><span>发布日期:"+data.pubDate+"</span></p>\n" +
          "            </div>")
  }

}

//请求后台刷新文章
function refreshArticles(){
  var username = $("#username-box").text();
  $.post("/user/refreshArticles", {"username":username}, function (data){
    console.log(data);
  });
}

function displayPersonalInformation(){
  var sourceBox = document.getElementById("source-container");
  var articleBox = document.getElementById("article-container");
  var informationBox = document.getElementById("information-container");
  var changeInfoBox = document.getElementById("popOut-info");
  document.getElementById("popOut-source").style.display = "none";
  document.getElementById("popOut-wechat").style.display = "none";
  changeInfoBox.style.display = "none";
  sourceBox.style.display = "none";
  articleBox.style.display = "none";
  informationBox.style.display = "block";

}

function displaySources(){
  sourceManage();
  var sourceBox = document.getElementById("source-container");
  var articleBox = document.getElementById("article-container");
  var informationBox = document.getElementById("information-container");
  var changeInfoBox = document.getElementById("popOut-info");
  document.getElementById("popOut-source").style.display = "none";
  document.getElementById("popOut-wechat").style.display = "none";
  changeInfoBox.style.display = "none";
  sourceBox.style.display = "block";
  articleBox.style.display = "none";
  informationBox.style.display = "none";
}

function quickAddSource() {
  var username = $("#username-box").text();
  let url= document.getElementById("quickAddInput").value;

  $.post("/user/addSubscribe",{"url":url, "username":username},function (data){
      alert(data);
  });

}

function quickChangeInfo(){
  var originalUsername = $("#username-box").text();
  var usernameData = $("#quickChangeUsername").val();
  var passwordData = md5($("#quickChangePassword").val());

  $.post("/user/quickChangeInfo", {"originalUsername":originalUsername,"username":usernameData, "password":passwordData}, function (data){
    alert(data);
  });
}

function sourceManage(){
  var username = $("#username-box").text();
  var sourceManager = $("#source-manager");
  var sourceBin = $("#source-bin");

  sourceManager.html("            <div class=\"aui-content-up-form\">\n" +
      "              <h2>订阅源管理</h2>\n" +
      "            </div>");

  sourceBin.html("            <div class=\"aui-content-up-form\">\n" +
      "              <h2>订阅源回收站</h2>\n" +
      "            </div>");

  $.post("/user/getCurrentSubscribe", {"username":username}, function (data){
    for(let i=0;i<data.length;i++)
      addElement2SourceManager(data[i]['class'], data[i]['url'], data[i]['userID'], data[i]['sourceID']);
  });
  $.post("/user/getDeletedSubscribe", {"username":username}, function (data){
    for (let i = 0; i < data.length; i++){
      addElement2SourceBin(data[i]['url'], data[i]['date'], data[i]['userID'], data[i]['sourceID']);
    }
  });

}
function resubscribe(e, userID, sourceID){
  $.post("/user/resubscribe", {"userID":userID, "sourceID":sourceID}, function (data){
    $(e).parent().remove();
    addElement2SourceManager(data['class'], data['url'], userID, sourceID);


  });
}
function unsubscribeForever(e, userID, sourceID){
  var sourceManager = $("#source-manager");
  $.post("/user/unsubscribeForever", {"userID":userID, "sourceID":sourceID}, function (data){
    $(e).parent().remove();
  });
}

function addElement2SourceManager(class2URL, url, userID, sourceID){
  var sourceManager = $("#source-manager");
  sourceManager.append("            <div class=\"aui-form-group clear\">\n" +
      "              <label class=\"aui-label-control\">\n" +
      "                "+class2URL+"\n" +
      "              </label>\n" +
      "              <div class=\"aui-form-input url-box\">\n" +
      "                <span class=\"urls\">"+url+"</span>\n" +
      "              </div>\n" +
      "              <a class=\"button button-glow button-rounded button-caution\" onclick='unsubscribe(this,"+userID+","+sourceID+",\""+url+"\")'>取消订阅</a>\n" +
      "              <span class=\"button-dropdown button-dropdown-plain\" data-buttons=\"dropdown\">\n" +
      "                <button class=\"button button-caution button-pill\">\n" +
      "                  分类\n" +
      "                  <i class=\"fa fa-caret-down\"></i>\n" +
      "                </button>\n" +
      "                <ul class=\"button-dropdown-list\">\n" +
      "                  <li><a onclick='sourceClass(\"教育\","+userID+","+sourceID+")'>教育</a></li>\n" +
      "                  <li class=\"button-dropdown-divider\">\n" +
      "                    <a onclick='sourceClass(\"娱乐\","+userID+","+sourceID+")'>娱乐</a>\n" +
      "                  </li>\n" +
      "                  <li>\n" +
      "                    <a onclick='sourceClass(\"博客\","+userID+","+sourceID+")'>博客</a>\n" +
      "                  </li>\n" +
      "                </ul>\n" +
      "              </span>\n" +
      "            </div>")
}
function addElement2SourceBin(url, date, userID, sourceID){
  var sourceBin = $("#source-bin");
  sourceBin.append("                      <div class=\"aui-form-group clear\">\n" +
      "                        <label class=\"aui-label-control\">\n" +
      "                          "+url+"\n" +
      "                        </label>\n" +
      "                        <div class=\"aui-form-input url-box\">\n" +
      "                          <span class=\"urls\">"+date+"</span>\n" +
      "                        </div>\n" +
      "                        <a class=\"button button-glow button-rounded button-caution \" onclick='resubscribe(this,"+userID+","+sourceID+")'>恢复订阅</a>\n" +
      "                        <a class=\"button button-glow button-rounded button-caution\"  onclick='unsubscribeForever(this,"+userID+","+sourceID+")'>彻底删除</a>\n" +
      "                      </div>");
}


function unsubscribe(e,userID, sourceID, url){
  $.post("/user/unsubscribe", {"userID":userID, "sourceID":sourceID}, function (data){
    $(e).parent().remove();
    addElement2SourceBin(url, new Date().toUTCString(), userID, sourceID);
  });
}
function sourceClass(sourceClassName, userID, sourceID){
  $.post("/user/sourceClass", {"sourceClassName": sourceClassName, "userID":userID, "sourceID":sourceID}, function (data){
    alert(data);
  })
}



function submitInfoChangeData(){
  if (checkUsernameInput() && checkPasswordInput() && checkPhoneInput() && checkBirthInput() && checkEmailInput()){
    let originalUsername = $("#username-box").text();
    let usernameInputValue = $("#changeUsernameInput").val();
    let passwordInputValue = md5($("#changePasswordInput").val());
    let birthInputValue = $("#changeBirthInput").val();
    let emailInputValue = $("#changeEmailInput").val();
    let phoneInputValue = $("#changePhoneInput").val();

    $.post("/user/changeInfo", {"originalUsername":originalUsername, "username":usernameInputValue, "password":passwordInputValue, "email":emailInputValue, "birth":birthInputValue, "phone":phoneInputValue}, function (data){
      if (data == "修改成功！"){
        alert("修改成功！请重新登录");
        window.location.href = "/";
      }else
        alert(data);
    });
  }else{
    alert("请正确填写信息！");
  }

}
function applyInfoEdit(){
  console.log(1);
  $("#changeUsernameInput").attr("readonly", false);
  $("#changeUsernameInput").click(function (){
    this.placeholder = "";
  })
  $("#changePasswordInput").attr("readonly", false);
  $("#changePasswordInput").click(function (){
    this.placeholder = "";
  })
  $("#changeEmailInput").attr("readonly", false);
  $("#changeEmailInput").click(function (){
    this.placeholder = "";
  })
  $("#changeBirthInput").attr("readonly", false);
  $("#changeBirthInput").click(function (){
    this.placeholder = "";
  })
  $("#changePhoneInput").attr("readonly", false);
  $("#changePhoneInput").click(function (){
    this.placeholder = "";
  })
}

//检测用户名合法性,长度5-10个字符
function checkUsernameInput(){
  var usernameReg = /^.{5,10}$/;
  var usernameInputValue = $("#changeUsernameInput").val();
  return usernameReg.test(usernameInputValue);
}
//检测密码合法性,必须为大小写字母和数字的组合，长度为6-10个字符
function checkPasswordInput(){
  //js正则表达式用/.../括起来
  var passwordReg = /^[A-Za-z0-9]{6,10}$/;
  var passwordInputValue = $("#changePasswordInput").val();
  return passwordReg.test(passwordInputValue);
}
//检测手机号合法性,长度必须为11位数字
function checkPhoneInput(){
  var phoneReg = /^\d{11}$/;
  var phoneInputValue = $("#changePhoneInput").val();
  return phoneReg.test(phoneInputValue);
}
//检测出生年月合法性,格式为 yyyy-mm-dd
function checkBirthInput(){
  var birthInputValue = $("#changeBirthInput").val();
  var birthReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
  return birthReg.test(birthInputValue);
}
//检测邮箱输入合法性
function checkEmailInput(){
  var emailInputValue = $("#changeEmailInput").val();
  var emailReg = /^[A-Za-z0-9._%-]+@([A-Za-z0-9-]+\.)+[A-Za-z]{2,4}$/;
  return emailReg.test(emailInputValue);
}
window.onload = function (){
  $("#changeUsernameInput").blur(function (){
    var usernameTip = $("#usernameTip");
    if (checkUsernameInput()  || ($("#changeUsernameInput").attr("readonly") == "readonly")){
      usernameTip.css("color","#999")
    }else{
      usernameTip.css("color", "red");
    }
  })
  $("#changePasswordInput").blur(function (){
    var passwordTip = $("#passwordTip");
    if (checkPasswordInput()  || ($("#changePasswordInput").attr("readonly") == "readonly")){
      passwordTip.css("color","#999");
    }else{
      //jquery通过css来改变style
      passwordTip.css("color","red");
    }
  })
  $("#changePhoneInput").blur(function (){
    var phoneTip = $("#phoneTip");

    if (checkPhoneInput() || ($("#changePhoneInput").attr("readonly") == "readonly")){
      phoneTip.css("color","#999")
    }else{
      phoneTip.css("color", "red");
    }
  })
  $("#changeBirthInput").blur(function (){
    var birthTip = $("#birthdayTip");
    if (checkBirthInput()  || ($("#changeBirthInput").attr("readonly") == "readonly")){
      birthTip.css("color","#999")
    }else{
      birthTip.css("color", "red");
    }
  })
  $("#changeEmailInput").blur(function (){
    var emailTip = $("#emailTip");
    if (checkEmailInput()  || ($("#changeEmailInput").attr("readonly") == "readonly")){
      emailTip.css("color","#999");
    }else{
      emailTip.css("color","red");
    }
  })


};
