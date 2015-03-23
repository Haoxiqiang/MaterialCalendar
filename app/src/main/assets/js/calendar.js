/*****************************************************************************
                                   日期资料
*****************************************************************************/
var ttime=0;
var tInfo=new Array(
0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,
0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,
0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,
0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,
0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,
0x06ca0,0x0b550,0x15355,0x04da0,0x0a5b0,0x14573,0x052b0,0x0a9a8,0x0e950,0x06aa0,
0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,
0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b6a0,0x195a6,
0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,
0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,0x092e0,
0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,
0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,
0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,
0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,
0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0,
0x14b63);

var solarMonth=new Array(31,28,31,30,31,30,31,31,30,31,30,31);
var Gan=new Array("甲","乙","丙","丁","戊","己","庚","辛","壬","癸");
var Zhi=new Array("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥");
var Animals=new Array("鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪");
var solarTerm = new Array("小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至");
var sTermInfo = new Array(0,21208,42467,63836,85337,107014,128867,150921,173149,195551,218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,440795,462224,483532,504758);
var nStr1 = new Array('日','一','二','三','四','五','六','七','八','九','十');
var nStr2 = new Array('初','十','廿','卅','□');

//国历节日 *表示放假日
var sFtv = new Array(
"0101*元旦&元旦 新年", 
"0106 &中国第13亿人口日",
"0108 &周恩来逝世纪念日",
"0115 &释迦如来成道日",
"0121 &列宁逝世纪念日 国际声援南非日 弥勒佛诞辰",
"0202 &世界湿地日",
"0207 &二七大罢工纪念日",
"0210 &国际气象节",
"0214 情人节&情人节",
"0215 &中国12亿人口日",
"0219 &邓小平逝世纪念日",
"0221 &国际母语日 反对殖民制度斗争日",
"0222 &苗族芦笙节",
"0224 &第三世界青年日",
"0228 &世界居住条件调查日",
"0301 &国际海豹日",
"0303 &全国爱耳日",
"0305 &学雷锋纪念日 中国青年志愿者服务日",
"0308 妇女节&国际劳动妇女节",
"0309 &保护母亲河日",
"0311 &国际尊严尊敬日",
"0312 植树节&孙中山逝世纪念日",
"0314 &国际警察日 白色情人节",
"0315 &消费者权益日",
"0316 &手拉手情系贫困小伙伴全国统一行动日",
"0317 &中国国医节 国际航海日  爱尔兰圣帕特里克节",
"0318 &全国科技人才活动日",
"0321 &世界森林日 消除种族歧视国际日 世界儿歌日 世界睡眠日",
"0322 &世界水日",
"0323 &世界气象日",
"0324 &世界防治结核病日",
"0329 &中国黄花岗七十二烈士殉难纪念",
"0330 &巴勒斯坦国土日",
"0401 愚人节&全国爱国卫生运动月(四月) 税收宣传月(四月)",
"0402 &国际儿童图书日",
"0407 &世界卫生日",
"0411 &世界帕金森病日",
"0421 &全国企业家活动日",
"0422 &世界地球日 世界法律日",
"0423 &世界图书和版权日",
"0424 &亚非新闻工作者日 世界青年反对殖民主义日",
"0425 &全国预防接种宣传日",
"0426 &世界知识产权日",
"0430 &世界交通安全反思日",
"0501*劳动节&国际劳动节",
"0503 &世界哮喘日 世界新闻自由日",
"0504 青年节&中国五四青年节 科技传播日",
"0505 &碘缺乏病防治日 日本男孩节",
"0508 &世界红十字日",
"0512 &国际护士节",
"0515 &国际家庭日",
"0517 &国际电信日",
"0518 &国际博物馆日",
"0520 &全国学生营养日 全国母乳喂养宣传日",
"0523 &国际牛奶日",
"0526 &世界向人体条件挑战日",
"0530 &中国“五卅”运动纪念日",
"0531 &世界无烟日 英国银行休假日", 
"0601 儿童节&国际儿童节",
"0605 &世界环境保护日",
"0614 &世界献血者日",
"0617 &防治荒漠化和干旱日",
"0620 &世界难民日",
"0622 &中国儿童慈善活动日",
"0623 &国际奥林匹克日",
"0625 &全国土地日",
"0626 &国际禁毒日 国际宪章日 禁止药物滥用和非法贩运国际日 支援酷刑受害者国际日",
"0630 &世界青年联欢节",
"0701 建党节&香港回归纪念日 中共诞辰 世界建筑日",
"0702 &国际体育记者日",
"0706 &朱德逝世纪念日",
"0707 &抗日战争纪念日",
"0711 &世界人口日 中国航海日",
"0726 &世界语创立日",
"0728 &第一次世界大战爆发",
"0730 &非洲妇女日",
"0801 建军节&中国人民解放军建军节",
"0805 &恩格斯逝世纪念日",
"0806 &国际电影节",
"0808 &中国男子节(爸爸节)",
"0812 &国际青年节",
"0813 &国际左撇子日",
"0815 &抗日战争胜利纪念",
"0826 &全国律师咨询日",
"0902 &日本签署无条件投降书日",
"0903 &中国抗日战争胜利纪念日",
"0905 &瑞士萨永中世纪节",
"0906 &帕瓦罗蒂去世",
"0908 &国际扫盲日 国际新闻工作者日",
"0909 &毛泽东逝世纪念日",
"0910 教师节&中国教师节 世界预防自杀日", 
"0914 &世界清洁地球日",
"0916 &国际臭氧层保护日 中国脑健康日",
"0918 &九·一八事变纪念日",
"0920 &国际爱牙日",
"0921 &世界停火日 预防世界老年性痴呆宣传日",
"0927 &世界旅游日",
"0928 &孔子诞辰",
"0930 &国际翻译日",
"1001*国庆节&世界音乐日 国际老人节",
"1002*&国庆节假日 国际和平与民主自由斗争日",
"1003*&国庆节假日",
"1004 &世界动物日",
"1005 &国际教师节",
"1006 &中国老年节",
"1008 &全国高血压日 世界视觉日",
"1009 &世界邮政日 万国邮联日",
"1010 &辛亥革命纪念日 世界精神卫生日 世界居室卫生日",
"1013 &世界保健日 国际教师节 中国少年先锋队诞辰日 世界保健日",
"1014 &世界标准日",
"1015 &国际盲人节(白手杖节)",
"1016 &世界粮食日",
"1017 &世界消除贫困日",
"1020 &世界骨质疏松日",
"1022 &世界传统医药日",
"1024 &联合国日 世界发展新闻日",
"1028 &中国男性健康日",
"1031 万圣节&万圣节 世界勤俭日",
"1102 &达摩祖师圣诞",
"1106 &柴科夫斯基逝世悼念日",
"1107 &十月社会主义革命纪念日",
"1108 &中国记者日",
"1109 &全国消防安全宣传教育日",
"1110 &世界青年节",
"1111 光棍节&光棍节 国际科学与和平周",
"1112 &孙中山诞辰纪念日",
"1114 &世界糖尿病日",
"1115 &泰国大象节",
"1117 &国际大学生节 世界学生节 世界戒烟日",
"1120 &世界儿童日",
"1121 &世界问候日 世界电视日",
"1129 &国际声援巴勒斯坦人民国际日",
"1201 &世界艾滋病日",
"1202 &废除一切形式奴役世界日",
"1203 &世界残疾人日",
"1204 &全国法制宣传日",
"1205 &国际经济和社会发展志愿人员日 世界弱能人士日",
"1207 &国际民航日",
"1208 &国际儿童电视日",
"1209 &世界足球日 一二·九运动纪念日",
"1210 &世界人权日",
"1211 &世界防止哮喘日",
"1212 &西安事变纪念日",
"1213 &南京大屠杀纪念日",
"1214 &国际儿童广播电视节",
"1215 &世界强化免疫日",
"1220 &澳门回归纪念",
"1221 &国际篮球日",
"1224 平安夜&平安夜",
"1225 圣诞节&圣诞节",
"1226 &毛泽东诞辰纪念日&节礼日",
"1229 &国际生物多样性日")

//农历节日 *表示放假日
var lFtv = new Array(
"0101*春节&春节 大年初一",
"0102*初二&大年初二",
"0115 元宵节&元宵节",
"0505*端午节&端午节",
"0707 七夕节&七夕情人节",
"0715 中元节&中元节",
"0815*中秋节&中秋节",
"0909 重阳节&重阳节",
"1208 腊八节&腊八节",
"1223 小年&小年",
"0100*除夕&除夕")

//某月的第几个星期几
var wFtv = new Array(
"0150 &世界麻风日", //一月的最后一个星期日（月倒数第一个星期日）
"0351 &全国中小学生安全教育日",
"0453 &秘书节",
"0512 &国世界哮喘日",
"0520 母亲节&国际母亲节 救助贫困母亲日",
"0530 &全国助残日",
"0532 &国际牛奶日",
"0626 &中国文化遗产日",
"0630 父亲节&国际父亲节",
"0716 &国际合作节",
"0730 &被奴役国家周",
"0932 &国际和平日",
"0936 &全民国防教育日",
"0940 &国际聋人节 世界儿童日",
"0950 &世界海事日 世界心脏病日",
"1011 &国际住房日 世界建筑日 世界人居日",
"1023 &国际减轻自然灾害日(减灾日)",
"1024 &世界视觉日",
"1144 感恩节&感恩节",
"1220 &国际儿童电视广播日")

/*****************************************************************************
日期计算
*****************************************************************************/

//====================================== 返回农历 y年的总天数
function lYearDays(y) {
var i, sum = 348;
for(i=0x8000; i>0x8; i>>=1) sum += (tInfo[y-1900] & i)? 1: 0;
return(sum+leapDays(y));
}

//====================================== 返回农历 y年闰月的天数
function leapDays(y) {
if(leapMonth(y))  return((tInfo[y-1900] & 0x10000)? 30: 29);
else return(0);
}

//====================================== 返回农历 y年闰哪个月 1-12 , 没闰返回 0
function leapMonth(y) {
return(tInfo[y-1900] & 0xf);
}

//====================================== 返回农历 y年m月的总天数
function monthDays(y,m) {
return( (tInfo[y-1900] & (0x10000>>m))? 30: 29 );
}


//====================================== 算出农历, 传入日期控件, 返回农历日期控件
//                                       该控件属性有 .year .month .day .isLeap
function Lunar(objDate) {
	var i, leap=0, temp=0;
	var offset   = (Date.UTC(objDate.getFullYear(),objDate.getMonth(),objDate.getDate()) - Date.UTC(1900,0,31))/86400000;
	for(i=1900; i<2050 && offset>0; i++) { temp=lYearDays(i); offset-=temp; }
	if(offset<0) { offset+=temp; i--; }
	this.year = i;
	leap = leapMonth(i); //闰哪个月
	this.isLeap = false;
	
	for(i=1; i<13 && offset>0; i++) {
		//闰月
		if(leap>0 && i==(leap+1) && this.isLeap==false){ 
			--i;
			this.isLeap = true; temp = leapDays(this.year); 
		}
		else
		{
			temp = monthDays(this.year, i);
		}
		
		//解除闰月
		if(this.isLeap==true && i==(leap+1)) this.isLeap = false;
		offset -= temp;
	}
	if(offset==0 && leap>0 && i==leap+1)
	if(this.isLeap){
		this.isLeap = false;
	}
	else{ 
		this.isLeap = true; --i;
	}
	if(offset<0){ offset += temp; --i; }
	this.month = i;
	this.day = offset + 1;
}

//==============================返回公历 y年某m+1月的天数
function solarDays(y,m) {
	if(m==1)
	return(((y%4 == 0) && (y%100 != 0) || (y%400 == 0))? 29: 28);
	else
	return(solarMonth[m]);
}
//============================== 传入 offset 返回干支, 0=甲子
function cyclical(num) {
	return(Gan[num%10]+Zhi[num%12]);
}

//============================== 阴历属性
function calElement(sYear,sMonth,sDay,week,lYear,lMonth,lDay,isLeap,cYear,cMonth,cDay) {

	this.isToday    = false;
	//瓣句
	this.sYear      = sYear;   //公元年4位数字
	this.sMonth     = sMonth;  //公元月数字
	this.sDay       = sDay;    //公元日数字
	this.week       = week;    //星期, 1个中文
	//农历
	this.lYear      = lYear;   //公元年4位数字
	this.lMonth     = lMonth;  //农历月数字
	this.lDay       = lDay;    //农历日数字
	this.isLeap     = isLeap;  //是否为农历闰月?
	//八字
	this.cYear      = cYear;   //年柱, 2个中文
	this.cMonth     = cMonth;  //月柱, 2个中文
	this.cDay       = cDay;    //日柱, 2个中文
	
	this.color      = '';
	
	this.lunarFestival = ''; //农历节日
	this.solarFestival = ''; //公历节日
	
	this.estDayFestival = ''; //右侧显示的复活节
	this.weekFestival = ''; //右侧显示的周相关的节日
	this.dateFestival = '';//右侧显示的日相关的公历节日
	this.lliFestival = '';//右侧显示的日相关的农历节日
	
	this.solarTerms    = ''; //节气
}

//===== 某年的第n个节气为几日(从0小寒起算)
function sTerm(y,n) {
	if(y==2009 && n==2){sTermInfo[n]=43467}
	var offDate = new Date( ( 31556925974.7*(y-1900) + sTermInfo[n]*60000  ) + Date.UTC(1900,0,6,2,5) );
	return(offDate.getUTCDate());
}

//============================== 返回阴历控件 (y年,m+1月)
/*
功能说明: 返回整个月的日期资料控件

使用方式: OBJ = new calendar(年,零起算月);

OBJ.length      返回当月最大日
OBJ.firstWeek   返回当月一日星期
OBJ.lastWeek 返回当月最后一日星期

由 OBJ[日期].属性名称 即可取得各项值

OBJ[日期].isToday  返回是否为今日 true 或 false

其他 OBJ[日期] 属性参见 calElement() 中的注解
*/
function calendar(y,m,tY,tM,tD) {
	var sDObj, lDObj, lY, lM, lD=1, lL, lX=0, tmp1, tmp2, tmp3, tmp4, tmp5;
	var cY, cM, cD; //年柱,月柱,日柱
	var lDPOS = new Array(3);
	var n = 0;
	var firstLM = 0;
	
	sDObj = new Date(y,m,1,0,0,0,0);    //当月一日日期
	
	this.length    = solarDays(y,m);    //公历当月天数
	this.firstWeek = sDObj.getDay();    //公历当月1日星期几
	this.lastWeek = new Date(y,m,this.length,0,0,0,0).getDay();
	
	////////年柱 1900年立春后为庚子年(60进制36)
	if(m<2)
		cY=cyclical(y-1900+36-1); 
	else
		cY=cyclical(y-1900+36);
	var term2=sTerm(y,2); //立春日期
	
	////////月柱 1900年1月小寒以前为 丙子月(60进制12)
	var firstNode = sTerm(y,m*2) //返回当月「节」为几日开始
	cM = cyclical((y-1900)*12+m+12);
	
	//当月一日与 1900/1/1 相差天数
	//1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
	var dayCyclical = Date.UTC(y,m,1,0,0,0,0)/86400000+25567+10;
	
	for(var i=0;i<this.length;i++) {
	
		if(lD>lX) {
			sDObj = new Date(y,m,i+1);    //当月一日日期
			lDObj = new Lunar(sDObj);     //农历
			lY    = lDObj.year;           //农历年
			lM    = lDObj.month;          //农历月
			lD    = lDObj.day;            //农历日
			lL    = lDObj.isLeap;         //农历是否闰月
			lX    = lL? leapDays(lY): monthDays(lY,lM); //农历当月最后一天
		
			if(n==0)
				firstLM = lM;
			lDPOS[n++] = i-lD+1;
		}
		
		//依节气调整二月分的年柱, 以立春为界
		if(m==1 && (i+1)==term2)
			cY=cyclical(y-1900+36);
		//依节气月柱, 以「节」为界
		if((i+1)==firstNode)
			cM = cyclical((y-1900)*12+m+13);
		//日柱
		cD = cyclical(dayCyclical+i);
		
		//sYear,sMonth,sDay,week,
		//lYear,lMonth,lDay,isLeap,
		//cYear,cMonth,cDay
		this[i] = new calElement(y, m+1, i+1, nStr1[(i+this.firstWeek)%7],
		lY, lM, lD++, lL,
		cY ,cM, cD );
	}
	
	//节气
	tmp1=sTerm(y,m*2  )-1;
	tmp2=sTerm(y,m*2+1)-1;
	this[tmp1].solarTerms = solarTerm[m*2];
	this[tmp2].solarTerms = solarTerm[m*2+1];
	
	if(m==3) this[tmp1].color = 'green'; //清明颜色
	
	//复活节只出现在3或4月
	if(m==2 || m==3) {
		var estDay = new easter(y);
		if(m == estDay.m){
			this[estDay.d-1].solarFestival = '复活节';
			this[estDay.d-1].estDayFestival = "复活节";
		}
	}
	
	//月周节日
	for(i in wFtv)
		if(wFtv[i].match(/^(\d{2})(\d)(\d)([\s\*])(.+)$/))
			if(Number(RegExp.$1)==(m+1)) {
				tmp1=Number(RegExp.$2);
				tmp2=Number(RegExp.$3);
				tmp4=RegExp.$5;
				tmp5=tmp4.split('&');
				if(tmp1<5){
					this[((this.firstWeek>tmp2)?7:0) + 7*(tmp1-1) + tmp2 - this.firstWeek].solarFestival = tmp5[0];
					this[((this.firstWeek>tmp2)?7:0) + 7*(tmp1-1) + tmp2 - this.firstWeek].weekFestival = tmp5[1];
				}
				else {
					tmp1 -= 5;
					tmp3 = (this.firstWeek+this.length-1)%7; //当月最后一天星期?
					this[this.length - tmp3 - 7*tmp1 + tmp2 - (tmp2>tmp3?7:0) - 1 ].solarFestival = tmp5[0];
					this[this.length - tmp3 - 7*tmp1 + tmp2 - (tmp2>tmp3?7:0) - 1 ].weekFestival = tmp5[1];
				}
			}
	
	//公历节日
	for(i in sFtv)
		if(sFtv[i].match(/^(\d{2})(\d{2})([\s\*])(.+)$/))
			if(Number(RegExp.$1)==(m+1)) {
				tmp4=RegExp.$4;
				tmp5=tmp4.split('&');
				this[Number(RegExp.$2)-1].solarFestival = tmp5[0];
				this[Number(RegExp.$2)-1].dateFestival = tmp5[1];
				if(RegExp.$3=='*') this[Number(RegExp.$2)-1].color = 'green';
			}
	
	
	//农历节日
		for(i in lFtv)
			if(lFtv[i].match(/^(\d{2})(.{2})([\s\*])(.+)$/)) {
				tmp1=Number(RegExp.$1)-firstLM;
				if(tmp1==-11)
					tmp1=1;
				if(tmp1 >=0 && tmp1<n) {
					tmp2 = lDPOS[tmp1] + Number(RegExp.$2) -1;
					if( tmp2 >= 0 && tmp2<this.length && this[tmp2].isLeap!=true) {
						tmp4=RegExp.$4;
						tmp5=tmp4.split('&');
						this[tmp2].lunarFestival += tmp5[0];
						this[tmp2].lliFestival += tmp5[1];
						if(RegExp.$3=='*')
							this[tmp2].color = 'green';
					}
			}
		}


	//今日
	if(y==tY && m==tM) this[tD-1].isToday = true;
}

//======================================= 返回该年的复活节(春分后第一次满月周后的第一主日)
function easter(y) {

	var term2=sTerm(y,5); //取得春分日期
	var dayTerm2 = new Date(Date.UTC(y,2,term2,0,0,0,0)); //取得春分的公历日期控件(春分一定出现在3月)
	var lDayTerm2 = new Lunar(dayTerm2); //取得取得春分农历
	
	if(lDayTerm2.day<15) //取得下个月圆的相差天数
	var lMlen= 15-lDayTerm2.day;
	else
	var lMlen= (lDayTerm2.isLeap? leapDays(y): monthDays(y,lDayTerm2.month)) - lDayTerm2.day + 15;
	
	//一天等于 1000*60*60*24 = 86400000 毫秒
	var l15 = new Date(dayTerm2.getTime() + 86400000*lMlen ); //求出第一次月圆为公历几日
	var dayEaster = new Date(l15.getTime() + 86400000*( 7-l15.getUTCDay() ) ); //求出下个周日
	
	this.m = dayEaster.getUTCMonth();
	this.d = dayEaster.getUTCDate();

}

//====================== 中文日期
function cDay(d){
	var s;
	
	switch (d) {
	case 10:
	s = '初十'; break;
	case 20:
	s = '二十'; break;
	break;
	case 30:
	s = '三十'; break;
	break;
	default :
	s = nStr2[Math.floor(d/10)];
	s += nStr1[d%10];
	}
	return(s);
}