package cn.itcast.service.impl;

import cn.itcast.dao.UserDao.UserDao;
import cn.itcast.domain.TpDoc;
import cn.itcast.domain.TpFol;
import cn.itcast.domain.TpUser;
import cn.itcast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service()
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    /**
     * 注册
     * @param user
     */
    @Override
    public void register(TpUser user) {
        userDao.register(user);
    }


    @Override
    public List<TpDoc> findDol(Integer user_id, String fid) {
        return userDao.findDol(user_id.toString(),fid);
    }

    @Override
    public TpFol findFol1(String fid, String uid) {
        return userDao.findFolByUidFid(fid,uid);
    }

    @Override
    public TpUser find(String username, String password) {
        TpUser one = userDao.findOne(username,password);
        System.out.println("find:"+"执行了");
        return one;
    }

    /**
     * 获取该用户的Fol
     * @param id
     * @return
     */
    @Override
    public List<TpFol> findFol(Integer id) {
        return userDao.findFol(id);
    }

    /**
     * 上传新文件
     * @param doc
     * @return
     */
    @Override
    public void insertDol(TpDoc doc) {
        String dname = doc.getDname();
        Integer dsize = doc.getDsize();
        String dsize1 = dsize.toString();
       String dupload1 = doc.getDupload();
        Integer dfid = doc.getDfid();
        String dfid1 = dfid.toString();
        Integer duid = doc.getDuid();
        String duid1 = duid.toString();
        System.out.println(dname);
        System.out.println(dsize1);
        System.out.println(dupload1);
        System.out.println(dfid1);
        System.out.println(duid1);
//        return userDao.insertDol(did,dname, dsize1, dupload1, dfid1, duid1);
         userDao.insertDol(doc);
    }

    @Override
    public void insertFol(TpFol fol) {
        userDao.insertFol(fol);
    }


}
