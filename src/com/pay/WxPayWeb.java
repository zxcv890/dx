//package com.pay;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class WxPayWeb {
//	/**
//	 * 支付确认界面
//	 */
//	public void jspay(){
//		UserInfo ui = UserList.getUser(this);
//		String gid = this.getPara("gid");
//		Record pay = Db.findFirst("select * from tpay where oid=?", gid);
//		if ( pay == null ){
//			this.renderWxErrHtml("订单错误");return;
//		}
//
//		String yopenid = pay.getStr("yopenid");
//		int isprocess = pay.getInt("isprocess");
//
//		if ( isprocess == 2 ){	//预支付失败
//			this.renderWxErrHtml("预支付失败，订单已失效。");return;
//		}else if ( isprocess == 4 ){
//			this.renderWxInfoHtml("已支付成功，重勿重复支付。");return;
//		}else if ( isprocess > 4  ){
//			this.renderWxErrHtml("支付失败，订单已失效。");return;
//		}
//
//		if ( !ui.getOpenId().equals(yopenid) ){
//			pay.set("yopenid", ui.getOpenId()).set("uid", ui.userid);
//			Db.update("tpay", pay);
//		}
//
//		BaseJSON json = null;
//		if ( isprocess == 0 ){//做预支付操作
//			json = WxPay.CreateJSPay(pay, ui.getOpenId());
//			if ( json.status == 1 ) {
//
//			} else {
//				this.renderWxErrHtml(json.msg);return;
//			}
//		}else{
//			json = new BaseJSON(1, "", pay.get("yid").toString());
//		}
//
//		String wxPath = WxConfiger.ServerUrl + "wxpay/jspay?showwxpaytitle=1&gid="+gid;
//		String wxConStr = WxWebSign.getSignString(wxPath);
//		String wxPayStr = WxWebSign.getWebPaySignString(json.obj.toString());
//		this.setAttr("wxConStr", wxConStr);
//		this.setAttr("wxPayStr", wxPayStr);
//		this.setAttr("payid", pay.getStr("oid"));
//		this.setAttr("pname", pay.getStr("yname"));
//		this.setAttr("money", (new DecimalFormat("0.00"))
//				.format(1/100f));//pay.getLong("ymoney")
//		this.setAttr("date", (new SimpleDateFormat("yyyy-MM-dd")
//				.format(new Date())));
//		this.render("wxjspay.jsp");
//	}
//
//	/**
//	 * 预支付生成接口
//	 */
//	public void fillapi(){
//		String money = this.getPara("money");
//		String cname = this.getPara("cname");
//		if ( this.isNotNumber(money) || money.length() > 8 ){
//			this.renderWxErrHtml("参数错误");return;
//		}
//
//		long num1 = Long.parseLong(money);
//		if ( num1 < 1 || num1 > 9999999 ){
//			this.renderWxErrHtml("数值不合法，请输入100-9999999之间的数值");return;
//		}
//
//		DecimalFormat df = new DecimalFormat("00000000");
//		long tick = Tools.getOnlyCode();
//		String gid = tick + df.format( num1 );
//
//		Record r = new Record();
//		r.set("oid", gid)
//		.set("ymoney", num1)
//		.set("yname", cname)
//		.set("ctime", System.currentTimeMillis());
//		Db.save("tpay", r);
//		BaseJSON bj = new BaseJSON(1, "", gid);
//		this.renderText(bj.toString());
//	}
//
//	public void testpay(){
//
//	}
//
//	public void onestep(){
//		UserInfo ui = UserList.getUser(this);
//		String money = this.getPara("money");
//		String cname = this.getPara("cname");
//		String yh = this.getPara("yh");
//		if ( this.isNotNumber(money) || money.length() > 8 ){
//			this.renderWxErrHtml("参数错误");return;
//		}
//
//		long num1 = Long.parseLong(money);
//		if ( num1 < 1 || num1 > 9999999 ){
//			this.renderWxErrHtml("数值不合法，请输入100-9999999之间的数值");return;
//		}
//
//		DecimalFormat df = new DecimalFormat("00000000");
//		long tick = Tools.getOnlyCode();
//		String gid = tick + df.format( num1 );
//
//		Record r = new Record();
//		r.set("oid", gid)
//		.set("ymoney", num1)
//		.set("yname", cname)
//		.set("ctime", System.currentTimeMillis());
//		Db.save("tpay", r);
//		Db.update("update tpay set paytype = ?,payid = ? where id = ?",this.getPara("paytype"),this.getPara("payid"),r.get("id"));
//
//		Record pay = Db.findFirst("select * from tpay where oid=?", gid);
//		if ( pay == null ){
//			this.renderWxErrHtml("订单错误");return;
//		}
//
//		String yopenid = pay.getStr("yopenid");
//		int isprocess = pay.getInt("isprocess");
//
//		if ( isprocess == 2 ){	//预支付失败
//			this.renderWxErrHtml("预支付失败，订单已失效。");return;
//		}else if ( isprocess == 4 ){
//			this.renderWxInfoHtml("已支付成功，重勿重复支付。");return;
//		}else if ( isprocess > 4  ){
//			this.renderWxErrHtml("支付失败，订单已失效。");return;
//		}
//
//		if ( !ui.getOpenId().equals(yopenid) ){
//			pay.set("yopenid", ui.getOpenId()).set("uid", ui.userid);
//			Db.update("tpay", pay);
//		}
//
//		BaseJSON json = null;
//		if ( isprocess == 0 ){//做预支付操作
//			json = WxPay.CreateJSPay(pay, ui.getOpenId());
//			if ( json.status == 1 ) {
////				Record ddr = new Record();
////				String comment = PluginTool.upGrade(ui.userid+"", getPara("type"));
////				ddr.set("dnum", new Date().getTime() + ui.userid).set("uid", ui.userid)
////				   .set("type", 1).set("opnum", Integer.parseInt(money) * 100).set("comment", comment).set("ctime", new Date()).set("status", 1);
////				Db.save("t_paylog", ddr);
//				this.renderWxOKHtml("升级成功");
//			} else {
//				this.renderWxErrHtml(json.msg);return;
//			}
//		}else{
//			json = new BaseJSON(1, "", pay.get("yid").toString());
//		}
//
//		String wxPath = WxConfiger.ServerUrl + "wxpay/onestep?showwxpaytitle=1";
//		String wxConStr = WxWebSign.getSignString(wxPath);
//		String wxPayStr = WxWebSign.getWebPaySignString(json.obj.toString());
//		this.setAttr("wxConStr", wxConStr);
//		this.setAttr("wxPayStr", wxPayStr);
//		this.setAttr("payid", pay.getStr("oid"));
//		this.setAttr("pname", pay.getStr("yname"));
//		this.setAttr("money", (new DecimalFormat("0.00"))
//				.format(pay.getLong("ymoney")/100f));
//		this.setAttr("date", (new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
//		this.setAttr("paytype", this.getPara("paytype"));
//		this.setAttr("zmoney", this.getPara("zmoney"));
//		this.setAttr("orderid", this.getPara("payid"));
//		this.setAttr("kjian", this.getPara("kjian"));
//		this.setAttr("yh", yh);
//		setAttr("id", pay.get("id"));
//		setAttr("type", getPara("type"));
//		setAttr("userid", ui.userid);
//		this.render("wxjspay.jsp");
//	}
//}
