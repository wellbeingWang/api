
function img(url,type,adcode,originAdID,originUnionName,unionName,adId,siteCode,t,cookie,clientip,fromWhere,ct,cp){
	var msgIm = new Image();
	var sendUrl =  url + '?type=' + type + '&adcode=' + adcode + '&unionName=' + unionName + '&adId=' + adId + '&siteCode=' + siteCode;
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
requestStartTime=new Date().getTime(),cookieval='',ip='',firstAdId=0,firstUnionName='',ct=0,cp=0;
function b5m_FillSlot(index) {
    var originAdID = null;
    var originUnionName = null;
    var unionName = null;
    var adId = null;
    var isCallBack = false;
    var type = 'BACKFLOW_RV';
    if (index < b5m_ADS.length && b5m_ADS[index][0] != undefined) {
        if (arguments.length == 1) {
            if(index > 0){
            	originAdID = b5m_ADS[index - 1][1];
                originUnionName = b5m_ADS[index - 1][2];
                unionName = b5m_ADS[index][2];
                adId = b5m_ADS[index][1];
            	isCallBack = true;
            }else if(index == 0){
            	if(b5man_params.isFrameBack){
            		originAdID = firstAdId;
                    originUnionName = firstUnionName;
            		unionName = b5m_ADS[index][2];
                    adId = b5m_ADS[index][1];
                	isCallBack = true;
            	}
            }
        } else if (arguments.length == 3) {
            originAdID = arguments[1];
            originUnionName = arguments[2];
            unionName = b5m_ADS[index][2];
            adId = b5m_ADS[index][1];
            isCallBack = true;
        }
        document.write(b5m_ADS[index][0]);
    } else {
        if (arguments.length == 1) {
            originAdID = b5m_ADS[index - 1][1];
            originUnionName = b5m_ADS[index - 1][2];
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
    }
    if(isCallBack){
    	img(backUrl, type, adcode, originAdID, originUnionName, unionName, adId, siteCode);
        img(stormUrl, type, adcode, originAdID, originUnionName, unionName, adId, siteCode, requestStartTime, cookieval, ip);
    }
}

function b5m_init_param(params){
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
	ct=params.ct;
	cp=params.cp;
}

function b5m_first_show(){
	if (b5m_ADS[level] != null) {
        b5m_FillSlot(level);
        var fromWhere = encodeURIComponent(encodeURIComponent(function() {
            if (window.parent != window) {
                return document.referrer;
            } else {
                return location.href;
            }
        } ()));
        img(confirmUrl, 'CV', adcode, null, null, firstUnionName, firstAdId, siteCode, null, null, null,fromWhere);
        img(stormUrl, 'CV', adcode, null, null, firstUnionName, firstAdId, siteCode, requestStartTime, cookieval, ip,null,ct,cp);
    }
    img(stormUrl, 'RV', adcode, null, null, firstUnionName, firstAdId, siteCode, requestStartTime, cookieval, ip,null,ct,cp);
}

function b5m_show(){
	b5m_init_param(b5man_params);
	if(b5man_params.isFrameBack){
		if(b5man_params.isTheEnd == 'y'){
			b5m_FillSlot(level);
		}
		if(b5man_params.isTheEnd == 'n'){
			b5m_FillSlot(level,firstAdId,firstUnionName);
		}
	}else{
		b5m_first_show();
	}
}

b5m_show();
