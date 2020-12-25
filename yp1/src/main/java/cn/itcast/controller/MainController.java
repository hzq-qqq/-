package cn.itcast.controller;

import cn.itcast.domain.*;
import cn.itcast.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private ErrorMsg errorMsg;

    /**
     * 登录
     */
    @RequestMapping("/login")
     public void login(String username, String password,HttpServletRequest request,HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        System.out.println("login");
        System.out.println(username);
        System.out.println(password);
        TpUser user = userService.find(username,password);
         if (user == null){
             System.out.println("登录失败，该账号或者密码错误");
             errorMsg.setErrMsg("登录失败，该账号或者密码错误");
             errorMsg.setStatus(false);
         }
         else if (user.getIsReg().equals("N")){
             System.out.println("登录失败，该账号还未注册");
             errorMsg.setErrMsg("登录失败，该账号还未注册");
             errorMsg.setStatus(false);
         }
         else {
             errorMsg.setStatus(true);
             request.getSession().setAttribute("user",user);// 存入用户至session
         }
        System.out.println(user);
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(errorMsg);
        response.getWriter().write(json);
     }

    /**
     * 注册
     * @param request
     * @param response
     * @throws IOException
     */
     @RequestMapping("/reg")
     public void register(HttpServletRequest request,HttpServletResponse response) throws IOException {
         System.out.println("reg");
         String username = request.getParameter("uName");
         String password = request.getParameter("uPwd");
         String vip = request.getParameter("vip");
         String email = request.getParameter("uEmail");
         TpUser user = new TpUser();
         user.setUsername(username);
         user.setPassword(password);
         user.setVip(vip);
         user.setEmail(email);
         user.setIsReg("Y");
         TpUser user1 = userService.find(username, password);
         if (user1 == null){
             userService.register(user);
             errorMsg.setStatus(true);
         }
         else {
             errorMsg.setErrMsg("注册失败,用户名存在");
             errorMsg.setStatus(false);
         }
         response.setContentType("application/json;charset=utf-8");
         ObjectMapper mapper = new ObjectMapper();
         String json = mapper.writeValueAsString(errorMsg);
         response.getWriter().write(json);
     }

    /**
     * 获取用户信息
     * @param request
     * @param response
     */
    @RequestMapping("/findUserName")
     public void findUserName(HttpServletRequest request,HttpServletResponse response) throws IOException {
         TpUser user= (TpUser)request.getSession().getAttribute("user");
         System.out.println("findUserName: " + user);
         response.setContentType("application/json;charset=utf-8");
         ObjectMapper mapper = new ObjectMapper();
         String json = mapper.writeValueAsString(user);
         response.getWriter().write(json);
     }

    /**
     * 获取文件夹信息
     * @param
     * @param
     * @return
     */
    @RequestMapping("/findFol")
    public void findFol(HttpServletRequest request,HttpServletResponse response) throws IOException {
        TpUser user= (TpUser)request.getSession().getAttribute("user");
           // 获取 该用户的文件夹
        List<TpFol>tpFolList = userService.findFol(user.getUid());
        System.out.println("findFol: " + tpFolList);
        request.getSession().setAttribute("tpFolList",tpFolList);
        ObjectMapper mapper  = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        String json = mapper.writeValueAsString(tpFolList);
        response.getWriter().write(json);
    }

    /**
     * 获取文件信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/findDoc")
    public void findDol(HttpServletRequest request,HttpServletResponse response) throws IOException {
        TpUser user= (TpUser)request.getSession().getAttribute("user");
        String fid = request.getParameter("fid");
        request.getSession().setAttribute("fid",fid);
        System.out.println("findDol_fid: " + fid);
        List<TpDoc> docList = userService.findDol(user.getUid(),fid);
        TpFol fol = userService.findFol1(fid, user.getUid().toString());
        request.getSession().setAttribute("fol",fol);
        ObjectMapper mapper  = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        String json = mapper.writeValueAsString(docList);
        response.getWriter().write(json);
    }


    /**
     * 上传文件
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/newDol")
    public void newDoc(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fid = (String) request.getSession().getAttribute("fid");
        System.out.println(fid);
        TpUser user = (TpUser) request.getSession().getAttribute("user");
        Integer id = user.getUid();
        String uid = id.toString();
        System.out.println(uid);
        System.out.println("上传文件");
        TpFol fol = userService.findFol1(fid,uid);
        String filePath =  request.getServletContext().getRealPath("/fol");
        filePath += "\\" + fol.getFname();
        System.out.println(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

//        创建撒上传文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        String filename = null;
        int size = 0;
        String typt = null;
        // 解析request
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        // 遍历
        assert items != null;
        for (FileItem item : items) {
            // 进行判断，当前item对象是否是上传文件项
            if (item.isFormField()) {
                // 说明普通表单向
            } else {
                // 说明上传文件项
                // 获取上传文件的名称
                 filename = item.getName();
                size = (int) item.getSize();
                // 完成文件上传
                try {
                    item.write(new File(filePath , filename));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 删除临时文件
                item.delete();
            }
//            将该文件的信息存入 Doc中
            TpDoc doc = new TpDoc();
            doc.setDname(filename);
            doc.setDsize(size);
            doc.setDupload(new Date().toString());
            doc.setDfid(Integer.parseInt(fid));
            doc.setDuid(id);
            System.out.println(doc.getDname());
            // 将新建文件存入数据库中
            userService.insertDol(doc);
            Integer did = doc.getDid();
            System.out.println("did:"+did);
           ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(doc);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }
    }

    /**
     * 创建文件
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/newFol")
    public void newFol(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String folderName = request.getParameter("folderName");
        System.out.println(folderName);
        String filePath= request.getServletContext().getRealPath("/fol");
        System.out.println(filePath);
        TpUser user = (TpUser)request.getSession().getAttribute("user");
        TpFol fol = new TpFol();
         fol.setFname(folderName);
         fol.setF_path(filePath + "\\" + folderName);
         fol.setF_uid(user.getUid());
        System.out.println(fol);

        System.out.println("path:" + fol.getF_path());
         userService.insertFol(fol);
        System.out.println("insrtFol:" + "执行了");
        System.out.println(fol);
        File file = new File(fol.getF_path());
        if (!file.exists()){
            file.mkdirs();
        }
        System.out.println("fol:" + fol);
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fol);
        response.getWriter().write(json);
    }

    /**
     * 文件下载
     * @param request
     * @param response
     */
    @RequestMapping("/docUpload")
    public void docUpload(HttpServletRequest request,HttpServletResponse response)throws Exception{
        System.out.println("docUpload");
        String docName = request.getParameter("docName");
//        System.out.println(docName);
        // 获取本地该文件 并将其加入到内存中
        String realPath = request.getServletContext().getRealPath("/fol/");
        // 获取文件夹名称 需要下载文件的路径
        TpFol newFol =(TpFol) request.getSession().getAttribute("fol");
        realPath += newFol.getFname();
        realPath += "\\" + docName;
        System.out.println(realPath);
        String realPath1 = "file:///" + realPath;

//        java.io.File f = new java.io.File(realPath);
//        java.io.FileInputStream fis = new java.io.FileInputStream(f);
//
//        response.reset();
//
//        response.setHeader("Server", "playyuer@Microshaoft.com");
////        告诉客户端可以实现断点多线程续传
//        response.setHeader("Accept-Ranges","bytes");
////        设置 续传时的文件字节
//        long p = 0; // 文件下载开始的字节
//        long l = 0;
////        当前不是第一次下载
//        if (request.getHeader("Range")!=null){
////            设置下载的响应格式
//            response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
////            获取请求开始的字节长度
//           p =  Long.parseLong(request.getHeader("Range").replaceAll("bytes=","").replaceAll("-",""));
//        }
//        if (p!=0){
//            //不是从最开始下载
//            response.setHeader("Content-Range","bytes " + new Long(p).toString() + "-" + new Long(l -1).toString() + "/" + new Long(l).toString());
//        }

        // 加入该该文件进内存并且返回数据至view
        FileInputStream fis = new FileInputStream(realPath);
        // 设置响应信息的格式
        String mimeType = request.getServletContext().getMimeType(docName);
        response.setHeader("content-type",mimeType);
        // 设置文件的打开方式
        response.setHeader("content-disposition","attachment;filename="+docName);
        ServletOutputStream os = response.getOutputStream();
        byte[] buff = new byte[1024 * 8];
         int len = 0;
         while((len = fis.read(buff)) != -1){
             os.write(buff,0,len);
         }
        System.out.println("upload");
    }
}
