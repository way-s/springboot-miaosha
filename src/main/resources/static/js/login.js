//判断是否为手机号的正则表达式
        function isPhoneFun(phone){
            const myreg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
            if (!myreg.test(phone)) {
                return false;
            } else {
                return true;
            }
        }
        /**
         * 检查字符串是否为合法email地址
         * @param {String} 字符串
         * @return {bool} 是否为合法email地址
         */
        function isEmail(aemail) {
            var bValidate = RegExp(/^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(aemail);
            if (bValidate) {
                return true;
            } else {
                return false;
            }
        }
        let setTime = 60;
        function Countdown(obj) {
            if (setTime === 0) {
                obj.prop('disabled', false);
                obj.val("获取验证码");
                setTime = 60;
            } else {
                setTime--;
                obj.prop('disabled', true);
                obj.val(setTime + "s后重新发送");
                setTimeout(function () {
                    Countdown(obj)
                }, 1000); //每1000毫秒执行一次
            }
        }

        $(document).ready(function () {
            $('#horizontalTab').easyResponsiveTabs({
                type: 'default', //Types: default, vertical, accordion
                width: 'auto', //auto or any width like 600px
                fit: true   // 100% fit in a container
            });

            $("#emailBottom").click(function () {
                var email = $("#email").val();
                var password = $("#password2").val();
                if (email === "" || password === "") {
                    alert("请填写邮箱和验证码！");
                    return false;
                } else if (email !== "" || email !== null || password !== "" || password !== null) {
                    if (!isEmail(email)) {
                        alert("邮箱格式错误！");
                        return false;
                    }
                }
                const xhr = new XMLHttpRequest();
                xhr.withCredentials = true;
                xhr.open("post",'email/check?password='+password);
                xhr.send();
                xhr.onload=function(){
                    if(xhr.responseText==="200"){
                        alert("登录成功！");
                    }else {
                        alert("验证码错误！");
                    }
                };

            });

            $("#sendEmail").click(function () {
                const email = $("#email").val();
                if (email === "" || email === null) {
                    alert("请填写邮箱！");
                    return false;
                } else if (email !== "" || email !== null) {
                    if (!isEmail(email)) {
                        alert("邮箱格式错误");
                        return false;
                    }
                }
                var xhr = new XMLHttpRequest();
                xhr.open("post", 'email/login?email=' + email);
                xhr.send();
                xhr.onload = function () {
                    if (xhr.readyState===4 && xhr.status === 200) {
                        alert("发送成功！");
                    } else {
                        alert("发送错误，请重试！")
                    }
                }
                Countdown($("#sendEmail"));//开始倒计时
            });

            $("#phoneButton").click(function () {
                var phone = $("#phone").val();
                var password = $("#password1").val();
                if (phone === "" || password === "") {
                    alert("手机号和密码不能为空！");
                    return false;
                }else if (phone !== "" || phone !== null) {
                    if (!isPhoneFun(phone)) {
                        alert("手机号格式错误");
                        return false;
                    }
                }
                const xhr = new XMLHttpRequest();
                xhr.withCredentials = true;
                xhr.open("post",'email/check?password='+password);
                xhr.send();
                xhr.onload=function(){
                    if(xhr.responseText==="200"){
                        alert("登录成功！");
                    }else {
                        alert("验证码错误！");
                    }
                };
            });

            $("#sendPhone").click(function () {
                const phone = $("#phone").val();
                if (phone === "" || phone === null) {
                    alert("请填写手机号！");
                    return false;
                } else if (phone !== "" || phone !== null) {
                    if (!isPhoneFun(phone)) {
                        alert("手机号格式错误");
                        return false;
                    }
                }
                const xhr = new XMLHttpRequest();
                xhr.open("post", 'phone/login?phone=' + phone);
                xhr.send();
                xhr.onload = function () {
                    if (xhr.readyState===4 && xhr.status === 200) {
                        alert("发送成功！");
                    } else {
                        alert("发送错误，请重试！")
                    }
                }
                Countdown($("#sendPhone"));//开始倒计时
            });

        });