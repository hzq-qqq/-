package cn.itcast.dao.UserDao;

import cn.itcast.domain.TpDoc;
import cn.itcast.domain.TpFol;
import cn.itcast.domain.TpUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface UserDao {

    @Select("select * from yp_user where username = #{username} and password = #{password}")
    @Results(id = "userMap",
            value = {
                    @Result(id = true, column = "uid", property = "uid"),
                    @Result(column = "username", property = "username"),
                    @Result(column = "username", property = "username"),
                    @Result(column = "password", property = "password"),
                    @Result(column = "email", property = "email"),
                    @Result(column = "vip", property = "vip")
            })
    TpUser findOne(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM yp.`yp_fol` WHERE f_uid = #{uid};")
    @Results(id = "folMap",
            value = {
                    @Result(id = true, column = "fid", property = "fid"),
                    @Result(column = "fname", property = "fname"),
                    @Result(column = "p_fid", property = "p_fid"),
                    @Result(column = "f_uid", property = "f_uid"),
                    @Result(column = "f_path", property = "f_path")
            })
    List<TpFol> findFol(@Param("uid") Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "did", keyColumn = "did")
    @Insert("insert into yp_doc values(null,#{dname},#{dsize},#{dupload},#{dfid},#{duid})")
    void insertDol(TpDoc doc);


    @Insert("insert into yp_fol values(null,#{fname},#{f_uid},#{f_path})")
    @Options(useGeneratedKeys = true, keyProperty = "fid", keyColumn = "fid")
    void insertFol(TpFol fol);


    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    @Insert("insert into yp_user values(null,#{username},#{password},#{email},#{vip},#{isReg})")
    void register(TpUser user);


        @Select("SELECT * FROM yp.`yp_doc` where dfid = #{fid} and duid = #{uid}")
    @Results(id = "docMap",
            value = {
                    @Result(id = true, column = "did", property = "did"),
                    @Result(column = "dname", property = "dname"),
                    @Result(column = "dsize", property = "dsize"),
                    @Result(column = "dtupload", property = "dupload"),
                    @Result(column = "dfid", property = "dfid"),
                    @Result(column = "duid", property = "duid")
            })
    List<TpDoc> findDol(@Param("uid") String uid, @Param("fid") String fid);


        @Select("select * from yp_fol where fid = #{fid} and f_uid = #{uid}")
        @ResultMap("folMap")
    TpFol findFolByUidFid(@Param("fid") String fid,@Param("uid") String uid);
}
