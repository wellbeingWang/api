/*! Copyright 2014 Panshi Inc. All Rights Reserved. */
(function(c, a) {
    var b = {
        version: "3",
        adUrl: "http://t.adyun.com/adshow?v=3"
    };
    b.ad = b.ad || {
            seed: 0,
            iframe_preffix: "panshi_ad_frame"
        };
    b.ad.image = b.ad.image || {
            minWidth: 300,
            minHeight: 200,
            containerId: null,
            arrList: []
        };
    b.ad.kinds = {
        TD: 1,
        LINK: 2,
        SIDE: 3,
        BUTTON: 4,
        WINDOW: 5,
        FMEDIA: 7,
        FLOAT: 9
    };
    b.lang = b.lang || {};
    b.lang.isString = function(d) {
        return "[object String]" == Object.prototype.toString.call(d)
    };
    b.isString = b.lang.isString;
    b.lang.isFunction = function(d) {
        return "[object Function]" == Object.prototype.toString.call(d)
    };
    b.lang.isArray = function(d) {
        return "[object Array]" == Object.prototype.toString.call(d)
    };
    b.lang.gc = function(d) {
        return d != null ? '"' + d + '"': '""'
    };
    b.gc = b.lang.gc;
    b.lang.getDate = function() {
        return new Date()
    };
    b.gt = b.lang.getDate;
    b.lang.encode = function(m, d) {
        var f;
        var g;
        var h = [];
        var n = [121, 113, 105, 97, 89, 81, 73, 65, 57, 49, 41, 33, 25, 17, 9, 1, 255, 247, 239, 231, 223, 215, 207, 199, 191, 183, 175, 167, 159, 151, 143, 135, 123, 115, 107, 99, 91, 83, 75, 67, 59, 51, 43, 35, 27, 19, 11, 3, 249, 241, 233, 225, 217, 209, 201, 193, 185, 177, 169, 161, 153, 145, 137, 129, 125, 117, 109, 101, 93, 85, 77, 69, 61, 53, 45, 37, 29, 21, 13, 5, 251, 243, 235, 227, 219, 211, 203, 195, 187, 179, 171, 163, 155, 147, 139, 131, 127, 119, 111, 103, 95, 87, 79, 71, 63, 55, 47, 39, 31, 23, 15, 7, 248, 240, 232, 224, 216, 208, 200, 192, 184, 176, 168, 160, 152, 144, 136, 128, 120, 112, 104, 96, 88, 80, 72, 64, 56, 48, 40, 32, 24, 16, 8, 0, 252, 244, 236, 228, 220, 212, 204, 196, 188, 180, 172, 164, 156, 148, 140, 132, 122, 114, 106, 98, 90, 82, 74, 66, 58, 50, 42, 34, 26, 18, 10, 2, 254, 246, 238, 230, 222, 214, 206, 198, 190, 182, 174, 166, 158, 150, 142, 134, 124, 116, 108, 100, 92, 84, 76, 68, 60, 52, 44, 36, 28, 20, 12, 4, 253, 245, 237, 229, 221, 213, 205, 197, 189, 181, 173, 165, 157, 149, 141, 133, 126, 118, 110, 102, 94, 86, 78, 70, 62, 54, 46, 38, 30, 22, 14, 6, 250, 242, 234, 226, 218, 210, 202, 194, 186, 178, 170, 162, 154, 146, 138, 130];
        var e = new Array();
        e[0] = (m & 255);
        e[1] = (m >> 8 & 255);
        e[2] = (m >> 16 & 255);
        e[3] = (m >> 24 & 255);
        var l = 0;
        var k = 0;
        while (l < d.length) {
            f = d.charCodeAt(l);
            f ^= e[k];
            g = n[f].toString(16);
            if (g.length == 1) {
                h.push("0")
            }
            h.push(g);
            k++;
            k %= 4;
            l++
        }
        return h.join("").toUpperCase()
    };
    b.lang.dwrite = function(d) {
        document.write(d)
    };
    b.wr = b.lang.dwrite;
    b.lang.string = b.lang.string || {};
    b.lang.string.toCamelCase = function(d) {
        if (d.indexOf("-") < 0 && d.indexOf("_") < 0) {
            return d
        }
        return d.replace(/[-_][^-_]/g,
            function(e) {
                return e.charAt(1).toUpperCase()
            })
    };
    b.string = b.lang.string;
    b.array = b.array || {};
    b.array.removeAt = function(d, e) {
        return d.splice(e, 1)[0]
    };
    b.lang.toArray = function(d) {
        if (d === null || d === undefined) {
            return []
        }
        if (b.lang.isArray(d)) {
            return d
        }
        if (typeof d.length !== "number" || typeof d === "string" || b.lang.isFunction(d)) {
            return [d]
        }
        if (d.item) {
            var e = d.length,
                f = new Array(e);
            while (e--) {
                f[e] = d[e]
            }
            return f
        }
        return [].slice.call(d)
    };
    b.browser = b.browser || {};
    b.browser.ie = b.ie = /msie (\d+\.\d+)/i.test(navigator.userAgent) ? document.documentMode || +parseFloat(RegExp["$1"]) : 0;
    b.browser.opera = /opera(\/| )(\d+(\.\d+)?)(.+?(version\/(\d+(\.\d+)?)))?/i.test(navigator.userAgent) ? +(RegExp["$6"] || RegExp["$2"]) : 0;
    b.browser.isWebkit = /webkit/i.test(navigator.userAgent);
    b.browser.isGecko = /gecko/i.test(navigator.userAgent) && !/like gecko/i.test(navigator.userAgent);
    b.browser.isMobile = /AppleWebKit.*Mobile.*/i.test(navigator.userAgent);
    b.browser.isIos = /\(i[^;]+;( U;)? CPU.+Mac OS X/i.test(navigator.userAgent);
    b.browser.isAndroid = /android/i.test(navigator.userAgent);
    b.browser.isStrict = document.compatMode == "CSS1Compat";
    b.dom = b.dom || {
            backCompat: "BackCompat",
            documentElement: "documentElement"
        };
    b.dom.g = function(d) {
        if (b.isString(d)) {
            return document.getElementById(d)
        } else {
            if (d && d.nodeName && (d.nodeType == 1 || d.nodeType == 9)) {
                return d
            }
        }
        return null
    };
    b.g = b.dom.g;
    b.dom.getDocument = function(d) {
        d = b.dom.g(d) || window;
        return d.nodeType == 9 ? d: d.ownerDocument || d.document
    };
    b.gd = b.dom.getDocument;
    b.dom.createElement = function(d, g) {
        var f = b.isString(d) ? document.createElement(d) : d;
        g = b.isString(g) ? b.g(g) : (g || document.body);
        g.appendChild(f);
        return f
    };
    b.c = b.dom.createElement;
    b.dom.getComputedStyle = function(e, f) {
        e = b.dom.g(e);
        var g = b.dom.getDocument(e),
            h;
        if (g.defaultView && g.defaultView.getComputedStyle) {
            h = g.defaultView.getComputedStyle(e, null);
            if (h) {
                return h[f] || h.getPropertyValue(f)
            }
        }
        return ""
    };
    b.dom.styleFixer = b.dom.styleFixer || {};
    b.dom.styleFilter = b.dom.styleFilter || [];
    b.dom.styleFilter.filter = function(g, j, i) {
        for (var h = 0,
                 k = b.dom.styleFilter,
                 l; l = k[h]; h++) {
            if (l = l[i]) {
                j = l(g, j)
            }
        }
        return j
    };
    b.dom.getStyle = function(j, f) {
        var h = b.dom;
        j = h.g(j);
        f = b.string.toCamelCase(f);
        var i = j.style[f] || (j.currentStyle ? j.currentStyle[f] : "") || h.getComputedStyle(j, f);
        if (!i) {
            var g = h.styleFixer[f];
            if (g) {
                i = g.get ? g.get(j) : b.dom.getStyle(j, g)
            }
        }
        if (g = h.styleFilter) {
            i = g.filter(f, i, "get")
        }
        return i
    };
    b.dom.setStyle = function(f, d, h) {
        var g = b.g(f);
        g.style[d] = h
    };
    b.dom.getPosition = function(x) {
        x = b.dom.g(x);
        var o = b.dom.getDocument(x),
            u = b.browser,
            r = b.dom.getStyle,
            v = u.isGecko > 0 && o.getBoxObjectFor && r(x, "position") == "absolute" && (x.style.top === "" || x.style.left === ""),
            q = {
                left: 0,
                top: 0
            },
            s = u.ie && !u.isStrict ? o.body: o.documentElement,
            n,
            w;
        if (x == s) {
            return q
        }
        if (x.getBoundingClientRect) {
            w = x.getBoundingClientRect();
            q.left = Math.floor(w.left) + Math.max(o.documentElement.scrollLeft, o.body.scrollLeft);
            q.top = Math.floor(w.top) + Math.max(o.documentElement.scrollTop, o.body.scrollTop);
            q.left -= o.documentElement.clientLeft;
            q.top -= o.documentElement.clientTop;
            var p = o.body,
                m = parseInt(r(p, "borderLeftWidth")),
                t = parseInt(r(p, "borderTopWidth"));
            if (u.ie && !u.isStrict) {
                q.left -= isNaN(m) ? 2 : m;
                q.top -= isNaN(t) ? 2 : t
            }
        } else {
            n = x;
            do {
                q.left += n.offsetLeft;
                q.top += n.offsetTop;
                if (u.isWebkit > 0 && r(n, "position") == "fixed") {
                    q.left += o.body.scrollLeft;
                    q.top += o.body.scrollTop;
                    break
                }
                n = n.offsetParent
            } while ( n && n != x );
            if (u.opera > 0 || u.isWebkit > 0 && r(x, "position") == "absolute") {
                q.top -= o.body.offsetTop
            }
            n = x.offsetParent;
            while (n && n != o.body) {
                q.left -= n.scrollLeft;
                if (!u.opera || n.tagName != "TR") {
                    q.top -= n.scrollTop
                }
                n = n.offsetParent
            }
        }
        return q
    };
    b.gp = b.dom.getPosition;
    b.dom.addEvent = function(g, e, h) {
        var i = function() {
            h.call(g, e)
        };
        e = e.replace(/^on/i, "").toLowerCase();
        b.isString(g) && (g = b.g(g));
        g.addEventListener ? g.addEventListener(e, i, !1) : g.attachEvent && g.attachEvent("on" + e, i)
    };
    b.ae = b.dom.addEvent;
    b.page = b.page || {};
    b.page.getLocation = function() {
        return window.preview_site || window.location.href
    };
    b.page.getQuery = function() {
        return window.location.search
    };
    b.page.getViewHeight = function() {
        var d = document,
            e = d.compatMode == "BackCompat" ? d.body: d.documentElement;
        return e.clientHeight
    };
    b.page.getViewWidth = function() {
        var d = document,
            e = d.compatMode == "BackCompat" ? d.body: d.documentElement;
        return e.clientWidth
    };
    b.page.getScrollLeft = function() {
        var d = document;
        return window.pageXOffset || d.documentElement.scrollLeft || d.body.scrollLeft
    };
    b.page.getScrollTop = function() {
        var d = document;
        return window.pageYOffset || d.documentElement.scrollTop || d.body.scrollTop
    };
    b.cookie = b.cookie || {};
    b.cookie._isValidKey = function(d) {
        return new RegExp('^[^\\x00-\\x20\\x7f\\(\\)<>@,;:\\\\\\"\\[\\]\\?=\\{\\}\\/\\u0080-\\uffff]+$').test(d)
    };
    b.cookie.set = function(h, g, e) {
        if (!b.cookie._isValidKey(h)) {
            return
        }
        e = e || {};
        var f = e.expires;
        if ("number" == typeof e.expires) {
            f = new Date();
            f.setTime(f.getTime() + e.expires)
        }
        document.cookie = encodeURIComponent(h) + "=" + encodeURIComponent(g) + (e.path ? "; path=" + e.path: "; path=/") + (f ? "; expires=" + f.toUTCString() : "") + (e.domain ? "; domain=" + e.domain: "") + (e.secure ? "; secure": "")
    };
    b.cookie.get = function(d) {
        if (b.cookie._isValidKey(d)) {
            var f = new RegExp("(^| )" + encodeURIComponent(d) + "=([^;]*)(;|$)"),
                e = f.exec(document.cookie);
            if (e) {
                return decodeURIComponent(e[2]) || null
            }
        }
        return null
    };
    b.ad.getIndex = function(e, f) {
        window[e] != null ? ++window[e] : window[e] = f;
        return window[e]
    };
    b.ad.GC = function() {
        return this.getIndex("conIndex", 1)
    };
    b.ad.GA = function() {
        return this.getIndex("adIndex", 0)
    };
    b.ad.getUrl = function(e, d, f) {
        this.seed = Math.ceil(Math.random() * 10000000);
        return b.adUrl + ["&a=" + e, "b=" + d, "d=" + this.seed, "c=" + b.lang.encode(this.seed, encodeURIComponent(f)), "g=" + this.GA()].join("&")
    };
    b.ad.GF = function(d, f, g) {
        var e = this.iframe_preffix + "_" + this.GC();
        return ["<iframe id=", b.gc(e), " name=", b.gc(e), " width=", b.gc(d), " height=", b.gc(f), 'frameborder="0" src=', b.gc(g), ' marginwidth="0" marginheight="0" vspace="0" hspace="0" allowtransparency="true" scrolling="no"></iframe>'].join("")
    };
    b.ad.css = b.ad.css || {};
    b.ad.css.box = {
        all: "maring:0;padding:0;display:block;visibility:visible;border:none;background:none;float:none;overflow:hidden;z-index:2147483640;",
        base: "width:100%;height:100%;left:0;top:0;",
        close: "width:50px;height:20px;right:0;position:absolute;cursor:pointer;background:#fff;filter:alpha(opacity=0);opacity:0;cursor:pointer;z-index:2147483647;",
        x: {
            left: "left",
            right: "right"
        },
        y: {
            top: "top",
            bottom: "bottom"
        },
        offset: {
            top: 20,
            bottom: 1,
            left: 1,
            right: 1
        }
    };
    b.ad.css.image = {
        all: "position:absolute;margin:0;padding:0;overflow:hidden;text-align:left;z-index:2147483647;",
        baseHeight: 24,
        Height: 46
    };
    b.ad.GB = function(l, n, o, p, g) {
        if (!document.body) {
            return ! 1
        }
        var d = "panshi_ad_box_" + this.GC(),
            q = b.ie,
            j = q == 0 || (q >= 7 && document.compatMode != b.dom.backCompat);
        var k = this.css.box.all + "width:" + p + "px;height:" + g + "px;position:" + (j ? "fixed": "absolute") + ";" + this.css.box.x[o] + ":" + this.css.box.offset[o] + "px;" + this.css.box.y[n] + ":" + this.css.box.offset[n] + "px;";
        var m = "<div id=" + b.gc(d) + " style=" + b.gc(k) + "><div onclick='this.parentNode.style.display=\"none\"' style=" + b.gc(this.css.box.close) + "></div><div style=" + b.gc(this.css.box.all + this.css.box.base) + ">" + this.GF(p, g, l) + "</div></div>";
        b.wr(m);
        if (!j && (m = b.g(d))) {
            var r = null;
            var j = function() {
                if (r == null) {
                    r = setTimeout(function() {
                            var f = b.page.getScrollTop(),
                                h = b.page.getViewHeight();
                            var e = n == b.ad.css.box.y.bottom ? (f + h - g - b.ad.css.box.offset[n]) : (f + b.ad.css.box.offset[n]);
                            e = e < f ? f: e;
                            m.style.top = e + "px"
                        },
                        300);
                    r = null
                }
            };
            b.ae(window, "scroll", j);
            b.ae(window, "resize", j)
        }
    };
    b.ad.image.get = function() {
        var d = (d = this.containerId) ? b.g(d) : document.body;
        return b.lang.toArray("img" === d.nodeName.toLowerCase() ? [d] : d.getElementsByTagName("img"))
    };
    b.ad.image.box = function(e, d) {
        var f = document.createElement("div")
    };
    b.ad.image.plus = function(i) {
        var g = function() {
            for (var d = 0; d < b.ad.image.arrList.length; d++) {
                var h = b.ad.image.arrList[d],
                    f = b.gp(h[0]);
                b.dom.setStyle(h[1], "top", f.top + h[0].height - b.ad.css.image.baseHeight + "px");
                b.dom.setStyle(h[1], "left", f.left + "px");
                b.dom.setStyle(h[1], "width", h[0].width + "px");
                b.dom.setStyle(h[1], "height", b.ad.css.image.baseHeight + "px")
            }
        };
        var m = function(d) {
                var e = parseInt(b.dom.getStyle(d, "top").replace(/px/g, ""));
                b.dom.setStyle(d, "top", e - b.ad.css.image.Height + "px");
                b.dom.setStyle(d, "height", b.ad.css.image.Height + b.ad.css.image.baseHeight + "px")
            },
            l = function(d) {
                var e = parseInt(b.dom.getStyle(d, "top").replace(/px/g, ""));
                b.dom.setStyle(d, "top", e + b.ad.css.image.Height + "px");
                b.dom.setStyle(d, "height", b.ad.css.image.baseHeight + "px")
            };
        var k = function() {
            for (var e = 0; e < b.ad.image.arrList.length; e++) {
                var d = b.ad.image.arrList[e];
                b.ae(d[1], "mouseover",
                    function() {
                        m(this)
                    });
                b.ae(d[1], "mouseout",
                    function() {
                        l(this)
                    });
                b.ae(d[0], "mouseover",
                    function() {
                        var f = parseInt(this.getAttribute("box"));
                        m(b.ad.image.arrList[f][1])
                    });
                b.ae(d[0], "mouseout",
                    function() {
                        var f = parseInt(this.getAttribute("box"));
                        l(b.ad.image.arrList[f][1])
                    })
            }
        };
        var j = function() {
            var h = b.ad.image.get(),
                p = 0;
            for (var f = 0; f < h.length; f++) {
                var e = h[f];
                if (e.complete && e.width >= b.ad.image.minWidth && e.height >= b.ad.image.minHeight) {
                    var d = b.c("div"),
                        o = "panshi_img_box_" + b.ad.GC();
                    d.id = o;
                    b.dom.setStyle(d, "cssText", b.ad.css.image.all);
                    b.ad.image.arrList.push([e, d]);
                    e.setAttribute("box", p);
                    d.innerHTML = b.ad.GF(e.width, b.ad.css.image.baseHeight + b.ad.css.image.Height, i());
                    p++
                }
            }
            h = null;
            g();
            k()
        };
        "complete" === document.readyState ? j() : b.ae(window, "load", j);
        b.ae(window, "resize",
            function() {
                var d = null;
                if (d == null) {
                    setTimeout(g, 300);
                    d = null
                }
            })
    };
    b.ad.IMG = b.ad.image.plus;
    b.ad.init = function() {
        b.a = c || "";
        b.b = a || "";
        b.ad.image.containerId = b.ad.image.containerId || window.panshi_c || null;
        window.panshi_a = window.panshi_b = window.panshi_c = null;
        if (b.a == "" || b.b == "") {
            return ! 1
        }
        if (b.a.charAt(b.a.length - 1) === "O") {
            b.adUrl += "&log=0";
            b.a = b.a.substring(0, b.a.length - 1)
        }
        var h = b.a.split("_");
        b.ad.slot = h[1] || "";
        h = b.b.split("_");
        this.width = parseInt(h[0] || 120);
        this.height = parseInt(h[1] || 270);
        this.kind = parseInt(h[2] || 1);
        this.domain = b.page.getLocation();
        if (this.domain.length > 512) {
            this.domain = this.domain.substring(0, 512)
        }
        var j = b.page.getQuery();
        if (j != "") {
            var f = j.substr(1).split("&"),
                d;
            for (var g = 0; g < f.length; g++) {
                d = f[g];
                if (d.substring(0, 2) == "t=") {
                    var k = d.substr(2).split("%2c");
                    if (k.length == 5 && k[3] == this.width && k[4] == this.height) {
                        b.adUrl += "&t=" + k[0] + "%2c" + k[1] + "%2c" + k[2]
                    }
                }
                if (d.substring(0, 3) == "_x=") {
                    b.adUrl += "&x=4%2c" + d.substr(3)
                }
            }
        }
        return ! 0
    };
    b.ad.render = function() {
        if (!this.init()) {
            return ! 1
        }
        var d = this.width,
            f = this.height,
            g = b.ad.css.box.offset.top,
            i = function() {
                return b.ad.getUrl(b.a, b.b, b.ad.domain)
            };
        switch (this.kind) {
            case this.kinds.TD:
            case this.kinds.LINK:
                b.wr(this.GF(d, f, i()));
                break;
            case this.kinds.BUTTON:
                this.GB(i(), this.css.box.y.bottom, this.css.box.x.left, d, f + g);
                this.GB(i(), this.css.box.y.bottom, this.css.box.x.right, d, f + g);
                break;
            case this.kinds.SIDE:
                this.GB(i(), this.css.box.y.top, this.css.box.x.left, d, f + g + 55);
                this.GB(i(), this.css.box.y.top, this.css.box.x.right, d, f + g + 55);
                break;
            case this.kinds.FMEDIA:
            case this.kinds.WINDOW:
                this.GB(i(), this.css.box.y.bottom, this.css.box.x.right, d, f + g);
                break;
            case this.kinds.FLOAT:
                this.IMG(i);
                break
        }
    };
    b.ad.render()
})(panshi_a, panshi_b);
/**
 * Created by lscm on 2016/8/25.
 */
