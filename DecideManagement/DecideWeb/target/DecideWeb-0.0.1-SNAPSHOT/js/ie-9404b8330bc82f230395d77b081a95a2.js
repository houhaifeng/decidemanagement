/*! HTML5 Shiv vpre3.5 | @afarkas @jdalton @jon_neal @rem | MIT/GPL2 Licensed */
(function(window,document){var options=window.html5||{};var reSkip=/^<|^(?:button|form|map|select|textarea|object|iframe)$/i;var saveClones=/^<|^(?:a|b|button|code|div|fieldset|form|h1|h2|h3|h4|h5|h6|i|iframe|img|input|label|li|link|ol|option|p|param|q|script|select|span|strong|style|table|tbody|td|textarea|tfoot|th|thead|tr|ul)$/i;var supportsHtml5Styles;var supportsUnknownElements;(function(){var a=document.createElement("a");a.innerHTML="<xyz></xyz>";supportsHtml5Styles=("hidden" in a);if(supportsHtml5Styles&&typeof injectElementWithStyles=="function"){injectElementWithStyles("#modernizr{}",function(node){node.hidden=true;supportsHtml5Styles=(window.getComputedStyle?getComputedStyle(node,null):node.currentStyle).display=="none"})}supportsUnknownElements=a.childNodes.length==1||(function(){try{(document.createElement)("a")}catch(e){return true}var frag=document.createDocumentFragment();return(typeof frag.cloneNode=="undefined"||typeof frag.createDocumentFragment=="undefined"||typeof frag.createElement=="undefined")}())}());function addStyleSheet(ownerDocument,cssText){var p=ownerDocument.createElement("p"),parent=ownerDocument.getElementsByTagName("head")[0]||ownerDocument.documentElement;p.innerHTML="x<style>"+cssText+"</style>";return parent.insertBefore(p.lastChild,parent.firstChild)}function getElements(){var elements=html5.elements;return typeof elements=="string"?elements.split(" "):elements}function shivMethods(ownerDocument){var cache={},docCreateElement=ownerDocument.createElement,docCreateFragment=ownerDocument.createDocumentFragment,frag=docCreateFragment();ownerDocument.createElement=function(nodeName){if(!html5.shivMethods){docCreateElement(nodeName)}var node;if(cache[nodeName]){node=cache[nodeName].cloneNode()}else{if(saveClones.test(nodeName)){node=(cache[nodeName]=docCreateElement(nodeName)).cloneNode()}else{node=docCreateElement(nodeName)}}return node.canHaveChildren&&!reSkip.test(nodeName)?frag.appendChild(node):node};ownerDocument.createDocumentFragment=Function("h,f","return function(){var n=f.cloneNode(),c=n.createElement;h.shivMethods&&("+getElements().join().replace(/\w+/g,function(nodeName){cache[nodeName]=docCreateElement(nodeName);frag.createElement(nodeName);return'c("'+nodeName+'")'})+");return n}")(html5,frag)}function shivDocument(ownerDocument){var shived;if(ownerDocument.documentShived){return ownerDocument}if(html5.shivCSS&&!supportsHtml5Styles){shived=!!addStyleSheet(ownerDocument,"article,aside,details,figcaption,figure,footer,header,hgroup,nav,section{display:block}audio{display:none}canvas,video{display:inline-block;*display:inline;*zoom:1}[hidden]{display:none}audio[controls]{display:inline-block;*display:inline;*zoom:1}mark{background:#FF0;color:#000}")}if(!supportsUnknownElements){shived=!shivMethods(ownerDocument)}if(shived){ownerDocument.documentShived=shived}return ownerDocument}var html5={elements:options.elements||"abbr article aside audio bdi canvas data datalist details figcaption figure footer header hgroup mark meter nav output progress section summary time video",shivCSS:!(options.shivCSS===false),shivMethods:!(options.shivMethods===false),type:"default",shivDocument:shivDocument};window.html5=html5;shivDocument(document)}(this,document));
/*! matchMedia() polyfill - Test a CSS media type/query in JS. Authors & copyright (c) 2012: Scott Jehl, Paul Irish, Nicholas Zakas. Dual MIT/BSD license */
/*! NOTE: If you're already including a window.matchMedia polyfill via Modernizr or otherwise, you don't need this part */
window.matchMedia=window.matchMedia||(function(doc,undefined){var bool,docElem=doc.documentElement,refNode=docElem.firstElementChild||docElem.firstChild,fakeBody=doc.createElement("body"),div=doc.createElement("div");div.id="mq-test-1";div.style.cssText="position:absolute;top:-100em";fakeBody.appendChild(div);return function(q){div.innerHTML='&shy;<style media="'+q+'"> #mq-test-1 { width: 42px; }</style>';docElem.insertBefore(fakeBody,refNode);bool=div.offsetWidth==42;docElem.removeChild(fakeBody);return{matches:bool,media:q}}})(document);
/*! Respond.js v1.1.0: min/max-width media query polyfill. (c) Scott Jehl. MIT/GPLv2 Lic. j.mp/respondjs  */
(function(win){win.respond={};respond.update=function(){};respond.mediaQueriesSupported=win.matchMedia&&win.matchMedia("only all").matches;if(respond.mediaQueriesSupported){return}var doc=win.document,docElem=doc.documentElement,mediastyles=[],rules=[],appendedEls=[],parsedSheets={},resizeThrottle=30,head=doc.getElementsByTagName("head")[0]||docElem,base=doc.getElementsByTagName("base")[0],links=head.getElementsByTagName("link"),requestQueue=[],ripCSS=function(){var sheets=links,sl=sheets.length,i=0,sheet,href,media,isCSS;for(;i<sl;i++){sheet=sheets[i],href=sheet.href,media=sheet.media,isCSS=sheet.rel&&sheet.rel.toLowerCase()==="stylesheet";if(!!href&&isCSS&&!parsedSheets[href]){if(sheet.styleSheet&&sheet.styleSheet.rawCssText){translate(sheet.styleSheet.rawCssText,href,media);parsedSheets[href]=true}else{if((!/^([a-zA-Z:]*\/\/)/.test(href)&&!base)||href.replace(RegExp.$1,"").split("/")[0]===win.location.host){requestQueue.push({href:href,media:media})}}}}makeRequests()},makeRequests=function(){if(requestQueue.length){var thisRequest=requestQueue.shift();ajax(thisRequest.href,function(styles){translate(styles,thisRequest.href,thisRequest.media);parsedSheets[thisRequest.href]=true;makeRequests()})}},translate=function(styles,href,media){var qs=styles.match(/@media[^\{]+\{([^\{\}]*\{[^\}\{]*\})+/gi),ql=qs&&qs.length||0,href=href.substring(0,href.lastIndexOf("/")),repUrls=function(css){return css.replace(/(url\()['"]?([^\/\)'"][^:\)'"]+)['"]?(\))/g,"$1"+href+"$2$3")},useMedia=!ql&&media,i=0,j,fullq,thisq,eachq,eql;if(href.length){href+="/"}if(useMedia){ql=1}for(;i<ql;i++){j=0;if(useMedia){fullq=media;rules.push(repUrls(styles))}else{fullq=qs[i].match(/@media *([^\{]+)\{([\S\s]+?)$/)&&RegExp.$1;rules.push(RegExp.$2&&repUrls(RegExp.$2))}eachq=fullq.split(",");eql=eachq.length;for(;j<eql;j++){thisq=eachq[j];mediastyles.push({media:thisq.split("(")[0].match(/(only\s+)?([a-zA-Z]+)\s?/)&&RegExp.$2||"all",rules:rules.length-1,hasquery:thisq.indexOf("(")>-1,minw:thisq.match(/\(min\-width:[\s]*([\s]*[0-9\.]+)(px|em)[\s]*\)/)&&parseFloat(RegExp.$1)+(RegExp.$2||""),maxw:thisq.match(/\(max\-width:[\s]*([\s]*[0-9\.]+)(px|em)[\s]*\)/)&&parseFloat(RegExp.$1)+(RegExp.$2||"")})}}applyMedia()},lastCall,resizeDefer,getEmValue=function(){var ret,div=doc.createElement("div"),body=doc.body,fakeUsed=false;div.style.cssText="position:absolute;font-size:1em;width:1em";if(!body){body=fakeUsed=doc.createElement("body")}body.appendChild(div);docElem.insertBefore(body,docElem.firstChild);ret=div.offsetWidth;if(fakeUsed){docElem.removeChild(body)}else{body.removeChild(div)}ret=eminpx=parseFloat(ret);return ret},eminpx,applyMedia=function(fromResize){var name="clientWidth",docElemProp=docElem[name],currWidth=doc.compatMode==="CSS1Compat"&&docElemProp||doc.body[name]||docElemProp,styleBlocks={},lastLink=links[links.length-1],now=(new Date()).getTime();if(fromResize&&lastCall&&now-lastCall<resizeThrottle){clearTimeout(resizeDefer);resizeDefer=setTimeout(applyMedia,resizeThrottle);return}else{lastCall=now}for(var i in mediastyles){var thisstyle=mediastyles[i],min=thisstyle.minw,max=thisstyle.maxw,minnull=min===null,maxnull=max===null,em="em";if(!!min){min=parseFloat(min)*(min.indexOf(em)>-1?(eminpx||getEmValue()):1)}if(!!max){max=parseFloat(max)*(max.indexOf(em)>-1?(eminpx||getEmValue()):1)}if(!thisstyle.hasquery||(!minnull||!maxnull)&&(minnull||currWidth>=min)&&(maxnull||currWidth<=max)){if(!styleBlocks[thisstyle.media]){styleBlocks[thisstyle.media]=[]}styleBlocks[thisstyle.media].push(rules[thisstyle.rules])}}for(var i in appendedEls){if(appendedEls[i]&&appendedEls[i].parentNode===head){head.removeChild(appendedEls[i])}}for(var i in styleBlocks){var ss=doc.createElement("style"),css=styleBlocks[i].join("\n");ss.type="text/css";ss.media=i;head.insertBefore(ss,lastLink.nextSibling);if(ss.styleSheet){ss.styleSheet.cssText=css}else{ss.appendChild(doc.createTextNode(css))}appendedEls.push(ss)}},ajax=function(url,callback){var req=xmlHttp();if(!req){return}req.open("GET",url,true);req.onreadystatechange=function(){if(req.readyState!=4||req.status!=200&&req.status!=304){return}callback(req.responseText)};if(req.readyState==4){return}req.send(null)},xmlHttp=(function(){var xmlhttpmethod=false;try{xmlhttpmethod=new XMLHttpRequest()}catch(e){xmlhttpmethod=new ActiveXObject("Microsoft.XMLHTTP")}return function(){return xmlhttpmethod}})();ripCSS();respond.update=ripCSS;function callMedia(){applyMedia(true)}if(win.addEventListener){win.addEventListener("resize",callMedia,false)}else{if(win.attachEvent){win.attachEvent("onresize",callMedia)}}})(this);