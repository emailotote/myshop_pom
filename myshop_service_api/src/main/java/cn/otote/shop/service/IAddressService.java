package cn.otote.shop.service;

import cn.otote.shop.entity.Address;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/29 14:51.
 */
public interface IAddressService {
    List<Address> queryAddressByUid(Integer uid);

    Integer insertAddress(Address address);
}
