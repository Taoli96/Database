package rentCar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import rentCar.entity.CustomerInfo;
import rentCar.service.CustomerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xpb on 2017/6/25.
 */
@Controller
@RequestMapping("/Customer")
public class CustomerController {
        private int loginResult =100;
        private int registerResult =100;
        @Resource
        private CustomerService customerService;
        @RequestMapping("list")
        public String list(){
            List<CustomerInfo> list=customerService.findAll();
            System.out.println(String.valueOf(list));
            return String.valueOf(list);
        }


    /*
    登录实现，获取登录输入流，进行处理，返回登录结果。
    */

        @RequestMapping(value = "/Login",method= RequestMethod.POST)
        public void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            System.out.println("请求为" + req);
            System.out.println("请求Service为" +customerService);
            BufferedReader br = req.getReader();

            System.out.println("获取请求流："+br);

            String str, jsonString = "";
            while((str = br.readLine()) != null){
                jsonString += str;
                System.out.println("str为" + str);
            }
            System.out.println(jsonString);
            System.out.println("请求为" + jsonString);
            JSONObject jsonObject= JSONObject.parseObject(jsonString);
            String userName2 = (String)jsonObject.get("userName");
            String passWord2 = (String) jsonObject.get("customerPassword");

            String userName1 = (String) req.getAttribute("userName");
            String passWord1 = (String) req.getAttribute("passWord");
            String userName = req.getParameter("userName");
            String passWord = req.getParameter("passWord");

            System.out.println("69请求的userName为" + userName + "\n69请求的passWord为" + passWord);
            System.out.println("69请求的userName1为" + userName1 + "\n69请求的passWord1为" + passWord1);
            System.out.println("69请求的userName2为" + userName2 + "\n69请求的passWord2为" + passWord2);
            loginResult = customerService.login(userName2, passWord2);
            String result1=loginResult+"This is a 登录结果";
            System.out.println("结果为" + result1);
            Map<String, Object> map = new HashMap<>();
            map.put("RESULT_KEY", loginResult);
            map.put("userName",userName);
            map.put("passWord",passWord);
            map.put("userName1",userName1);
            map.put("passWord1",passWord1);
            resp.addHeader("Content-Type","application/json; charset=utf-8");
            resp.addHeader("Accept-Encoding","gzip");
            resp.setContentType("text/plain;charset=utf-8" );
            resp.setCharacterEncoding("UTF-8");

            String result = JSON.toJSONString(map);

            System.out.println("结果为" + result);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(result);
            printWriter.close();

        }

      /*
    注册实现，获取注册输入流，进行处理，返回注册结果。
    注册时，生成订单。返回注册结果。
    */
        @RequestMapping(value = "/Register",method=RequestMethod.POST)
        public void register(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            System.out.println("注册请求为" + req);
            System.out.println("请求registerService为" +customerService);
            BufferedReader br = req.getReader();
            System.out.println("获取注册请求流："+br);

            String str, jsonString = "";
            while((str = br.readLine()) != null){
                jsonString += str;
                System.out.println("str为" + str);
            }
            System.out.println(jsonString);
            System.out.println("注册请求为" + jsonString);
            JSONObject jsonObject= JSONObject.parseObject(jsonString);
            String customerTel = (String)jsonObject.get("customerTel");
            String passWord = (String) jsonObject.get("customerPassword");

            System.out.println("注册请求的customerTel为" + customerTel +
                    "\n69注册请求的passWord为" + passWord);
            CustomerInfo customerInfo = JSON.parseObject(jsonString,CustomerInfo.class);
            HashMap mapType = (HashMap)JSON.parseObject(jsonString,Map.class);
            //registerResult = customerService.register(customerInfo);
            registerResult = customerService.register(mapType);
            String result1=registerResult+"This is a 注册结果";
            System.out.println("结果为" + result1);
            Map<String, Object> map = new HashMap<>();
            map.put("RESULT_KEY", registerResult);
            map.put("customerTel",customerTel);
            map.put("passWord",passWord);


            resp.addHeader("Content-Type","application/json; charset=utf-8");
            resp.addHeader("Accept-Encoding","gzip");
            resp.setContentType("text/plain;charset=utf-8" );
            resp.setCharacterEncoding("UTF-8");

            String result = JSON.toJSONString(map);
            System.out.println("结果为" + result);
            //resp.setCharacterEncoding("application/json;charset=utf-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(result);
            printWriter.close();

        }

    @RequestMapping(value = "/Register",method=RequestMethod.GET)
    public ModelAndView register1(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("注册请求为" + req);
        System.out.println("请求registerService为" +customerService);
        BufferedReader br = req.getReader();
        System.out.println("获取注册请求流："+br);
          String  carNo=req.getParameter("carNo");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("client_information.jsp"); //返回的文件名
        mav.addObject("carNo",carNo);
        resp.addHeader("Content-Type","application/json; charset=utf-8");
        resp.addHeader("Accept-Encoding","gzip");
        resp.setContentType("text/plain;charset=utf-8" );
        resp.setCharacterEncoding("UTF-8");
        return mav;

    }

    /*
    更改密码实现，获取输入流，进行处理，返回结果。
    */

        @RequestMapping(value = "/updatePassword",method=RequestMethod.POST)
        public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            System.out.println("更改密码请求为" + req);
            System.out.println("更改密码请求userService为" + customerService);
            BufferedReader br = req.getReader();
            System.out.println("获取更改密码请求流："+br);

            String str, jsonString = "";
            while((str = br.readLine()) != null){
                jsonString += str;
                System.out.println("str为" + str);
            }
            System.out.println(jsonString);
            System.out.println("更改密码请求为" + jsonString);
            JSONObject jsonObject= JSONObject.parseObject(jsonString);
            String customerTel = (String)jsonObject.get("customerTel");
            String passWord= (String) jsonObject.get("customerPassword");

            System.out.println("更改密码请求的customerTel为" + customerTel +
                    "\n更改密码请求的passWord为" + passWord);
            int updateResult = customerService.updatePassword(customerTel,passWord);
            System.out.println("此次更改密码的结果为"+updateResult);
            String result1=updateResult+"This is a 更改密码结果";
            System.out.println("结果为" + result1);
            Map<String, Object> map = new HashMap<>();
            map.put("RESULT_KEY", registerResult);

            resp.addHeader("Content-Type","application/json; charset=utf-8");
            resp.addHeader("Accept-Encoding","gzip");
            resp.setContentType("text/plain;charset=utf-8" );
            resp.setCharacterEncoding("UTF-8");

            String result = JSON.toJSONString(map);

            System.out.println("结果为" + result);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(result);
            printWriter.close();

        }

    /*
    删除用户实现，获取输入流，进行处理，返回结果。
    */

        @RequestMapping(value = "/deleteUser",method=RequestMethod.POST)
        public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            BufferedReader br = req.getReader();
            System.out.println("获取注册请求流："+br);

            String str, jsonString = "";
            while((str = br.readLine()) != null){
                jsonString += str;
                System.out.println("str为" + str);
            }
            System.out.println(jsonString);
            System.out.println("注册请求为" + jsonString);
            JSONObject jsonObject= JSONObject.parseObject(jsonString);
            String customerTel=(String)jsonObject.get("customerTel");
            System.out.println("删除用户请求的电话为" + customerTel);
            int deleteResult =customerService.deleteUser(customerTel);
            System.out.println("此次删除的结果为"+deleteResult);
            String result1=deleteResult+"This is a 删除用户结果";
            System.out.println("结果为" + result1);
            Map<String, Object> map = new HashMap<>();
            map.put("RESULT_KEY", registerResult);

            resp.addHeader("Content-Type","application/json; charset=utf-8");
            resp.addHeader("Accept-Encoding","gzip");
            resp.setContentType("text/plain;charset=utf-8" );
            resp.setCharacterEncoding("UTF-8");
            String result = JSON.toJSONString(map);
            System.out.println("结果为" + result);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(result);
            printWriter.close();

        }


    }


