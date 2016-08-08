var front = {
    modules : {},
    util: {}
};

front.modules = (function () {
    var $$modules = {}
    var _random = function () {
        return ((1+Math.random()) * 0x10000|0).toString(16).substring(1);
    }
    var _uuid = function() {
        return [_random()+_random(), _random(), _random(), _random(), _random()+_random()+_random()].join("-");
    }
    var _register = function (oInstance, params) {
        if (oInstance == null || oInstance == undefined) return;
        if (typeof oInstance === "function") oInstance = new oInstance(params || {});
        var instanceName = _uuid();
        try {
            $$modules[instanceName] = oInstance;
            console.log(instanceName + " be listed on modules.");
        } catch (e) {
            console.log(instanceName + " be failed on modules.");
        }
    }
    var _broadcast = function (broadCastName, params) {
        for (var module in $$modules)  if (broadCastName in $$modules[module]) $$modules[module][broadCastName](params || {});
    }

    var logger = {
        oldConsoleLog: null,
        enable: function() {
            if ( this.oldConsoleLog == null) return;
            window['console']['log'] = this.oldConsoleLog;
        },
        disable: function() {
            this.oldConsoleLog = console.log;
            window['console']['log'] = function(){};
        }
    };
    window.addEventListener("load", function() {
        _broadcast("$$START_UP$$");
    });
    return {
        staticVersion: /*[[${staticVersion}]]*/ '1',
        register: _register,
        enableLogger: function () {
            logger.enable()
        },
        disableLogger: function () {
            logger.disable()
        },
        broadcast: _broadcast
    }
})();

front.util =(function(){
    var _stringByteLength = function (source) {
        return (function (s, b, i, c) {
            for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
            return b;
        })(source);
    };
    var _cloneObject = function (obj) {
        if (null == obj || "object" != typeof obj) return obj;
        var copy = obj.constructor();
        for (var attr in obj) {
            if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
        }
        return copy;
    };
    var _isSameArray = function (a, b) {
        var i = 0, j;
        if (typeof a == "object" && a) {
            if (Array.isArray(a)) {
                if (!Array.isArray(b) || a.length != b.length) return false;
                for (j = a.length; i < j; i++) if (!_isSameArray(a[i], b[i])) return false;
                return true;
            } else {
                for (j in b) if (b.hasOwnProperty(j)) i++;
                for (j in a) if (a.hasOwnProperty(j)) {
                    if (!_isSameArray(a[j], b[j])) return false;
                    i--;
                }
                return !i;
            }
        }
        return a === b;
    };
    var _regexCheck = function (regex, val) {
        return regex.test(val);
    }
    var _runCallback = function (callback) {
        if (typeof callback === 'function') callback();
    };
    var _getCookie = function (name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    };
    var _centerPopup = function(w, h, optional) {
        var windowWidth = w;
        var windowHeight = h;
        var windowLeft = 0;
        var windowTop = 0;
        if (typeof window.screenX != "undefined" && (window.screenX >= 0 && window.screenY >= 0)) { // Chrome
            windowLeft = (window.screen.availWidth - windowWidth) / 2;
            windowTop = (window.screen.availHeight - windowHeight) / 2;
        } else if (typeof window.screenLeft != "undefined" && (window.screenLeft >= 0 && window.screenTop >= 0)) {// IE
            windowLeft = (window.screen.availWidth - windowWidth) / 2;
            windowTop = (window.screen.availHeight - windowHeight) / 2;
        }
        var addOption="";
        if (typeof optional === "undefined") {} else { addOption = optional; }
        return {
            position: {
                windowWidth:windowWidth,
                windowHeight:windowHeight,
                windowLeft:windowLeft,
                windowTop:windowTop
            },
            option: 'width=' + windowWidth + ',height=' + windowHeight + ',left=' + windowLeft + ',top=' + windowTop+addOption
        };
    };
    return {
        byteLength: _stringByteLength,
        cloneObject: _cloneObject,
        isSameArray: _isSameArray,
        regex: _regexCheck,
        callback: _runCallback,
        getCookie: _getCookie,
        popup: _centerPopup
    };
})();

front.modules.register(function() {
    /* 테스트 모듈 1*/
    return {
        "$$START_UP$$" : function() {
            if (/*[[${isRealMode}]]*/false) front.modules.disableLogger();
            console.log("테스트 모듈 1 등록 후 StartUp 호출됨");
        }
    }
});

front.modules.register(function() {
    var header = $(".navbar-wrapper .container");
    return {
        "$$START_UP$$" : function() {
            if ( jQuery ) {
                $("#search").click(function () {
                    location.href="?q="+$("#q").val();
                });
                $(document).bind("scroll", function() {
                    var sTop = document.documentElement.scrollTop || document.body.scrollTop;
                    var hTop = header.offset().top;
                    console.log("!", sTop < hTop);
                });

            }
        }
    }
});