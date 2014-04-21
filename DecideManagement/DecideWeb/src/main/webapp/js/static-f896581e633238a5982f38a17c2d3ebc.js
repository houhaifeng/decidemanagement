(function($){$.timeago=function(timestamp){if(timestamp instanceof Date){return inWords(timestamp)}else{if(typeof timestamp==="string"){return inWords($.timeago.parse(timestamp))}else{return inWords($.timeago.datetime(timestamp))}}};var $t=$.timeago;$.extend($.timeago,{settings:{refreshMillis:60000,allowFuture:false,strings:{prefixAgo:null,prefixFromNow:null,suffixAgo:"ago",suffixFromNow:"from now",seconds:"less than a minute",minute:"about a minute",minutes:"%d minutes",hour:"about an hour",hours:"about %d hours",day:"a day",days:"%d days",month:"about a month",months:"%d months",year:"about a year",years:"%d years",numbers:[]}},inWords:function(distanceMillis){var $l=this.settings.strings;var prefix=$l.prefixAgo;var suffix=$l.suffixAgo;if(this.settings.allowFuture){if(distanceMillis<0){prefix=$l.prefixFromNow;suffix=$l.suffixFromNow}distanceMillis=Math.abs(distanceMillis)}var seconds=distanceMillis/1000;var minutes=seconds/60;var hours=minutes/60;var days=hours/24;var years=days/365;function substitute(stringOrFunction,number){var string=$.isFunction(stringOrFunction)?stringOrFunction(number,distanceMillis):stringOrFunction;var value=($l.numbers&&$l.numbers[number])||number;return string.replace(/%d/i,value)}var words=seconds<45&&substitute($l.seconds,Math.round(seconds))||seconds<90&&substitute($l.minute,1)||minutes<45&&substitute($l.minutes,Math.round(minutes))||minutes<90&&substitute($l.hour,1)||hours<24&&substitute($l.hours,Math.round(hours))||hours<48&&substitute($l.day,1)||days<30&&substitute($l.days,Math.floor(days))||days<60&&substitute($l.month,1)||days<365&&substitute($l.months,Math.floor(days/30))||years<2&&substitute($l.year,1)||substitute($l.years,Math.floor(years));return $.trim([prefix,words,suffix].join(" "))},parse:function(iso8601){var s=$.trim(iso8601);s=s.replace(/\.\d\d\d+/,"");s=s.replace(/-/,"/").replace(/-/,"/");s=s.replace(/T/," ").replace(/Z/," UTC");s=s.replace(/([\+\-]\d\d)\:?(\d\d)/," $1$2");return new Date(s)},datetime:function(elem){var isTime=$(elem).get(0).tagName.toLowerCase()==="time";var iso8601=isTime?$(elem).attr("datetime"):$(elem).attr("title");return $t.parse(iso8601)}});$.fn.timeago=function(){var self=this;self.each(refresh);var $s=$t.settings;if($s.refreshMillis>0){setInterval(function(){self.each(refresh)},$s.refreshMillis)}return self};function refresh(){var data=prepareData(this);if(!isNaN(data.datetime)){$(this).text(inWords(data.datetime))}return this}function prepareData(element){element=$(element);if(!element.data("timeago")){element.data("timeago",{datetime:$t.datetime(element)});var text=$.trim(element.text());if(text.length>0){element.attr("title",text)}}return element.data("timeago")}function inWords(date){return $t.inWords(distance(date))}function distance(date){return(new Date().getTime()-date.getTime())}document.createElement("abbr");document.createElement("time")}(jQuery));$(function(){var imageWidth=72;var imageHeight=62;var hideCount=0;function hideYeti(){$("#theYeti").animate({bottom:"-"+imageHeight+"px"},500,yetiHidden)}function yetiHidden(){hideCount++;if(hideCount==1){$("#theYeti").css("right",($(window).width()-imageWidth-40)+"px")}else{$("#theYeti").css("right","20px")}}function shortPeek(){$("#theYeti").animate({bottom:"-"+(imageHeight*5/8)+"px"},4000,hideYeti)}function peekPauseHide(){$("#theYeti").animate({bottom:"-5px"},6000,function(){setTimeout(hideYeti,1000)})}function peekPauseRun(){function run(){var width=$(window).width()+imageWidth;$("#theYeti").animate({right:width},1500,function(){$("#theYeti").hide()})}$("#theYeti").animate({bottom:"-5px"},3500,function(){setTimeout(run,4000)})}var time=0,timeStep=20000;setTimeout(shortPeek,time+=timeStep);setTimeout(peekPauseHide,time+=timeStep);setTimeout(peekPauseRun,time+=timeStep)});$(function(){var $window=$(window);var $body=$("body");var $info=$(".info");var $landing=$(".landing");$(".home_link").click(function(){if(activeSection==INFO){transitionUp()}return false});function transitionUp(){decide.util.dom.scrollTop(true)}$backToTop=$(".back_to_top");$backToTop.click(function(){transitionUp();return false});$("body.how_it_works_page .video_thumbs li").click(function(){var target=$(this);$(".multi_video .video_frame div").fadeOut(function(){$(this).remove();$(target.find("a").attr("href").toString()).clone().appendTo(".multi_video .video_frame").fadeIn()})})});$(function(){$(".enable_tooltip").tooltip();$("#iphone_link").click(function(){$("#iphone_screenshots").show();$("#iphone_link").addClass("active");$("#ipad_screenshots").hide();$("#ipad_link").removeClass("active")});$("#ipad_link").click(function(){$("#iphone_screenshots").hide();$("#iphone_link").removeClass("active");$("#ipad_screenshots").show();$("#ipad_link").addClass("active")});$(".member_portrait").popover({trigger:"hover",delay:{show:200,hide:100},placement:"bottom",html:true});var pageName=$("body").data("view");switch(pageName){case"about":$("#about_link").addClass("active");break;case"press":$("#press_link").addClass("active");break;case"jobs":$("#jobs_link").addClass("active");break}});