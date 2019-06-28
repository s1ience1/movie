package com.dayuanit.movie.movie1.handler;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.dayuanit.movie.movie1.entity.Film;
import com.dayuanit.movie.movie1.entity.Order;
import com.dayuanit.movie.movie1.exception.BizException;
import com.dayuanit.movie.movie1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class AlipayHandle {

    @Value("${alipay.pay.url}")
    private String payUrl;

    @Value("${alipay.appid}")
    private String appId;

    @Value("${alipay.app.private.key}")
    private String appPrivateKey;

    @Value("${alipay.ali.public.key}")
    private String aliPublicKey;

    @Value("${alipay.body}")
    private String body;

    @Value("${alipay.return.url}")
    private String returnUrl;

    @Value("${alipay.notify.url}")
    private String notifyUrl;

    @Autowired
    private OrderService orderService;

    public String createPayForm(Order order, Film film) {

        AlipayClient alipayClient = new DefaultAlipayClient(payUrl,appId,appPrivateKey,
                "json","UTF-8",aliPublicKey,"RSA2");



        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        //支付完成后 跳转的地址
        alipayRequest.setReturnUrl(returnUrl+order.getId());

        //异步通知支付结果的地址
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址

        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+order.getId()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":\""+order.getAmount()+"\"," +
                "    \"subject\":\""+film.getFilmName()+"\"," +
                "    \"body\":\""+body+"\","+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }


    public void processAliNotify(Map<String, String[]> parameterMap) {

        Map<String, String> map = new HashMap<>(parameterMap.size());

        //遍历  entrySet把map里的键值全部取出来
        for (Map.Entry<String,String[]> map2 : parameterMap.entrySet()) {
            map.put(map2.getKey(),map2.getValue()[0]);
        }

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(map, aliPublicKey, "UTF-8", "RSA2"); //调用SDK验证签名
            if(!signVerified){
                throw new BizException("验签失败");
            }

            // 验签成功
            String trade_status = map.get("trade_status");
            // 交易状态不是  TRADE_SUCCESS也不是 TRADE_FINISHED
            if (!"TRADE_SUCCESS".equals(trade_status) && !"TRADE_FINISHED".equals(trade_status) ) {
                throw new BizException("支付失败");
            }

            String appId = map.get("app_id");
            if (!appId.equals(appId)) {
                throw new BizException("appId不正确");
            }

            // 验证 订单号是否存在 和 支付金额是否准确
            String tradeId = map.get("out_trade_no");
            String totalAmount = map.get("total_amount");
            orderService.alipayNotify(Integer.parseInt(tradeId),totalAmount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processAliQuery(Order order) {
        AlipayClient alipayClient = new DefaultAlipayClient(payUrl,appId,appPrivateKey,"json","UTF-8",aliPublicKey,"RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+order.getId()+"\"" +
                "  }");

        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);

            if(!response.isSuccess()){
                System.out.println("调用失败");
                throw new BizException("调用失败");
            }

            String tradeStatus = response.getTradeStatus();
            if (!"TRADE_SUCCESS".equals(tradeStatus) && !"TRADE_FINISHED".equals(tradeStatus)) {
                throw new BizException("支付失败");
            }

            String totalAmount = response.getTotalAmount();

            orderService.aliQuery(order,totalAmount);


        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
