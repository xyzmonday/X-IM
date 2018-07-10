 var HtmlUtil = {
      /*1.用浏览器内部转换器实现html转码*/
      htmlEncode:function (html){
          //1.首先动态创建一个容器标签元素，如DIV
          var temp = document.createElement ("div");
          //2.然后将要转换的字符串设置为这个元素的innerText(ie支持)或者textContent(火狐，google支持)
          (temp.textContent != undefined ) ? (temp.textContent = html) : (temp.innerText = html);
          //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
          var output = temp.innerHTML;
         temp = null;
         return output;
     },
     /*2.用浏览器内部转换器实现html解码*/
     htmlDecode:function (text){
         //1.首先动态创建一个容器标签元素，如DIV
         var temp = document.createElement("div");
         //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
         temp.innerHTML = text;
         //3.最后返回这个元素的innerText(ie支持)或者textContent(火狐，google支持)，即得到经过HTML解码的字符串了。
         var output = temp.innerText || temp.textContent;
         temp = null;
         return output;
     },
     /*3.用正则表达式实现html转码*/
     htmlEncodeByRegExp:function (str){  
          var s = "";
          if(str.length == 0) return "";
          s = str.replace(/&/g,"&amp;");
          s = s.replace(/</g,"&lt;");
          s = s.replace(/>/g,"&gt;");
          s = s.replace(/ /g,"&nbsp;");
          s = s.replace(/\'/g,"&#39;");
          s = s.replace(/\"/g,"&quot;");
          return s;  
    },
    /*4.用正则表达式实现html解码*/
    htmlDecodeByRegExp:function (str){  
          var s = "";
          if(str.length == 0) return "";
          s = str.replace(/&amp;/g,"&");
          s = s.replace(/&lt;/g,"<");
          s = s.replace(/&gt;/g,">");
          s = s.replace(/&nbsp;/g," ");
          s = s.replace(/&#39;/g,"\'");
          s = s.replace(/&quot;/g,"\"");
          return s;  
    }
 };
 
 
 var BrowserUtil = {info:function (){ 
	    var Browser={name:"",version:""}; 
	    var ua=navigator.userAgent.toLowerCase(); 
	    var s; 
	    (s=ua.match(/msie ([\d.]+)/))?Browser.ie=s[1]: 
	    (s=ua.match(/firefox\/([\d.]+)/))?Browser.firefox=s[1]: 
	    (s=ua.match(/chrome\/([\d.]+)/))?Browser.chrome=s[1]: 
	    (s=ua.match(/opera.([\d.]+)/))?Browser.opera=s[1]: 
	    (s=ua.match(/version\/([\d.]+).*safari/))?Browser.safari=s[1]:0; 
	    if(Browser.ie){//Js判断为IE浏览器 
	    	Browser.name ="IE";
	    	Browser.version = Browser.ie;
	        return Browser;
	    } 
	    if(Browser.firefox){//Js判断为火狐(firefox)浏览器 
	    	Browser.name ="Firefox";
	    	Browser.version = Browser.firefox;
	        return Browser;
	    } 
	    if(Browser.chrome){//Js判断为谷歌chrome浏览器 
	    	Browser.name ="Chrome";
	    	Browser.version = Browser.chrome;
	        return Browser;
	    } 
	    if(Browser.opera){//Js判断为opera浏览器 
	        Browser.name ="Opera";
	    	Browser.version = Browser.opera;
	        return Browser;
	    } 
	    if(Browser.safari){//Js判断为苹果safari浏览器 
	    	Browser.name ="Safari";
	    	Browser.version = Browser.safari;
	        return Browser;
	    } 
	    return Browser;
	}
 };


 /*
函数：格式化日期
参数：formatStr-格式化字符串
d：将日显示为不带前导零的数字，如1
dd：将日显示为带前导零的数字，如01
ddd：将日显示为缩写形式，如Sun
dddd：将日显示为全名，如Sunday
M：将月份显示为不带前导零的数字，如一月显示为1
MM：将月份显示为带前导零的数字，如01
MMM：将月份显示为缩写形式，如Jan
MMMM：将月份显示为完整月份名，如January
yy：以两位数字格式显示年份
yyyy：以四位数字格式显示年份
h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
hh：使用12小时制将小时显示为带前导零的数字
H：使用24小时制将小时显示为不带前导零的数字
HH：使用24小时制将小时显示为带前导零的数字
m：将分钟显示为不带前导零的数字
mm：将分钟显示为带前导零的数字
s：将秒显示为不带前导零的数字
ss：将秒显示为带前导零的数字
l：将毫秒显示为不带前导零的数字
ll：将毫秒显示为带前导零的数字
tt：显示am/pm
TT：显示AM/PM
返回：格式化后的日期
*/
 Date.prototype.format = function (formatStr) {
     var date = this;
     /*
     函数：填充0字符
     参数：value-需要填充的字符串, length-总长度
     返回：填充后的字符串
     */
     var zeroize = function (value, length) {
         if (!length) {
             length = 2;
         }
         value = new String(value);
         for (var i = 0, zeros = ''; i < (length - value.length); i++) {
             zeros += '0';
         }
         return zeros + value;
     };
     return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
         switch ($0) {
             case 'd': return date.getDate();
             case 'dd': return zeroize(date.getDate());
             case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
             case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
             case 'M': return date.getMonth() + 1;
             case 'MM': return zeroize(date.getMonth() + 1);
             case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
             case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
             case 'yy': return new String(date.getFullYear()).substr(2);
             case 'yyyy': return date.getFullYear();
             case 'h': return date.getHours() % 12 || 12;
             case 'hh': return zeroize(date.getHours() % 12 || 12);
             case 'H': return date.getHours();
             case 'HH': return zeroize(date.getHours());
             case 'm': return date.getMinutes();
             case 'mm': return zeroize(date.getMinutes());
             case 's': return date.getSeconds();
             case 'ss': return zeroize(date.getSeconds());
             case 'l': return date.getMilliseconds();
             case 'll': return zeroize(date.getMilliseconds());
             case 'tt': return date.getHours() < 12 ? 'am' : 'pm';
             case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';
         }
     });
 }
