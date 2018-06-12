package de.thmWeb.metrics.services;

import com.codahale.metrics.annotation.Timed;
import de.thmWeb.metrics.models.ShopUser;
import de.thmWeb.metrics.persistence.ShopUserPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShopUserService {

    private static final Logger LOG = LoggerFactory.getLogger(ShopUserService.class);

    private final ShopUserPersistence shopUserPersistence;

    public ShopUserService(ShopUserPersistence shopUserPersistence) {
        this.shopUserPersistence = shopUserPersistence;
    }


    @Timed
    public ShopUser createShopUser(final ShopUser shopUser) {
        shopUser.setIdx(UUID.randomUUID().toString());
        shopUserPersistence.save(shopUser);

        return shopUser;
    }

    @Timed
    public ShopUser updateShopUser(final ShopUser shopUser) {

        final ShopUser byIdx = shopUserPersistence.findByIdx(shopUser.getIdx());
        if (byIdx != null) {
            shopUserPersistence.save(shopUser);
            return shopUser;
        }

        return null;
    }

    @Timed
    public List<ShopUser> getAllShopUser() {

        return shopUserPersistence.findAll();
    }

}
