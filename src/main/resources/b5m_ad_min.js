
function img(url,type,adcode,originAdID,originUnionName,unionName,allUnionName,adId,siteCode,t,cookie,clientip,fromWhere,ct,cp){
    var msgIm = new Image();
    var sendUrl =  url + '?type=' + type + '&adcode=' + adcode + '&unionName=' + unionName + '&adId=' + adId + '&siteCode=' + siteCode+ '&allUnionName=' + allUnionName;
    if(originAdID){
        sendUrl += '&originAdID=' + originAdID;
    }
    if(originUnionName){
        sendUrl += '&originUnionName=' + originUnionName;
    }
    if(t){
        sendUrl += '&t=' + t;
    }
    if(cookie){
        sendUrl += '&cookie=' + cookie;
    }
    if(clientip){
        sendUrl += '&clientip=' + clientip;
    }
    if(ct){
        sendUrl += '&ct=' + ct;
    }
    if(cp){
        sendUrl += '&cp=' + cp;
    }
    if(fromWhere){
        sendUrl += '&fromWhere=' + fromWhere;
    }
    msgIm.src = sendUrl;
}

var level = 0,confirmUrl='',backUrl='',adcode='',stormUrl='',siteCode='',
    requestStartTime=new Date().getTime(),cookieval='',ip='',firstAdId=0,firstUnionName='',ct=0,cp=0, allUnionName='';
function ganx0_FillSlot(index) {
    var originAdID = null;
    var originUnionName = null;
    var unionName = null;
    var adId = null;
    var isCallBack = false;
    var type = 'BACKFLOW_RV';
    if (index < ganx0_ADS.length && ganx0_ADS[index][0] != undefined) {
        if (arguments.length <= 2) {
            if(index > 0){
                originAdID = ganx0_ADS[index - 1][1];
                originUnionName = ganx0_ADS[index - 1][2];
                unionName = ganx0_ADS[index][2];
                adId = ganx0_ADS[index][1];
                isCallBack = true;
            }else if(index == 0){
                if(b5man_params.isFrameBack){
                    originAdID = firstAdId;
                    originUnionName = firstUnionName;
                    unionName = ganx0_ADS[index][2];
                    adId = ganx0_ADS[index][1];
                    isCallBack = true;
                }
            }
        } else if (arguments.length == 3) {
            originAdID = arguments[1];
            originUnionName = arguments[2];
            unionName = ganx0_ADS[index][2];
            adId = ganx0_ADS[index][1];
            isCallBack = true;
        }
        if(arguments.length == 2){
            if(arguments[1]==0){
                if(!window.jQuery){
                    var div = document.createElement("script");
                    div.type="text/javascript";
                    div.src = "http://code.jquery.com/jquery-1.7.2.min.js";
                    document.body.appendChild(div);
                    div.onload=function(){
                        document.write = function(node){
                            //$("body").append(node);
                            $(this).append(node);
                        }
                        document.writeln = function(node){
                            //$("body").append(node);
                            $(this).append(node);
                        }
                        $(this).append(ganx0_ADS[index][0]);
                        //document.write(ganx0_ADS[index][0]);
                    }
                }else{
                    document.write = function(node){
                        //$("body").append(node);
                        $(this).append(node);
                    }
                    document.writeln = function(node){
                        //$("body").append(node);
                        $(this).append(node);
                    }
                    document.write(ganx0_ADS[index][0]);
                }
            }
        }else{
            document.write(ganx0_ADS[index][0]);
        }
    } else {
        if (arguments.length == 1) {
            originAdID = ganx0_ADS[index - 1][1];
            originUnionName = ganx0_ADS[index - 1][2];
            unionName = '0';
            adId = 0;
            isCallBack = true;
        } else if (arguments.length == 3) {
            originAdID = arguments[1];
            originUnionName = arguments[2];
            unionName = '0';
            adId = 0;
            isCallBack = true;
        }
        var callback_func = "ganx0_callback_"+adcode+"()";
        eval(callback_func);
    }
    if(isCallBack){
        img(backUrl, type, adcode, originAdID, originUnionName, unionName, allUnionName, adId, siteCode, requestStartTime, cookieval);
        img(stormUrl, type, adcode, originAdID, originUnionName, unionName, allUnionName, adId, siteCode, requestStartTime, cookieval, ip);
    }
}

function ganx0_init_param(params){
    level = params.level;
    confirmUrl=params.confirmUrl;
    backUrl=params.backUrl;
    stormUrl=params.stormUrl;
    siteCode=params.siteCode;
    adcode=params.adcode;
    cookieval=params.cookieval;
    ip=params.ip;
    firstAdId=params.firstAdId;
    firstUnionName=params.firstUnionName;
    allUnionName=params.allUnionName;
    ct=params.ct;
    cp=params.cp;
}

function ganx0_first_show(){
    if (ganx0_ADS[level] != null) {
        ganx0_FillSlot(level);
        var fromWhere = encodeURIComponent(encodeURIComponent(function() {
            if (window.parent != window) {
                return document.referrer;
            } else {
                return location.href;
            }
        } ()));
        img(confirmUrl, 'CV', adcode, null, null, firstUnionName,  allUnionName,firstAdId, siteCode, null, null, null,fromWhere);
        img(backUrl,  'CV', adcode, null, null, firstUnionName, allUnionName, firstAdId, siteCode, requestStartTime, cookieval, null, fromWhere);
        ///img(stormUrl, 'CV', adcode, null, null, firstUnionName, allUnionName, firstAdId, siteCode, requestStartTime, cookieval, ip,null,ct,cp);
    }
    img(stormUrl, 'RV', adcode, null, null, firstUnionName,  allUnionName,firstAdId, siteCode, requestStartTime, cookieval, ip,null,ct,cp);
}

function ganx0_show(){
    ganx0_init_param(b5man_params);
    if(b5man_params.isFrameBack){
        if(b5man_params.isTheEnd == 'y'){
            ganx0_FillSlot(level);
        }
        if(b5man_params.isTheEnd == 'n'){
            ganx0_FillSlot(level,firstAdId,firstUnionName);
        }
    }else{
        ganx0_first_show();
    }
}
if (!document.getElementsByClassName) {
    document.getElementsByClassName = function (className, element) {
        var children = (element || document).getElementsByTagName('*');
        var elements = new Array();
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            var classNames = child.className.split(' ');
            for (var j = 0; j < classNames.length; j++) {
                if (classNames[j] == className) {
                    elements.push(child);
                    break;
                }
            }
        }
        return elements;
    };
}
var oldDocumentWrite = document.write;
ganx0_show();
