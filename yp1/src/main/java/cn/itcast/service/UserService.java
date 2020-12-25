package cn.itcast.service;

import cn.itcast.domain.TpDoc;
import cn.itcast.domain.TpFol;
import cn.itcast.domain.TpUser;

import java.util.List;

public interface UserService {
      TpUser find(String username, String password) ;
    List<TpFol> findFol(Integer id);


   void insertDol(TpDoc doc);

    void insertFol(TpFol fol);


    void register(TpUser user);


    List<TpDoc> findDol(Integer user_id, String fid);

    TpFol findFol1(String fid, String uid);
}
