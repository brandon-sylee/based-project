var front = (function () {
    var $$modules = {};
    var _random = function () {
        return Math.floor(Math.random() * 10000) + 1;
    };
    var _log = function (v1, v2, v3, v4, v5, v6) {
        if (/*[[${isRealMode}]]*/true) console.log(v1, v2 || '', v3 || '', v4 || '', v5 || '', v6 || '');
    };
    var _register = function (oInstance, params) {
        if (oInstance == null || oInstance == undefined) return;
        if (typeof oInstance === "function") oInstance = new oInstance(params || {});
        var instanceName = "anony" + _random() + "mous" + _random();
        try {
            $$modules[instanceName] = oInstance;
            _log(instanceName + " be listed on modules.");
        } catch (e) {
            _log(instanceName + " be failed on modules.");
        }
    };
    var _broadcast = function (broadCastName, params) {
        for (var module in $$modules)  if (broadCastName in $$modules[module]) $$modules[module][broadCastName](params || {});
    };
    return {
        staticVersion: /*[[${staticVersion}]]*/ '1',
        core: {
            register: _register,
            broadcast: _broadcast
        },
        util: {
            log: _log,
            test: function () {
                console.log(/*[[#{title}]]*/"Awesome!!!!");
            }
        }
    }
})();