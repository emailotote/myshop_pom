package cn.otote.shop.myshop_service_provider.service.impl;

import cn.otote.shop.dao.IAddressDao;
import cn.otote.shop.entity.Address;
import cn.otote.shop.service.IAddressService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/29 14:52.
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressDao addressDao;

    @Override
    public List<Address> queryAddressByUid(Integer uid) {
        return addressDao.queryAddressByUid(uid);
    }

    @Override
    public Integer insertAddress(Address address) {
        return addressDao.insertAddress(address);
    }
}
