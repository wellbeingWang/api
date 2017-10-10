
<meta content="always" name="referrer">
<body>
<script>
    var url = "${destUrl}";
    var target = "${target}";
    var uid = "${uid}";
    var cid = "${cid}";
    var lt = "${ltt}";
    var ad = "${ad}";
    var ip = "${ip}";
    var pi = "${pi}";
    var st = "${st}";
    var dl = "${dl}";
    var cl = "${cl}";
    var ssub = "${ssub}";
    var fb = "${fb}";
    var imgUrl = "${logUrl}";

    function redirect(url) {
        if (/MSIE [678]\.\d+/.test(navigator.userAgent)) {
            var referLink = document.createElement('a');
            referLink.href = url;
            document.body.appendChild(referLink);
            referLink.click();
        } else {
            window.location.replace(url);
        }
    }
/*    var query;
    var query = [
        'target=' + encodeURIComponent(target),
        'dl=' + encodeURIComponent(dl),
        'cl=' + encodeURIComponent(cl),
        'uid='+ uid,
        'lt='+ lt,
        'ad='+ ad,
        'ip='+ ip,
        'pi='+ pi,
        'st='+ st,
        'ssub='+ ssub,
        'fb='+ fb,
        'ref=' + encodeURIComponent(document.referrer),
        '_=' + +new Date()
    ].join('&');

    window['__timg__'] = new Image();
    window['__timg__'].src = imgUrl + query;*/
    redirect("http://www.alloyding.xyz");
</script>
</body>