package cn.otote.shop.dao;

import cn.otote.shop.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/29 14:47.
 */
public interface IAddressDao {

    List<Address> queryAddressByUid(Integer uid);

    Integer insertAddress(@Param("address") Address address);

    Address queryAddressById(Integer id);

}
